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
import lombok.experimental.Accessors;

/**
 * 区域权限表
 */
@ApiModel(value = "com-ciyun-renshe-entity-AreaPermissions")
@Getter
@Setter
@ToString
@Accessors(chain = true)
@TableName(value = "sys_area_permissions")
public class AreaPermissions {
    public static final String COL_FLAG = "flag";
    @TableId(value = "permission_id", type = IdType.AUTO)
    @ApiModelProperty(value = "")
    private Integer permissionId;

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
     * 1 super 2 市管理员 3 区管理员 4 街道管理员 5. 网格管理员
     */
    @TableField(value = "admin_type")
    @ApiModelProperty(value = " 1 super 2 市管理员 3 区管理员 4 街道管理员 5. 网格管理员")
    private Integer adminType;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value = "用户id")
    private Integer userId;

    /**
     * 网格id
     */
    @TableField(value = "grid_id")
    @ApiModelProperty(value = "网格id")
    private Integer gridId;

    public static final String COL_PERMISSION_ID = "permission_id";

    public static final String COL_AREA_ID = "area_id";

    public static final String COL_STREET_ID = "street_id";

    public static final String COL_ADMIN_TYPE = "admin_type";

    public static final String COL_USER_ID = "user_id";

    public static final String COL_GRID_ID = "grid_id";
}