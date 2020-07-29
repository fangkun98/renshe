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
 * 添加部门结构
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
@ApiModel(value = "添加部门结构")
public class CreateDeptVO {

    /**
     * 部门名称
     */
    @NotBlank(message = "部门名称不能为空")
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
     * 企业群群主的userId
     */
    @ApiModelProperty(value = "企业群群主的userId")
    private String orgDeptOwner;

    /**
     * 群所在部门
     */
    @ApiModelProperty(value = "企业群群主的userId")
    private String position;

}
