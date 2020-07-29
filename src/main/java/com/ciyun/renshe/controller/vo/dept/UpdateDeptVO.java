package com.ciyun.renshe.controller.vo.dept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * 修改部门结构
 *
 * @Date 2020/4/7 15:25
 * @Author Admin
 * @Version 1.0
 */
@Valid
@Getter
@Setter
@ToString
@Accessors(chain = true)
@ApiModel(value = "修改部门结构")
public class UpdateDeptVO {

    /*@NotNull(message = "部门 id 不能为空")
    @ApiModelProperty(value = "部门id")
    private Integer deptId;*/

    /**
     * 部门名称
     */
    @ApiModelProperty(value = "部门名称")
    private String deptName;

    /**
     * 父部门id，根部门id为1
     */
    @ApiModelProperty(value = "父部门id，根部门id为1")
    private String parentId;

    /**
     * 在父部门中的排序值，order值小的排序靠前
     */
    @ApiModelProperty(value = "在父部门中的排序值，order值小的排序靠前")
    private Integer deptOrder;

    /**
     * 钉钉返回的 部门id
     */
    @NotBlank(message = "钉钉部门 id 不能为空")
    @ApiModelProperty(value = "钉钉返回的 部门id")
    private String ddDeptId;

}
