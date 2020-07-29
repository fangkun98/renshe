package com.ciyun.renshe.common.dingding.message.type;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 图片消息
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel("图片消息结构")
public class Image {
    /**
     * 消息类型，此时固定为：image
     */
    public static final String MSG_TYPE = "image";

    @ApiModelProperty("媒体文件id，可以通过媒体文件接口上传图片获取。建议宽600像素 x 400像素，宽高比3 : 2")
    private String mediaId;
}
