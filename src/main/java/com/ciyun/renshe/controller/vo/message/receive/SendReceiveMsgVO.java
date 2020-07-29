package com.ciyun.renshe.controller.vo.message.receive;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Date 2020/4/30 16:44
 * @Author Admin
 * @Version 1.0
 */
@Getter
@Setter
@ApiModel("发送应达消息")
public class SendReceiveMsgVO {

    @NotNull(message = "atId不能为空")
    @ApiModelProperty("atId")
    public Integer atId;

    @NotNull(message = "当前登录者的管理员userId不能为空")
    @ApiModelProperty("当前登录者的管理员userId")
    public Integer userId;

    @NotBlank(message = "消息发送内容不能为空")
    @ApiModelProperty("消息发送内容")
    public String messageContent;

    @NotBlank(message = "消息发送内容不能为空")
    @ApiModelProperty("群地址")
    public String sessionWebhook;

    @NotBlank(message = "群地址过期时间")
    @ApiModelProperty("群地址过期时间")
    public Long sessionWebhookExpiredTime;

    @NotBlank(message = "senderStaffId不能为空")
    @ApiModelProperty("senderStaffId")
    public String senderStaffId;
}
