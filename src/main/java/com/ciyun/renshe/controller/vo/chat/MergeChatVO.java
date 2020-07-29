package com.ciyun.renshe.controller.vo.chat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @Date 2020/4/9 14:39
 * @Author Admin
 * @Version 1.0
 */
@Getter
@Setter
@ToString
@ApiModel("群合并结构")
public class MergeChatVO {

    /**
     * 群名称
     */
    @NotBlank(message = "群名称不能为空")
    @ApiModelProperty(value = "群名称，必传")
    private String name;

    /**
     * 群主userId，员工唯一标识ID；必须为该会话useridlist的成员之一
     */
    @NotBlank(message = "群主userId 不能为空")
    @ApiModelProperty(value = "群主userId，员工唯一标识ID；必须为该会话useridlist的成员之一")
    private String owner;

    @NotEmpty
    @ApiModelProperty(value = "要合并的群，数组中为 chatId")
    private List<String> chatList;

}
