package com.ciyun.renshe.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 分页结果返回类
 *
 * @Date 2019/8/19 17:15
 * @Author Admin
 * @Version 1.0
 */

@Getter
@Setter
@ApiModel("通用分页返回对象")
@AllArgsConstructor
public class PageResult {
    @ApiModelProperty("总条数")
    private Long total;
    @ApiModelProperty("分页数据数据")
    private Object rows;

}
