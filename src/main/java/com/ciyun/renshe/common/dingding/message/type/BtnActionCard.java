package com.ciyun.renshe.common.dingding.message.type;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 独立卡片消息结构
 */
@Valid
@Getter
@Setter
@Accessors(chain = true)
@ApiModel("独立（按钮）卡片消息结构")
public class BtnActionCard {

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

    @ApiModelProperty("使用独立跳转ActionCard样式时的按钮排列方式，竖直排列(0)，横向排列(1)；必须与btn_json_list同时设置")
    private String orientation = "0";

    @ApiModelProperty("使用独立跳转ActionCard样式时的按钮列表；必须与btn_orientation同时设置")
    private List<BtnList> btnLists;

}


