package com.ciyun.renshe.controller.vo.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 批量增加员工角色对应结构
 *
 * @Date 2020/4/8 15:26
 * @Author Admin
 * @Version 1.0
 */
@Valid
@Getter
@Setter
@ToString
@ApiModel("批量增加员工角色对应结构")
public class AddRolesForEmpsVO {

    @NotEmpty(message = "角色 id 数组 不能为空")
    @ApiModelProperty("角色 id 数组")
    private List<String> roleIds;

    @NotEmpty(message = "用户 id 数组")
    @ApiModelProperty("用户 id 数组")
    private List<String> userIds;
}
