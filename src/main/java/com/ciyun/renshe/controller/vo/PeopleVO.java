package com.ciyun.renshe.controller.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Date 2020/4/2 11:45
 * @Author Admin
 * @Version 1.0
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel("peopleVO")
public class PeopleVO implements Serializable {

    private static final long serialVersionUID = -2744924033239490967L;

    @ApiModelProperty("用户名称")
    @NotBlank(message = "用户名称不能为空")
    private String userName;

    @ApiModelProperty("用户密码")
    @NotBlank(message = "密码不能为空")
    private String password;
}
