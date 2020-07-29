package com.ciyun.renshe.entity.po;

import com.ciyun.renshe.common.dingding.message.type.ActionCard;
import lombok.Getter;
import lombok.Setter;

/**
 * @Date 2020/5/18 18:25
 * @Author Admin
 * @Version 1.0
 */
@Getter
@Setter
public class MessagePO {
    private String msgtype;

    private ActionCard actionCard;
}
