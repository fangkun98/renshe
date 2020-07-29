package com.ciyun.renshe.controller.vo.manager;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @Date 2020/5/12 14:30
 * @Author Admin
 * @Version 1.0
 */
@Getter
@Setter
@ApiModel("保存管理员")
public class SaveAdmin {

    @ApiModelProperty(value = "管理员名称")
    private String adminName;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String mobile;
}
