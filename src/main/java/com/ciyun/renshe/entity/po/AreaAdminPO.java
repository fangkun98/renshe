package com.ciyun.renshe.entity.po;

import lombok.Getter;
import lombok.Setter;

/**
 * @Date 2020/4/15 10:34
 * @Author Admin
 * @Version 1.0
 */
@Getter
@Setter
public class AreaAdminPO {
    private Integer userId;
    private UserPO user;
    private Integer isOwner;
}
