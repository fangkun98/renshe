package com.ciyun.renshe.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ciyun.renshe.common.MessageInfo;
import com.ciyun.renshe.common.Result;
import com.ciyun.renshe.common.StatusCode;
import com.ciyun.renshe.common.dingding.DDErrResult;
import com.ciyun.renshe.common.dingding.DingDingUtil;
import com.ciyun.renshe.common.dingding.sdk.DingDingSDKUtils;
import com.ciyun.renshe.common.dingding.sdk.request.ChatCreateParam;
import com.ciyun.renshe.common.dingding.sdk.request.ChatUpdateParam;
import com.ciyun.renshe.controller.vo.admin.*;
import com.ciyun.renshe.entity.*;
import com.ciyun.renshe.entity.po.AreaAdminPO;
import com.ciyun.renshe.entity.po.DingDingAreaPO;
import com.ciyun.renshe.entity.po.UserPO;
import com.ciyun.renshe.manager.dingding.DingDingDepartment;
import com.ciyun.renshe.manager.dingding.dto.DingDingApiResult;
import com.ciyun.renshe.manager.dingding.dto.deptartment.CreateDepartmentParam;
import com.ciyun.renshe.mapper.*;
import com.ciyun.renshe.service.AreaService;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiChatUpdateRequest;
import com.dingtalk.api.response.OapiChatCreateResponse;
import com.dingtalk.api.response.OapiChatUpdateResponse;
import com.taobao.api.ApiException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements AreaService {

    private final AreaMapper areaMapper;
    private final StreetMapper streetMapper;
    private final UserRoleMapper userRoleMapper;
    private final AreaPermissionsMapper areaPermissionsMapper;
    private final GridMapper gridMapper;
    private final ChatMapper chatMapper;
    private final UserChatMapper userChatMapper;
    private final UserMapper userMapper;
    private final PollChatMapper pollChatMapper;
    private final PollAdminMapper pollAdminMapper;
    private final DingDingDepartment dingDingDepartment;
    private final DeptMapper deptMapper;

    /**
     * 保存区域管理员
     *
     * @param saveAdminVO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result saveAreaAdmin(SaveAdminVO saveAdminVO) {

        Result result = new Result(MessageInfo.ADD_INFO.getInfo());

        // 说明是添加市管理员
        /*if (saveAdminVO.getRoleId().equals(2) && saveAdminVO.getStreetId() == null) {
            List<AreaPermissions> areaPermissions = areaPermissionsMapper.selectList(Wrappers.<AreaPermissions>lambdaQuery()
                    .eq(AreaPermissions::getAreaId, saveAdminVO.getAreaId())
                    .eq(AreaPermissions::getUserId, saveAdminVO.getUserId())
                    .isNull(AreaPermissions::getStreetId)
                    .isNull(AreaPermissions::getGridId)
                    .eq(AreaPermissions::getAdminType, 2));
            if (!areaPermissions.isEmpty()) {
                return result.setMessage("选中的人员已经是此区域的管理员了");
            }
        }*/
        // 说明添加区管理员
        if (saveAdminVO.getRoleId().equals(3) && saveAdminVO.getAreaId() != null) {
            List<AreaPermissions> areaPermissions = areaPermissionsMapper.selectList(Wrappers.<AreaPermissions>lambdaQuery()
                    .eq(AreaPermissions::getAreaId, saveAdminVO.getAreaId())
                    .eq(AreaPermissions::getUserId, saveAdminVO.getUserId())
                    .isNull(AreaPermissions::getStreetId)
                    .isNull(AreaPermissions::getGridId)
                    .eq(AreaPermissions::getAdminType, 3));
            if (!areaPermissions.isEmpty()) {
                return result.setMessage("选中的人员已经是此区域的管理员了");
            }
        }
        // 说明添加街道管理员
        if (saveAdminVO.getRoleId().equals(4) && saveAdminVO.getStreetId() != null) {
            List<AreaPermissions> areaPermissions = areaPermissionsMapper.selectList(Wrappers.<AreaPermissions>lambdaQuery()
                    .eq(AreaPermissions::getStreetId, saveAdminVO.getStreetId())
                    .eq(AreaPermissions::getUserId, saveAdminVO.getUserId())
                    .eq(AreaPermissions::getAdminType, 4));
            if (!areaPermissions.isEmpty()) {
                return result.setMessage("选中的人员已经是此区域的管理员了");
            }
        }
        // 说明添加网格管理员
        if (saveAdminVO.getRoleId().equals(5) && saveAdminVO.getGridId() != null) {
            List<AreaPermissions> areaPermissions = areaPermissionsMapper.selectList(Wrappers.<AreaPermissions>lambdaQuery()
                    .eq(AreaPermissions::getGridId, saveAdminVO.getGridId())
                    .eq(AreaPermissions::getUserId, saveAdminVO.getUserId())
                    .eq(AreaPermissions::getAdminType, 5));
            if (!areaPermissions.isEmpty()) {
                return result.setMessage("选中的人员已经是此区域的管理员了");
            }
        }

        // 将用户保存到用户角色表中信息表
        userRoleMapper.insert(new UserRole().setUserId(saveAdminVO.getUserId())
                .setRoleId(saveAdminVO.getRoleId()) );

        User user = userMapper.selectById(saveAdminVO.getUserId());
        user.setIsAdmin(1);
        userMapper.updateById(user);

        AreaPermissions areaPermissions = new AreaPermissions();
        BeanUtils.copyProperties(saveAdminVO, areaPermissions);

        // 如果为街道管理员，查询出对应的区id
        if (saveAdminVO.getRoleId().equals(4)) {
            Street street = streetMapper.selectById(saveAdminVO.getStreetId());
            areaPermissions.setAreaId(street.getAreaId());
        }
        areaPermissions.setAdminType(saveAdminVO.getRoleId());

        // 保存信息到区域权限表
        areaPermissionsMapper.insert(areaPermissions);

        return result;
    }

    /**
     * 根据 区 街道，网格查询对应管理员
     *
     * @param areaAdminVO
     * @return
     */
    @Override
    public Result getAreaAdmin(GetAreaAdminVO areaAdminVO) {
        List<AreaAdminPO> areaAdmin = areaPermissionsMapper
                .getAreaAdmin(areaAdminVO.getAreaId(), areaAdminVO.getStreetId(),
                        areaAdminVO.getGridId(), areaAdminVO.getRoleId());
        Iterator<AreaAdminPO> iterator = areaAdmin.iterator();
        while (iterator.hasNext()) {
            AreaAdminPO next = iterator.next();
            // 排除中间表中的数据
            if (next.getUser() == null) {
                iterator.remove();
            }
        }

        if (areaAdminVO.getGridId() != null) {

            List<AreaAdminPO> area = new ArrayList<>(10);

            Grid grid = gridMapper.selectById(areaAdminVO.getGridId());
            String chatId = grid.getChatId();

            Chat chat = chatMapper.selectOne(Wrappers.<Chat>lambdaQuery().eq(Chat::getChatId, chatId));
            String owner = chat.getOwner();
            User userByDDUserId = userMapper.findUserByDDUserId(owner);

            AreaAdminPO areaAdminPO = new AreaAdminPO();
            areaAdminPO.setIsOwner(1);

            UserPO userPO = new UserPO();
            BeanUtils.copyProperties(userByDDUserId, userPO);
            areaAdminPO.setUser(userPO);
            areaAdminPO.setUserId(userByDDUserId.getUserId());
            area.add(areaAdminPO);

            String managerUser = chat.getManagerUser();
            List list = JSON.parseObject(managerUser, List.class);
            list.remove(owner);

            for (Object o : list) {
                AreaAdminPO admin = new AreaAdminPO();
                User userByDDUserId1 = userMapper.findUserByDDUserId((String) o);
                UserPO u = new UserPO();
                BeanUtils.copyProperties(userByDDUserId1, u);
                admin.setUser(u);
                admin.setUserId(userByDDUserId1.getUserId());
                admin.setIsOwner(0);
                area.add(admin);
            }

            return new Result<>(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(), area);

        }
        return new Result<>(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(), areaAdmin);
    }

    /**
     * 删除用户对应区域权限
     *
     * @param deleteAdminVO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAreaAdmin(DeleteAdminVO deleteAdminVO) {
        areaPermissionsMapper.delete(Wrappers.<AreaPermissions>lambdaQuery()
                .eq(AreaPermissions::getUserId, deleteAdminVO.getUserId())
                .eq(AreaPermissions::getAdminType, deleteAdminVO.getRoleId()));
    }

    /**
     * 修改网格管理员，需要修改对应 群的群主
     *
     * @param updateGridAdminVO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result updateGridAdmin(UpdateGridAdminVO updateGridAdminVO) {

        Result result = new Result(MessageInfo.UPDATE_INFO.getInfo());

        if (updateGridAdminVO.getPermission() == null) {
            return result.setFlag(false).setMessage("数据不完整，没有传递 permissionData").setCode(StatusCode.ERROR);
        } else {
            if (CollectionUtil.isEmpty(updateGridAdminVO.getPermission().getRoleIds())) {
                return result.setFlag(false).setMessage("数据不完整，没有传递 permissionData").setCode(StatusCode.ERROR);
            }
        }
        User u = userMapper.selectById(updateGridAdminVO.getBeforeUserId());
        Chat c = chatMapper.selectOne(Wrappers.<Chat>lambdaQuery().eq(Chat::getChatId, updateGridAdminVO.getChatId()));

        List<Integer> roleIdList = updateGridAdminVO.getPermission().getRoleIds();

        // 如果只是网格管理员
        if (roleIdList.contains(5) && !roleIdList.contains(4)
                && !roleIdList.contains(3) && !roleIdList.contains(2) && !roleIdList.contains(1)) {
            // 如果群主的id和要替换的群主id不同，不能操作
            if (c.getOwner().equals(u.getDdUserId())) {
                result.setFlag(false).setCode(StatusCode.ACCESSREEOR)
                        .setMessage("你没有权限操作替换群管理员");
            }
        }

        if (updateGridAdminVO.getBeforeUserId().equals(updateGridAdminVO.getUserId())) {
            return result.setFlag(false).setCode(StatusCode.ERROR).setMessage("不能指定同一个管理员");
        }
        // 修改群主
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/chat/update");
        OapiChatUpdateRequest request = new OapiChatUpdateRequest();
        request.setChatid(updateGridAdminVO.getChatId());
        request.setOwner(updateGridAdminVO.getDdUserId());

        // 将新添加的群主添加到群成员中
        List<String> user = new ArrayList<>();
        user.add(updateGridAdminVO.getDdUserId());
        request.setAddUseridlist(user);

        try {
            OapiChatUpdateResponse response = client.execute(request, DingDingUtil.ACCESS_TOKEN);
            log.info("请求钉钉修改群信息接口，返回信息为：{}", JSON.toJSONString(request));
            if (response.getErrcode().equals(0L)) {

                // 修改对应的群信息,群主信息
                Chat chat = chatMapper.selectById(updateGridAdminVO.getChatId());
                chat.setOwner(updateGridAdminVO.getDdUserId());

                User user1 = userMapper.selectById(updateGridAdminVO.getBeforeUserId());
                log.info("之前群主的信息：{}", JSON.toJSONString(user1));

                String managerUser = chat.getManagerUser();
                List list = JSON.parseObject(managerUser, List.class);

                log.info("之前的userId为：{}", updateGridAdminVO.getBeforeUserId());
                Iterator iterator = list.iterator();
                while (iterator.hasNext()) {
                    Object next = iterator.next();
                    if (next.equals(user1.getDdUserId())) {
                        log.info("匹配到了相关值");
                        iterator.remove();
                    }
                }

                log.info("删除后：{}", JSON.toJSONString(list));
                list.add(0, updateGridAdminVO.getDdUserId());
                log.info("添加后：{}", JSON.toJSONString(list));
                // 设置群管理员
                chat.setManagerUser(JSON.toJSONString(list));
                chatMapper.updateById(chat);

                // 将之前用户角色中间表中的数据修改为无效
                userRoleMapper.delete(Wrappers.<UserRole>lambdaQuery()
                        .eq(UserRole::getUserId, updateGridAdminVO.getBeforeUserId())
                        .eq(UserRole::getRoleId, 5));

                // 给新群主增加权限
                UserRole userRole = new UserRole();
                userRole.setRoleId(5);
                userRole.setUserId(updateGridAdminVO.getUserId());
                userRoleMapper.insert(userRole);

                // 删除区域权限表
                areaPermissionsMapper.delete(Wrappers.<AreaPermissions>lambdaQuery()
                        .eq(AreaPermissions::getGridId, updateGridAdminVO.getGridId())
                        .eq(AreaPermissions::getAdminType, 5)
                        .eq(AreaPermissions::getUserId, updateGridAdminVO.getBeforeUserId()));

                // 给新群主增加区域权限
                AreaPermissions areaPermissions = new AreaPermissions();
                areaPermissions.setAdminType(5);
                areaPermissions.setUserId(updateGridAdminVO.getUserId());
                areaPermissions.setGridId(updateGridAdminVO.getGridId());
                Grid grid = gridMapper.selectById(updateGridAdminVO.getGridId());
                areaPermissions.setStreetId(grid.getStreetId());
                Street street = streetMapper.selectById(grid.getStreetId());
                areaPermissions.setAreaId(street.getAreaId());
                areaPermissionsMapper.insert(areaPermissions);

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
     * 保存网格管理员 还会创建对应群
     *
     * @param saveGridAdminVO
     * @return
     * @Version 1.0.1 2020-7-7 15:14:55
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result saveGridAdmin(SaveGridAdminVO saveGridAdminVO) throws ApiException {

        Result result = new Result(MessageInfo.ADD_INFO.getInfo());

        // 先查询是否已经存在

        // 2020年7月7日15:09:54
        // 新增加一步，创建对应的部门，部门和创建的群聊进行绑定

        // 1. 创建对应的群聊 得到 chatId
        // 2. 在 chat 表创建对应群
        // 2. 在 用户角色中间表创建对应的 用户角色关系
        // 2. 在 用户群聊中间表创建对应关系
        // 3. 在数据库 grid 表 创建对应的网格数据
        // 4. 在区域权限表创建对应的权限
        // 5. 在轮询表中创建数据

        List<Grid> grids = gridMapper.selectList(Wrappers.<Grid>lambdaQuery().eq(Grid::getGridName, saveGridAdminVO.getGridName()));
        if (CollectionUtil.isNotEmpty(grids)) {
            return result.setFlag(false).setCode(StatusCode.ERROR).setMessage("该网格名称已经存在");
        }

        String gridName = saveGridAdminVO.getGridName();

        // 查询街道对应的的部门，获取父级部门id
        String pDeptId = findDeptIdByStreetId(saveGridAdminVO.getStreetId());
        // 在钉钉上创建对应的部门
        Long dept = createDept(gridName, pDeptId);
        Integer deptId = saveDept(gridName, dept, pDeptId);

        // 创建群
        ChatCreateParam param = new ChatCreateParam();
        param.setName(saveGridAdminVO.getGridName());
        param.setOwner(saveGridAdminVO.getDdUserId());
        List<String> user = new ArrayList<>(1);
        user.add(saveGridAdminVO.getDdUserId());
        param.setUseridlist(user);

        OapiChatCreateResponse response = null;
        try {
            response = DingDingSDKUtils.createChat(param);
            if (response.getErrcode().equals(0L)) {

                // 创建群
                Chat chat = new Chat();
                chat.setChatId(response.getChatid());
                chat.setName(saveGridAdminVO.getGridName());
                chat.setOwner(saveGridAdminVO.getDdUserId());
                chat.setUserIdList(JSON.toJSONString(user));
                // 保存对应的部门id
                chat.setDeptId(deptId);
                chat.setFlag(1);
                chat.setCreateTime(new Date());
                // 添加到管理员中
                List<String> admins = new ArrayList<>(1);
                admins.add(saveGridAdminVO.getDdUserId());
                chat.setManagerUser(JSON.toJSONString(admins));
                chatMapper.insert(chat);

                // 创建对应的 用户角色中间表
                UserRole userRole = new UserRole();
                userRole.setUserId(saveGridAdminVO.getUserId());
                userRole.setRoleId(saveGridAdminVO.getRoleId());
                userRoleMapper.insert(userRole);

                // 创建管理员轮询表
                PollAdmin pollAdmin = new PollAdmin();
                pollAdmin.setChatId(chat.getChatId());
                pollAdmin.setChatName(chat.getName());
                pollAdmin.setNextNum(1);
                pollAdmin.setPollCount(1);
                pollAdminMapper.insert(pollAdmin);

                // 在数据库 grid 表 创建对应的网格数据
                Grid grid = new Grid();
                grid.setChatId(response.getChatid());
                grid.setFlag(1);
                grid.setGridName(saveGridAdminVO.getGridName());
                grid.setStreetId(saveGridAdminVO.getStreetId());
                gridMapper.insert(grid);

                // 用户群聊中间表
                UserChat userChat = new UserChat();
                userChat.setUserId(saveGridAdminVO.getUserId());
                userChat.setChatId(response.getChatid());
                userChat.setStreetId(saveGridAdminVO.getStreetId());
                userChatMapper.insert(userChat);

                // 在区域权限表创建对应的权限
                // 创建对应的区域权限
                Street street = streetMapper.selectById(saveGridAdminVO.getStreetId());
                AreaPermissions areaPermissions = new AreaPermissions();
                areaPermissions.setAdminType(saveGridAdminVO.getRoleId())
                        .setUserId(saveGridAdminVO.getUserId())
                        .setAreaId(street.getAreaId())
                        .setStreetId(saveGridAdminVO.getStreetId())
                        .setGridId(grid.getGridId());
                areaPermissionsMapper.insert(areaPermissions);

                // 将群加入轮询表
                // 1. 先查询 轮询表中是否有街道下的相关数据
                //      1.1 如果有，进行更新群总数
                //      1.2 如果没有，直接插入数据
                List<PollChat> pollChats = pollChatMapper.selectList(Wrappers.<PollChat>lambdaQuery()
                        .eq(PollChat::getStreetId, saveGridAdminVO.getStreetId()));
                if (CollectionUtil.isNotEmpty(pollChats)) {
                    PollChat pollChat = pollChats.get(0);
                    // 将街道下的群总数加 1
                    pollChat.setGridCount(pollChat.getGridCount() + 1);
                    pollChatMapper.updateById(pollChat);
                } else {
                    PollChat pollChat = new PollChat();
                    pollChat.setGridCount(1);
                    pollChat.setPreviousNum(1);
                    pollChat.setStreetId(saveGridAdminVO.getStreetId());
                    pollChatMapper.insert(pollChat);
                }
                return result;
            } else {
                return DDErrResult.dataError(result, response.getErrmsg());
            }

        } catch (Exception e) {

            // 如果群创建成功后抛出了异常，将群解散
            if (response != null && response.getErrcode().equals(0L)) {
                // 将创建的群解散
                ChatUpdateParam chatUpdateParam = new ChatUpdateParam();
                List<String> ddIds = new ArrayList<>(1);
                ddIds.add(saveGridAdminVO.getDdUserId());
                chatUpdateParam.setDelUserIdList(ddIds);
                chatUpdateParam.setChatId(response.getChatid());
                try {
                    DingDingSDKUtils.updateChat(chatUpdateParam);
                } catch (ApiException apiException) {
                    apiException.printStackTrace();
                }
            }

            e.printStackTrace();
            return DDErrResult.connException(result);
        }
    }

    /**
     * 根据街道id 查询对应的部门id
     *
     * @param streetId
     * @return
     */
    private String findDeptIdByStreetId(Integer streetId) {
        Street street = streetMapper.selectById(streetId);
        Assert.notNull(street, "对应街道不存在，请联系相关人员");
        String streetName = street.getStreetName();
        log.info("streetName:{}", streetName);
        Dept dept = deptMapper.selectOne(Wrappers.<Dept>lambdaQuery()
                .eq(Dept::getDeptName, streetName)
                .eq(Dept::getFlag, 1));
        log.info("dept:{}", JSON.toJSONString(dept));
        return dept.getParentId();
    }

    /**
     * 在钉钉创建创建部门
     *
     * @param deptName
     * @return
     */
    private Long createDept(String deptName, String parentId) {
        // 创建部门
        CreateDepartmentParam departmentParam = new CreateDepartmentParam();
        departmentParam.setName(deptName);
        departmentParam.setParentid(parentId);
        try {
            DingDingApiResult dingApiResult = dingDingDepartment.createDepartment(departmentParam);
            log.info("创建钉钉部门成功，返回值：{}", JSON.toJSONString(dingApiResult));
            return dingApiResult.getId();
        } catch (ApiException e) {
            e.printStackTrace();
            throw new RuntimeException("调用钉钉创建部门失败");
        }
    }

    /**
     * 保存钉钉信息到数据库，会在钉钉创建对应的
     *
     * @param deptName 网格名称
     * @param deptId   钉钉部门id
     * @param pDeptId  钉钉父部门id
     * @return
     */
    private Integer saveDept(String deptName, Long deptId, String pDeptId) {
        Dept dept = new Dept();
        dept.setFlag(1);
        dept.setCreateTime(new Date());
        dept.setDeptName(deptName);
        dept.setParentId(pDeptId);
        dept.setDdDeptId(deptId);
        deptMapper.insert(dept);
        return dept.getDeptId();
    }

    /**
     * 查询全部区域数据，包含街道
     *
     * @return
     */
    @Override
    public Result findAllAreaInfo() {
        List<DingDingAreaPO> allAreaInfo = areaMapper.findAllAreaInfo();

        Map<String, Object> map = new LinkedHashMap<>(16);
        map.put("name", "青岛市");
        map.put("code", 0);
        map.put("sub", allAreaInfo);

        return new Result<>(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(), map);
    }

    /**
     * 查询全部管理员
     *
     * @return
     */
    @Override
    public Result findAllAdmin() {
        Result result = new Result(MessageInfo.GET_INFO.getInfo());
        List<User> users = userMapper.selectList(Wrappers.<User>lambdaQuery().eq(User::getFlag, 1).eq(User::getIsAdmin, 1));
        return result.setData(users);
    }

    @Override
    public List<Area> findAllArea() {
        QueryWrapper<Area> wrapper = new QueryWrapper<>();
        List<Area> areas = areaMapper.selectList(wrapper);
        return areas;
    }

}
