package com.ciyun.renshe.service;

import com.ciyun.renshe.common.Result;
import com.ciyun.renshe.controller.vo.admin.*;
import com.ciyun.renshe.entity.Area;
import com.baomidou.mybatisplus.extension.service.IService;
import com.taobao.api.ApiException;

import java.util.List;

public interface AreaService extends IService<Area> {

    /**
     * 保存区域管理员
     *
     * @param saveAdminVO
     */
    Result saveAreaAdmin(SaveAdminVO saveAdminVO);

    /**
     * 根据 区 街道，网格查询对应管理员
     *
     * @param areaAdminVO
     * @return
     */
    Result getAreaAdmin(GetAreaAdminVO areaAdminVO);

    /**
     * 删除用户对应区域权限
     *
     * @param deleteAdminVO
     */
    void deleteAreaAdmin(DeleteAdminVO deleteAdminVO);

    /**
     * 修改网格管理员，需要修改对应 群的群主
     *
     * @param updateGridAdminVO
     */
    Result updateGridAdmin(UpdateGridAdminVO updateGridAdminVO);

    /**
     * 保存网格管理员 还会创建对应群
     *
     * @param saveGridAdminVO
     * @return
     */
    Result saveGridAdmin(SaveGridAdminVO saveGridAdminVO) throws ApiException;

    /**
     * 查询全部区域数据，包含街道
     *
     * @return
     */
    Result findAllAreaInfo();

    /**
     * 查询全部管理员
     *
     * @return
     */
    Result findAllAdmin();


    List<Area> findAllArea();
}
