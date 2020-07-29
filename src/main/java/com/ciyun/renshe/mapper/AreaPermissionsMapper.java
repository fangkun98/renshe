package com.ciyun.renshe.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ciyun.renshe.entity.AreaPermissions;import com.ciyun.renshe.entity.po.AreaAdminPO;import org.apache.ibatis.annotations.Param;import java.util.List;

public interface AreaPermissionsMapper extends BaseMapper<AreaPermissions> {
    /**
     * 根据 区 街道，网格查询对应管理员
     */
    List<AreaAdminPO> getAreaAdmin(@Param("areaId") Integer areaId,
                                   @Param("streetId") Integer streetId,
                                   @Param("gridId") Integer gridId,
                                   @Param("adminType") Integer adminType);

    /**
     * 根据用户名和用户的角色查询出对应所管理的区域
     *
     * @param userId
     * @param roleId
     * @return
     */
    List<AreaPermissions> findAreaByUserIdAndUserRoleId(@Param("userId") Integer userId, @Param("roleId") Integer roleId);
}