package com.ciyun.renshe.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "com-ciyun-renshe-entity-UserInfoSwap")
@Getter
@Setter
@ToString
@TableName(value = "sys_user_info_swap")
public class UserInfoSwap {
    @TableId(value = "swap_id", type = IdType.AUTO)
    @ApiModelProperty(value = "")
    private Integer swapId;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value = "用户id")
    private Integer userId;

    /**
     * 理由
     */
    @TableField(value = "reason")
    @ApiModelProperty(value = "理由")
    private String reason;

    /**
     * 用户名称
     */
    @TableField(value = "name")
    @ApiModelProperty(value = "用户名称")
    private String name;

    /**
     * 公司名称
     */
    @TableField(value = "company_name")
    @ApiModelProperty(value = "公司名称")
    private String companyName;

    /**
     * 统一社会信用代码
     */
    @TableField(value = "credit_code")
    @ApiModelProperty(value = "统一社会信用代码")
    private String creditCode;

    /**
     * 企业在社保系统中的单位编号
     */
    @TableField(value = "unit_no")
    @ApiModelProperty(value = "企业在社保系统中的单位编号")
    private String unitNo;

    /**
     * 企业在社保系统中的单位编号中的密码
     */
    @TableField(value = "password")
    @ApiModelProperty(value = "企业在社保系统中的单位编号中的密码")
    private String password;

    /**
     * 身份证号
     */
    @TableField(value = "id_card")
    @ApiModelProperty(value = "身份证号")
    private String idCard;

    /**
     * 职务
     */
    @TableField(value = "position")
    @ApiModelProperty(value = "职务")
    private String position;

    /**
     * 固定电话
     */
    @TableField(value = "fixed_phone")
    @ApiModelProperty(value = "固定电话")
    private String fixedPhone;

    /**
     * 手机号码，企业内必须唯一，不可重复。
     */
    @TableField(value = "mobile")
    @ApiModelProperty(value = "手机号码，企业内必须唯一，不可重复。")
    private String mobile;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    @ApiModelProperty(value = "邮箱")
    private String email;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 钉钉返回的 userId
     */
    @TableField(value = "dd_user_id")
    @ApiModelProperty(value = "钉钉返回的 userId")
    private String ddUserId;

    /**
     * 组织代码
     */
    @TableField(value = "organize_code")
    @ApiModelProperty(value = "组织代码")
    private String organizeCode;

    /**
     * 市
     */
    @TableField(value = "city")
    @ApiModelProperty(value = "市")
    private String city;

    /**
     * 区
     */
    @TableField(value = "area")
    @ApiModelProperty(value = "区")
    private String area;

    /**
     * 街道
     */
    @TableField(value = "street")
    @ApiModelProperty(value = "街道")
    private String street;

    /**
     * X轴坐标
     */
    @TableField(value = "position_x")
    @ApiModelProperty(value = "X轴坐标")
    private String positionX;

    /**
     * Y轴坐标
     */
    @TableField(value = "position_y")
    @ApiModelProperty(value = "Y轴坐标")
    private String positionY;

    /**
     * 企业详细地址
     */
    @TableField(value = "address")
    @ApiModelProperty(value = "企业详细地址")
    private String address;

    /**
     * 区id
     */
    @TableField(value = "area_id")
    @ApiModelProperty(value = "区id")
    private Integer areaId;

    /**
     * 街道id
     */
    @TableField(value = "street_id")
    @ApiModelProperty(value = "街道id")
    private Integer streetId;

    /**
     * 企业规模id  sys_enterprise_scale
     */
    @TableField(value = "es_id")
    @ApiModelProperty(value = "企业规模id  sys_enterprise_scale")
    private Integer esId;

    /**
     * 行业id     sys_enterprise_scale
     */
    @TableField(value = "industry_id")
    @ApiModelProperty(value = "行业id     sys_enterprise_scale")
    private String industryId;

    /**
     * 0 未审核  1 同意  2不同意
     */
    @TableField(value = "is_pass")
    @ApiModelProperty(value = "0 未审核  1 同意  2不同意 ")
    private Integer isPass;

    /**
     * 用户选择的街道id
     */
    @TableField(value = "select_chat_id")
    @ApiModelProperty(value = "用户选择的街道id")
    private String selectChatId;

    /**
     * 之前的街道id
     */
    @TableField(value = "before_street_id")
    @ApiModelProperty(value = "之前的街道id")
    private Integer beforeStreetId;

    /**
     * 申请加入群名称
     */
    @TableField(value = "chat_name")
    @ApiModelProperty(value = "申请加入群名称")
    private String chatName;

    /**
     * 当前群名称
     */
    @TableField(value = "current_chat_name")
    @ApiModelProperty(value = "当前群名称")
    private String currentChatName;

    /**
     * 申请时间
     */
    @TableField(value = "application_time")
    @ApiModelProperty(value = "申请时间")
    private Date applicationTime;

    /**
     * 管理员回复信息
     */
    @TableField(value = "admin_remark")
    @ApiModelProperty(value = "管理员回复信息")
    private String adminRemark;

    /**
     * 审核人
     */
    @TableField(value = "admin_name")
    @ApiModelProperty(value = "审核人")
    private String adminName;

    public static final String COL_SWAP_ID = "swap_id";

    public static final String COL_USER_ID = "user_id";

    public static final String COL_REASON = "reason";

    public static final String COL_NAME = "name";

    public static final String COL_COMPANY_NAME = "company_name";

    public static final String COL_CREDIT_CODE = "credit_code";

    public static final String COL_UNIT_NO = "unit_no";

    public static final String COL_PASSWORD = "password";

    public static final String COL_ID_CARD = "id_card";

    public static final String COL_POSITION = "position";

    public static final String COL_FIXED_PHONE = "fixed_phone";

    public static final String COL_MOBILE = "mobile";

    public static final String COL_REMARK = "remark";

    public static final String COL_EMAIL = "email";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_DD_USER_ID = "dd_user_id";

    public static final String COL_ORGANIZE_CODE = "organize_code";

    public static final String COL_CITY = "city";

    public static final String COL_AREA = "area";

    public static final String COL_STREET = "street";

    public static final String COL_POSITION_X = "position_x";

    public static final String COL_POSITION_Y = "position_y";

    public static final String COL_ADDRESS = "address";

    public static final String COL_AREA_ID = "area_id";

    public static final String COL_STREET_ID = "street_id";

    public static final String COL_ES_ID = "es_id";

    public static final String COL_INDUSTRY_ID = "industry_id";

    public static final String COL_IS_PASS = "is_pass";

    public static final String COL_SELECT_CHAT_ID = "select_chat_id";

    public static final String COL_BEFORE_STREET_ID = "before_street_id";

    public static final String COL_CHAT_NAME = "chat_name";

    public static final String COL_CURRENT_CHAT_NAME = "current_chat_name";

    public static final String COL_APPLICATION_TIME = "application_time";

    public static final String COL_ADMIN_REMARK = "admin_remark";

    public static final String COL_ADMIN_NAME = "admin_name";
}