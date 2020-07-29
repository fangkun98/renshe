package com.ciyun.renshe.controller.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * @Date 2020/4/16 10:53
 * @Author Admin
 * @Version 1.0
 */
@Valid
@ApiModel("注册钉钉小程序时添加用户信息")
@Getter
@Setter
@ToString
public class UpdateUser2DDVO {

    @NotNull(message = "用户id 不能为空")
    @ApiModelProperty("用户id")
    public Integer userId;

    @ApiModelProperty("手机号")
    public String mobile;

    @ApiModelProperty("成员名称")
    public String name;

    @ApiModelProperty("区Id")
    public Integer areaId;

    @ApiModelProperty("区名称")
    public String area;

    @ApiModelProperty("街道id")
    public Integer streetId;

    @ApiModelProperty("街道名称")
    public String street;

    @NotBlank(message = "钉钉UserId 不能为空")
    @ApiModelProperty("钉钉UserId")
    public String ddUserId;

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
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    private String companyName;

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

    @ApiModelProperty(value = "理由")
    private String reason;

}
