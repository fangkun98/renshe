package com.ciyun.renshe.controller.vo.message;

import com.ciyun.renshe.common.dingding.message.param.MessageBody;
import com.ciyun.renshe.controller.vo.permission.PermissionData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 发送工作通知对应接收结构
 *
 * @Date 2020/4/10 17:27
 * @Author Admin
 * @Version 1.0
 */
@Valid
@ApiModel("发送工作通知或资讯对应接收结构")
@Getter
@Setter
@Accessors(chain = true)
public class NoticeVO {

    @ApiModelProperty("接收者的用户userid列表,是钉钉 ddUserId")
    private List<String> userIdList;

    /*@ApiModelProperty("接收者的区列表")
    private List<Integer> areaIdList;*/
    @ApiModelProperty("消息发送者id, 是userId")
    private Integer createUserId;

    @NotNull(message = "messageBody 不能为空")
    @ApiModelProperty("消息内容，消息类型和样例参考“消息类型与数据格式”。最长不超过2048个字节")
    private MessageBody messageBody;

    @ApiModelProperty("文件路径")
    private String fileUrl;

    private String requestUrl;

    @NotNull(message = "类型不能为空，1 通知公告  2. 政策资讯")
    @ApiModelProperty("/1 通知公告  2. 政策资讯")
    private Integer type;

    @ApiModelProperty("富文本的内容")
    private String content;

    /**
     *
     */
    private String typeClass;

    /**
     * 1 为保存不发送到钉钉 2 为保存并发送到钉钉
     */
    @NotNull(message = "请选择保存还是发送")
    private Integer saveOrSaveSend;

    private PermissionData permission;

}
