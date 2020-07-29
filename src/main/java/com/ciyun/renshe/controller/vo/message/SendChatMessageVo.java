package com.ciyun.renshe.controller.vo.message;

import com.ciyun.renshe.common.dingding.message.param.MessageBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 发送群消息VO
 *
 * @Date 2020/4/13 11:07
 * @Author Admin
 * @Version 1.0
 */
@Valid
@Getter
@Setter
@ApiModel("发送群消息结构")
public class SendChatMessageVo {

    @NotEmpty(message = "群会话的 id 不能为空")
    @ApiModelProperty("群会话的id，可以通过以下方式获取：\n" +
            "\n" +
            "（1）调用服务端API获取。调用创建群会话接口的返回chatid字段\n" +
            "\n" +
            "（2）调用前端API获取。小程序调用选择会话获取，H5微应用调用根据corpid选择会话获取。")
    private List<String> chatIds;

    @NotNull(message = "发送消息用户id不能为空")
    @ApiModelProperty("发送消息用户id")
    private Integer userId;

    @NotNull(message = "messageBody 不能为空")
    @ApiModelProperty("消息内容，消息类型和样例参考“消息类型与数据格式”。最长不超过2048个字节")
    private MessageBody messageBody;

    @ApiModelProperty("文件路径")
    private String fileUrl;

    private String requestUrl;

    @NotNull(message = "类型不能为空，1 通知公告  2. 政策资讯")
    @ApiModelProperty("1 通知公告  2. 政策资讯")
    private Integer type;

    @ApiModelProperty("富文本的内容")
    private String content;

    @ApiModelProperty("重新发送时间")
    private String reSendMsg;

    //@Future(message = "必须是一个将来的时间")
    @ApiModelProperty("重新发送时间")
    private String reSendTime;

    /**
     *
     */
    private String typeClass;

    /**
     * 1 为保存不发送到钉钉 2 为保存并发送到钉钉
     */
    @NotNull(message = "请选择保存还是发送")
    private Integer saveOrSaveSend;

}
