package com.ciyun.renshe.controller.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @Date 2020/4/15 14:41
 * @Author Admin
 * @Version 1.0
 */
@Getter
@Setter
@ApiModel("向钉钉小程序中添加用户")
public class SaveUserToDDVO {

    @NotBlank(message = "人社密码不能为空")
    @ApiModelProperty("人社密码不能为空")
    private String password;

    /*@NotBlank(message = "账号不能为空")
    @ApiModelProperty("企业在社保系统中的登录账户")
    private String unitNo;*/

    /*public String mobile;*/
    @NotBlank(message = "人社用户名不能为空")
    @ApiModelProperty("人社用户名不能为空")
    public String unitNo;

    @NotBlank(message = "调用接口凭证")
    @ApiModelProperty("免登授权码")
    public String code;


   /* @ApiModelProperty("公司名称")
    public String companyName;


    @ApiModelProperty("社会信用代码")
    public String creditCode;*/



}
