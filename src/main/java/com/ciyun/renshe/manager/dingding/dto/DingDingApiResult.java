package com.ciyun.renshe.manager.dingding.dto;

import lombok.Data;

/**
 * 创建部门返回信息
 *
 * @author kys
 * @version 1.0
 * @date 2020/7/7 13:36
 */
@Data
public class DingDingApiResult {
    /**
     * 返回码
     */
    private Long errcode;
    /**
     * 对返回码的文本描述内容
     */
    private String errmsg;
    /**
     * 创建的部门id
     */
    private Long id;
}
