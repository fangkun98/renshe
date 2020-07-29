package com.ciyun.renshe.entity.po;

import com.ciyun.renshe.entity.Chat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 企业规模表
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class EnterpriseScalePO {

    @ApiModelProperty(value = "")
    private Integer esId;

    /**
     * 规模名称
     */
    @ApiModelProperty(value = "规模名称")
    private String name;

    /**
     * 1 有效 0无效
     */
    @ApiModelProperty(value = "1 有效 0无效")
    private Integer flag;

    /**
     * 1 为 规模 0为标签
     */
    @ApiModelProperty(value = "1 为 规模 0为标签")
    private Integer state;

    @ApiModelProperty(value = "创建者id")
    private Integer createUserId;

    private List<Chat> chatList;

}