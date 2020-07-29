package com.ciyun.renshe.common.dingding.message.type;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 文件消息
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel("文件消息结构")
public class File {
    /**
     * 消息类型，此时固定为：file
     */
    public static final String MSG_TYPE = "file";

    @ApiModelProperty("媒体文件id。引用的媒体文件最大10MB")
    private String mediaId;
}