package com.ciyun.renshe.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ciyun.renshe.common.MessageInfo;
import com.ciyun.renshe.common.Result;
import com.ciyun.renshe.common.StatusCode;
import com.ciyun.renshe.entity.SysRole;
import com.ciyun.renshe.mapper.SysRoleMapper;
import com.ciyun.renshe.service.SysRoleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    private final SysRoleMapper sysRoleMapper;

    /**
     * 查询全部角色
     *
     * @return
     */
    @Override
    public Result findAllRole() {
        List<SysRole> sysRoles = sysRoleMapper.selectList(null);
        return new Result().setData(sysRoles)
                .setFlag(true).setMessage(MessageInfo.GET_INFO.getInfo())
                .setCode(StatusCode.OK);
    }

    /**
     * 给对应的角色分配权限
     *
     * @param roleId     角色id
     * @param permission 权限
     * @return
     */
    @Override
    public void saveRolePermission(Integer roleId, String permission) {
        SysRole sysRole = new SysRole();
        sysRole.setRoleId(roleId);
        sysRole.setPermission(permission);
        sysRoleMapper.updateById(sysRole);
    }
}

