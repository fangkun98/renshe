package com.ciyun.renshe.service;

import com.ciyun.renshe.common.Result;
import com.ciyun.renshe.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

public interface SysRoleService extends IService<SysRole> {

    /**
     * 查询全部角色
     *
     * @return
     */
    Result findAllRole();

    /**
     * 给对应的角色分配权限
     *
     * @param roleId
     * @param permission
     * @return
     */
    void saveRolePermission(Integer roleId, String permission);
}

