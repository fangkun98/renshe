package com.ciyun.renshe.common.dingding.message.type;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 链接消息
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel("链接消息结构")
public class Link {
    /**
     * 消息类型，此时固定为：link
     */
    public static final String MSG_TYPE = "link";

    @ApiModelProperty("消息点击链接地址，当发送消息为小程序时支持小程序跳转链接")
    private String messageUrl;
    @ApiModelProperty("图片地址")
    private String picUrl;
    @ApiModelProperty("消息标题，建议100字符以内")
    private String title;
    @ApiModelProperty("消息描述，建议500字符以内")
    private String text;
}