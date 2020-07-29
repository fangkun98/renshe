package com.ciyun.renshe.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 通用结果返回类
 *
 * @Date 2019/8/19 17:10
 * @Author Admin
 * @Version 1.0
 */
@Getter
@Setter
@ApiModel("通用返回值类")
//创建者模式（建造者模式）
@Builder
//set方法返回当前对象
@Accessors(chain = true)
public class Result<T> {
//  @ApiModelProperty属性的说明

    @ApiModelProperty("成功或者失败")
    private boolean flag;
    @ApiModelProperty("返回值码，0 代表成功")
    private Integer code;
    @ApiModelProperty("返回值描述")
    private String message;
    @ApiModelProperty("返回的数据")
    private T data;

    public Result() {
        super();
        this.flag = true;
        this.code = StatusCode.OK;
    }

    public Result(String message) {
        super();
        this.flag = true;
        this.code = StatusCode.OK;
        this.message = message;
    }

    public Result(boolean flag, Integer code, String message) {
        super();
        this.flag = flag;
        this.code = code;
        this.message = message;
    }

    public Result(boolean flag, Integer code, String message, T data) {
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
