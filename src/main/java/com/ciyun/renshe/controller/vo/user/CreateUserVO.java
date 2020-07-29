package com.ciyun.renshe.controller.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 添加成员结构
 *
 * @Date 2020/4/7 14:56
 * @Author Admin
 * @Version 1.0
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@ApiModel(value = "添加成员结构")
public class CreateUserVO implements Serializable {

    private static final long serialVersionUID = 7898010873665153754L;

    @NotBlank(message = "成员名称不能为空")
    @ApiModelProperty(value = "成员名称,必传!")
    private String name;

    @ApiModelProperty(value = "职位信息")
    private String position;

    @NotBlank(message = "手机号不能为空")
    @ApiModelProperty(value = "手机号码，企业内必须唯一，不可重复。必传!")
    private String mobile;

    @ApiModelProperty(value = "办公地点")
    private String workPlace;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty(value = "工号")
    private Integer jobNumber;

    @ApiModelProperty(value = "入职时间")
    private Date hiredDate;

    @NotEmpty(message = "成员所属部门 id 不能为空")
    @ApiModelProperty(value = "数组类型，数组里面值为整型，成员所属部门id列表,数组中必须有 1,必传!")
    private List<Long> department;

    @ApiModelProperty(value = "组织代码")
    private String organizeCode;

    /**
     * 市
     */
    @ApiModelProperty(value = "市")
    private String city;

    /**
     * 区
     */
    @ApiModelProperty(value = "区")
    private String area;

    /**
     * 街道
     */
    @ApiModelProperty(value = "街道")
    private String street;

    /**
     * X轴坐标
     */
    @ApiModelProperty(value = "X轴坐标")
    private String positionX;

    /**
     * Y轴坐标
     */
    @ApiModelProperty(value = "Y轴坐标")
    private String positionY;

}
