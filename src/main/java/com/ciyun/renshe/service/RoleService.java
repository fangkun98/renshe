package com.ciyun.renshe.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ciyun.renshe.common.Page;
import com.ciyun.renshe.common.Result;
import com.ciyun.renshe.controller.vo.role.AddRolesForEmpsVO;
import com.ciyun.renshe.controller.vo.role.CreateRoleVO;
import com.ciyun.renshe.controller.vo.role.UpdateRoleScopeVO;
import com.ciyun.renshe.controller.vo.role.UpdateRoleVO;
import com.ciyun.renshe.entity.Role;

public interface RoleService extends IService<Role> {

    /**
     * 获取角色列表
     *
     * @param page
     * @return
     */
    Result getRoleList(Page page);

    /**
     * 创建角色组
     *
     * @param name
     * @return
     */
    Result addRoleGroup(String name);

    /**
     * 获取角色下的员工列表
     *
     * @param page
     * @param roleId
     * @return
     */
    Result getRoleUserList(Page page, Long roleId);

    /**
     * 创建角色
     *
     * @param roleVO
     * @return
     */
    Result addRole(CreateRoleVO roleVO);

    /**
     * 更新角色
     *
     * @param roleVO
     * @return
     */
    Result updateRole(UpdateRoleVO roleVO);

    /**
     * 删除角色
     *
     * @param roleId
     * @return
     */
    Result deleteRole(Long roleId);

    /**
     * 批量增加员工角色
     *
     * @param addRolesForEmpsVO
     * @return
     */
    Result addRolesForEmps(AddRolesForEmpsVO addRolesForEmpsVO);

    /**
     * 批量删除员工角色
     *
     * @param roleIds
     * @param userIds
     * @return
     */
    Result removeRolesForEmps(String roleIds, String userIds);

    /**
     * 设定角色成员管理范围
     *
     * @param roleScopeVO
     * @return
     */
    Result scopeUpdate(UpdateRoleScopeVO roleScopeVO);


}




