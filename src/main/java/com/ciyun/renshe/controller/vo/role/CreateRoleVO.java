package com.ciyun.renshe.controller.vo.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 添加角色对应结构
 *
 * @Date 2020/4/8 14:52
 * @Author Admin
 * @Version 1.0
 */
@Valid
@Getter
@Setter
@ToString
@ApiModel("添加角色对应结构")
public class CreateRoleVO {
    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    @ApiModelProperty(value = "角色名称")
    private String roleName;

    /**
     * 角色组id
     */
    @NotNull(message = "角色组id 不能为空")
    @ApiModelProperty(value = "角色组id")
    private Long groupId;
}
