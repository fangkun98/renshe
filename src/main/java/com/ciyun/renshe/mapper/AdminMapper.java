package com.ciyun.renshe.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ciyun.renshe.entity.Admin;
import com.ciyun.renshe.entity.po.AdminPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminMapper extends BaseMapper<Admin> {
    List<AdminPO> findAllAdmin(@Param("adminName") String adminName, @Param("mobile") String mobile);
}