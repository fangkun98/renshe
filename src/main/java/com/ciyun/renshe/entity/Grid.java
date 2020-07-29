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

/**
 * 网格员
 */
@ApiModel(value = "com-ciyun-renshe-entity-Grid")
@Getter
@Setter
@ToString
@TableName(value = "sys_grid")
public class Grid {
    @TableId(value = "grid_id", type = IdType.AUTO)
    @ApiModelProperty(value = "网格Id")
    private Integer gridId;

    /**
     * 街道id
     */
    @TableField(value = "street_id")
    @ApiModelProperty(value = "街道id")
    private Integer streetId;

    /**
     * 网格名称
     */
    @TableField(value = "grid_name")
    @ApiModelProperty(value = "网格名称")
    private String gridName;

    /**
     * 对应的群id
     */
    @TableField(value = "chat_id")
    @ApiModelProperty(value = "对应的群id")
    private String chatId;

    /**
     * 1 有效 0无效
     */
    @TableField(value = "flag")
    @ApiModelProperty(value = " 1 有效 0无效")
    private Integer flag;

    public static final String COL_GRID_ID = "grid_id";

    public static final String COL_STREET_ID = "street_id";

    public static final String COL_GRID_NAME = "grid_name";

    public static final String COL_CHAT_ID = "chat_id";

    public static final String COL_FLAG = "flag";
}