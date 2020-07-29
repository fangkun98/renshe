package com.ciyun.renshe.controller.vo.solve;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 添加反馈问题结构
 *
 * @Date 2020/4/3 15:34
 * @Author Admin
 * @Version 1.0
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@ApiModel(value = "添加反馈问题结构")
public class CreateSolveVo {

    /**
     * 问题 id
     */
    @ApiModelProperty(value = "问题 id")
    @NotNull(message = "problemId 不能为空")
    private Integer problemId;

    /**
     * 反馈内容
     */
    @ApiModelProperty(value = "反馈内容")
    @NotBlank(message = "反馈内容不能为空")
    private String solveContent;

    /**
     * 反馈问题的人员
     */
    @ApiModelProperty(value = "反馈问题的人员")
    private Integer solveUserId;

}





