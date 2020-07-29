package com.ciyun.renshe.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 提示信息
 *
 * @Date 2019/8/22 16:57
 * @Author Admin
 * @Version 1.0
 */
@Getter
@ToString
@AllArgsConstructor
public enum MessageInfo {

    /**
     * 查询成功
     */
    GET_INFO(200, "查询成功"),
    /**
     * 新增成功
     */
    ADD_INFO(200, "新增成功"),
    /**
     * 修改成功
     */
    UPDATE_INFO(200, "修改成功"),
    /**
     * 删除成功
     */
    DELETE_INFO(200, "删除成功"),

    /**
     * 参数校验失败
     */
    VALIDATION_INFO(400, "参数校验失败"),

    /**
     * 成功执行了查询，但结果为空
     */
    SELECT_NULL(20001, "查询结果为空"),

    /**
     * 导入 Excel 信息成功
     */
    IMPORT_SUCCESS(200, "导入成功"),

    /**
     * 下载文件成功提醒
     */
    DOWNLOAD_SUCCESS(200, "下载成功"),

    /**
     * id 为空提示
     */
    ID_NULL(400, "Id 值为空"),

    /**
     * 登录成功提示
     */
    LOGIN_SUCCESS(200, "用户登录成功"),

    /**
     * 登录失败提示
     */
    LOGIN_FAIL(400, "用户名密码不正确或身份校验失败"),

    DD_REEOR(500, "请求钉钉接口错误，连接DingDing服务器超时！"),
    DD_SEND_MSG_SUCCESS(200, "消息发送成功");

    /**
     * 对应数字
     */
    private final int value;

    /**
     * 显示的提示信息
     */
    private final String info;

}
