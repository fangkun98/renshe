package com.ciyun.renshe.controller.vo.message.receive;

import lombok.Getter;
import lombok.Setter;

/**
 * @Date 2020/4/28 13:36
 * @Author Admin
 * @Version 1.0
 */
@Getter
@Setter
public class AtUser {
    /**
     * 加密的发送者ID
     */
    private String dingtalkId;

    /**
     * 发送者在企业内的userid（企业内部群有）
     */
    private String staffId;
}
