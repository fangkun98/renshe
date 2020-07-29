package com.ciyun.renshe.controller.vo.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * 修改角色信息对应结构
 *
 * @Date 2020/4/8 14:52
 * @Author Admin
 * @Version 1.0
 */
@Valid
@Getter
@Setter
@ToString
@ApiModel("修改角色信息对应结构")
public class UpdateRoleVO {

    /**
     * 角色id
     */
    @NotBlank(message = "角色id不能为空")
    @ApiModelProperty(value = "角色id")
    private String roleId;

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    @ApiModelProperty(value = "角色名称")
    private String roleName;

}
