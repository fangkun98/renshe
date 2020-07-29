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

@ApiModel(value="com-ciyun-renshe-entity-UserSwap")
@Getter
@Setter
@ToString
@TableName(value = "sys_user_swap")
public class UserSwap {
    /**
     * 用户id
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value="用户id")
    private Integer userId;

    /**
     * swapid
     */
    @TableField(value = "swap_id")
    @ApiModelProperty(value="swapid")
    private Integer swapId;

    public static final String COL_USER_ID = "user_id";

    public static final String COL_SWAP_ID = "swap_id";
}