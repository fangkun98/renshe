package com.ciyun.renshe.controller.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 修改成员结构
 *
 * @Date 2020/4/7 14:56
 * @Author Admin
 * @Version 1.0
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@ApiModel(value = "修改成员结构")
public class UpdateUserVO implements Serializable {

    private static final long serialVersionUID = -3162838749792741952L;

    @ApiModelProperty(value = "成员名称")
    private String name;

    @ApiModelProperty(value = "职位信息")
    private String position;

    @ApiModelProperty(value = "手机号码，企业内必须唯一，不可重复。")
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

    @ApiModelProperty(value = "数组类型，数组里面值为整型，成员所属部门id列表")
    private List<Long> department;

    /**
     * 钉钉返回的 userId
     */
    @NotBlank(message = "ddUserId 不能为空")
    @ApiModelProperty(value = "钉钉返回的 userId")
    private String ddUserId;
}
