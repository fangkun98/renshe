package com.ciyun.renshe.controller;

import com.ciyun.renshe.common.Page;
import com.ciyun.renshe.common.Result;
import com.ciyun.renshe.controller.vo.role.AddRolesForEmpsVO;
import com.ciyun.renshe.controller.vo.role.CreateRoleVO;
import com.ciyun.renshe.controller.vo.role.UpdateRoleScopeVO;
import com.ciyun.renshe.controller.vo.role.UpdateRoleVO;
import com.ciyun.renshe.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Date 2020/4/8 14:11
 * @Author Admin
 * @Version 1.0
 */
@Validated
@CrossOrigin
@RestController
@Api(tags = "角色模块")
@RequestMapping("/role")
public class RoleController {
    private RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * 获取角色列表
     *
     * @param page
     * @return
     */
    @GetMapping
    @ApiOperation("获取角色列表")
    public Result getRoleList(Page page) {
        return roleService.getRoleList(page);
    }

    /**
     * 获取角色下的员工列表
     *
     * @param page
     * @param roleId
     * @return
     */
    @GetMapping("/user")
    @ApiOperation("获取角色下的员工列表")
    public Result getRoleUserList(Page page, @RequestParam Long roleId) {
        return roleService.getRoleUserList(page, roleId);
    }

    /**
     * 创建角色组
     *
     * @param name 角色组名称
     * @return
     */
    @PostMapping("/group")
    @ApiOperation("创建角色组")
    @ApiImplicitParam(name = "name", value = "角色组名称", required = true, paramType = "query", dataType = "String")
    public Result addRoleGroup(@RequestParam String name) {
        return roleService.addRoleGroup(name);
    }

    /**
     * 创建角色
     *
     * @param roleVO
     * @return
     */
    @PostMapping
    @ApiOperation("创建角色")
    public Result addRole(@RequestBody @Valid CreateRoleVO roleVO) {
        return roleService.addRole(roleVO);
    }

    /**
     * 更新角色
     *
     * @param roleVO
     * @return
     */
    @PostMapping("/update")
    @ApiOperation("更新角色")
    public Result updateRole(@RequestBody @Valid UpdateRoleVO roleVO) {
        return roleService.updateRole(roleVO);
    }

    /**
     * 删除角色
     *
     * @param roleId
     * @return
     */
    @GetMapping("/delete")
    @ApiOperation("删除角色")
    @ApiImplicitParam(name = "roleId", value = "角色 id", required = true, paramType = "query", dataType = "Long")
    public Result deleteRole(@RequestParam Long roleId) {
        return roleService.deleteRole(roleId);
    }

    /**
     * 批量增加员工角色
     *
     * @return
     */
    @PostMapping("/batch/role")
    @ApiOperation("增加员工角色")
    public Result addRolesForEmps(@RequestBody @Valid AddRolesForEmpsVO addRolesForEmpsVO) {
        return roleService.addRolesForEmps(addRolesForEmpsVO);
    }

    /**
     * 批量删除员工角色
     *
     * @param roleIds
     * @param userIds
     * @return
     */
    @GetMapping("/delete/batch/role")
    @ApiOperation("批量删除员工角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleIds", value = "角色id，使用,分隔", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "userIds", value = "用户id，使用,分隔", required = true, paramType = "query", dataType = "String")
    })
    public Result removeRolesForEmps(@RequestParam String roleIds, @RequestParam String userIds) {
        return roleService.removeRolesForEmps(roleIds, userIds);
    }

    /**
     * 设定角色成员管理范围
     *
     * @return
     */
    @PostMapping("/scope/update")
    @ApiOperation("设定角色成员管理范围")
    public Result scopeUpdate(@RequestBody UpdateRoleScopeVO roleScopeVO) {
        return roleService.scopeUpdate(roleScopeVO);
    }
}
