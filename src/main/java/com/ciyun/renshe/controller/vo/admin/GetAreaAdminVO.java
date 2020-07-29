package com.ciyun.renshe.controller.vo.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @Date 2020/4/15 10:29
 * @Author Admin
 * @Version 1.0
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@ApiModel("获取对应区域的管理员信息所需数据")
public class GetAreaAdminVO {

    @ApiModelProperty("区id")
    private Integer areaId;
    @ApiModelProperty("街道id")
    private Integer streetId;
    @ApiModelProperty("网格id")
    private Integer gridId;
    @NotBlank(message = "权限id不能为空")
    @ApiModelProperty("权限的id")
    private Integer roleId;
}
