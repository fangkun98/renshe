package com.ciyun.renshe.manager.renshe;

import com.ciyun.renshe.manager.renshe.dto.DsInfoDTO;

/**
 * 人社用户校验
 *
 * @author kys
 * @version 1.0
 * @date 2020/7/9 9:14
 */
public interface UserVerification {
    /**
     * 人社用户校验
     *
     * @param accountNum 社会统一信用代码
     * @param password   密码
     * @return
     */
    DsInfoDTO userVerification(String accountNum, String password);
}
