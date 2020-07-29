package com.ciyun.renshe.controller.vo.problem;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 保存问题接收参数
 *
 * @Date 2020/4/3 15:28
 * @Author Admin
 * @Version 1.0
 */
@ApiModel(value = "保存问题接收参数")
@Getter
@Setter
public class CreateProblemVo {

    /**
     * 提出问题的用户id
     */
    @ApiModelProperty(value = "提出问题的用户id")
    @NotNull(message = "提出问题的用户id 不能为空")
    private Integer problemUserId;

    /**
     * 提出问题内容
     */
    @NotBlank(message = "问题内容不能为空")
    @ApiModelProperty(value = "提出问题内容")
    private String problemContent;

}
