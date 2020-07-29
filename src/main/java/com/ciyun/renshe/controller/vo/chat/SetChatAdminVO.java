package com.ciyun.renshe.controller.vo.chat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 设置群管理员 VO
 *
 * @Date 2020/4/27 10:13
 * @Author Admin
 * @Version 1.0
 */
@Getter
@Setter
@ApiModel("设置群管理员")
public class SetChatAdminVO {

    @ApiModelProperty("群会话id")
    @NotBlank(message = "群id不能为空")
    private String chatId;

    @ApiModelProperty("群成员 userId 列表，字符串使用英文逗号分割")
    @NotBlank(message = "用户ids不能为空")
    private String userIds;

    @ApiModelProperty("设置2为添加为管理员，设置3为删除该管理员")
    @NotNull(message = "role 不能为空")
    private Long role;
}
