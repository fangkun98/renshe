package com.ciyun.renshe.common.dingding.message.type;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 文本消息
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel("文本消息结构")
@NoArgsConstructor
@AllArgsConstructor
public class Text {

    /**
     * 消息类型，此时固定为：text
     */
    public static final String MSG_TYPE = "text";
    @ApiModelProperty("消息内容，建议500字符以内")
    private String content;
}
