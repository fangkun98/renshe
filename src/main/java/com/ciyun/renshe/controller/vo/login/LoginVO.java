package com.ciyun.renshe.controller.vo.login;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @Date 2020/4/14 14:27
 * @Author Admin
 * @Version 1.0
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@ApiModel("钉钉登录结构")
public class LoginVO {

    @NotBlank(message = "code 不能为空")
    @ApiModelProperty("免登授权码 https://ding-doc.dingtalk.com/doc#/dev/wcoaey")
    private String code;
}
