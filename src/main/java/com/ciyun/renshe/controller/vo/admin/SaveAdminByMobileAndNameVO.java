package com.ciyun.renshe.controller.vo.admin;

import com.ciyun.renshe.controller.vo.permission.PermissionData;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Date 2020/5/27 18:15
 * @Author Admin
 * @Version 1.0
 */
@Data
public class SaveAdminByMobileAndNameVO {

    private Integer areaId;
    private Integer streetId;

    @NotBlank(message = "手机号不能为空")
    private String mobile;

    @NotBlank(message = "管理员名称不能为空")
    private String name;

    @NotNull(message = "权限数据不能为空")
    private PermissionData permission;

}
