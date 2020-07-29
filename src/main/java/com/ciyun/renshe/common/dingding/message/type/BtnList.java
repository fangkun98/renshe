package com.ciyun.renshe.common.dingding.message.type;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 卡片消息结构 对应的 按钮结构
 */
@Getter
@Setter
@ApiModel("卡片消息结构 对应的 按钮结构")
public class BtnList {
    /**
     * 使用独立跳转ActionCard样式时的按钮的标题，最长20个字符
     */
    @ApiModelProperty("使用独立跳转ActionCard样式时的按钮的标题，最长20个字符")
    private String title;

    /**
     * 消息点击链接地址，当发送消息为小程序时支持小程序跳转链接，最长500个字符
     */
    @ApiModelProperty("消息点击链接地址，当发送消息为小程序时支持小程序跳转链接，最长500个字符")
    private String actionUrl;
}

