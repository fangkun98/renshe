package com.ciyun.renshe.controller.vo.chat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * 发送群消息结构
 *
 * @Date 2020/4/8 17:14
 * @Author Admin
 * @Version 1.0
 */
@Valid
@Getter
@Setter
@ToString
@ApiModel("发送群消息")
public class SendChatVO {

    @NotBlank(message = "群会话的 id 不能为空")
    @ApiModelProperty("群会话的id，可以通过以下方式获取：\n" +
            "\n" +
            "（1）调用服务端API获取。调用创建群会话接口的返回chatid字段\n" +
            "\n" +
            "（2）调用前端API获取。小程序调用选择会话获取，H5微应用调用根据corpid选择会话获取。")
    private String chatId;

    @NotBlank(message = "消息不能为空")
    @ApiModelProperty("消息内容")
    private String msg;


}
