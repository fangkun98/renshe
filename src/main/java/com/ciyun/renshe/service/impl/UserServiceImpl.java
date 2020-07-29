package com.ciyun.renshe.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ciyun.renshe.common.*;
import com.ciyun.renshe.common.dingding.DDErrResult;
import com.ciyun.renshe.common.dingding.DingDingUtil;
import com.ciyun.renshe.common.dingding.login.LoginUtil;
import com.ciyun.renshe.common.dingding.message.DDSendMessageUtils;
import com.ciyun.renshe.common.dingding.message.MessageTypeConvert;
import com.ciyun.renshe.common.dingding.message.param.MessageBody;
import com.ciyun.renshe.common.dingding.message.type.Text;
import com.ciyun.renshe.common.dingding.sdk.ChatSDKUtil;
import com.ciyun.renshe.common.dingding.sdk.DingDingSDKUtils;
import com.ciyun.renshe.common.dingding.sdk.request.ChatUpdateParam;
import com.ciyun.renshe.common.dingding.sdk.request.MessageCorpParam;
import com.ciyun.renshe.common.dingding.sdk.request.SendChatMessageParam;
import com.ciyun.renshe.controller.vo.login.LoginVO;
import com.ciyun.renshe.controller.vo.permission.PermissionData;
import com.ciyun.renshe.controller.vo.user.*;
import com.ciyun.renshe.entity.*;
import com.ciyun.renshe.entity.po.AreaUserPO;
import com.ciyun.renshe.entity.po.GridInUserPO;
import com.ciyun.renshe.entity.po.StreetUserPO;
import com.ciyun.renshe.manager.amap.AMapResult;
import com.ciyun.renshe.manager.amap.AMapUtil;
import com.ciyun.renshe.manager.amap.Info;
import com.ciyun.renshe.manager.dingding.DingDingUser;
import com.ciyun.renshe.manager.renshe.UserVerification;
import com.ciyun.renshe.manager.renshe.dto.DsInfoDTO;
import com.ciyun.renshe.mapper.*;
import com.ciyun.renshe.service.UserService;
import com.ciyun.renshe.service.dto.excel.EnterpriseExcelDTO;
import com.ciyun.renshe.service.dto.excel.UserExcelDTO;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.*;
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

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final AreaMapper areaMapper;
    private final UserMapper userMapper;
    private final UserRoleMapper userRoleMapper;
    private final StreetMapper streetMapper;
    private final GridMapper gridMapper;
    private final ChatMapper chatMapper;
    private final UserChatMapper userChatMapper;
    private final AreaPermissionsMapper areaPermissionsMapper;
    private final SysRoleMapper sysRoleMapper;
    private final AdminMapper adminMapper;
    private final UserSwapMapper userSwapMapper;
    private final UserInfoSwapMapper userInfoSwapMapper;
    private final DingDingUser dingDingUser;
    private final DeptMapper deptMapper;
    private final UserVerification userVerification;

    /**
     * 根据名称和手机号查询用户
     *
     * @param name
     * @param phone
     * @return
     */
    @Override
    public PageInfo<User> searchUser(Page page, String name, String phone) {

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(name)) {
            wrapper.like("company_name", name);
        }
        if (StringUtils.isNotBlank(phone)) {
            wrapper.eq("mobile", phone);
        }
        wrapper.eq("flag", 1);

        PageInfo<User> info = PageHelper.startPage(Integer.parseInt(page.getPageNum().toString()),
                Integer.parseInt(page.getPageSize().toString()))
                .doSelectPageInfo(() -> userMapper.selectList(wrapper));

        return info;
    }

    /**
     * 获取部门下的用户详情
     *
     * @param page
     * @param departmentId
     * @return
     */
    @Override
    public Result getUserDetailByDeptId(Page page, String departmentId) {
        Result<Map<String, Object>> result = new Result<>(MessageInfo.GET_INFO.getInfo());

        // 请求钉钉 获取部门用户详情 接口

        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/listbypage");
        OapiUserListbypageRequest request = new OapiUserListbypageRequest();

        // 设置部门id
        if (StringUtils.isNotBlank(departmentId)) {
            request.setDepartmentId(Long.parseLong(departmentId));
        } else {
            request.setDepartmentId(1L);
        }

        request.setOffset(page.getPageNum() - 1L);
        request.setSize(Long.parseLong(page.getPageSize().toString()));
        // 按添加时间降序排序
        request.setOrder("entry_desc");
        request.setHttpMethod("GET");
        try {
            OapiUserListbypageResponse response = client.execute(request, DingDingUtil.ACCESS_TOKEN);
            if (response.getErrcode().equals(0L)) {
                Map<String, Object> map = new HashMap<>(16);
                // 下一页是否还有数值，true 为还有
                map.put("hasMore", response.getHasMore());
                // 成员信息列表
                map.put("userlist", response.getUserlist());

                return result.setData(map);
            } else {
                return DDErrResult.dataError(result, response.getErrmsg());
            }
        } catch (ApiException e) {
            e.printStackTrace();
            return DDErrResult.connException(result);
        }

    }

    /**
     * 添加成员
     *
     * @param userVO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result createUser(CreateDDUserVO userVO) {

        Result result = new Result(MessageInfo.ADD_INFO.getInfo());

        // 请求钉钉接口
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/create");
        OapiUserCreateRequest request = new OapiUserCreateRequest();
        request.setMobile(userVO.getMobile());
        request.setName(userVO.getName());

        // 将所属部门集合添加
        List<Long> departments = new ArrayList<>(userVO.getDepartment().size());
        departments.addAll(userVO.getDepartment());
        request.setDepartment(JSON.toJSONString(departments));

        try {
            OapiUserCreateResponse response = client.execute(request, DingDingUtil.ACCESS_TOKEN);
            if (response.getErrcode().equals(0L)) {
                // 将用户信息进行更新
                User user = new User();
                BeanUtils.copyProperties(userVO, user);

                //user.setDdUserId(response.getUserid())
                //.setDeptIds(JSONObject.toJSONString(userVO.getDepartment()));
                userMapper.updateById(user);
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
     * 添加成员信息,向数据库添加成员
     *
     * @param userVO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result saveUser(CreateUserVO userVO) {
        Result result = new Result(MessageInfo.ADD_INFO.getInfo());
        User user = new User();
        BeanUtils.copyProperties(userVO, user);
        user.setFlag(1);
        user.setCreateTime(new Date());
        // 请求成功 钉钉接口后 将信息保存到数据库中
        userMapper.insert(user);
        return result;
    }

    /**
     * 修改成员信息
     *
     * @param userVOS
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result updateUser(List<UpdateUserVO> userVOS) {
        Result result = new Result(MessageInfo.UPDATE_INFO.getInfo());

        if (userVOS.isEmpty()) {
            return result.setCode(StatusCode.ERROR).setFlag(false).setMessage("JSON 数据错误");
        }

        for (UpdateUserVO userVO : userVOS) {
            // 请求 钉钉接口
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/update");
            OapiUserUpdateRequest request = new OapiUserUpdateRequest();
            request.setUserid(userVO.getDdUserId());
            // 如果名称不为空
            if (StringUtils.isNotBlank(userVO.getName())) {
                request.setName(userVO.getName());
            }
            // 如果 部门 id 不为空
            if (!userVO.getDepartment().isEmpty()) {
                /*List<Long> departments = new ArrayList<>(userVO.getDepartment().size());
                departments.addAll(userVO.getDepartment());*/
                request.setDepartment(userVO.getDepartment());
            }
            // 如果手机号不为空
            if (StringUtils.isNotBlank(userVO.getMobile())) {
                request.setMobile(userVO.getMobile());
            }

            try {
                OapiUserUpdateResponse response = client.execute(request, DingDingUtil.ACCESS_TOKEN);
                if (response.getErrcode().equals(0L)) {
                    User user = new User();
                    BeanUtils.copyProperties(userVO, user);
                    user.setFlag(1);
                    // 将 部门 ids JSON 化
                    if (!userVO.getDepartment().isEmpty()) {
                        //user.setDeptIds(JSONObject.toJSONString(userVO.getDepartment()));
                    }
                    // 将相应的数值进行修改
                    userMapper.update(user, Wrappers.<User>lambdaQuery()
                                    .eq(User::getDdUserId, userVO.getDdUserId()));
                } else {
                    log.info("更新用户数据错误，原因：{}", response.getErrcode());
                    return DDErrResult.dataError(result, response.getErrmsg());

                }
            } catch (ApiException e) {
                e.printStackTrace();
                return DDErrResult.connException(result);
            }
        }
        return result;
    }

    /**
     * 删除成员信息
     *
     * @param ddUserId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result deleteUser(String ddUserId) {

        Result result = new Result(MessageInfo.DELETE_INFO.getInfo());

        // 请求钉钉接口
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/delete");
        OapiUserDeleteRequest request = new OapiUserDeleteRequest();
        request.setUserid(ddUserId);
        request.setHttpMethod("GET");

        try {
            OapiUserDeleteResponse response = client.execute(request, DingDingUtil.ACCESS_TOKEN);
            if (response.getErrcode().equals(0L)) {
                User user = new User();
                user.setFlag(0);
                userMapper.update(user, Wrappers.<User>lambdaQuery()
                        .eq(User::getDdUserId, ddUserId));
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
     * 获取管理员列表
     *
     * @return
     */
    @Override
    public Result getAdminList() {

        Result<List<OapiUserGetAdminResponse.AdminList>> result = new Result<>(MessageInfo.GET_INFO.getInfo());

        try {
            OapiUserGetAdminResponse response = DingDingSDKUtils.getUserAdminList();
            if (response.getErrcode().equals(0L)) {
                return result.setData(response.getAdminList());
            } else {
                return DDErrResult.dataError(result, response.getErrmsg());
            }
        } catch (ApiException e) {
            e.printStackTrace();
            return DDErrResult.connException(result);
        }
    }

    /**
     * 钉钉后台免登录
     *
     * @param loginVO
     * @return
     */
    @Override
    public Result doLogin(LoginVO loginVO) {
        Result<Object> result = new Result<>();
        try {
            OapiUserGetuserinfoResponse response = LoginUtil.loginUser(loginVO.getCode());
            log.info("请求后台登录：返回值：{}", JSON.toJSONString(response));
            if (response.getErrcode().equals(0L)) {

                User user = userMapper.findUserByDDUserId(response.getUserid());

                if (user != null && user.getIsAdmin() != 0) {

                    List<Admin> admins = adminMapper.selectList(Wrappers.<Admin>lambdaQuery()
                            .eq(Admin::getMobile, user.getMobile())
                            .eq(Admin::getFlag, 1));
                    if (CollectionUtil.isEmpty(admins)) {
                        return new Result(false, StatusCode.ERROR, "暂无权限登录此系统");
                    }

                    Map<String, Object> map = new HashMap<>(16);
                    ReturnUserVO userVO = new ReturnUserVO();
                    BeanUtils.copyProperties(user, userVO);

                    // 查询对应角色
                    List<UserRole> userRoles = userRoleMapper.selectList(Wrappers.<UserRole>lambdaQuery()
                            .eq(UserRole::getUserId, user.getUserId())
                            .groupBy(UserRole::getRoleId));
                    if (userRoles.isEmpty()) {
                        // 6 为普通用户
                        userVO.setRole(6);
                        userVO.setPermission("[]");
                    } else {
                        List<Integer> roleIds = new ArrayList<>(userRoles.size());
                        userRoles.forEach(userRole -> roleIds.add(userRole.getRoleId()));
                        // 拿到最大的权限
                        userVO.setRole(Collections.min(roleIds));
                        SysRole sysRole = sysRoleMapper.selectById(userVO.getRole());

                        userVO.setPermission(sysRole.getPermission());

                        // 存放区管理员
                        List<Integer> areaIds = new ArrayList<>(10);
                        List<Integer> streetIds = new ArrayList<>(10);
                        List<Integer> grids = new ArrayList<>(10);

                        List<AreaPermissions> areaPermissions = areaPermissionsMapper.selectList(Wrappers.<AreaPermissions>lambdaQuery()
                                .in(AreaPermissions::getAdminType, roleIds)
                                .eq(AreaPermissions::getUserId, user.getUserId()));

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
                        permissionData.setUserId(user.getUserId());
                        permissionData.setRoleIds(roleIds);
                        permissionData.setAreaIds(areaIds);
                        permissionData.setStreetIds(streetIds);
                        permissionData.setGrids(grids);

                        userVO.setPermissionData(permissionData);
                    }

                    map.put("userInfo", userVO);
                    return result.setData(map);
                } else {
                    return result.setMessage("抱歉，你不能登陆此系统").setFlag(false).setCode(StatusCode.ERROR);
                }

            } else {
                return DDErrResult.dataError(result, response.getErrmsg());
            }
        } catch (ApiException e) {
            e.printStackTrace();
            log.warn("请求钉钉后台免登录接口错误，输入参数：{}", JSON.toJSONString(loginVO));
            return DDErrResult.connException(result);
        }
    }

    /**
     * 钉钉小程序免登录
     *
     * @param loginVO
     * @return
     */
    @Override
    public Result ddLogin(LoginVO loginVO) {

        Result result = new Result(MessageInfo.LOGIN_SUCCESS.getInfo());
        try {
            OapiUserGetuserinfoResponse response = LoginUtil.loginUser(loginVO.getCode());
            log.info("请求钉钉免登录接口，返回值为：{}", JSON.toJSONString(response));
            if (response.getErrcode().equals(0L)) {
                String userid = response.getUserid();
                User userByDDUserId = userMapper.findUserByDDUserId(userid);
                if (userByDDUserId == null) {
                    return result.setFlag(false).setCode(StatusCode.ERROR).setMessage("您还未注册小程序，请先注册小程序");
                } else {
                    // 查询对应角色
                    List<UserRole> userRoles = userRoleMapper.selectList(Wrappers.<UserRole>lambdaQuery()
                            .eq(UserRole::getUserId, userid));
                    if (userRoles.isEmpty()) {
                        // 6 为普通用户
                        userByDDUserId.setRoleId(6);
                    } else {
                        List<Integer> list = new ArrayList<>(userRoles.size());
                        userRoles.forEach(userRole -> list.add(userRole.getRoleId()));
                        // 拿到最大的权限
                        Integer min = Collections.min(list);
                        userByDDUserId.setRoleId(min);
                    }
                    List<UserChat> userChats = userChatMapper.selectList(Wrappers.<UserChat>lambdaQuery()
                            .eq(UserChat::getStreetId, userByDDUserId.getStreetId())
                            .eq(UserChat::getUserId, userByDDUserId.getUserId()));
                    if (CollectionUtil.isNotEmpty(userChats)) {
                        UserChat userChat = userChats.get(0);
                        Chat chat = chatMapper.selectOne(Wrappers.<Chat>lambdaQuery().eq(Chat::getChatId, userChat.getChatId()));
                        userByDDUserId.setChatInfo(chat);
                    }
                    return result.setData(userByDDUserId);
                }
            } else {
                //return DDErrResult.dataError(result, response.getErrmsg());
                return new Result(false, StatusCode.ERROR, response.getErrmsg());
            }
        } catch (ApiException e) {
            e.printStackTrace();
            return DDErrResult.connException(result);
        }
    }

    /**
     * 将用户保存到钉钉小程序中
     *
     * @param saveUser
     * @return  
     */
    @Override
    public Result saveUserToDD(SaveUserToDDVO saveUser) {

        Result result = new Result(MessageInfo.ADD_INFO.getInfo());

        // 调用钉钉免登录接口获取 ddUserId
        OapiUserGetuserinfoResponse oapiUserGetuserinfoResponse = null;
        try {
            oapiUserGetuserinfoResponse = LoginUtil.loginUser(saveUser.getCode());
            // 说明调用成功
            if (!oapiUserGetuserinfoResponse.getErrcode().equals(0L)) {
                return DDErrResult.dataError(result, oapiUserGetuserinfoResponse.getErrmsg());
            }
        } catch (ApiException e) {
            e.printStackTrace();
            return DDErrResult.connException(result);
        }
        String ddUserId = oapiUserGetuserinfoResponse.getUserid();

        // 请求单位编号密码校验
        String password = saveUser.getPassword();
        String accountNum = saveUser.getUnitNo();
        // 调用人社接口查询是否正确
        DsInfoDTO dsInfoDTO = userVerification.userVerification(accountNum, password);

        User user = new User();
        // 使用userId调用获取用户详情接口
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get");
        OapiUserGetRequest request = new OapiUserGetRequest();
        request.setUserid(ddUserId);
        request.setHttpMethod("GET");
        OapiUserGetResponse response = null;
        try {
            response = client.execute(request, DingDingUtil.ACCESS_TOKEN);
            if (!response.getErrcode().equals(0L)) {
                return DDErrResult.dataError(result, response.getErrmsg());
            }
        } catch (ApiException e) {
            e.printStackTrace();
            return DDErrResult.connException(result);
        }

        user.setMobile(response.getMobile());
        user.setDdUserId(response.getUserid());

        String companyName = dsInfoDTO.getCompanyName();
        user.setCompanyName(companyName == null ? "" : companyName);

        // 公司地址
        String address = dsInfoDTO.getAddress();
        user.setAddress(address == null ? "" : address);

        // 单位编码
        String unitNo = dsInfoDTO.getUnitNo();
        user.setUnitNo(unitNo == null ? "" : unitNo);

        user.setName(response.getName());
        user.setFlag(1);
        String idCard = dsInfoDTO.getIdCard();
        user.setIdCard(idCard == null ? "" : idCard);

        // 获取区id
        String areaId = dsInfoDTO.getAreaId();
        if (StringUtils.isNotBlank(areaId)) {
            Area area = areaMapper.selectById(areaId);
            if (area != null) {
                user.setAreaId(Integer.parseInt(areaId));
                user.setArea(area.getName());

                List<Street> streets = streetMapper.selectList(Wrappers.<Street>lambdaQuery()
                        .eq(Street::getAreaId, areaId));
                user.setStreetId(streets.get(0).getStreetId());
                user.setStreet(streets.get(0).getStreetName());
            }
        }

        // 获取到用户名称和手机号
        user.setIsAdmin(0);
        user.setIsConfirm(0);
        user.setCreateTime(new Date());
        user.setPassword(saveUser.getPassword());
        userMapper.insert(user);

        return result.setData(user);
    }

    /**
     * 人社账号校验
     *
     * @param accountNum 社会统一信用代码
     * @param password   密码
     */
    private void rensheAccountVerification(String accountNum, String password) {

    }

    /**
     * 根据用户ID 修改用户信息，并将用户自动分配到对应的 网格群下
     * <p>
     * 1. 先将用户信息进行修改
     * 2. 将用户分配到对应的街道下，轮询到一个网格群中
     * 3. 给对应的群的群主发送通知消息
     *
     * @param updateUser2DDVO
     * @return
     * @update 2020年7月7日15:40:21
     * 1.  在将用户分配到网格下前需要做以下工作
     * 1.1 将用户修改到对应的群绑定的部门下
     * 1.2 将用户从主部门删除
     * @Version 1.0.1
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result updateDingDingUser(UpdateUser2DDVO updateUser2DDVO) {

        Result<Object> result = new Result<>(MessageInfo.UPDATE_INFO.getInfo());

        if (updateUser2DDVO.getStreetId() == null) {
            result.setFlag(false).setCode(StatusCode.ERROR).setMessage("请选择街道");
        }

        // 1. 先将用户信息进行修改
        User user = new User();
        BeanUtils.copyProperties(updateUser2DDVO, user);
        user.setIsConfirm(1);

        if (CollectionUtil.isNotEmpty(updateUser2DDVO.getIndustryId())) {
            List<String> list = new ArrayList<>(10);
            for (Map<String, Object> map : updateUser2DDVO.getIndustryId()) {
                list.add(map.get("id") + "");
            }
            String join = String.join(",", list);
            user.setIndustryId(join + ",");
        }
        log.info("userInfo:{}", JSONObject.toJSONString(user));

        if (StringUtils.isNotBlank(updateUser2DDVO.getAddress())) {

            // 调用高德地图获取地址
            String address = AMapUtil.getXY(updateUser2DDVO.getAddress());
            log.info("address:{}", address);

            AMapResult aMapResult = JSONObject.parseObject(address, AMapResult.class);
            if (aMapResult.getStatus().equals("1")) {
                if (CollectionUtil.isNotEmpty(aMapResult.getGeocodes())) {
                    List<Info> geocodes = aMapResult.getGeocodes();
                    Info info = geocodes.get(0);
                    String[] split = info.getLocation().split(",");
                    user.setPositionX(split[1]);
                    user.setPositionY(split[0]);
                }
            }
        } else {
            return result.setData("请输入企业所在地址").setCode(StatusCode.ERROR).setFlag(false);
        }

        User selectById = userMapper.selectById(updateUser2DDVO.getUserId());

        if (selectById == null) {
            return result.setMessage("错误！未查询到此用户！");
        }

        // 如果用户自己指定了街道id，那就进入指定的群，没有选择就轮询进入一个群
        if (StringUtils.isNotBlank(updateUser2DDVO.getSelectChatId())) {
            String selectChatId = updateUser2DDVO.getSelectChatId();
            // 将群成员进行更新
            Chat chat = chatMapper.selectById(selectChatId);

            // 查询群对应的部门id
            if (chat.getDeptId() != null) {
                Dept dept = deptMapper.selectById(chat.getDeptId());
                String ddUserId = selectById.getDdUserId();
                Long ddDeptId = dept.getDdDeptId();
                // 移动部门
                moveUserDept(ddUserId, ddDeptId);
            }

            ChatUpdateParam param = new ChatUpdateParam();
            List<String> list = new ArrayList<>(1);
            list.add(selectById.getDdUserId());
            param.setAddUserIdList(list);
            param.setChatId(selectChatId);
            try {
                OapiChatUpdateResponse response = DingDingSDKUtils.updateChat(param);
                if (response.getErrcode().equals(0L)) {

                    OapiChatSendRequest.Msg msg = new OapiChatSendRequest.Msg();
                    msg.setMsgtype("text");
                    OapiChatSendRequest.Text text = new OapiChatSendRequest.Text();
                    text.setContent("欢迎" + selectById.getName() + "加入群聊");
                    msg.setText(text);

                    // 给对应的群发送欢迎消息
                    DDSendMessageUtils.sendChatMessage(new SendChatMessageParam()
                            .setMsg(msg).setChatId(selectChatId));

                    // 将群 id 添加到对应的群
                    UserChat userChat = new UserChat();
                    userChat.setChatId(selectChatId)
                            .setUserId(updateUser2DDVO.getUserId())
                            .setStreetId(updateUser2DDVO.getStreetId());
                    userChatMapper.insert(userChat);

                    // 给对应的群主发送通知消息
                    // 将用户成功添加之后，通知 群主发送成员添加消息

                    // 给群主发送消息
                    MessageCorpParam messageCorpParam = new MessageCorpParam();
                    messageCorpParam.setUserIdList(chat.getOwner());
                    messageCorpParam.setToAllUser(false);
                    OapiMessageCorpconversationAsyncsendV2Request.Msg corpMsg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
                    corpMsg.setMsgtype("text");
                    corpMsg.setText(new OapiMessageCorpconversationAsyncsendV2Request.Text());
                    String contentText = "成员" + updateUser2DDVO.getName() + "首次加入钉钉小程序，已经自动添加到了" + chat.getName() + "群聊";
                    corpMsg.getText().setContent(contentText);
                    messageCorpParam.setMsg(corpMsg);
                    DDSendMessageUtils.messageCorpconversationAsync(messageCorpParam);

                    userMapper.updateById(user);
                    Chat chatInfo = chatMapper.selectOne(Wrappers.<Chat>lambdaQuery().eq(Chat::getChatId, selectChatId));
                    user.setChatInfo(chatInfo);

                    return result.setData(user);
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
        userMapper.updateById(user);
        return result.setData(user);
    }

    /**
     * 将用户移动到指定部门下
     *
     * @param ddUserId 钉钉userId
     * @param deptId   部门id
     */
    private void moveUserDept(String ddUserId, Long deptId) {
        List<Long> deptList = new ArrayList<>(1);
        deptList.add(deptId);
        try {
            dingDingUser.updateUserDept(ddUserId, deptList);
        } catch (ApiException e) {
            e.printStackTrace();
            log.info("将用户移动到指定部门失败");
            throw new RuntimeException("将用户移动到指定部门失败");
        }
    }

    /**
     * 已经成为钉钉小程序用户后，修改用户信息， 修改对应群，并通知双方群主
     *
     * @param userInfoVO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result updateUserInfo(UpdateUserInfoVO userInfoVO) {

        Result result = new Result(MessageInfo.UPDATE_INFO.getInfo());

        if (StringUtils.isNotBlank(userInfoVO.getReason())) {

            User user = userMapper.selectById(userInfoVO.getUserId());

            UserInfoSwap userInfoSwap = new UserInfoSwap();
            userInfoSwap.setCompanyName(user.getCompanyName());
            BeanUtils.copyProperties(userInfoVO, userInfoSwap);

            userInfoSwap.setIsPass(0);

            if (userInfoVO.getBeforeStreetId() != null) {
                List<UserChat> userChats = userChatMapper.selectList(Wrappers.<UserChat>lambdaQuery()
                        .eq(UserChat::getUserId, userInfoVO.getUserId())
                        .eq(UserChat::getStreetId, userInfoVO.getBeforeStreetId()));
                if (CollectionUtil.isNotEmpty(userChats)) {
                    UserChat userChat = userChats.get(0);
                    String chatId = userChat.getChatId();
                    Chat chat = chatMapper.selectOne(Wrappers.<Chat>lambdaQuery()
                            .select(Chat::getName).eq(Chat::getChatId, chatId));
                    // 当前群名称
                    userInfoSwap.setCurrentChatName(chat.getName());
                }
            }

            //userInfoSwapMapper.insert(userInfoSwap);

            userInfoSwap.setApplicationTime(new Date());
            userInfoSwapMapper.insert(userInfoSwap);

            if (StringUtils.isNotBlank(userInfoSwap.getSelectChatId())) {
                String chatId = userInfoSwap.getSelectChatId();
                Chat chat = chatMapper.selectOne(Wrappers.<Chat>lambdaQuery().eq(Chat::getChatId, chatId));

                // 申请加入群名称
                userInfoSwap.setChatName(chat.getName());
                userInfoSwapMapper.updateById(userInfoSwap);

                String managerUser = chat.getManagerUser();
                if (StringUtils.isNotBlank(managerUser)) {
                    List list = JSON.parseObject(managerUser, List.class);
                    if (CollectionUtil.isNotEmpty(list)) {
                        List<User> users = userMapper.selectList(Wrappers.<User>lambdaQuery().select(User::getUserId)
                                .in(User::getDdUserId, list));
                        for (User userInfo : users) {
                            UserSwap userSwap = new UserSwap();
                            userSwap.setUserId(userInfo.getUserId());
                            userSwap.setSwapId(userInfoSwap.getSwapId());
                            userSwapMapper.insert(userSwap);
                        }
                    }
                }
            }

            return result;
        } else {
            // 如果没有换区
            User user = userMapper.selectById(userInfoVO.getUserId());
            // 如果用户自己选择
            if (StringUtils.isNotBlank(userInfoVO.getSelectChatId()) &&
                    userInfoVO.getStreetId() != null) {

                Integer streetId = user.getStreetId();
                // 如果更改了街道或者街道为空
                if (streetId == null || !streetId.equals(userInfoVO.getStreetId())) {
                    String selectChatId = userInfoVO.getSelectChatId();

                    ChatUpdateParam param = new ChatUpdateParam();
                    List<String> list = new ArrayList<>(1);
                    list.add(userInfoVO.getDdUserId());
                    param.setAddUserIdList(list);
                    param.setChatId(selectChatId);
                    try {
                        OapiChatUpdateResponse response = DingDingSDKUtils.updateChat(param);
                        if (response.getErrcode().equals(0L)) {
                            // 将群成员进行更新
                            Chat chat = chatMapper.selectById(selectChatId);

                            OapiChatSendRequest.Msg msg = new OapiChatSendRequest.Msg();
                            msg.setMsgtype("text");
                            OapiChatSendRequest.Text text = new OapiChatSendRequest.Text();
                            text.setContent("欢迎" + userInfoVO.getName() + "加入群聊");
                            msg.setText(text);

                            // 给对应的群发送欢迎消息
                            DDSendMessageUtils.sendChatMessage(new SendChatMessageParam()
                                    .setMsg(msg).setChatId(selectChatId));

                            // 将群 id 添加到对应的群
                            UserChat userChat = new UserChat();
                            userChat.setChatId(selectChatId)
                                    .setUserId(user.getUserId())
                                    .setStreetId(userInfoVO.getStreetId());
                            userChatMapper.insert(userChat);

                            // 给对应的群主发送通知消息
                            // 将用户成功添加之后，通知 群主发送成员添加消息

                            // 给群主发送消息
                            MessageCorpParam messageCorpParam = new MessageCorpParam();
                            messageCorpParam.setUserIdList(chat.getOwner());
                            messageCorpParam.setToAllUser(false);
                            OapiMessageCorpconversationAsyncsendV2Request.Msg corpMsg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
                            corpMsg.setMsgtype("text");
                            corpMsg.setText(new OapiMessageCorpconversationAsyncsendV2Request.Text());
                            String contentText = "成员" + user.getName() + "已经自动添加到了" + chat.getName() + "群聊";
                            corpMsg.getText().setContent(contentText);
                            messageCorpParam.setMsg(corpMsg);
                            DDSendMessageUtils.messageCorpconversationAsync(messageCorpParam);

                            //userMapper.updateById(sysuser);

                            // 先得到修改之前的群信息，将用户从群中移除， 并通知群主
                            //    根据  streetId 和 userId 查询到对应的群id
                            List<UserChat> userChats = userChatMapper.selectList(Wrappers.<UserChat>lambdaQuery()
                                    .eq(UserChat::getStreetId, userInfoVO.getBeforeStreetId())
                                    .eq(UserChat::getUserId, userInfoVO.getUserId()));

                            log.info("userChats:{}", JSON.toJSONString(userChats));

                            if (!userChats.isEmpty()) {
                                // 将用户所在街道的群退出并通知群主
                                for (UserChat userChat1 : userChats) {
                                    //UserChat userChat = userChats.get(0);
                                    String beforeChatId = userChat1.getChatId();
                                    log.info("beforeChatId:{}", beforeChatId);

                                    try {
                                        // 将用户从之前群中移除
                                        List<String> list1 = new ArrayList<>(1);
                                        list1.add(userInfoVO.getDdUserId());
                                        OapiChatUpdateResponse chatUpdateResponse = DingDingSDKUtils.updateChat(new ChatUpdateParam()
                                                .setChatId(beforeChatId)
                                                .setDelUserIdList(list1));
                                        log.info("调用钉钉修改群信息接口，用户id为{}，返回内容为{}", userInfoVO.getUserId(), JSON.toJSONString(chatUpdateResponse));
                                        if (chatUpdateResponse.getErrcode().equals(0L)) {
                                            //
                                            Chat chat1 = chatMapper.selectById(beforeChatId);

                                            // 将用户成功移除之后，通知 群主发送 成员离开 消息

                                            // 给群主发送消息
                                            MessageCorpParam messageCorpParam1 = new MessageCorpParam();
                                            messageCorpParam1.setUserIdList(chat1.getOwner());
                                            messageCorpParam1.setToAllUser(false);
                                            OapiMessageCorpconversationAsyncsendV2Request.Msg msg1 = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
                                            msg1.setMsgtype("text");
                                            msg1.setText(new OapiMessageCorpconversationAsyncsendV2Request.Text());
                                            String contentText1 = "成员" + userInfoVO.getName() + "修改了个人信息，已经离开了" + chat1.getName() + "群聊";
                                            msg1.getText().setContent(contentText1);
                                            messageCorpParam1.setMsg(msg1);
                                            OapiMessageCorpconversationAsyncsendV2Response corpResponse1 = DDSendMessageUtils.messageCorpconversationAsync(messageCorpParam1);
                                            log.info("调用钉钉发送工作通知消息，用户id为：{}，返回内容为：{}", userInfoVO.getUserId(), JSON.toJSONString(corpResponse1));

                                            if (corpResponse1.getErrcode().equals(0L)) {
                                                // 删除 用户群组表中的相应记录
                                                userChatMapper.delete(Wrappers.<UserChat>lambdaQuery()
                                                        .eq(UserChat::getStreetId, userInfoVO.getBeforeStreetId())
                                                        .eq(UserChat::getChatId, beforeChatId)
                                                        .eq(UserChat::getUserId, userInfoVO.getUserId()));

                                            } else {
                                                log.info("发送工作通知消息失败，要接收的userId为{}，错误消息为{}", chat1.getOwner(), corpResponse1.getErrmsg());
                                            }
                                        } else {
                                            log.info("调用钉钉修改群信息失败，原因:{}", chatUpdateResponse.getErrmsg());
                                        }
                                    } catch (ApiException e) {
                                        e.printStackTrace();
                                    }
                                }

                            }
                        }
                    } catch (ApiException e) {
                        e.printStackTrace();
                    }
                }
            } else if (StringUtils.isNotBlank(userInfoVO.getSelectChatId()) && userInfoVO.getStreetId() == null) {

                // 如果选择了群但是没选择街道，说明只修改群聊

                // 需要将以前的用户群聊中间表数据进行删除
                User selectById = userMapper.selectById(userInfoVO.getUserId());

                //Integer streetId = user.getStreetId();

                String userInfoVOSelectChatId = userInfoVO.getSelectChatId();

                OapiChatGetResponse chatInfoResp = null;
                try {
                    chatInfoResp = ChatSDKUtil.getChatInfo(userInfoVOSelectChatId);
                } catch (ApiException e) {
                    e.printStackTrace();
                }
                OapiChatGetResponse.ChatInfo chatInfo1 = chatInfoResp.getChatInfo();
                List<String> useridlist = chatInfo1.getUseridlist();

                if (!useridlist.contains(selectById.getDdUserId())) {
                    List<UserChat> userChats = userChatMapper.selectList(Wrappers.<UserChat>lambdaQuery()
                            .eq(UserChat::getUserId, selectById.getUserId())
                            .eq(UserChat::getStreetId, selectById.getStreetId()));

                    if (CollectionUtil.isNotEmpty(userChats)) {
                        //UserChat userChat = userChats.get(0);
                        for (UserChat userChat : userChats) {
                            // 将用户从之前群中移除
                            ChatUpdateParam deleteUserParam = new ChatUpdateParam();
                            List<String> deleteUserList = new ArrayList<>(1);
                            deleteUserList.add(selectById.getDdUserId());
                            // 设置要删除的人员
                            deleteUserParam.setDelUserIdList(deleteUserList);
                            // 设置群id
                            deleteUserParam.setChatId(userChat.getChatId());
                            try {
                                DingDingSDKUtils.updateChat(deleteUserParam);
                                // 将数据进行删除
                                userChatMapper.delete(Wrappers.<UserChat>lambdaQuery()
                                        .eq(UserChat::getUserId, selectById.getUserId())
                                        .eq(UserChat::getStreetId, selectById.getStreetId()));
                            } catch (ApiException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    // 将新选择的群组进行添加
                    ChatUpdateParam chatUpdateParam = new ChatUpdateParam();
                    // 设置群id
                    chatUpdateParam.setChatId(userInfoVO.getSelectChatId());
                    List<String> stringArrayList = new ArrayList<>(1);
                    stringArrayList.add(selectById.getDdUserId());
                    // 设置添加人员
                    chatUpdateParam.setAddUserIdList(stringArrayList);
                    try {
                        OapiChatUpdateResponse chatUpdateResponse = DingDingSDKUtils.updateChat(chatUpdateParam);
                        if (chatUpdateResponse.getErrcode().equals(0L)) {
                            // 添加到人员群组中间表
                            UserChat uc = new UserChat();
                            uc.setStreetId(selectById.getStreetId());
                            uc.setUserId(selectById.getUserId());
                            uc.setChatId(userInfoVO.getSelectChatId());
                            userChatMapper.insert(uc);

                            // 成功添加后 给群发送欢迎新人消息
                            OapiChatSendRequest.Msg chatSendRequestMsg = new OapiChatSendRequest.Msg();
                            chatSendRequestMsg.setMsgtype("text");
                            OapiChatSendRequest.Text text = new OapiChatSendRequest.Text();
                            text.setContent("欢迎" + userInfoVO.getName() + "加入群聊");
                            chatSendRequestMsg.setText(text);

                            // 给对应的群发送欢迎消息
                            DDSendMessageUtils.sendChatMessage(new SendChatMessageParam()
                                    .setMsg(chatSendRequestMsg).setChatId(userInfoVO.getSelectChatId()));
                        }
                    } catch (ApiException e) {
                        e.printStackTrace();
                    }
                }
            }

            User sysuser = new User();
            BeanUtils.copyProperties(userInfoVO, sysuser);

            if (CollectionUtil.isNotEmpty(userInfoVO.getIndustryId())) {
                List<String> arrayList = new ArrayList<>(10);
                for (Map<String, Object> map : userInfoVO.getIndustryId()) {
                    arrayList.add(map.get("id") + "");
                }
                String join = String.join(",", arrayList);
                sysuser.setIndustryId(join + ",");
            }

            if (StringUtils.isNotBlank(sysuser.getAddress())) {
                String address = AMapUtil.getXY(sysuser.getAddress());
                log.info("address:{}", address);
                AMapResult aMapResult = JSONObject.parseObject(address, AMapResult.class);

                if (aMapResult.getStatus().equals("1")) {
                    if (CollectionUtil.isNotEmpty(aMapResult.getGeocodes())) {
                        List<Info> geocodes = aMapResult.getGeocodes();
                        Info info = geocodes.get(0);
                        String[] split = info.getLocation().split(",");
                        sysuser.setPositionX(split[1]);
                        sysuser.setPositionY(split[0]);
                    }
                }
            }
            userMapper.updateById(sysuser);
            User userInfo = userMapper.selectById(sysuser.getUserId());
            List<UserChat> userChats = userChatMapper.selectList(Wrappers.<UserChat>lambdaQuery()
                    .eq(UserChat::getUserId, userInfo.getUserId())
                    .eq(UserChat::getStreetId, userInfo.getStreetId()));
            if (CollectionUtil.isNotEmpty(userChats)) {
                UserChat userChat = userChats.get(0);
                Chat chat = chatMapper.selectOne(Wrappers.<Chat>lambdaQuery().eq(Chat::getChatId, userChat.getChatId()));
                userInfo.setChatInfo(chat);
            }
            return result.setData(userInfo);
        }

        //return result;
    }

    /**
     * 根据用户id查询用户详细信息
     *
     * @param userId
     * @return
     */
    @Override
    public User findDdUserByUserId(Integer userId) {
        return userMapper.selectById(userId);
    }

    /**
     * 根据行业 id 或者 规模id 查询对应用户
     *
     * @param esId
     * @param industryId
     * @return
     */
    @Override
    public PageResult findUserByEsId(Page page, Integer esId, Integer esIdAll, Integer industryId, Integer industryIdAll) {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (esId != null && esIdAll == null) {
            queryWrapper.eq("es_id", esId);
        }
        if (industryId != null && industryIdAll == null) {
            queryWrapper.eq("industry_id", industryId);
        }

        queryWrapper.eq("flag", 1);

        PageInfo<User> pageInfo = PageHelper.startPage(page.getPageNum().intValue(), page.getPageSize().intValue())
                .doSelectPageInfo(() -> userMapper.selectList(queryWrapper));
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public Integer findCountUser(String city, String areaId, String streetId, String gridId, String chatId) {
        return userMapper.findCountUser(city, areaId, streetId, gridId, chatId);
    }

    @Override
    public Integer findCompanyCount(String city, String areaId, String streetId, String gridId, String chatId) {
        return userMapper.findCompanyCount(city, areaId, streetId, gridId, chatId);
    }

    @Override
    public Integer findCompanyCountByAll(String city, String areaId, String streetId, String gridId, String chatId, String esId, String industryId) {
        return userMapper.findCompanyCountAll(city, areaId, streetId, gridId, chatId, esId, industryId);
    }

    @Override
    public Integer findCountUserByAll(String city, String areaId, String streetId, String gridId, String chatId, String esId, String industryId) {
        return userMapper.findCountUserAll(city, areaId, streetId, gridId, chatId, esId, industryId);
    }


    @Override
    public List<Map<String, Object>> findCountUserByTime(String time) {

        return userMapper.findCountUserBytime(time);
    }

    @Override
    public List<Map<String, Object>> findCountUserByDate(String time) {

        return userMapper.findCountUserByDate(time);
    }

    @Override
    public List<Map<String, Object>> findCompany(String city, String areaId, String streetId, String gridId, String chatId, String esId, String industryId
            , String name, String phone, String email) {
        return userMapper.findCompanyAll(city, areaId, streetId, gridId, chatId, esId, industryId, name, phone, email);
    }

    @Override
    public List<EnterpriseExcelDTO> findCompanyExcel(String city, String areaId, String streetId, String gridId, String chatId, String esId, String industryId, String name, String phone, String email) {
        return userMapper.findCompanyAllExcel(city, areaId, streetId, gridId, chatId, esId, industryId, name, phone, email);
    }

    @Override
    public List<Map<String, Object>> findUser(String city, String areaId, String streetId, String gridId, String chatId, String esId, String industryId
            , String name, String phone, String email, String peopleName) {
        return userMapper.findUserAll(city, areaId, streetId, gridId, chatId, esId, industryId, name, phone, email, peopleName);
    }

    @Override
    public List<UserExcelDTO> findUserAllExcel(String city, String areaId, String streetId, String gridId, String chatId, String esId, String industryId
            , String name, String phone, String email, String peopleName) {
        return userMapper.findUserAllExcel(city, areaId, streetId, gridId, chatId, esId, industryId, name, phone, email, peopleName);
    }

    /**
     * 查询对应权限的所有用户
     *
     * @param roleId
     * @param userId
     * @return
     */
    @Override
    public Result getAreaUserList(Integer roleId, Integer userId) {

        Result result = new Result(MessageInfo.GET_INFO.getInfo());

        if (roleId.equals(1) || roleId.equals(2)) {

            Map<String, Object> map = new HashMap<>(10);
            map.put("cityId", 0);
            map.put("id", 0);
            map.put("name", "青岛市");
            map.put("children", areaMapper.findAllAreaInUser(null));
            return result.setData(map);
        }

        // 如果为区管理员
        if (roleId.equals(3)) {
            List<AreaPermissions> areaPermissions = areaPermissionsMapper.selectList(Wrappers.<AreaPermissions>lambdaQuery()
                    .eq(AreaPermissions::getAdminType, roleId)
                    .eq(AreaPermissions::getUserId, userId));
            List<Integer> areaIds = new ArrayList<>(5);
            areaPermissions.forEach(ap -> areaIds.add(ap.getAreaId()));
            if (CollectionUtil.isNotEmpty(areaIds)) {

                Map<String, Object> map = new HashMap<>(10);
                map.put("cityId", 0);
                map.put("id", 0);
                map.put("name", "青岛市");
                map.put("children", areaMapper.findAllAreaInUser(areaIds));

                return result.setData(map);
            }
        }

        // 如果为街道管理员
        if (roleId.equals(4)) {
            // 查询出所有的街道
            List<AreaPermissions> areaPermissions = areaPermissionsMapper.selectList(Wrappers.<AreaPermissions>lambdaQuery()
                    .eq(AreaPermissions::getAdminType, roleId)
                    .eq(AreaPermissions::getUserId, userId));

            //List<Integer> streetIds = new ArrayList<>(10);
            //areaPermissions.forEach(ap -> streetIds.add(ap.getStreetId()));

            Map<String, Object> map = new HashMap<>(16);
            ArrayList<Object> objects = new ArrayList<>();
            if (CollectionUtil.isNotEmpty(areaPermissions)) {
                for (AreaPermissions areaPermission : areaPermissions) {
                    AreaUserPO areaUserPO = new AreaUserPO();
                    Area area = areaMapper.selectById(areaPermission.getAreaId());
                    areaUserPO.setAreaId(area.getAreaId());
                    areaUserPO.setId(area.getAreaId());
                    areaUserPO.setName(area.getName());
                    List<StreetUserPO> streetsUserByAreaId = streetMapper.findStreetsUserByStreetId(areaPermission.getStreetId());
                    areaUserPO.setChildren(streetsUserByAreaId);

                    objects.add(areaUserPO);
                }
                map.put("children", objects);
                map.put("id", 0);
                map.put("name", "青岛市");
                map.put("areaId", 0);
            }

            return result.setData(map);
        }

        // 如果为网格管理员
        if (roleId.equals(5)) {
            List<AreaPermissions> areaPermissions = areaPermissionsMapper.selectList(Wrappers.<AreaPermissions>lambdaQuery()
                    .eq(AreaPermissions::getAdminType, roleId)
                    .eq(AreaPermissions::getUserId, userId));
            List<Integer> streetIds = new ArrayList<>(10);
            areaPermissions.forEach(ap -> streetIds.add(ap.getStreetId()));

            if (CollectionUtil.isNotEmpty(streetIds)) {
                List<GridInUserPO> gridUserById = gridMapper.findGridUserById(streetIds);
                return result.setData(gridUserById);
            }
        }
        return result.setData(new ArrayList<>());
    }

    /**
     * 审核用户信息列表
     *
     * @param userInfoListVO
     * @return
     */
    @Override
    public Result findExamineUserInfoList(FindExamineUserInfoListVO userInfoListVO) {

        log.info(JSON.toJSONString(userInfoListVO));
        //Page page = userInfoListVO.getPage();

        PermissionData permissionData = userInfoListVO.getPermission();
        List<Integer> roleIds = permissionData.getRoleIds();

        List<Integer> swapIdList = new ArrayList<>(10);
        // 如果包含5说明是网格管理员
        if (roleIds.contains(5)) {
            List<UserSwap> userSwaps = userSwapMapper.selectList(Wrappers.<UserSwap>lambdaQuery()
                    .eq(UserSwap::getUserId, permissionData.getUserId()));
            swapIdList = new ArrayList<>(10);
            List<Integer> finalSwapIdList = swapIdList;
            userSwaps.forEach(userSwap -> finalSwapIdList.add(userSwap.getSwapId()));
        }

        QueryWrapper<UserInfoSwap> queryWrapper = new QueryWrapper<>();
        // 如果名称不为空
        if (StringUtils.isNotBlank(userInfoListVO.getName())) {
            queryWrapper.like("name", userInfoListVO.getName());
        }

        // 如果 swapId 不为空
        if (CollectionUtil.isNotEmpty(swapIdList)) {
            queryWrapper.in("swap_id", swapIdList);
        }

        queryWrapper.orderByAsc("is_pass");
        queryWrapper.orderByDesc("application_time");

        PageInfo<UserInfoSwap> pageInfo = PageHelper.startPage(userInfoListVO.getPageNum().intValue(), userInfoListVO.getPageSize().intValue())
                .doSelectPageInfo(() -> userInfoSwapMapper.selectList(queryWrapper));

        return new Result(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(),
                new PageResult(pageInfo.getTotal(), pageInfo.getList()));
    }

    /**
     * 修改成功或者失败
     *
     * @param swapId
     * @param isPass
     * @param feedBack
     * @param adminId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result updateExamineInfo(Integer swapId, Integer isPass, String feedBack, Integer adminId) {

        Result result = new Result(MessageInfo.UPDATE_INFO.getInfo());

        UserInfoSwap userInfoSwap = userInfoSwapMapper.selectById(swapId);

        User adminUser = userMapper.selectById(adminId);

        userInfoSwap.setAdminName(adminUser.getName());

        if (isPass.equals(2)) {
            //UserInfoSwap userInfoSwap = new UserInfoSwap();
            //userInfoSwap.setSwapId(swapId);
            userInfoSwap.setIsPass(isPass);
            userInfoSwap.setAdminRemark(feedBack);
            userInfoSwapMapper.updateById(userInfoSwap);

            User user = userMapper.selectById(userInfoSwap.getUserId());
            String ddUserId = user.getDdUserId();

            MessageBody messageBody = new MessageBody();
            messageBody.setText(new Text().setContent(feedBack));
            messageBody.setMsgType(1);

            OapiMessageCorpconversationAsyncsendV2Request.Msg msg = MessageTypeConvert.messageCorpconvert(messageBody);

            MessageCorpParam param = new MessageCorpParam();
            param.setUserIdList(ddUserId);
            param.setMsg(msg);
            param.setToAllUser(false);
            try {
                OapiMessageCorpconversationAsyncsendV2Response response = DDSendMessageUtils.messageCorpconversationAsync(param);

                log.info("response:{}", JSON.toJSONString(response));
            } catch (ApiException e) {
                e.printStackTrace();
            }
            return result;
        } else {

            // 如果为成功
            UpdateUserInfoVO updateUserInfoVO = new UpdateUserInfoVO();
            BeanUtils.copyProperties(userInfoSwap, updateUserInfoVO);
            updateUserInfoVO.setReason("");
            updateUserInfoVO.setStreetId(userInfoSwap.getStreetId());
            result = this.updateUserInfo(updateUserInfoVO);

            if (result.getCode().equals(0)) {
                userInfoSwap.setIsPass(isPass);
                userInfoSwap.setAdminRemark(feedBack);
                userInfoSwapMapper.updateById(userInfoSwap);
            }
        }
        return result;
    }

    /**
     * 统计各个区域下的人员总数
     *
     * @param city
     * @param areaId
     * @param streetId
     * @param chatId
     * @return
     */
    @Override
    public Integer findUserCount(String city, String areaId, String streetId, String chatId) {
        if (StringUtils.isNotBlank(city)) {
            Integer count = userMapper.selectCount(Wrappers.lambdaQuery(User.class)
                    .eq(User::getFlag, 1)
                    .eq(User::getIsConfirm, 1));
            return count;
        }
        if (StringUtils.isNotBlank(areaId)) {
            Integer count = userMapper.selectCount(Wrappers.lambdaQuery(User.class)
                    .eq(User::getFlag, 1)
                    .eq(User::getIsConfirm, 1)
                    .eq(User::getAreaId, areaId));
            return count;
        }
        if (StringUtils.isNotBlank(streetId)) {
            Integer count = userMapper.selectCount(Wrappers.lambdaQuery(User.class)
                    .eq(User::getFlag, 1)
                    .eq(User::getIsConfirm, 1)
                    .eq(User::getStreetId, streetId));
            return count;
        }

        if (StringUtils.isNotBlank(chatId)) {
            Integer count = userChatMapper.selectCount(Wrappers.<UserChat>lambdaQuery()
                    .eq(UserChat::getChatId, chatId));

            return count;
        }
        return 0;
    }

    @Override
    public Integer findCountCompany(String city, String areaId, String streetId, String gridId, String chatId) {
        if (StringUtils.isNotBlank(city)) {
            List<User> users = userMapper.selectList(Wrappers.lambdaQuery(User.class)
                    .select(User::getUserId)
                    .eq(User::getFlag, 1)
                    .ne(User::getCompanyName, "")
                    .eq(User::getIsConfirm, 1)
                    .groupBy(User::getCompanyName));
            return users.size();
        }
        if (StringUtils.isNotBlank(areaId)) {
            List<User> users = userMapper.selectList(Wrappers.lambdaQuery(User.class)
                    .select(User::getUserId)
                    .eq(User::getFlag, 1)
                    .ne(User::getCompanyName, "")
                    .eq(User::getIsConfirm, 1)
                    .eq(User::getAreaId, areaId).groupBy(User::getCompanyName));
            return users.size();
        }
        if (StringUtils.isNotBlank(streetId)) {
            List<User> users = userMapper.selectList(Wrappers.lambdaQuery(User.class)
                    .select(User::getUserId)
                    .eq(User::getFlag, 1)
                    .ne(User::getCompanyName, "")
                    .eq(User::getIsConfirm, 1)
                    .eq(User::getStreetId, streetId).groupBy(User::getCompanyName));
            return users.size();
        }

        if (StringUtils.isNotBlank(chatId)) {

            List<UserChat> userChats = userChatMapper.selectList(Wrappers.<UserChat>lambdaQuery().eq(UserChat::getChatId, chatId));

            List<Integer> userIdList = new ArrayList<>(userChats.size());
            userChats.forEach(userChat -> userIdList.add(userChat.getUserId()));

            if (CollectionUtil.isNotEmpty(userIdList)) {
                List<User> users = userMapper.selectList(Wrappers.<User>lambdaQuery()
                        .select(User::getUserId)
                        .ne(User::getCompanyName, "")
                        .in(User::getUserId, userIdList)
                        .groupBy(User::getCompanyName));
                return users.size();
            } else {
                return 0;
            }
        }
        return 0;
    }

    /**
     * 修改公司备注
     *
     * @param updateCompanyRemarkVO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCompanyRemark(UpdateCompanyRemarkVO updateCompanyRemarkVO) {
        User user = new User();
        user.setUserId(updateCompanyRemarkVO.getUserId());
        user.setCompanyRemark(updateCompanyRemarkVO.getRemark());
        userMapper.updateById(user);
    }

    /**
     * 根据用户id修改网格和换群操作
     *
     * @param updateUserGridVO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result updateUserGrid(UpdateUserGridVO updateUserGridVO) {

        Result result = new Result(MessageInfo.UPDATE_INFO.getInfo());

        // 查询用户信息
        User user = userMapper.selectById(updateUserGridVO.getUserId());
        // 查询网格对应的 chatId
        Integer gridId = updateUserGridVO.getGridId();
        Grid grid = gridMapper.selectById(gridId);
        // 得到对应的群聊
        Chat chat = chatMapper.selectOne(Wrappers.<Chat>lambdaQuery().eq(Chat::getChatId, grid.getChatId()));

        // 将用户从之前群移除,先查询用户目前所在的群
        List<UserChat> userChats = userChatMapper.selectList(Wrappers.<UserChat>lambdaQuery()
                .eq(UserChat::getUserId, updateUserGridVO.getUserId())
                .isNotNull(UserChat::getStreetId));

        // 将对应的 街道删除
        userChatMapper.delete(Wrappers.<UserChat>lambdaQuery()
                .isNotNull(UserChat::getStreetId)
                .eq(UserChat::getUserId, updateUserGridVO.getUserId()));

        // 将新的网格进行添加
        UserChat userChat = new UserChat();
        userChat.setUserId(updateUserGridVO.getUserId());
        userChat.setChatId(chat.getChatId());
        userChat.setStreetId(grid.getStreetId());
        userChatMapper.insert(userChat);

        // 将用户从之前群移除
        if (CollectionUtil.isNotEmpty(userChats)) {
            UserChat uc = userChats.get(0);
            String chatId = uc.getChatId();
            String ddUserId = user.getDdUserId();

            ChatUpdateParam param = new ChatUpdateParam();
            param.setChatId(chatId);
            List<String> delUser = new ArrayList<>(1);
            delUser.add(ddUserId);
            param.setDelUserIdList(delUser);
            try {
                OapiChatUpdateResponse response = DingDingSDKUtils.updateChat(param);
                if (!response.getErrcode().equals(0L)) {
                    log.error("管理端将用户修改群组失败，{}", JSON.toJSONString(response));
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return DDErrResult.dataError(result, response.getErrmsg());
                }
            } catch (ApiException e) {
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return DDErrResult.connException(result);
            }
        }

        // 将用户添加到新的群
        ChatUpdateParam param = new ChatUpdateParam();
        param.setChatId(chat.getChatId());
        List<String> addUser = new ArrayList<>(1);
        addUser.add(user.getDdUserId());
        param.setAddUserIdList(addUser);
        try {
            DingDingSDKUtils.updateChat(param);
        } catch (ApiException e) {
            e.printStackTrace();
        }

        updateUserAddress(user.getUserId(), grid.getStreetId());

        // 发送欢迎消息
        sendWelcomeMessage(user.getName(), chat.getChatId());

        return result;
    }

    /**
     * 修改用户的 区 和街道信息
     *
     * @param userId
     * @param streetId
     */
    private void updateUserAddress(Integer userId, Integer streetId) {
        User userInfo = userMapper.selectById(userId);
        Street street = streetMapper.selectById(streetId);
        Area area = areaMapper.selectById(street.getAreaId());
        User user = new User();
        user.setUserId(userInfo.getUserId());
        user.setStreetId(streetId);
        user.setAreaId(area.getAreaId());

        user.setArea(area.getName());
        user.setStreet(street.getStreetName());

        userMapper.updateById(user);
    }

    /**
     * 发送入群欢迎消息
     *
     * @param userName
     * @param chatId
     */
    private void sendWelcomeMessage(String userName, String chatId) {
        MessageBody messageBody = new MessageBody();
        Text text = new Text();
        text.setContent("欢迎" + userName + "加入群聊");
        messageBody.setText(text);
        messageBody.setMsgType(1);
        OapiChatSendRequest.Msg msg = MessageTypeConvert.sendChatMessage(messageBody);

        SendChatMessageParam sendChatMessageParam = new SendChatMessageParam();
        sendChatMessageParam.setChatId(chatId);
        sendChatMessageParam.setMsg(msg);

        try {
            DDSendMessageUtils.sendChatMessage(sendChatMessageParam);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据标签查询对应的公司列表
     *
     * @param esId
     * @param page
     * @return
     */
    @Override
    public Result findUserByTag(Integer esId, Page page) {
        PageInfo<User> pageInfo = PageHelper.startPage(page.getPageNum().intValue(), page.getPageSize().intValue())
                .doSelectPageInfo(() -> userMapper.selectList(Wrappers.<User>lambdaQuery()
                        .like(User::getIndustryId, esId + ",")));
        return new Result(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(), new PageResult(pageInfo.getTotal(), pageInfo.getList()));
    }

    /**
     * 修改用户公司
     *
     * @param updateUserCompany
     * @return
     */
    @Override
    public Result updateUserCompany(UpdateUserCompanyVO updateUserCompany) {
        DsInfoDTO dsInfoDTO = userVerification.userVerification(updateUserCompany.getUnitNo(), updateUserCompany.getPassword());
        User user = userMapper.selectById(updateUserCompany.getUserId());
        User updateUser = new User();
        updateUser.setUserId(user.getUserId());
        updateUser.setCompanyName(dsInfoDTO.getCompanyName());
        updateUser.setUnitNo(dsInfoDTO.getUnitNo());
        updateUser.setAddress(dsInfoDTO.getAddress());
        userMapper.updateById(updateUser);
        return new Result(true, StatusCode.OK, MessageInfo.UPDATE_INFO.getInfo());
    }
}
