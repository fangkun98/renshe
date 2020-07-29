package com.ciyun.renshe.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ciyun.renshe.common.*;
import com.ciyun.renshe.common.dingding.DDErrResult;
import com.ciyun.renshe.common.dingding.DingDingUtil;
import com.ciyun.renshe.common.dingding.sdk.ChatSDKUtil;
import com.ciyun.renshe.common.dingding.sdk.DingDingSDKUtils;
import com.ciyun.renshe.common.dingding.sdk.request.ChatCreateParam;
import com.ciyun.renshe.common.dingding.sdk.request.ChatUpdateParam;
import com.ciyun.renshe.controller.vo.chat.*;
import com.ciyun.renshe.controller.vo.permission.PermissionData;
import com.ciyun.renshe.entity.*;
import com.ciyun.renshe.entity.po.AreaPO;
import com.ciyun.renshe.entity.po.GridPO;
import com.ciyun.renshe.entity.po.StreetPO;
import com.ciyun.renshe.mapper.*;
import com.ciyun.renshe.service.ChatService;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiChatCreateRequest;
import com.dingtalk.api.request.OapiChatGetRequest;
import com.dingtalk.api.request.OapiChatSendRequest;
import com.dingtalk.api.request.OapiChatSubadminUpdateRequest;
import com.dingtalk.api.response.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taobao.api.ApiException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

/**
 * 群相关
 *
 * @author Admin
 */
@Slf4j
@Service
@AllArgsConstructor
public class ChatServiceImpl extends ServiceImpl<ChatMapper, Chat> implements ChatService {

    private final ChatMapper chatMapper;
    private final MessageMapper messageMapper;
    private final AreaMapper areaMapper;
    private final AreaPermissionsMapper areaPermissionsMapper;
    private final StreetMapper streetMapper;
    private final GridMapper gridMapper;
    private final UserMapper userMapper;
    private final UserRoleMapper userRoleMapper;
    private final UserChatMapper userChatMapper;
    private final PollAdminMapper pollAdminMapper;
    private final PollChatMapper pollChatMapper;
    private final NoticeChatMapper noticeChatMapper;

    /**
     * 获取各个区域下面的数据
     *
     * @return
     */
    @Override
    public Result getChatInfo(Page page, GetChatInfoVO chatInfoVO, PermissionData permissionData) {
        Map<String, Object> map = new HashMap<>(7);

        if (chatInfoVO.getCity() != null) {

            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("flag", 1);
            if (StringUtils.isNotBlank(chatInfoVO.getName())) {
                queryWrapper.like("company_name", chatInfoVO.getName());
            }
            if (StringUtils.isNotBlank(chatInfoVO.getMobile())) {
                queryWrapper.eq("mobile", chatInfoVO.getMobile());
            }
            queryWrapper.eq("is_admin", 0);
            queryWrapper.eq("is_confirm", 1);
            PageInfo<User> pageInfo = PageHelper.startPage(page.getPageNum().intValue(), page.getPageSize().intValue())
                    .doSelectPageInfo(() -> userMapper.selectList(queryWrapper));
            map.put("count", pageInfo.getTotal());
            map.put("pageInfo", new PageResult(pageInfo.getTotal(), pageInfo.getList()));
            return new Result(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(), map);
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        QueryWrapper<User> countWrapper = new QueryWrapper<>();

        if (chatInfoVO.getAreaId() != null) {
            queryWrapper.eq("area_id", chatInfoVO.getAreaId());
            countWrapper.eq("area_id", chatInfoVO.getAreaId());
        }
        if (chatInfoVO.getStreetId() != null) {
            queryWrapper.eq("street_id", chatInfoVO.getStreetId());
            countWrapper.eq("street_id", chatInfoVO.getStreetId());
        }

        if (StringUtils.isNotBlank(chatInfoVO.getName())) {
            queryWrapper.like("company_name", chatInfoVO.getName());
        }
        if (StringUtils.isNotBlank(chatInfoVO.getMobile())) {
            queryWrapper.eq("mobile", chatInfoVO.getMobile());
        }

        if (chatInfoVO.getGridId() != null) {
            // 这个条件下说明只查询网格下的人员
            PageInfo<User> pageInfo = PageHelper.startPage(Integer.parseInt(page.getPageNum().toString()), Integer.parseInt(page.getPageSize().toString()))
                    .doSelectPageInfo(() -> userMapper.getGrIdChatInfo(chatInfoVO.getChatId(), chatInfoVO.getMobile(), chatInfoVO.getName()));
            //Integer count = userMapper.getGrIdChatInfoCount(chatInfoVO.getChatId());

            //map.put("count", count);
            map.put("pageInfo", new PageResult(pageInfo.getTotal(), pageInfo.getList()));
            return new Result(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(), map);
        }
        queryWrapper.eq("flag", 1);
        queryWrapper.eq("is_admin", 0);
        queryWrapper.eq("is_confirm", 1);
        Integer count = userMapper.selectCount(queryWrapper);
        PageInfo<User> pageInfo = PageHelper.startPage(Integer.parseInt(page.getPageNum().toString()), Integer.parseInt(page.getPageSize().toString()))
                .doSelectPageInfo(() -> userMapper.selectList(queryWrapper));
        map.put("count", count);
        map.put("pageInfo", new PageResult(pageInfo.getTotal(), pageInfo.getList()));
        return new Result(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(), map);
    }

    /**
     * 创建会话
     *
     * @param chatVO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result createChat(CreateChatVO chatVO) {

        Result result = new Result(MessageInfo.ADD_INFO.getInfo());

        List<Chat> chatList = chatMapper.selectList(Wrappers.<Chat>lambdaQuery().eq(Chat::getName, chatVO.getName()));
        if (CollectionUtil.isNotEmpty(chatList)) {
            return result.setFlag(false).setCode(StatusCode.ERROR).setMessage("存在同名群聊名称！请换一个试试。");
        }
        // 获取到当前用户
        Integer userId = chatVO.getPermission().getUserId();
        List<AreaPermissions> permissions = areaPermissionsMapper.selectList(Wrappers.<AreaPermissions>lambdaQuery()
                .eq(AreaPermissions::getUserId, userId));
        List<Integer> areaIds = new ArrayList<>(10);
        permissions.forEach(p -> areaIds.add(p.getAreaId()));

        if (chatVO.getUserIdList().size() > 40) {
            return result.setFlag(false).setCode(StatusCode.ERROR).setMessage("钉钉限制，一次操作最多只能添加 40 人");
        }

        // 调用钉钉创建会话接口
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/chat/create");
        OapiChatCreateRequest request = new OapiChatCreateRequest();
        request.setName(chatVO.getName());
        request.setOwner(chatVO.getOwner());
        request.setManagementType(1L);
        if (CollectionUtil.isNotEmpty(chatVO.getUserIdList())) {
            if (!chatVO.getUserIdList().contains(chatVO.getOwner())) {
                chatVO.getUserIdList().add(chatVO.getOwner());
            }
        } else {
            List<String> objects = new ArrayList<>(1);
            objects.add(chatVO.getOwner());
            chatVO.setUserIdList(objects);
        }
        request.setUseridlist(chatVO.getUserIdList());
        request.setShowHistoryType(1L);
        try {
            OapiChatCreateResponse response = client.execute(request, DingDingUtil.ACCESS_TOKEN);
            if (response.getErrcode().equals(0L)) {
                Chat chat = new Chat();
                BeanUtils.copyProperties(chatVO, chat);
                // 将群信息保存到数据库
                chat.setChatId(response.getChatid());
                chat.setCreateTime(new Date());
                chat.setFlag(1);
                chat.setType(chatVO.getType());
                chat.setUserIdList(JSONObject.toJSONString(chatVO.getUserIdList()));
                List<String> managerUser = new ArrayList<>(1);
                managerUser.add(chat.getOwner());
                chat.setManagerUser(JSON.toJSONString(managerUser));

                if (CollectionUtil.isNotEmpty(areaIds)) {
                    chat.setAreaId(areaIds.get(0));
                }
                chatMapper.insert(chat);

                // 将添加的群保存到群中间表
                for (String s : chatVO.getUserIdList()) {
                    UserChat userChat = new UserChat();
                    userChat.setChatId(chat.getChatId());

                    User userByDDUserId = userMapper.findUserByDDUserId(s);
                    if (userByDDUserId != null) {
                        userChat.setUserId(userByDDUserId.getUserId());
                        userChatMapper.insert(userChat);
                    }
                }
                return result;
            } else {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return DDErrResult.dataError(result, response.getErrmsg());
            }
        } catch (ApiException e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return DDErrResult.connException(result);
        }
    }

    /**
     * 发送群消息
     *
     * @param sendChatVO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result sendChat(SendChatVO sendChatVO) {
        Result result = new Result("发送成功");

        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/chat/send");
        OapiChatSendRequest request = new OapiChatSendRequest();
        request.setChatid(sendChatVO.getChatId());

        OapiChatSendRequest.Msg msg = new OapiChatSendRequest.Msg();
        msg.setMsgtype("text");
        OapiChatSendRequest.Text text = new OapiChatSendRequest.Text();
        text.setContent(sendChatVO.getMsg());
        msg.setText(text);

        request.setMsg(msg);
        try {
            OapiChatSendResponse response = client.execute(request, DingDingUtil.ACCESS_TOKEN);
            if (response.getErrcode().equals(0L)) {
                messageMapper.insert(new Message()
                        .setChatId(sendChatVO.getChatId())
                        .setContent(JSONObject.toJSONString(msg))
                        .setCreateTime(new Date()).setMsgId(response.getMessageId()));
                return result;
            } else {
                return DDErrResult.dataError(result, response.getErrmsg());
            }
        } catch (ApiException e) {
            e.printStackTrace();
            return DDErrResult.connException(result);
        }

    }

    /**
     * 设置群管理员
     *
     * @param userId
     * @param roleIds
     * @param chatId  群会话id
     * @param userIds 群成员userid列表，字符串使用英文逗号分割
     * @param role    设置2为添加为管理员，设置3为删除该管理员
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result subAdmin(Integer userId, String roleIds, String chatId, String userIds, Long role) {

        Result result = new Result(MessageInfo.UPDATE_INFO.getInfo());

        Chat chat = chatMapper.selectById(chatId);

        List<String> roleList = new ArrayList<>(Arrays.asList(roleIds.split(",")));

        // 如果为网格管理员
        if (roleList.contains("5")) {
            // 跳过市管理员和 super 管理员
            if (!roleList.contains("1") && !roleList.contains("2")
                    && !roleList.contains("3") && !roleList.contains("4")) {
                if (!userIds.equals(chat.getOwner())) {
                    return result.setMessage("你不是群主，没有权限操作管理员").setCode(StatusCode.ERROR).setFlag(false);
                }
            }
        }

        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/chat/subadmin/update");
        OapiChatSubadminUpdateRequest req = new OapiChatSubadminUpdateRequest();
        req.setChatid(chatId);
        req.setUserids(userIds);
        req.setRole(role);

        ChatUpdateParam param = new ChatUpdateParam();
        param.setChatId(chatId);
        List<String> users = new ArrayList<>(1);
        users.add(userIds);
        param.setAddUserIdList(users);
        try {
            DingDingSDKUtils.updateChat(param);
        } catch (ApiException e) {
            e.printStackTrace();
        }

        User userByDDUserId = userMapper.findUserByDDUserId(userIds);
        try {
            OapiChatSubadminUpdateResponse response = client.execute(req, DingDingUtil.ACCESS_TOKEN);
            if (response.getErrcode().equals(0L)) {

                // 如果为新增管理员
                if (role.equals(2L)) {

                    // 这个是 要添加的管理员
                    String[] userArray = userIds.split(",");

                    //if (StringUtils.isNotBlank(chat.getManagerUser())) {

                    // 获得原有的管理员
                    List list = JSONObject.parseObject(chat.getManagerUser(), List.class);
                    Set<String> set = new LinkedHashSet<>(16);

                    // 如果管理员列表中不存在
                    if (!list.contains(userArray)) {
                        set.addAll(Arrays.asList(userArray));
                    }
                    set.addAll(list);

                    chat.setManagerUser(JSONObject.toJSONString(set));
                    chatMapper.updateById(chat);

                    // 向区域权限中间表插入数据
                    AreaPermissions areaPermissions = new AreaPermissions();
                    areaPermissions.setAdminType(5);
                    List<Grid> grids = gridMapper.selectList(Wrappers.<Grid>lambdaQuery().eq(Grid::getChatId, chatId));
                    Grid grid = grids.get(0);
                    areaPermissions.setGridId(grid.getGridId());
                    areaPermissions.setUserId(userByDDUserId.getUserId());
                    Street street = streetMapper.selectById(grid.getStreetId());
                    areaPermissions.setStreetId(street.getStreetId());
                    areaPermissions.setAreaId(street.getAreaId());
                    areaPermissionsMapper.insert(areaPermissions);

                    PollAdmin pollAdmin = new PollAdmin();
                    pollAdmin.setPollCount(set.size());

                    pollAdminMapper.update(pollAdmin, Wrappers.<PollAdmin>lambdaQuery()
                            .eq(PollAdmin::getChatId, chat.getChatId()));

                } else if (role.equals(3L)) {
                    //Chat chat = chatMapper.selectById(chatId);
                    if (StringUtils.isNotBlank(chat.getManagerUser())) {
                        // 这个是 要删除的管理员
                        String[] userArray = userIds.split(",");
                        // 获得原有的管理员
                        List<Object> list = new ArrayList<>(JSONObject.parseObject(chat.getManagerUser(), List.class));
                        list.removeAll(Arrays.asList(userArray));
                        chat.setManagerUser(JSONObject.toJSONString(list));
                        // 将结果进行更新
                        chatMapper.updateById(chat);

                        PollAdmin pollAdmin = new PollAdmin();
                        //pollAdmin.setChatName(chat.getName());
                        pollAdmin.setPollCount(list.size());

                        pollAdminMapper.update(pollAdmin, Wrappers.<PollAdmin>lambdaQuery()
                                .eq(PollAdmin::getChatId, chat.getChatId()));

                        List<Grid> grids = gridMapper.selectList(Wrappers.<Grid>lambdaQuery().eq(Grid::getChatId, chatId));
                        Grid grid = grids.get(0);
                        areaPermissionsMapper.delete(Wrappers.<AreaPermissions>lambdaQuery()
                                .eq(AreaPermissions::getUserId, userByDDUserId.getUserId())
                                .eq(AreaPermissions::getGridId, grid.getGridId())
                                .eq(AreaPermissions::getAdminType, 5));
                    }
                }

                return result;
            } else {
                return DDErrResult.dataError(result, response.getErrmsg());
            }
        } catch (ApiException e) {
            e.printStackTrace();
            return DDErrResult.connException(result);
        }
    }

    /**
     * 获取全部群聊
     *
     * @param page
     * @return
     */
    @Override
    public Result getAllChat(Page page) {
        Result<PageInfo<Object>> result = new Result<PageInfo<Object>>(MessageInfo.GET_INFO.getInfo());
        PageInfo<Object> info = PageHelper.startPage(Integer.parseInt(page.getPageNum().toString()), Integer.parseInt(page.getPageSize().toString()))
                .doSelectPageInfo(() -> chatMapper.selectList(null));
        return result.setData(info);
    }

    /**
     * 根据地点查询群
     *
     * @param page
     * @param position
     * @return
     */
    @Override
    public Result getChatByPosition(Page page, String position) {
        Result<PageResult> result = new Result<>(MessageInfo.GET_INFO.getInfo());
        PageInfo<Chat> info = PageHelper.startPage(Integer.parseInt(page.getPageNum().toString()), Integer.parseInt(page.getPageSize().toString()))
                .doSelectPageInfo(() -> chatMapper.selectList(Wrappers.<Chat>lambdaQuery()
                        .eq(Chat::getPosition, position)
                        .eq(Chat::getFlag, 1)
                        .orderByDesc(Chat::getCreateTime)));
        return result.setData(new PageResult(info.getTotal(), info.getList()));
    }

    /**
     * 群组合并
     *
     * @param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result mergeChat(MergeChatVO mergeChatVO) {

        Result result = new Result("合并成功");

        List<String> chatList = mergeChatVO.getChatList();

        Set<String> userIds = new HashSet<>(100);

        for (String chat : chatList) {
            // 请求钉钉获取会话接口
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/chat/get");
            OapiChatGetRequest request = new OapiChatGetRequest();
            request.setHttpMethod("GET");
            request.setChatid(chat);
            try {
                OapiChatGetResponse response = client.execute(request, DingDingUtil.ACCESS_TOKEN);
                // 将群中的所有人员添加到集合中
                userIds.addAll(response.getChatInfo().getUseridlist());
            } catch (ApiException e) {
                e.printStackTrace();
                return DDErrResult.connException(result);
            }
        }

        // 判断人员是否大于1000
        if (userIds.size() > 1000) {
            return result.setFlag(false).setCode(StatusCode.ERROR)
                    .setMessage(StrUtil.format("钉钉限制，群成员最多只能为1000个，当前合并后为{}个", userIds.size()));
        }

        // 此标记表明新创建的群 是创建还是修改
        //
        int flag = 1;

        // 初始化 chatId
        String chatId = "";

        // 要添加到群的人员
        List<String> addUserIds = new LinkedList<>(userIds);

        // 如果大于40，进入此条件，钉钉每次向群中添加数据
        while (addUserIds.size() > 40) {

            // flag 大于 100 退出 while
            if (flag >= 100) {
                break;
            }

            if (flag == 1) {
                // 取前39个
                List<String> userIdList = addUserIds.subList(0, 39);
                // 执行创建群功能
                try {
                    OapiChatCreateResponse response = DingDingSDKUtils.createChat(
                            new ChatCreateParam()
                                    .setOwner(mergeChatVO.getOwner())
                                    .setName(mergeChatVO.getName())
                                    .setUseridlist(userIdList));
                    if (response.getErrcode().equals(0L)) {
                        // 将 chatId 赋值
                        chatId = response.getChatid();

                        // 执行成功后解散之前的两个群
                        for (String cId : chatList) {
                            Chat chat = new Chat();
                            chat.setChatId(cId);
                            chat.setFlag(0);
                            chatMapper.updateById(chat);
                        }

                        // 将添加后的数据进行移除操作
                        addUserIds.removeAll(userIdList);
                    }
                } catch (ApiException e) {
                    log.info("合并群失败，连接钉钉接口超时");
                    e.printStackTrace();
                }

                // 将 flag ++
                flag++;
            }

            if (flag > 1) {
                // 执行向群中添加人员
                List<String> userIdList = addUserIds.subList(0, 40);

                ChatUpdateParam chatUpdateParam = new ChatUpdateParam();
                chatUpdateParam.setChatId(chatId);
                chatUpdateParam.setAddUserIdList(userIdList);

                try {
                    OapiChatUpdateResponse response = DingDingSDKUtils.updateChat(chatUpdateParam);
                    if (response.getErrcode().equals(0L)) {
                        log.info("合并群，向群中添加人员,成功，flag为：" + flag);
                        addUserIds.removeAll(userIdList);
                    } else {
                        log.info("合并群，向群中添加人员,失败，flag为：" + flag);
                    }
                } catch (ApiException e) {
                    e.printStackTrace();
                    log.info("合并群，向群中添加人员,失败，连接异常，flag为：" + flag);
                }
                flag++;
            }
        }

        // userIds 为原始数据，addUserIds为每次添加完成删减后的数据
        // 此种情况说明 上面的 while 循环中 addUserIds 中还有数据，但小于 40个，
        // 应该将剩余的数据再次添加到群中
        if (userIds.size() > 40 && addUserIds.size() <= 40) {
            ChatUpdateParam chatUpdateParam = new ChatUpdateParam();
            chatUpdateParam.setChatId(chatId);
            chatUpdateParam.setAddUserIdList(addUserIds);
            try {
                OapiChatUpdateResponse response = DingDingSDKUtils.updateChat(chatUpdateParam);
                if (response.getErrcode().equals(0L)) {
                    addUserIds.clear();
                } else {
                    log.info("合并群，向群中添加人员,失败");
                }
            } catch (ApiException e) {
                e.printStackTrace();
                log.info("合并群，向群中添加人员,失败，连接异常");
            }
        }

        //这种情况要合并群中的成员合计少于 40 人，直接进行创建群添加用户
        if (userIds.size() <= 40) {
            // 执行创建群功能
            try {
                OapiChatCreateResponse response = DingDingSDKUtils.createChat(
                        new ChatCreateParam()
                                .setOwner(mergeChatVO.getOwner())
                                .setName(mergeChatVO.getName())
                                .setUseridlist(new ArrayList<>(userIds)));
                if (response.getErrcode().equals(0L)) {
                    // 执行成功后解散之前的两个群
                    for (String cid : chatList) {
                        Chat chat = new Chat();
                        chat.setFlag(0);
                        chat.setChatId(cid);
                        chatMapper.updateById(chat);
                    }
                } else {
                    log.info("合并群失败，要合并的群名为{},失败原因{}", mergeChatVO.getName(), response.getErrmsg());
                }
            } catch (ApiException e) {
                log.info("合并群失败，连接钉钉接口超时");
                e.printStackTrace();
            }
        }

        return result;
    }

    /**
     * 修改会话信息 ,  只用来修改 群禁言 和 @ALL
     *
     * @param updateChatVO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result updateChat(UpdateChatVO updateChatVO) {

        Result<Object> result = new Result<>(MessageInfo.UPDATE_INFO.getInfo());

        ChatUpdateParam param = new ChatUpdateParam();
        param.setChatId(updateChatVO.getChatId());
        //BeanUtils.copyProperties(updateChatVO, param);
        if (updateChatVO.getChatBannedType() != null) {
            param.setChatBannedType(updateChatVO.getChatBannedType().longValue());
        }
        if (updateChatVO.getMentionAllAuthority() != null) {
            param.setMentionAllAuthority(updateChatVO.getMentionAllAuthority().longValue());
        }

        try {
            OapiChatUpdateResponse response = DingDingSDKUtils.updateChat(param);
            if (response.getErrcode().equals(0L)) {
                Chat chat = new Chat();
                BeanUtils.copyProperties(updateChatVO, chat);
                chatMapper.updateById(chat);
                return result;
            } else {
                return DDErrResult.dataError(result, response.getErrmsg());
            }
        } catch (ApiException e) {
            e.printStackTrace();
            return DDErrResult.connException(result);
        }
    }

    /**
     * 查询全部群所在地区
     *
     * @return
     */
    @Override
    public Result getAllPosition() {
        Result<List<String>> result = new Result<>(MessageInfo.GET_INFO.getInfo());
        List<String> mapList = chatMapper.getAllPosition();
        //List<Chat> chats = chatMapper.selectList(Wrappers.<Chat>lambdaQuery().groupBy(Chat::getPosition).having("flag", 1));
        return result.setData(mapList);
    }

    /**
     * 查看群发送消息的历史
     *
     * @param chatId
     * @return
     */
    @Override
    public Result getMessageHistory(Page page, String chatId) {

        Result<PageResult> result = new Result<>(MessageInfo.GET_INFO.getInfo());

        PageInfo<Message> messagePageInfo = PageHelper.startPage(Integer.parseInt(page.getPageNum().toString()), Integer.parseInt(page.getPageSize().toString()))
                .doSelectPageInfo(() -> messageMapper.selectList(Wrappers.<Message>lambdaQuery()
                        .eq(Message::getChatId, chatId)
                        .eq(Message::getContentType, 2)
                        .orderByDesc(Message::getCreateTime)));
        return result.setData(new PageResult(messagePageInfo.getTotal(), messagePageInfo.getList()));
    }

    /**
     * 查询对应权限的所有群聊
     *
     * @return
     */
    @Override
    public Result getChatList(PermissionData permissionData) {

        Result result = new Result(MessageInfo.GET_INFO.getInfo());
        List<Map<String, Object>> list = new ArrayList<>(1);
        Map<String, Object> map = new LinkedHashMap<>(10);
        map.put("cityId", 0);
        map.put("id", 0);
        map.put("name", "青岛市");

        // 如果为市管理员或 super,将全部列表展示
        if (permissionData.getRoleIds().contains(1) || permissionData.getRoleIds().contains(2)) {
            List<AreaPO> allArea = areaMapper.findAllArea(null);
            map.put("children", allArea);
            list.add(map);
            return result.setData(list);
        }

        // 最终存放的数据
        List<AreaPO> allAreaByAreaIds = new ArrayList<>(10);
        // 如果区管理员不为空
        if (CollectionUtil.isNotEmpty(permissionData.getAreaIds())) {
            // 区管理员
            List<AreaPermissions> areaPermissions = this.findAreaPermissions(3, permissionData.getAreaIds(), permissionData.getUserId());

            List<Integer> areaIds = new ArrayList<>(10);
            areaPermissions.forEach(ap -> areaIds.add(ap.getAreaId()));

            if (CollectionUtil.isNotEmpty(areaIds)) {
                allAreaByAreaIds = areaMapper.findAllAreaByAreaIds(areaIds);
            }
        }

        // 如果街道不为空
        if (CollectionUtil.isNotEmpty(permissionData.getStreetIds())) {

            Set<Integer> areaList = new HashSet<>(10);
            List<Integer> streetIds = permissionData.getStreetIds();
            List<StreetPO> streets = streetMapper.findStreetsByStreetIds(new ArrayList<>(streetIds));

            streets.forEach(streetPO -> areaList.add(streetPO.getAreaId()));

            Map<String, Object> areaMap = new LinkedHashMap<>(16);
            for (Integer areaId : areaList) {
                Area area = areaMapper.selectById(areaId);
                if (area != null) {
                    AreaPO areaPO = new AreaPO();
                    areaPO.setAreaId(areaId);
                    areaPO.setId(areaId);
                    areaPO.setName(area.getName());
                    areaMap.put(areaId.toString(), areaPO);
                }
            }

            for (StreetPO streetPO : streets) {
                AreaPO o = (AreaPO) areaMap.get(streetPO.getAreaId().toString());

                if (o != null) {
                    List<StreetPO> children = o.getChildren();
                    if (children == null) {
                        List<StreetPO> objects = new ArrayList<>();
                        objects.add(streetPO);
                        o.setChildren(objects);
                    } else {
                        children.add(streetPO);
                        o.setChildren(children);
                    }

                }
            }

            if (CollectionUtil.isNotEmpty(allAreaByAreaIds)) {

                List<String> areaNameList = new ArrayList<>(10);
                for (AreaPO allAreaByAreaId : allAreaByAreaIds) {
                    areaNameList.add(allAreaByAreaId.getName());
                }

                for (Map.Entry<String, Object> entry : areaMap.entrySet()) {
                    AreaPO areaPO = (AreaPO) entry.getValue();
                    // 如果市管理员之前查找到对应的市，跳过
                    if (!areaNameList.contains(areaPO.getName())) {
                        allAreaByAreaIds.add((AreaPO) entry.getValue());
                    }
                }
            } else {
                for (Map.Entry<String, Object> entry : areaMap.entrySet()) {
                    //allAreaByAreaIds = new ArrayList<>(10);
                    allAreaByAreaIds.add((AreaPO) entry.getValue());
                }
            }
        }

        if (CollectionUtil.isNotEmpty(permissionData.getGrids())) {
            List<Integer> gridList = permissionData.getGrids();
            List<AreaPermissions> areaPermissions = this.findAreaPermissionsByGridIds(5, gridList, 125);

            Set<AreaPO> areaPOS = new LinkedHashSet<>(10);
            for (AreaPermissions areaPermission : areaPermissions) {
                Integer areaId = areaPermission.getAreaId();
                Integer streetId = areaPermission.getStreetId();
                Integer gridId = areaPermission.getGridId();

                // 根据区域权限表，查询出对应的区 街道 网格
                Area area = areaMapper.selectById(areaId);
                Street street = streetMapper.selectById(streetId);
                Grid grid = gridMapper.selectById(gridId);

                // 将 区 街道 网格 按照 树状结构进行拼装
                List<GridPO> gridPOList = new ArrayList<>(1);
                GridPO gridPO = new GridPO();
                gridPO.setId(gridId);
                gridPO.setGridId(gridId);
                gridPO.setName(grid.getGridName());
                gridPO.setChatId(grid.getChatId());
                gridPO.setStreetId(streetId);
                gridPOList.add(gridPO);

                List<StreetPO> streetPOList = new ArrayList<>(1);
                StreetPO streetPO = new StreetPO();
                streetPO.setId(streetId);
                streetPO.setStreetId(streetId);
                streetPO.setName(street.getStreetName());
                streetPO.setAreaId(areaId);
                streetPO.setChildren(gridPOList);
                streetPOList.add(streetPO);

                AreaPO areaPO = new AreaPO();
                areaPO.setAreaId(areaId);
                areaPO.setId(areaId);
                areaPO.setName(area.getName());
                areaPO.setChildren(streetPOList);

                areaPOS.add(areaPO);
            }
            for (AreaPO areaPO : areaPOS) {
                // 调用方法，如果 子节点存在跳过，不存在添加
                allAreaByAreaIds = this.areaExistMerge(allAreaByAreaIds, areaPO);
            }

        }
        map.put("children", allAreaByAreaIds);
        list.add(map);
        return result.setData(list);
    }

    /**
     * 根据用户id查询对应的通知公告
     *
     * @param userId
     * @param page
     * @return
     */
    @Override
    public PageInfo<Message> getCorpMessageByUserId(Integer userId, Page page) {

        PageInfo<Message> pageInfo = PageHelper.startPage(page.getPageNum().intValue(), page.getPageSize().intValue())
                .doSelectPageInfo(() -> messageMapper.selectList(Wrappers.<Message>lambdaQuery()
                        .eq(Message::getUserId, userId)
                        .eq(Message::getContentType, 1)
                        .eq(Message::getFlag, 1)));

        return pageInfo;
    }

    /**
     * 根据 群id 查询对应的群信息
     *
     * @param chatId
     */
    @Override
    public Chat getChatInfoByChatId(String chatId) {
        return chatMapper.selectById(chatId);
    }

    /**
     * 获取行业群
     *
     * @param page
     * @param name
     * @return
     */
    @Override
    public PageResult getIndustryChat(Page page, String name) {
        QueryWrapper queryWrapper = new QueryWrapper();

        if (StringUtils.isNotBlank(name)) {
            queryWrapper.eq("name", name);
        }
        queryWrapper.eq("type", 2);
        queryWrapper.eq("flag", 1);

        PageInfo<Object> pageInfo = PageHelper.startPage(page.getPageNum().intValue(), page.getPageSize().intValue())
                .doSelectPageInfo(() -> chatMapper.selectList(queryWrapper));
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 钉钉下获取全部群
     *
     * @param userId
     * @return
     */
    @Override
    public PageResult getDdChat(Page page, Integer userId) {

        // 6 为普通用户
        Integer roleId = 6;

        // 查询对应角色
        List<UserRole> userRoles = userRoleMapper.selectList(Wrappers.<UserRole>lambdaQuery()
                .eq(UserRole::getUserId, userId));
        if (!userRoles.isEmpty()) {
            List<Integer> list = new ArrayList<>(userRoles.size());
//            userRoles.forEach(userRole -> list.add(userRole.getRoleId()));

            // 拿到最大的权限
            roleId = Collections.min(list);
        }

        log.info("roleid{}", roleId);

        // 如果为市管理员或者超级管理员
        if (roleId.equals(1) || roleId.equals(2)) {

            PageInfo<List<Map>> pageInfo = PageHelper.startPage(page.getPageNum().intValue(), page.getPageSize().intValue())
                    .doSelectPageInfo(() -> chatMapper.findAllChat(null, null));
            return new PageResult(pageInfo.getTotal(), pageInfo.getList());
        }

        // 如果为区管理员
        if (roleId.equals(3)) {
            List<AreaPermissions> areaPermissions = areaPermissionsMapper.selectList(Wrappers.<AreaPermissions>lambdaQuery()
                    .eq(AreaPermissions::getAdminType, 3)
                    .eq(AreaPermissions::getUserId, userId));
            List<Integer> areaIds = new ArrayList<>(areaPermissions.size());
            areaPermissions.forEach(street -> areaIds.add(street.getAreaId()));

            if (!areaIds.isEmpty()) {
                List<Street> streets = streetMapper.selectList(Wrappers.<Street>lambdaQuery()
                        .in(Street::getAreaId, areaIds));
                List<Integer> streetIds = new ArrayList<>(streets.size());
                streets.forEach(street -> streetIds.add(street.getStreetId()));

                PageInfo<List<Map>> pageInfo = PageHelper.startPage(page.getPageNum().intValue(), page.getPageSize().intValue())
                        .doSelectPageInfo(() -> chatMapper.findAllChat(streetIds, null));
                return new PageResult(pageInfo.getTotal(), pageInfo.getList());
            } else {
                return new PageResult(0L, new ArrayList<>());
            }

        }

        // 如果为街道管理员
        if (roleId.equals(4)) {
            List<AreaPermissions> areaPermissions = areaPermissionsMapper.selectList(Wrappers.<AreaPermissions>lambdaQuery()
                    .eq(AreaPermissions::getAdminType, 4)
                    .eq(AreaPermissions::getUserId, userId));

            log.info("areaPermissions{}", JSON.toJSONString(areaPermissions));

            List<Integer> streetIds = new ArrayList<>(areaPermissions.size());
            areaPermissions.forEach(street -> streetIds.add(street.getStreetId()));

            PageInfo<List<Map>> pageInfo = PageHelper.startPage(page.getPageNum().intValue(), page.getPageSize().intValue())
                    .doSelectPageInfo(() -> chatMapper.findAllChat(streetIds, null));
            return new PageResult(pageInfo.getTotal(), pageInfo.getList());
        }

        // 如果为网格管理员
        if (roleId.equals(5)) {
            List<AreaPermissions> areaPermissions = areaPermissionsMapper.selectList(Wrappers.<AreaPermissions>lambdaQuery()
                    .eq(AreaPermissions::getAdminType, 5)
                    .eq(AreaPermissions::getUserId, userId));

            List<Integer> gridIds = new ArrayList<>(areaPermissions.size());
            areaPermissions.forEach(grid -> gridIds.add(grid.getGridId()));

            PageInfo<List<Map>> pageInfo = PageHelper.startPage(page.getPageNum().intValue(), page.getPageSize().intValue())
                    .doSelectPageInfo(() -> chatMapper.findAllChat(null, null));

        }

        return null;
    }

    /**
     * 根据各个id查询对应的全部群聊
     *
     * @param city
     * @param areaId
     * @param streetId
     * @param gridId
     * @param userId
     * @param roleId
     * @return
     */
    @Override
    public PageResult getChatList(Page page, Integer city, Integer areaId, Integer streetId,
                                  Integer gridId, Integer userId, Integer roleId) {
        Result<Object> result = new Result<>(MessageInfo.GET_INFO.getInfo());

        // 查询全部群聊
        if (city != null) {
            if (roleId.equals(1) || roleId.equals(2)) {
                PageInfo<Object> pageInfo = PageHelper.startPage(page.getPageNum().intValue(), page.getPageSize().intValue())
                        .doSelectPageInfo(() -> chatMapper.selectList(Wrappers.<Chat>lambdaQuery()
                                .eq(Chat::getFlag, 1)
                                .eq(Chat::getType, 1)));
                return new PageResult(pageInfo.getTotal(), pageInfo.getList());
            }
        }

        // 区下的所有群
        if (areaId != null) {
            Area area = areaMapper.selectById(areaId);
            //拿到所有的街道id
            List<Street> streets = streetMapper.selectList(Wrappers.<Street>lambdaQuery().eq(Street::getAreaId, area.getAreaId()));

            List<Integer> streetIds = new ArrayList<>(streets.size());
            streets.forEach(street -> streetIds.add(street.getStreetId()));

            if (CollectionUtil.isNotEmpty(streetIds)) {
                List<Grid> grids = gridMapper.selectList(Wrappers.<Grid>lambdaQuery()
                        .in(Grid::getStreetId, streetIds).eq(Grid::getFlag, 1));
                List<String> chatIds = new ArrayList<>(grids.size());
                grids.forEach(grid -> chatIds.add(grid.getChatId()));

                if (CollectionUtil.isNotEmpty(chatIds)) {
                    PageInfo<Object> pageInfo = PageHelper.startPage(page.getPageNum().intValue(), page.getPageSize().intValue())
                            .doSelectPageInfo(() -> chatMapper.selectList(Wrappers.<Chat>lambdaQuery()
                                    .in(Chat::getChatId, chatIds).eq(Chat::getFlag, 1).eq(Chat::getType, 1)));

                    //List<Chat> chatList = ;
                    return new PageResult(pageInfo.getTotal(), pageInfo.getList());
                } else {
                    return new PageResult(0L, new ArrayList<>());
                }

            } else {
                return new PageResult(0L, new ArrayList<>());
            }

        }

        // 所有街道下的群
        if (streetId != null) {
            Street street = streetMapper.selectById(streetId);
            List<Grid> grids = gridMapper.selectList(Wrappers.<Grid>lambdaQuery()
                    .eq(Grid::getStreetId, street.getStreetId()).eq(Grid::getFlag, 1));
            List<String> chatIds = new ArrayList<>(grids.size());
            grids.forEach(grid -> chatIds.add(grid.getChatId()));
            /*List<Chat> chatList = chatMapper.selectList(Wrappers.<Chat>lambdaQuery()
                    .in(Chat::getChatId, chatIds).eq(Chat::getFlag, 1).eq(Chat::getType, 1));*/

            if (CollectionUtil.isNotEmpty(chatIds)) {
                PageInfo<Object> pageInfo = PageHelper.startPage(page.getPageNum().intValue(), page.getPageSize().intValue())
                        .doSelectPageInfo(() -> chatMapper.selectList(Wrappers.<Chat>lambdaQuery()
                                .in(Chat::getChatId, chatIds).eq(Chat::getFlag, 1).eq(Chat::getType, 1)));
                return new PageResult(pageInfo.getTotal(), pageInfo.getList());
            } else {
                return new PageResult(0L, new ArrayList<>());
            }

        }

        // 网格下的群
        if (gridId != null) {
            Grid grid = gridMapper.selectById(gridId);

            PageInfo<Object> pageInfo = PageHelper.startPage(page.getPageNum().intValue(), page.getPageSize().intValue())
                    .doSelectPageInfo(() -> chatMapper.selectList(Wrappers.<Chat>lambdaQuery()
                            .eq(Chat::getChatId, grid.getChatId())
                            .eq(Chat::getFlag, 1)
                            .eq(Chat::getType, 1)));

            //List<Chat> chatList = chatMapper.selectList(Wrappers.<Chat>lambdaQuery().eq(Chat::getChatId, grid.getChatId()).eq(Chat::getFlag, 1).eq(Chat::getType, 1));
            return new PageResult(pageInfo.getTotal(), pageInfo.getList());
        }

        return new PageResult(0L, new ArrayList<>());
    }

    /**
     * 根据 规模id 或行业id查询对应群
     *
     * @param page
     * @param esId
     * @param industryId
     * @return
     */
    @Override
    public PageResult getChatByEs(Page page, Integer esId, Integer industryId, Integer esAll, Integer industryIdAll) {

        QueryWrapper<Chat> queryWrapper = new QueryWrapper<>();

        // 如果为1 说明查询全部 行业
        if (esAll != null && esAll.equals(1)) {
            queryWrapper.eq("type", 3);
        } else {
            if (esId != null) {
                queryWrapper.eq("es_id", esId);
                queryWrapper.eq("type", 3);
            }
        }

        if (industryIdAll != null && industryIdAll.equals(1)) {
            queryWrapper.eq("type", 2);
        } else {
            if (industryId != null) {
                queryWrapper.eq("industry_id", industryId);
                queryWrapper.eq("type", 2);
            }
        }

        queryWrapper.eq("flag", 1);

        PageInfo<Chat> pageInfo = PageHelper.startPage(page.getPageNum().intValue(), page.getPageSize().intValue())
                .doSelectPageInfo(() -> chatMapper.selectList(queryWrapper));

        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 根据群id查询群下所在的用户列表
     *
     * @param chatId
     * @return
     */
    @Override
    public Result getUserListByChatId(String chatId) {

        Result<Object> result = new Result<>(MessageInfo.GET_INFO.getInfo());

        /*PageInfo<User> pageInfo = PageHelper.startPage(Integer.parseInt(page.getPageNum().toString()), Integer.parseInt(page.getPageSize().toString()))
                .doSelectPageInfo(() -> );*/

        List<User> grIdChatInfo = userMapper.getGrIdChatInfo(chatId, null, null);
        if (CollectionUtil.isNotEmpty(grIdChatInfo)) {
            return result.setData(grIdChatInfo);
        } else {
            return result.setData(new ArrayList<>());
        }

        /*DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/chat/get");
        OapiChatGetRequest request = new OapiChatGetRequest();
        request.setHttpMethod("GET");
        request.setChatid(chatId);
        try {
            OapiChatGetResponse response = client.execute(request, DingDingUtil.ACCESS_TOKEN);
            if (response.getErrcode().equals(0L)) {
                List<String> useridlist = response.getChatInfo().getUseridlist();
                if (CollectionUtil.isNotEmpty(useridlist)) {
                    List<User> users = userMapper.selectList(Wrappers.<User>lambdaQuery()
                            .in(User::getDdUserId, useridlist)
                            .eq(User::getIsConfirm, 1)
                            .eq(User::getIsAdmin, 0)
                            .eq(User::getFlag, 1));
                    return result.setData(users);
                } else {
                    return result.setData(new ArrayList<>());
                }
            } else {
                return DDErrResult.dataError(result, response.getErrmsg());
            }
        } catch (ApiException e) {
            e.printStackTrace();
            return DDErrResult.connException(result);
        }*/
    }

    /**
     * 根据群名搜索群
     *
     * @param name
     * @param userId
     * @param roleId
     * @return
     */
    @Override
    public Result getChatInfoByName(String name, Integer userId, Integer roleId) {

        if (roleId.equals(1) || roleId.equals(2)) {
            List<Chat> chatList = chatMapper.selectList(Wrappers.<Chat>lambdaQuery()
                    .like(Chat::getName, name));
            return new Result(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(), chatList);
        } else {
            Set<String> chatIds = new HashSet<>();
            // 查询用户权限下所有的群id
            if (roleId.equals(3)) {
                List<AreaPermissions> areaPermissions = areaPermissionsMapper.selectList(Wrappers.<AreaPermissions>lambdaQuery()
                        .eq(AreaPermissions::getUserId, userId).eq(AreaPermissions::getAdminType, roleId));
                // 查询用户所管理的全部区管理员
                for (AreaPermissions areaPermission : areaPermissions) {
                    Integer areaId = areaPermission.getAreaId();
                    // 查询出区下的所有街道
                    List<StreetPO> streetsByAreaId = streetMapper.findStreetsByAreaId(areaId);
                    for (StreetPO streetPO : streetsByAreaId) {
                        // 得到街道下所有的网格
                        List<GridPO> gridPOS = streetPO.getChildren();
                        for (GridPO gridPO : gridPOS) {
                            chatIds.add(gridPO.getChatId());
                        }
                    }
                }
            }
            // 街道管理员
            if (roleId.equals(4)) {
                List<AreaPermissions> areaPermissions = areaPermissionsMapper.selectList(Wrappers.<AreaPermissions>lambdaQuery()
                        .eq(AreaPermissions::getUserId, userId).eq(AreaPermissions::getAdminType, roleId));
                for (AreaPermissions areaPermission : areaPermissions) {
                    Integer streetId = areaPermission.getStreetId();
                    StreetPO streetsByStreetId = streetMapper.findStreetsByStreetId(streetId);
                    List<GridPO> gridPOS = streetsByStreetId.getChildren();
                    for (GridPO gridPO : gridPOS) {
                        chatIds.add(gridPO.getChatId());
                    }
                }
            }
            // 如果为网格管理员
            if (roleId.equals(5)) {
                List<AreaPermissions> areaPermissions = areaPermissionsMapper.selectList(Wrappers.<AreaPermissions>lambdaQuery()
                        .eq(AreaPermissions::getUserId, userId).eq(AreaPermissions::getAdminType, roleId));
                List<Integer> gridIds = new ArrayList<>(10);
                for (AreaPermissions areaPermission : areaPermissions) {
                    Integer gridId = areaPermission.getGridId();
                    gridIds.add(gridId);
                }
                List<Grid> grids = gridMapper.selectList(Wrappers.<Grid>lambdaQuery().in(Grid::getGridId, gridIds));
                for (Grid grid : grids) {
                    chatIds.add(grid.getChatId());
                }
            }

            if (CollectionUtil.isNotEmpty(chatIds)) {
                List<Chat> chatList = chatMapper.selectList(Wrappers.<Chat>lambdaQuery()
                        .in(Chat::getChatId, chatIds)
                        .like(Chat::getName, name));
                return new Result(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(), chatList);
            } else {
                return new Result(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(), new ArrayList<>());
            }

        }

    }

    /**
     * 修改或者删除群下的人员
     *
     * @param updateChatUserVO
     * @return
     */
    @Override
    public Result updateOrDeleteChatUser(UpdateChatUserVO updateChatUserVO) {

        Result<Object> result = new Result<>(MessageInfo.UPDATE_INFO.getInfo());

        ChatUpdateParam param = new ChatUpdateParam();
        param.setChatId(updateChatUserVO.getChatId());

        if (CollectionUtil.isNotEmpty(updateChatUserVO.getAddUserIds())) {
            param.setAddUserIdList(updateChatUserVO.getAddUserIds());

            // 新增的用户添加到中间表
            for (String addUserId : updateChatUserVO.getAddUserIds()) {
                User userByDDUserId = userMapper.findUserByDDUserId(addUserId);
                if (userByDDUserId != null) {
                    UserChat userChat = new UserChat();
                    userChat.setUserId(userByDDUserId.getUserId());
                    userChat.setChatId(updateChatUserVO.getChatId());
                    userChatMapper.insert(userChat);
                }
            }

        }

        if (CollectionUtil.isNotEmpty(updateChatUserVO.getDeleteUserIds())) {
            param.setDelUserIdList(updateChatUserVO.getDeleteUserIds());

            // 删除的用户添加到中间表
            for (String addUserId : updateChatUserVO.getDeleteUserIds()) {
                User userByDDUserId = userMapper.findUserByDDUserId(addUserId);
                if (userByDDUserId != null) {
                    userChatMapper.delete(Wrappers.<UserChat>lambdaQuery()
                            .eq(UserChat::getChatId, updateChatUserVO.getChatId())
                            .eq(UserChat::getUserId, userByDDUserId.getUserId()));
                }
            }
        }

        try {
            OapiChatUpdateResponse response = DingDingSDKUtils.updateChat(param);
            if (response.getErrcode().equals(0L)) {

                if (CollectionUtil.isNotEmpty(updateChatUserVO.getAddUserIds())) {
                    // 新增的用户添加到中间表
                    for (String addUserId : updateChatUserVO.getAddUserIds()) {
                        User userByDDUserId = userMapper.findUserByDDUserId(addUserId);
                        if (userByDDUserId != null) {
                            UserChat userChat = new UserChat();
                            userChat.setUserId(userByDDUserId.getUserId());
                            userChat.setChatId(updateChatUserVO.getChatId());
                            userChatMapper.insert(userChat);
                        }
                    }
                }

                if (CollectionUtil.isNotEmpty(updateChatUserVO.getDeleteUserIds())) {
                    // 删除的用户添加到中间表
                    for (String addUserId : updateChatUserVO.getDeleteUserIds()) {
                        User userByDDUserId = userMapper.findUserByDDUserId(addUserId);
                        if (userByDDUserId != null) {
                            userChatMapper.delete(Wrappers.<UserChat>lambdaQuery()
                                    .eq(UserChat::getChatId, updateChatUserVO.getChatId())
                                    .eq(UserChat::getUserId, userByDDUserId.getUserId()));
                        }
                    }
                }

                return result;
            } else {
                return DDErrResult.dataError(result, response.getErrmsg());
            }

        } catch (ApiException e) {
            e.printStackTrace();
            return DDErrResult.connException(result);
        }
    }

    /**
     * 钉钉根据 userId 获取全部的群聊
     *
     * @param userId
     * @return
     */
    @Override
    public Result findAllChat2DD(Integer userId, Integer type) {

        List<Map> allChat2DD = chatMapper.findAllChat2DD(userId, type);

        for (Map map : allChat2DD) {
            Object chat_id = map.get("chat_id");
            try {
                OapiChatGetResponse response = ChatSDKUtil.getChatInfo(chat_id.toString());
                if (response.getErrcode().equals(0L)) {
                    List<String> useridlist = response.getChatInfo().getUseridlist();
                    map.put("count", useridlist.size());
                }
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
        return new Result(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(), allChat2DD);
    }

    /**
     * 查询全部除网格群之外的群
     *
     * @param page
     * @param userId
     * @param roleId
     * @return
     */
    @Override
    public Result findAllChatInfo(Page page, Integer userId, Integer roleId) {
        if (roleId.equals(1) || roleId.equals(2)) {
            List<Chat> chatList = chatMapper.selectList(Wrappers.<Chat>lambdaQuery()
                    .eq(Chat::getFlag, 1)
                    .notIn(Chat::getType, 1));
            return new Result(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(), chatList);
        } else if (roleId.equals(3)) {
            //User user = userMapper.selectById(userId);

            // 得到用户所有的区级权限信息
            List<AreaPermissions> permissions = areaPermissionsMapper.selectList(Wrappers.<AreaPermissions>lambdaQuery()
                    .eq(AreaPermissions::getUserId, userId)
                    .eq(AreaPermissions::getAdminType, 3));

            List<Integer> areaIds = new ArrayList<>(10);
            if (CollectionUtil.isNotEmpty(permissions)) {
                permissions.forEach(p -> areaIds.add(p.getAreaId()));
            }

            log.info("areaIds 的值为：{}：", JSON.toJSONString(areaIds));
            LambdaQueryWrapper<Chat> wrapper = new QueryWrapper<Chat>().lambda();
            if (CollectionUtil.isNotEmpty(areaIds)) {
                wrapper.in(Chat::getAreaId, areaIds);
            }
            wrapper.eq(Chat::getFlag, 1)
                    .ne(Chat::getType, 1);

            List<Chat> chatList = chatMapper.selectList(wrapper);
            return new Result(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(), chatList);
        } else {
            User user = userMapper.selectById(userId);
            LambdaQueryWrapper<Chat> wrapper = new QueryWrapper<Chat>().lambda();
            wrapper.eq(Chat::getFlag, 1)
                    .eq(Chat::getOwner, user.getDdUserId())
                    .ne(Chat::getType, 1);
            List<Chat> chatList = chatMapper.selectList(wrapper);
            return new Result(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(), chatList);
        }
    }

    /**
     * 设置群管理员
     *
     * @param
     * @return
     */
    /*@Override
    @Transactional(rollbackFor = Exception.class)
    public Result setChatAdmin(SetChatAdminVO setChatAdminVO) {

        Result result = new Result(MessageInfo.UPDATE_INFO.getInfo());

        SubAdminParam param = new SubAdminParam();
        BeanUtils.copyProperties(setChatAdminVO, param);
        try {
            OapiChatSubadminUpdateResponse response = ChatSDKUtil.subAdmin(param);
            if (response.getErrcode().equals(0L)) {
                chatMapper.updateById(new Chat()
                        .setChatId(setChatAdminVO.getChatId())
                        .setManagerUser(setChatAdminVO.getUserIds()));
                return result;
            } else {
                return DDErrResult.dataError(result, response.getErrmsg());
            }
        } catch (ApiException e) {
            e.printStackTrace();
            return DDErrResult.connException(result);
        }
    }*/
    @Override
    public Result findAllBigChat() {

        Result result = new Result(MessageInfo.GET_INFO.getInfo());
        return result.setData(chatMapper.selectList(Wrappers.<Chat>lambdaQuery()
                .eq(Chat::getType, 4).eq(Chat::getFlag, 1)));

    }

    /**
     * 解散群
     *
     * @param chatDisbandedVO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result chatDisbanded(ChatDisbandedVO chatDisbandedVO) {

        Result result = new Result(MessageInfo.DELETE_INFO.getInfo());

        String chatId = chatDisbandedVO.getChatId();
        PermissionData permissionData = chatDisbandedVO.getPermission();
        List<Integer> roleIds = permissionData.getRoleIds();

        User user = userMapper.selectById(chatDisbandedVO.getUserId());
        Chat chat = chatMapper.selectOne(Wrappers.<Chat>lambdaQuery().eq(Chat::getChatId, chatId));

        // 如果是网格管理员或者群管理员的话
        if (roleIds.contains(5) && !roleIds.contains(4) && !roleIds.contains(3) && !roleIds.contains(2) && !roleIds.contains(1)) {
            // 判断是否是群主
            if (!user.getDdUserId().equals(chat.getOwner())) {
                return result.setFlag(false).setCode(StatusCode.ACCESSREEOR).setMessage("群管理员没有权限解散该群");
            }
        }
        try {
            OapiChatGetResponse chatInfoResponse = ChatSDKUtil.getChatInfo(chatId);
            if (chatInfoResponse.getErrcode().equals(0L)) {
                OapiChatGetResponse.ChatInfo chatInfo = chatInfoResponse.getChatInfo();
                List<String> useridlist = chatInfo.getUseridlist();
                if (CollectionUtil.isNotEmpty(useridlist)) {

                    // 将数据进行分组，钉钉限制，每次最多只能移除 40 个
                    List<List<String>> userIdGropeList = ListUtil.fixedGrouping(useridlist, 40);
                    for (List<String> userIdGrope : userIdGropeList) {
                        if (CollectionUtil.isNotEmpty(userIdGrope)) {
                            // 调用钉钉移除接口
                            ChatUpdateParam param = new ChatUpdateParam();
                            param.setChatId(chatId);
                            param.setDelUserIdList(userIdGrope);
                            DingDingSDKUtils.updateChat(param);
                        }
                    }
                }

                // 将群修改为无效
                //chat.setFlag(0);
                //chatMapper.updateById(chat);
                chatMapper.delete(Wrappers.lambdaQuery(Chat.class)
                        .eq(Chat::getChatId, chat.getChatId()));

                // 将用户群聊中间表数据进行删除
                userChatMapper.delete(Wrappers.<UserChat>lambdaQuery()
                        .eq(UserChat::getChatId, chatId));

                Grid grid = gridMapper.selectOne(Wrappers.<Grid>lambdaQuery().eq(Grid::getChatId, chatId));
                // 不为空说明是网格群
                if (grid != null) {
                    Integer gridId = grid.getGridId();

                    // 将网格进行删除
                    gridMapper.deleteById(gridId);

                    // 删除网格管理员对应的权限
                    // 删除 adminType为5
                    List<AreaPermissions> areaPermissions = areaPermissionsMapper.selectList(Wrappers.<AreaPermissions>lambdaQuery()
                            .eq(AreaPermissions::getAdminType, 5)
                            .eq(AreaPermissions::getGridId, gridId));
                    List<Integer> userIds = new ArrayList<>(areaPermissions.size());
                    List<Integer> gridIds = new ArrayList<>(areaPermissions.size());
                    areaPermissions.forEach(ap -> userIds.add(ap.getUserId()));
                    areaPermissions.forEach(ap -> gridIds.add(ap.getGridId()));

                    // 将用户角色表中的数据进行删除
                    if (CollectionUtil.isNotEmpty(userIds)) {
                        userRoleMapper.delete(Wrappers.<UserRole>lambdaQuery()
                                .in(UserRole::getUserId, userIds).eq(UserRole::getRoleId, 5));
                    }

                    Integer streetId = grid.getStreetId();
                    PollChat pollChat = pollChatMapper.selectOne(Wrappers.<PollChat>lambdaQuery()
                            .eq(PollChat::getStreetId, streetId));
                    if (pollChat != null) {
                        // 将轮询表中的数据进行删除
                        // 如果轮询表中该街道的的总数小于等于1，删除该轮询表
                        if (pollChat.getGridCount() - 1 <= 0) {
                            pollChatMapper.deleteById(pollChat.getPollingId());
                        }
                    }

                    // 删除管理员回答轮询表
                    pollAdminMapper.delete(Wrappers.<PollAdmin>lambdaQuery()
                            .eq(PollAdmin::getChatId, chatId));
                }

                // 将群消息通知中间表数据进行删除
                noticeChatMapper.delete(Wrappers.<NoticeChat>lambdaQuery()
                        .eq(NoticeChat::getChatId, chatId));

            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            return result.setCode(StatusCode.ERROR).setFlag(false).setMessage("删除群失败，请再试一次");
        }
        return result;
    }

    @Override
    public Integer findAllChat() {
        Integer count = chatMapper.selectCount(Wrappers.<Chat>lambdaQuery().eq(Chat::getFlag, 1));
        return count;
    }

    @Deprecated
    public Result findChatList(PermissionData permissionData) {

        Result result = new Result(MessageInfo.GET_INFO.getInfo());
        List<Map<String, Object>> list = new ArrayList<>(1);
        Map<String, Object> map = new LinkedHashMap<>(10);
        map.put("cityId", 0);
        map.put("id", 0);
        map.put("name", "青岛市");

        // 如果为市管理员或 super,将全部列表展示
        if (permissionData.getRoleIds().contains(1) || permissionData.getRoleIds().contains(2)) {
            List<AreaPO> allArea = areaMapper.findAllArea(null);
            map.put("children", allArea);
            list.add(map);
            return result.setData(list);
        }

        List<Integer> areaIds = permissionData.getAreaIds();
        List<Integer> streetIds = permissionData.getStreetIds();
        List<Integer> grids = permissionData.getGrids();
        /*if (CollectionUtil.isNotEmpty(areaIds)) {
            areaMapper.selectList(Wrappers.<Area>lambdaQuery().eq());
        }*/

        return null;
    }

    public void findArea(PermissionData permissionData) {
        // 最终存放的数据
        List<AreaPO> allAreaByAreaIds = new ArrayList<>(10);
        // 如果区管理员不为空
        if (CollectionUtil.isNotEmpty(permissionData.getAreaIds())) {
            // 区管理员
            List<AreaPermissions> areaPermissions = this.findAreaPermissions(3, permissionData.getAreaIds(), permissionData.getUserId());

            List<Integer> areaIds = new ArrayList<>(10);
            areaPermissions.forEach(ap -> areaIds.add(ap.getAreaId()));

            if (CollectionUtil.isNotEmpty(areaIds)) {
                allAreaByAreaIds = areaMapper.findAllAreaByAreaIds(areaIds);
            }
        }

        // 如果街道不为空
        if (CollectionUtil.isNotEmpty(permissionData.getStreetIds())) {

            Set<Integer> areaList = new HashSet<>(10);
            List<Integer> streetIds = permissionData.getStreetIds();
            List<StreetPO> streets = streetMapper.findStreetsByStreetIds(new ArrayList<>(streetIds));

            log.info("streets:{}", JSON.toJSONString(streets));

            streets.forEach(streetPO -> areaList.add(streetPO.getAreaId()));

            Map<String, Object> areaMap = new LinkedHashMap<>(16);
            for (Integer areaId : areaList) {
                Area area = areaMapper.selectById(areaId);
                if (area != null) {
                    AreaPO areaPO = new AreaPO();
                    areaPO.setAreaId(areaId);
                    areaPO.setId(areaId);
                    areaPO.setName(area.getName());
                    areaMap.put(areaId.toString(), areaPO);
                }
            }
            log.info("areaMap:{}", JSON.toJSONString(areaMap));

            for (StreetPO streetPO : streets) {
                //
                AreaPO o = (AreaPO) areaMap.get(streetPO.getAreaId().toString());
                log.info("o:{}", JSON.toJSONString(o));

                if (o != null) {
                    List<StreetPO> children = o.getChildren();
                    if (children == null) {
                        List<StreetPO> objects = new ArrayList<>(1);
                        objects.add(streetPO);
                        o.setChildren(objects);
                    } else {
                        children.add(streetPO);
                        o.setChildren(children);
                    }

                }
            }

            if (CollectionUtil.isNotEmpty(allAreaByAreaIds)) {

                // 反查出来的所有区的名称集合
                List<String> areaNameList = new ArrayList<>(10);
                for (AreaPO allAreaByAreaId : allAreaByAreaIds) {
                    areaNameList.add(allAreaByAreaId.getName());
                }

                for (Map.Entry<String, Object> entry : areaMap.entrySet()) {
                    AreaPO areaPO = (AreaPO) entry.getValue();
                    // 如果市管理员之前查找到对应的市，跳过
                    if (!areaNameList.contains(areaPO.getName())) {
                        allAreaByAreaIds.add((AreaPO) entry.getValue());
                    }
                }
            } else {
                for (Map.Entry<String, Object> entry : areaMap.entrySet()) {
                    //allAreaByAreaIds = new ArrayList<>(10);
                    allAreaByAreaIds.add((AreaPO) entry.getValue());
                }
            }

            log.info("allAreaByAreaIds:{}", JSON.toJSONString(allAreaByAreaIds));
        }

        if (CollectionUtil.isNotEmpty(permissionData.getGrids())) {
            List<Integer> gridList = permissionData.getGrids();
            List<AreaPermissions> areaPermissions = this.findAreaPermissionsByGridIds(5, gridList, 125);

        /*Set<AreaPO> areaPOS = new LinkedHashSet<>(10);
        Set<StreetPO> streetPOS = new LinkedHashSet<>(10);
        Set<GridPO> gridPOS = new LinkedHashSet<>(10);*/
            Set<AreaPO> areaPOS = new LinkedHashSet<>(10);
            for (AreaPermissions areaPermission : areaPermissions) {
                Integer areaId = areaPermission.getAreaId();
                Integer streetId = areaPermission.getStreetId();
                Integer gridId = areaPermission.getGridId();

                Area area = areaMapper.selectById(areaId);
                Street street = streetMapper.selectById(streetId);
                Grid grid = gridMapper.selectById(gridId);

                List<GridPO> gridPOList = new ArrayList<>(1);
                GridPO gridPO = new GridPO();
                gridPO.setId(gridId);
                gridPO.setGridId(gridId);
                gridPO.setName(grid.getGridName());
                gridPO.setChatId(grid.getChatId());
                gridPO.setStreetId(streetId);
                gridPOList.add(gridPO);

                List<StreetPO> streetPOList = new ArrayList<>(1);
                StreetPO streetPO = new StreetPO();
                streetPO.setId(streetId);
                streetPO.setStreetId(streetId);
                streetPO.setName(street.getStreetName());
                streetPO.setAreaId(areaId);
                streetPO.setChildren(gridPOList);
                streetPOList.add(streetPO);

                AreaPO areaPO = new AreaPO();
                areaPO.setAreaId(areaId);
                areaPO.setId(areaId);
                areaPO.setName(area.getName());
                areaPO.setChildren(streetPOList);

                areaPOS.add(areaPO);
            }
            for (AreaPO areaPO : areaPOS) {
                List<AreaPO> areaPOS1 = this.areaExistMerge(allAreaByAreaIds, areaPO);
                allAreaByAreaIds = areaPOS1;
            }

        }
        Map<String, Object> map = new LinkedHashMap<>(10);
        map.put("cityId", 0);
        map.put("id", 0);
        map.put("name", "青岛市");
        map.put("children", allAreaByAreaIds);
        System.out.println(JSON.toJSONString(map));
        log.info(JSON.toJSONString(map));
    }

    /**
     * 判断当前传入的对象是否已经存在，如果不存在，将对象添加到集合中
     * <p>
     * 判断各个层级，如果存在，继续进入 子层 进行判断，子层没有就添加
     * <p>
     * 如：
     * 1 下 有 A， A 下有 B
     * 2 下有 A， A 下有 C
     * 将 1 和 2 带入方法 areaIsExist(1,2)
     * 得到的值为：
     * <p>
     * 1下有A，A下有 B 和 C
     *
     * @param areaPOS 区域集合
     * @param areaPO  当前传入的集合
     * @return
     */
    public List<AreaPO> areaExistMerge(List<AreaPO> areaPOS, AreaPO areaPO) {
        Integer currAreaId = areaPO.getAreaId();

        // 当前区域中的街道id集合
        List<StreetPO> streetPOS = areaPO.getChildren();
        List<Integer> currStreetIdS = new ArrayList<>(10);

        List<StreetPO> streetPOList = areaPO.getChildren();
        streetPOList.forEach(streetPO -> currStreetIdS.add(streetPO.getStreetId()));

        boolean flag = false;
        // 存放整体street数据
        List<Integer> streetIdList = new ArrayList<>(10);
        // 存放整体 GridId 数据
        List<Integer> gridIdList = new ArrayList<>(10);
        for (AreaPO area : areaPOS) {
            if (currAreaId.equals(area.getId())) {
                flag = true;
            }
            List<StreetPO> children = area.getChildren();
            // 将全部的街道id进行统计
            for (StreetPO child : children) {
                streetIdList.add(child.getId());
                // 将全部的网格id进行统计
                for (GridPO gridPO : child.getChildren()) {
                    gridIdList.add(gridPO.getGridId());
                }
            }
        }
        // 如果 flag 为 false，将此 areaPO 添加到集合中
        if (!flag) {
            areaPOS.add(areaPO);
        } else {
            // 说明区域集合中已经存在这个区了
            for (AreaPO po : areaPOS) {

                boolean streetFlag = false;

                if (streetIdList.contains(currStreetIdS.get(0))) {
                    streetFlag = true;
                }

                if (!streetFlag && po.getAreaId().equals(areaPO.getAreaId())) {
                    List<StreetPO> children = po.getChildren();
                    children.add(streetPOS.get(0));
                    po.setChildren(children);
                    continue;
                }

                // 这个 streetPOS 是要加入 area 中的
                for (StreetPO streetPO : po.getChildren()) {

                    // 得到 areaPO 中对应的街道
                    Integer streetId = streetPO.getStreetId();

                    // 包含的话说明已经存在,继续去判断网格
                    // 如果当前要添加的集合汇总存在
                    if (currStreetIdS.contains(streetPO.getStreetId())) {

                        // 这个 s 是对应的要添加街道中的 街道
                        StreetPO s = null;
                        for (StreetPO streetPO1 : streetPOS) {
                            if (streetPO1.getStreetId().equals(streetId)) {
                                s = streetPO1;
                            }
                        }
                        // 当前匹配到的街道下的所有网格
                        //List<GridPO> currStreetChildren = ;
                        if (s != null) {
                            for (GridPO gridPO : s.getChildren()) {
                                // 如果不包含，将此网格添加到街道中
                                // gridIdList
                                if (!gridIdList.contains(gridPO.getGridId())) {
                                    //
                                    List<GridPO> children = streetPO.getChildren();
                                    children.add(gridPO);
                                    streetPO.setChildren(children);
                                }
                            }
                        }
                    }
                }
            }
        }

        return areaPOS;

    }

    public String findAllAreaPermission(Integer userId) {
        // 存放区管理员
        List<Integer> areaIds = new ArrayList<>(10);
        List<Integer> streetIds = new ArrayList<>(10);
        List<Integer> grids = new ArrayList<>(10);
        // 查询对应角色
        List<UserRole> userRoles = userRoleMapper.selectList(Wrappers.<UserRole>lambdaQuery()
                .eq(UserRole::getUserId, userId)
                .groupBy(UserRole::getRoleId));
        List<Integer> roleIds = new ArrayList<>(userRoles.size());
        userRoles.forEach(userRole -> roleIds.add(userRole.getRoleId()));

        List<AreaPermissions> areaPermissions = areaPermissionsMapper.selectList(Wrappers.<AreaPermissions>lambdaQuery()
                .in(AreaPermissions::getAdminType, roleIds)
                .eq(AreaPermissions::getUserId, userId));

        for (AreaPermissions areaPermission : areaPermissions) {
            // 说明为区管理员
            if (areaPermission.getAreaId() != null && areaPermission.getStreetId() == null
                    && areaPermission.getGridId() == null) {
                areaIds.add(areaPermission.getAreaId());
            }

            // 说明为街道管理员
            if (areaPermission.getAreaId() != null && areaPermission.getStreetId() != null
                    && areaPermission.getGridId() == null) {
                streetIds.add(areaPermission.getStreetId());
            }

            // 说明为网格管理员
            if (areaPermission.getAreaId() != null && areaPermission.getStreetId() != null && areaPermission.getGridId() != null) {
                grids.add(areaPermission.getGridId());
            }
        }

        PermissionData permissionData = new PermissionData();
        permissionData.setUserId(userId);
        permissionData.setRoleIds(roleIds);
        permissionData.setAreaIds(areaIds);
        permissionData.setStreetIds(streetIds);
        permissionData.setGrids(grids);

        return JSON.toJSONString(permissionData);
    }

    /**
     * 根据 角色 id 和用户 id 查询对应权限
     *
     * @param roleId
     * @param userId
     * @return
     */
    private List<AreaPermissions> findAreaPermissions(Integer roleId, Integer userId) {
        // 如果为某个区的管理员
        return areaPermissionsMapper.selectList(Wrappers.<AreaPermissions>lambdaQuery()
                .eq(AreaPermissions::getAdminType, roleId)
                .eq(AreaPermissions::getUserId, userId));
    }

    private List<AreaPermissions> findAreaPermissions(Integer roleId, List<Integer> areaIds, Integer userId) {
        // 如果为某个区的管理员
        return areaPermissionsMapper.selectList(Wrappers.<AreaPermissions>lambdaQuery()
                .eq(AreaPermissions::getAdminType, roleId)
                .eq(AreaPermissions::getUserId, userId)
                .in(AreaPermissions::getAreaId, areaIds)
                .isNull(AreaPermissions::getStreetId)
                .isNull(AreaPermissions::getGridId));
    }

    private List<AreaPermissions> findAreaPermissionsByStreetIds(Integer roleId, List<Integer> streetIds, Integer userId) {
        // 如果为某个区的管理员
        return areaPermissionsMapper.selectList(Wrappers.<AreaPermissions>lambdaQuery()
                .eq(AreaPermissions::getAdminType, roleId)
                .eq(AreaPermissions::getUserId, userId)
                .in(AreaPermissions::getStreetId, streetIds)
                .isNotNull(AreaPermissions::getAreaId)
                .isNull(AreaPermissions::getGridId));
    }

    private List<AreaPermissions> findAreaPermissionsByGridIds(Integer roleId, List<Integer> GridIds, Integer userId) {
        // 如果为某个区的管理员
        return areaPermissionsMapper.selectList(Wrappers.<AreaPermissions>lambdaQuery()
                .eq(AreaPermissions::getAdminType, roleId)
                .eq(AreaPermissions::getUserId, userId)
                .in(AreaPermissions::getGridId, GridIds)
                .isNotNull(AreaPermissions::getAreaId)
                .isNotNull(AreaPermissions::getStreetId));
    }
}







