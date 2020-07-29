package com.ciyun.renshe.controller.vo.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;

/**
 * 设定角色成员管理范围结构
 *
 * @Date 2020/4/8 16:11
 * @Author Admin
 * @Version 1.0
 */
@Valid
@Getter
@Setter
@ToString
@ApiModel("设定角色成员管理范围结构")
public class UpdateRoleScopeVO {

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("角色id")
    private Long roleId;

    @ApiModelProperty("部门ids,使用,分隔")
    private String deptIds;
}
