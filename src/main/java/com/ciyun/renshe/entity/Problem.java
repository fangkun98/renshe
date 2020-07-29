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
import java.util.List;

/**
 * 问题表
 */
@ApiModel(value = "com-ciyun-renshe-entity-Problem")
@Getter
@Setter
@ToString
@TableName(value = "dd_problem")
public class Problem {
    private static final long serialVersionUID = 6948791039435549917L;
    @TableId(value = "problem_id", type = IdType.AUTO)
    @ApiModelProperty(value = "")
    private Integer problemId;

    /**
     * 提出问题的用户id
     */
    @TableField(value = "problem_user_id")
    @ApiModelProperty(value = "提出问题的用户id")
    private Integer problemUserId;

    /**
     * 1 已反馈 0 未反馈
     */
    @TableField(value = "is_solve")
    @ApiModelProperty(value = "1 已反馈 0 未反馈")
    private Integer isSolve;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 提出问题内容
     */
    @TableField(value = "problem_content")
    @ApiModelProperty(value = "提出问题内容")
    private String problemContent;

    /**
     * 1 有效 0 无效
     */
    @TableField(value = "flag")
    @ApiModelProperty(value = "1 有效 0 无效")
    private Integer flag;

    /**
     * 提出问题者所在的区
     */
    @TableField(value = "area_id")
    @ApiModelProperty(value = "提出问题者所在的区")
    private Integer areaId;

    /**
     * 街道id
     */
    @TableField(value = "street_id")
    @ApiModelProperty(value = "街道id")
    private Integer streetId;

    /**
     * 网格id
     */
    @TableField(value = "grid_id")
    @ApiModelProperty(value = "网格id")
    private Integer gridId;

    /**
     * 用户是否已经查看反馈 1 查看 0 未查看
     */
    @TableField(value = "is_look")
    @ApiModelProperty(value = "用户是否已经查看反馈 1 查看 0 未查看")
    private Integer isLook;

    @TableField(exist = false)
    private List<Solve> solveList;

    @TableField(exist = false)
    private String companyName;

    @TableField(exist = false)
    private String solveName;

    public static final String COL_PROBLEM_ID = "problem_id";

    public static final String COL_PROBLEM_USER_ID = "problem_user_id";

    public static final String COL_IS_SOLVE = "is_solve";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_PROBLEM_CONTENT = "problem_content";

    public static final String COL_FLAG = "flag";

    public static final String COL_AREA_ID = "area_id";

    public static final String COL_STREET_ID = "street_id";

    public static final String COL_GRID_ID = "grid_id";

    public static final String COL_IS_LOOK = "is_look";
}