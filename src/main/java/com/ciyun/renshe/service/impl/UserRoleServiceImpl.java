package com.ciyun.renshe.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ciyun.renshe.mapper.UserRoleMapper;
import com.ciyun.renshe.entity.UserRole;
import com.ciyun.renshe.service.UserRoleService;

@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}

