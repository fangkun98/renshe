package com.ciyun.renshe.controller;

import com.ciyun.renshe.common.MessageInfo;
import com.ciyun.renshe.common.Result;
import com.ciyun.renshe.common.StatusCode;
import com.ciyun.renshe.controller.vo.admin.*;
import com.ciyun.renshe.service.AdminService;
import com.ciyun.renshe.service.AreaService;
import com.ciyun.renshe.service.SysRoleService;
import com.taobao.api.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Date 2020/4/15 9:38
 * @Author Admin
 * @Version 1.0
 */
@Api(tags = "后台管理员操作")
//校验数据 若出现数据异常会统一抛出异常
@Validated
//处理跨域请求
@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AreaService areaService;
    private final SysRoleService sysRoleService;
    private final AdminService adminService;

    public AdminController(AreaService areaService, SysRoleService sysRoleService, AdminService adminService) {
        this.areaService = areaService;
        this.sysRoleService = sysRoleService;
        this.adminService = adminService;
    }

    /**
     * 查询全部管理员
     *
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("查询全部管理员")
    public Result findAllAdmin() {
        return areaService.findAllAdmin();
    }

    /**
     * 根据 区 街道，网格查询对应管理员
     *
     * @param areaAdminVO
     * @return
     */
    @GetMapping
    @ApiOperation("根据 区 街道，网格查询对应管理员")
    public Result getAreaAdmin(GetAreaAdminVO areaAdminVO) {
        return areaService.getAreaAdmin(areaAdminVO);
    }

    /**
     * 保存区域管理员 , 包含 区，街道
     *
     * @param saveAdminVO
     * @return
     */
    @PostMapping
    @ApiOperation("保存区域管理员")
    public Result saveAreaAdmin(@RequestBody @Valid SaveAdminVO saveAdminVO) {
        return areaService.saveAreaAdmin(saveAdminVO);
    }

    /**
     * 保存网格管理员 还会创建对应群
     *
     * @param saveGridAdminVO
     * @return
     */
    @PostMapping("/grid")
    @ApiOperation("保存网格管理员 还会创建对应群")
    public Result saveGridAdmin(@RequestBody @Valid SaveGridAdminVO saveGridAdminVO) throws ApiException {
        return areaService.saveGridAdmin(saveGridAdminVO);
    }

    /**
     * 删除用户对应区域权限
     *
     * @param deleteAdminVO
     * @return
     */
    @GetMapping("/delete")
    @ApiOperation("删除用户对应区域权限")
    public Result deleteAreaAdmin(DeleteAdminVO deleteAdminVO) {
        areaService.deleteAreaAdmin(deleteAdminVO);
        return new Result(true, StatusCode.OK, MessageInfo.DELETE_INFO.getInfo());
    }

    /**
     * 修改网格管理员，需要修改对应 群的群主
     *
     * @return
     */
    @PostMapping("/update/grid")
    @ApiOperation("修改网格管理员，需要修改对应 群的群主")
    public Result updateGridAdmin(@RequestBody @Valid UpdateGridAdminVO updateGridAdminVO) {
        return areaService.updateGridAdmin(updateGridAdminVO);
    }

    /**
     * 查询全部角色
     *
     * @return
     */
    @GetMapping("/role")
    @ApiOperation("查询全部角色")
    public Result findAllRole() {
        return sysRoleService.findAllRole();
    }

    /**
     * 给对应的角色分配权限
     *
     * @return
     */
    @PostMapping("/role/permission")
    @ApiOperation("给对应的角色分配权限")
    public Result saveRolePermission(@RequestBody @Valid SaveRolePermissionVO permissionVO) {
        sysRoleService.saveRolePermission(permissionVO.getRoleId(), permissionVO.getPermissions());
        return new Result(true, StatusCode.OK, MessageInfo.ADD_INFO.getInfo());
    }

    /**
     * 根据手机号和名称添加管理员
     *
     * @return
     */
    @PostMapping("/add/admin")
    @ApiOperation("根据手机号和名称添加管理员")
    public Result saveAdminByMobileAndName(@RequestBody @Valid SaveAdminByMobileAndNameVO adminVO) {
        return adminService.saveAdminByMobileAndName(adminVO);
    }
}
