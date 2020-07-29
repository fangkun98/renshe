package com.ciyun.renshe.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 用户表
 */
@ApiModel(value = "com-ciyun-renshe-entity-User")
@Getter
@Setter
@ToString
@TableName(value = "dd_user")
public class User {
    public static final String COL_ROLE_ID = "role_id";
    private static final long serialVersionUID = -5282157289035867961L;
    @TableId(value = "user_id", type = IdType.AUTO)
    @ApiModelProperty(value = "")
    private Integer userId;

    /**
     * 成员名称
     */
    @TableField(value = "name")
    @ApiModelProperty(value = "成员名称")
    private String name;

    /**
     * 公司名称
     */
    @TableField(value = "company_name")
    @ApiModelProperty(value = "公司名称")
    private String companyName;

    /**
     * 公司备注，管理员添加此信息，只在管理端显示
     */
    @TableField(value = "company_remark")
    @ApiModelProperty(value = "公司备注")
    private String companyRemark;

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
     * 部门名称
     */
    @TableField(value = "dept_name")
    @ApiModelProperty(value = "部门名称")
    private String deptName;

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
     * 1 有效 0 无效
     */
    @TableField(value = "flag")
    @ApiModelProperty(value = "1 有效 0 无效")
    private Integer flag;

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
     * 1 为超级管理员 2为管理员 0 为普通用户
     */
    @TableField(value = "is_admin")
    @ApiModelProperty(value = "1 为超级管理员 2为管理员 0 为普通用户")
    private Integer isAdmin;

    /**
     * 1 为已经认证，0为未认证
     */
    @TableField(value = "is_confirm")
    @ApiModelProperty(value = "1 为已经认证，0为未认证")
    private Integer isConfirm;

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
     * 管理员是否在线 1 在线 0不在线
     */
    @TableField(value = "is_on_line")
    @ApiModelProperty(value = "管理员是否在线 1 在线 0不在线")
    private Integer isOnLine;

    /**
     * 是否是内部人员 1 是 0 否
     */
    @TableField(value = "is_inner_user")
    @ApiModelProperty(value = "是否是内部人员 1 是 0 否")
    private Integer isInnerUser;

    @TableField(exist = false)
    private Integer roleId;

    @TableField(exist = false)
    private Chat chatInfo;

    public static final String COL_USER_ID = "user_id";

    public static final String COL_NAME = "name";

    public static final String COL_COMPANY_NAME = "company_name";

    public static final String COL_CREDIT_CODE = "credit_code";

    public static final String COL_UNIT_NO = "unit_no";

    public static final String COL_PASSWORD = "password";

    public static final String COL_ID_CARD = "id_card";

    public static final String COL_POSITION = "position";

    public static final String COL_FIXED_PHONE = "fixed_phone";

    public static final String COL_DEPT_NAME = "dept_name";

    public static final String COL_MOBILE = "mobile";

    public static final String COL_REMARK = "remark";

    public static final String COL_EMAIL = "email";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_FLAG = "flag";

    public static final String COL_DD_USER_ID = "dd_user_id";

    public static final String COL_ORGANIZE_CODE = "organize_code";

    public static final String COL_CITY = "city";

    public static final String COL_AREA = "area";

    public static final String COL_STREET = "street";

    public static final String COL_POSITION_X = "position_x";

    public static final String COL_POSITION_Y = "position_y";

    public static final String COL_ADDRESS = "address";

    public static final String COL_IS_ADMIN = "is_admin";

    public static final String COL_IS_CONFIRM = "is_confirm";

    public static final String COL_AREA_ID = "area_id";

    public static final String COL_STREET_ID = "street_id";

    public static final String COL_ES_ID = "es_id";

    public static final String COL_INDUSTRY_ID = "industry_id";

    public static final String COL_IS_ON_LINE = "is_on_line";

    public static final String COL_IS_INNER_USER = "is_inner_user";
}