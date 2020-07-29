package com.ciyun.renshe.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ciyun.renshe.common.Page;
import com.ciyun.renshe.common.Result;
import com.ciyun.renshe.controller.vo.admin.SaveAdminByMobileAndNameVO;
import com.ciyun.renshe.controller.vo.manager.SaveAdminVO;
import com.ciyun.renshe.entity.Admin;

public interface AdminService extends IService<Admin> {

    /**
     * 保存管理员
     *
     * @param admin
     */
    void saveAdmin(SaveAdminVO admin);

    /**
     * 根据adminId 删除管理员
     *
     * @param adminId
     * @return
     */
    Result deleteAdminById(Integer adminId);

    /**
     * 查询全部管理员
     *
     * @param page
     * @param adminName
     * @param mobile
     * @return
     */
    Result findAllAdmin(Page page, String adminName, String mobile);

    /**
     * 根据手机号和名称添加管理员
     *
     * @param adminVO
     * @return
     */
    Result saveAdminByMobileAndName(SaveAdminByMobileAndNameVO adminVO);
}


