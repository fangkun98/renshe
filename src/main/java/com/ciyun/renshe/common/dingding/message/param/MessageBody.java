package com.ciyun.renshe.common.dingding.message.param;

import com.ciyun.renshe.common.dingding.message.type.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * 消息各种格式
 *
 * @Date 2020/4/10 17:30
 * @Author Admin
 * @Version 1.0
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel("消息各种格式")
public class MessageBody {
    @ApiModelProperty("text 类型消息")
    private Text text;

    @ApiModelProperty("image 类型消息")
    private Image image;

   /* @ApiModelProperty("file 类型消息")
    private File file;

    @ApiModelProperty("link 类型消息")
    private Link link;*/

    @ApiModelProperty("actionCard 类型消息")
    private ActionCard actionCard;

   /* @ApiModelProperty("btnActionCard 类型消息")
    private BtnActionCard btnActionCard;*/

    @NotNull(message = "消息类型不能为空")
    @ApiModelProperty("1.Text 2.Image 3.File 4.Link 5.ActionCard 6.BtnActionCard")
    private Integer msgType;

}












