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

@ApiModel(value = "com-ciyun-renshe-entity-UserChat")
@Getter
@Setter
@ToString
@Accessors(chain = true)
@TableName(value = "dd_user_chat")
public class UserChat {
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "")
    private Long id;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value = "用户id")
    private Integer userId;

    /**
     * 群id
     */
    @TableField(value = "chat_id")
    @ApiModelProperty(value = "群id")
    private String chatId;

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
}