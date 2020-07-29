package com.ciyun.renshe.controller.vo.chat;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Date 2020/4/22 16:54
 * @Author Admin
 * @Version 1.0
 */
@Getter
@Setter
public class UpdateChatUserVO {

    private String chatId;

    private List<String> addUserIds;

    private List<String> deleteUserIds;
}
