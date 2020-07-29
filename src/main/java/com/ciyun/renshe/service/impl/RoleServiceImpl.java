package com.ciyun.renshe.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ciyun.renshe.common.*;
import com.ciyun.renshe.common.dingding.DDErrResult;
import com.ciyun.renshe.common.dingding.sdk.DingDingSDKUtils;
import com.ciyun.renshe.common.dingding.DingDingUtil;
import com.ciyun.renshe.common.dingding.sdk.request.RoleAddParam;
import com.ciyun.renshe.controller.vo.role.AddRolesForEmpsVO;
import com.ciyun.renshe.controller.vo.role.CreateRoleVO;
import com.ciyun.renshe.controller.vo.role.UpdateRoleScopeVO;
import com.ciyun.renshe.controller.vo.role.UpdateRoleVO;
import com.ciyun.renshe.entity.Role;
import com.ciyun.renshe.entity.RoleGroup;
import com.ciyun.renshe.mapper.RoleGroupMapper;
import com.ciyun.renshe.mapper.RoleMapper;
import com.ciyun.renshe.service.RoleService;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    private RoleMapper roleMapper;
    private RoleGroupMapper roleGroupMapper;

    public RoleServiceImpl(RoleMapper roleMapper, RoleGroupMapper roleGroupMapper) {
        this.roleMapper = roleMapper;
        this.roleGroupMapper = roleGroupMapper;
    }

    /**
     * 获取角色列表
     *
     * @param page
     * @return
     */
    @Override
    public Result getRoleList(Page page) {

        Result<OapiRoleListResponse.PageVo> result = new Result<>(MessageInfo.GET_INFO.getInfo());

        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/role/list");
        OapiRoleListRequest request = new OapiRoleListRequest();
        request.setOffset(page.getPageNum() - 1L);
        request.setSize(Long.parseLong(page.getPageSize().toString()));

        try {
            OapiRoleListResponse response = client.execute(request, DingDingUtil.ACCESS_TOKEN);
            if (response.getErrcode().equals(0L)) {
                // 将集合返回
                result.setData(response.getResult());
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
     * 获取角色下的员工列表
     *
     * @param page
     * @param roleId
     * @return
     */
    @Override
    public Result getRoleUserList(Page page, Long roleId) {

        Result<OapiRoleSimplelistResponse.PageVo> result = new Result<>(MessageInfo.GET_INFO.getInfo());

        // 请求钉钉 获取角色下的员工列表接口
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/role/simplelist");
        OapiRoleSimplelistRequest request = new OapiRoleSimplelistRequest();
        request.setRoleId(roleId);
        request.setOffset(page.getPageNum() - 1L);
        request.setSize(page.getPageSize());

        try {
            OapiRoleSimplelistResponse response = client.execute(request, DingDingUtil.ACCESS_TOKEN);

            if (response.getErrcode().equals(0L)) {
                return result.setData(response.getResult());
            } else {
                return DDErrResult.dataError(result, response.getErrmsg());
            }
        } catch (ApiException e) {
            e.printStackTrace();
            return DDErrResult.connException(result);
        }
    }

    /**
     * 创建角色组
     *
     * @param name
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result addRoleGroup(String name) {

        Result result = new Result(MessageInfo.ADD_INFO.getInfo());

        // 请求钉钉 创建角色组 接口
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/role/add_role_group");
        OapiRoleAddrolegroupRequest req = new OapiRoleAddrolegroupRequest();
        req.setName(name);
        try {
            OapiRoleAddrolegroupResponse response = client.execute(req, DingDingUtil.ACCESS_TOKEN);
            if (response.getErrcode().equals(0L)) {
                // 将 结果保存到表中
                roleGroupMapper.insert(new RoleGroup()
                        .setFlag(1)
                        .setCreateTime(new Date())
                        .setGroupId(response.getGroupId().toString()).setGroupName(name));
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
     * 创建角色
     *
     * @param roleVO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result addRole(CreateRoleVO roleVO) {

        Result result = new Result(MessageInfo.ADD_INFO.getInfo());
        try {
            RoleAddParam param = new RoleAddParam();
            BeanUtils.copyProperties(roleVO, param);
            // 请求钉钉接口
            OapiRoleAddRoleResponse response = DingDingSDKUtils.addRole(param);
            if (response.getErrcode().equals(0L)) {
                // 将角色进行保存
                roleMapper.insert(new Role()
                        .setCreateTime(new Date())
                        .setFlag(1)
                        .setGroupId(roleVO.getGroupId().toString())
                        .setRoleName(roleVO.getRoleName())
                        .setRoleId(response.getRoleId().toString()));
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
     * 更新角色
     *
     * @param roleVO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result updateRole(UpdateRoleVO roleVO) {

        Result result = new Result(MessageInfo.UPDATE_INFO.getInfo());

        // 请求钉钉更新角色接口
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/role/update_role");
        OapiRoleUpdateRoleRequest req = new OapiRoleUpdateRoleRequest();
        req.setRoleId(Long.parseLong(roleVO.getRoleId()));
        req.setRoleName(roleVO.getRoleName());
        try {
            OapiRoleUpdateRoleResponse response = client.execute(req, DingDingUtil.ACCESS_TOKEN);
            if (response.getErrcode().equals(0L)) {
                roleMapper.updateById(new Role()
                        .setRoleId(roleVO.getRoleId())
                        .setRoleName(roleVO.getRoleName()));
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
     * 删除角色
     *
     * @param roleId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result deleteRole(Long roleId) {

        Result<Object> result = new Result<>(MessageInfo.DELETE_INFO.getInfo());

        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/role/deleterole");
        OapiRoleDeleteroleRequest request = new OapiRoleDeleteroleRequest();
        request.setRoleId(roleId);

        try {
            OapiRoleDeleteroleResponse response = client.execute(request, DingDingUtil.ACCESS_TOKEN);
            if (response.getErrcode().equals(0L)) {
                // 删除对应的角色
                roleMapper.updateById(new Role().setRoleId(roleId.toString()).setFlag(0));
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
     * 增加员工角色
     *
     * @param addRolesForEmpsVO
     * @return
     */
    @Override
    public Result addRolesForEmps(AddRolesForEmpsVO addRolesForEmpsVO) {

        Result result = new Result(MessageInfo.ADD_INFO.getInfo());

        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/role/addrolesforemps");
        OapiRoleAddrolesforempsRequest request = new OapiRoleAddrolesforempsRequest();
        StringBuilder roleIds = new StringBuilder();
        StringBuilder userIds = new StringBuilder();

        if (addRolesForEmpsVO.getRoleIds().size() > 20) {
            return result.setMessage("一次操作不能大于 20 个");
        }

        if (addRolesForEmpsVO.getUserIds().size() > 20) {
            return result.setMessage("一次操作不能大于 20 个");
        }

        // 将角色 id 转为 ， 分隔
        addRolesForEmpsVO.getRoleIds().forEach(roleIds::append);
        /*for (String roleId : addRolesForEmpsVO.getRoleIds()) {
            roleIds.append(roleId);
        }*/
        addRolesForEmpsVO.getUserIds().forEach(userIds::append);

        log.info("roleIds：" + roleIds.toString());
        log.info("userIds：" + userIds.toString());

        request.setRoleIds(roleIds.toString());
        request.setUserIds(userIds.toString());

        try {
            OapiRoleAddrolesforempsResponse response = client.execute(request, DingDingUtil.ACCESS_TOKEN);
            if (response.getErrcode().equals(0L)) {
                log.info("增加员工角色,返回数据：{}", JSONObject.toJSONString(response));
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
     * 批量删除员工角色
     *
     * @param roleIds
     * @param userIds
     * @return
     */
    @Override
    public Result removeRolesForEmps(String roleIds, String userIds) {
        Result<Object> result = new Result<>(MessageInfo.DELETE_INFO.getInfo());

        String[] roleSplit = roleIds.split(",");
        String[] userSplit = userIds.split(",");
        if (roleSplit.length <= 20) {
            return result.setFlag(false).setCode(StatusCode.ERROR).setData("角色 Id 小于 20");
        }

        if (userSplit.length >= 100) {
            return result.setFlag(false).setCode(StatusCode.ERROR).setData("用户 Id 大于 100");
        }

        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/role/removerolesforemps");
        OapiRoleRemoverolesforempsRequest request = new OapiRoleRemoverolesforempsRequest();
        request.setRoleIds(roleIds);
        request.setUserIds(userIds);

        try {
            OapiRoleRemoverolesforempsResponse response = client.execute(request, DingDingUtil.ACCESS_TOKEN);
            if (response.getErrcode().equals(0L)) {
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
     * 设定角色成员管理范围
     *
     * @param roleScopeVO
     * @return
     */
    @Override
    public Result scopeUpdate(UpdateRoleScopeVO roleScopeVO) {
        Result result = new Result(MessageInfo.UPDATE_INFO.getInfo());

        DingTalkClient client = new DefaultDingTalkClient(
                "https://oapi.dingtalk.com/topapi/role/scope/update");
        OapiRoleScopeUpdateRequest request = new OapiRoleScopeUpdateRequest();
        request.setUserid(roleScopeVO.getUserId());
        request.setRoleId(roleScopeVO.getRoleId());
        if (StringUtils.isNotBlank(roleScopeVO.getDeptIds())) {
            request.setDeptIds(roleScopeVO.getDeptIds());
        }
        request.setHttpMethod("POST");
        try {
            OapiRoleScopeUpdateResponse response = client.execute(request, DingDingUtil.ACCESS_TOKEN);
            if (response.getErrcode().equals(0L)) {
                return result;
            } else {
                return DDErrResult.dataError(result, response.getErrmsg());
            }
        } catch (ApiException e) {
            e.printStackTrace();
            return DDErrResult.connException(result);
        }
    }

}




