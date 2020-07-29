package com.ciyun.renshe.common.dingding.sdk.request;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 创建角色
 *
 * @Date 2020/4/10 13:59
 * @Author Admin
 * @Version 1.0
 */
@Getter
@Setter
@Accessors(chain = true)
public class RoleAddParam {

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色组id
     */
    private Long groupId;
}
