package com.ciyun.renshe.common.dingding.sdk.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @Date 2020/4/27 10:27
 * @Author Admin
 * @Version 1.0
 */
@Getter
@Setter
public class SubAdminParam {
    private String chatId;
    private String userIds;
    private Long role;
}
