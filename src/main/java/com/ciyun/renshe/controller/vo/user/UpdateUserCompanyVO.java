package com.ciyun.renshe.controller.vo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 修改用户公司参数
 *
 * @author kys
 * @version 1.0
 * @date 2020/7/9 10:42
 */
@Data
public class UpdateUserCompanyVO {

    @NotBlank(message = "人社密码不能为空")
    @ApiModelProperty("人社密码")
    private String password;

    @NotBlank(message = "人社用户名不能为空")
    @ApiModelProperty("人社用户名")
    public String unitNo;

    @NotBlank(message = "人社用户名不能为空")
    public Integer userId;
}
