package com.ciyun.renshe.controller.vo.chat;

import com.ciyun.renshe.controller.vo.permission.PermissionData;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Date 2020/5/15 13:26
 * @Author Admin
 * @Version 1.0
 */
@Getter
@Setter
public class ChatDisbandedVO {

    @NotNull(message = "用户id不能为空")
    private Integer userId;

    @NotBlank(message = "群id不能为空")
    private String chatId;

    @NotNull(message = "权限数据不能为空")
    private PermissionData permission;
}
