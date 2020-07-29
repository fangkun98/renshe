package com.ciyun.renshe.controller.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 修改钉钉小程序用户信息
 *
 * @Date 2020/4/7 14:56
 * @Author Admin
 * @Version 1.0
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@ApiModel(value = "修改钉钉小程序用户信息")
public class UpdateUserInfoVO implements Serializable {

    @NotNull(message = "userId 不能为空")
    @ApiModelProperty(value = "用户id，必传")
    private Integer userId;

    /**
     * 成员名称
     */
    @NotEmpty(message = "成员名称不能为空")
    @ApiModelProperty(value = "成员名称,必传")
    private String name;

    /**
     * 手机号码，企业内必须唯一，不可重复。
     */
    @ApiModelProperty(value = "手机号码，企业内必须唯一，不可重复。")
    private String mobile;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String email;

    /**
     * 钉钉返回的 userId
     */
    @NotBlank(message = "ddUserId 不能为空")
    @ApiModelProperty(value = "钉钉返回的 userId，必传")
    private String ddUserId;

    /**
     * 组织代码
     */
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

    /**
     * 区id
     */
    @ApiModelProperty(value = "区id")
    private Integer areaId;

    /**
     * 街道id
     */
    @ApiModelProperty(value = "街道id")
    private Integer streetId;

    /**
     * 之前的 街道Id
     */
    @ApiModelProperty(value = "修改之前的 街道Id")
    private Integer beforeStreetId;

    /**
     * 企业规模id
     */
    @ApiModelProperty(value = "企业规模id")
    private Integer esId;

    /**
     * 行业id
     */
    @ApiModelProperty(value = "行业id")
    private List<Map<String,Object>> industryId;

    /**
     * 详细地址
     */
    @ApiModelProperty(value = "企业详细地址")
    private String address;

    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    private String companyName;
    /*@NotBlank(message = "群id 不能为空")
    @ApiModelProperty(value = "群id，必传")
    private String chatId;*/

    /**
     * 信用代码
     */
    @ApiModelProperty(value = "信用代码")
    private String creditCode;

    /**
     * 身份证号
     */
    @ApiModelProperty(value = "身份证号")
    private String idCard;

    /**
     * 职务
     */
    @ApiModelProperty(value = "职务")
    private String position;

    /**
     * 固定电话
     */
    @ApiModelProperty(value = "固定电话")
    private String fixedPhone;

    /**
     * 选择的街道id
     */
    @ApiModelProperty(value = "选择的街道id")
    private String selectChatId;

    @ApiModelProperty(value = "申请理由")
    private String reason;

}
