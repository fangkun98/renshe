package com.ciyun.renshe.controller;

import com.ciyun.renshe.common.MessageInfo;
import com.ciyun.renshe.common.Page;
import com.ciyun.renshe.common.Result;
import com.ciyun.renshe.common.StatusCode;
import com.ciyun.renshe.controller.vo.manager.SaveAdminVO;
import com.ciyun.renshe.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Date 2020/5/12 14:22
 * @Author Admin
 * @Version 1.0
 */
@Api(tags = "管理员操作")
@Validated
@CrossOrigin
@RestController
@RequestMapping("/adminManager")
public class AdminManagerController {

    public AdminService adminService;

    public AdminManagerController(AdminService adminService) {
        this.adminService = adminService;
    }

    /**
     * 查询全部管理员
     *
     * @param page
     * @param adminName
     * @param mobile
     * @return
     */
    @GetMapping
    @ApiOperation("查询全部管理员")
    public Result findAllAdmin(Page page, String adminName, String mobile) {
        return adminService.findAllAdmin(page, adminName, mobile);

    }

    /**
     * 保存管理员
     *
     * @param admin
     * @return
     */
    @PostMapping
    @ApiOperation("保存管理员")
    public Result saveAdmin(@RequestBody @Valid SaveAdminVO admin) {
        adminService.saveAdmin(admin);
        return new Result(true, StatusCode.OK, MessageInfo.ADD_INFO.getInfo());
    }

    /**
     * 根据adminId 删除管理员
     *
     * @return
     */
    @GetMapping("/delete")
    @ApiOperation("根据adminId 删除管理员")
    public Result deleteAdminById(Integer adminId) {
        return adminService.deleteAdminById(adminId);
    }
}
