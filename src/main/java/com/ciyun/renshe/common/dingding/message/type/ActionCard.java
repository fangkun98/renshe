package com.ciyun.renshe.common.dingding.message.type;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * 整体卡片消息结构
 */
@Valid
@Getter
@Setter
@Accessors(chain = true)
@ApiModel("整体卡片消息结构")
public class ActionCard {

    public static final String MSG_TYPE = "action_card";

    /**
     * 透出到会话列表和通知的文案，最长64个字符
     */
    @NotBlank(message = "title 不能为空")
    @ApiModelProperty("透出到会话列表和通知的文案，最长64个字符")
    private String title;

    /**
     * 消息内容，支持markdown，语法参考标准markdown语法。建议1000个字符以内
     */
    @ApiModelProperty("消息内容，支持markdown，语法参考标准markdown语法。建议1000个字符以内")
    private String markdown;

    @ApiModelProperty("使用整体跳转ActionCard样式时的标题，必须与single_url同时设置，最长20个字符")
    private String singleTitle;

    @ApiModelProperty("消息点击链接地址，当发送消息为小程序时支持小程序跳转链接，最长500个字符")
    private String singleUrl;

}