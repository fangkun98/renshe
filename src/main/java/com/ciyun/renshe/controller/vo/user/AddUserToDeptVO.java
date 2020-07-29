package com.ciyun.renshe.controller.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @Date 2020/4/10 14:27
 * @Author Admin
 * @Version 1.0
 */
@Valid
@Getter
@Setter
@ApiModel("")
public class AddUserToDeptVO {

    @NotEmpty(message = "数组不能为空")
    @ApiModelProperty("接收数组")
    public List<UpdateUserVO> userVOS;

}
