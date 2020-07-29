package com.ciyun.renshe.controller.vo.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Date 2020/4/27 14:45
 * @Author Admin
 * @Version 1.0
 */
@Getter
@Setter
@ApiModel("给角色添加权限")
public class SaveRolePermissionVO {
    @NotNull(message = "角色id不能为空")
    @ApiModelProperty("角色id")
    private Integer roleId;

    @NotBlank(message = "权限内容不能为空")
    @ApiModelProperty("权限内容")
    private String permissions;
}
