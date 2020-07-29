package com.ciyun.renshe.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 反馈问题
 */
@ApiModel(value = "反馈结构")
@Getter
@Setter
@ToString
@Accessors(chain = true)
@TableName(value = "dd_solve")
@JsonIgnoreProperties(value = {"handler"})
public class Solve implements Serializable {

    private static final long serialVersionUID = -1807426420609890034L;

    @TableId(value = "solve_id", type = IdType.AUTO)
    @ApiModelProperty(value = "新增反馈无需传递，修改需要")
    private Integer solveId;

    /**
     * 问题 id
     */
    @TableField(value = "problem_id")
    @ApiModelProperty(value = "问题 id")
    @NotNull(message = "problemId 不能为空")
    private Integer problemId;

    /**
     * 反馈时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField(value = "solve_time")
    @ApiModelProperty(value = "反馈时间", hidden = true)
    private Date solveTime;

    /**
     * 反馈内容
     */
    @TableField(value = "solve_content")
    @ApiModelProperty(value = "反馈内容")
    @NotBlank(message = "反馈内容不能为空")
    private String solveContent;

    /**
     * 反馈问题的人员
     */
    @TableField(value = "solve_user_id")
    @ApiModelProperty(value = "反馈问题的人员")
    private Integer solveUserId;

    /**
     * 1 有效 0 无效
     */
    @TableField(value = "flag")
    @ApiModelProperty(value = "1 有效 0 无效", hidden = true)
    private Integer flag;

}