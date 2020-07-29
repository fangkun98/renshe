package com.ciyun.renshe.manager.dingding;

import com.ciyun.renshe.manager.dingding.dto.DingDingApiResult;
import com.ciyun.renshe.manager.dingding.dto.deptartment.CreateDepartmentParam;
import com.ciyun.renshe.manager.dingding.dto.deptartment.UpdateDepartmentParam;
import com.taobao.api.ApiException;

/**
 * 钉钉部门相关操作
 *
 * @author kys
 * @version 1.0
 * @date 2020/7/7 13:22
 */
public interface DingDingDepartment {

    /**
     * 创建部门
     *
     * @return
     */
    DingDingApiResult createDepartment(CreateDepartmentParam param) throws ApiException;

    /**
     * 更新部门详情
     *
     * @return
     */
    DingDingApiResult updateDepartment(UpdateDepartmentParam param) throws ApiException;

    /**
     * 是否隐藏部门
     *
     * @param deptId 部门id
     * @param deptHiding 是否隐藏部门，true表示隐藏，false表示显示
     * @return
     */
    DingDingApiResult updateDepartmentHiding(Long deptId, Boolean deptHiding) throws ApiException;

    /**
     * 删除部门
     *
     * @param deptId
     * @return
     */
    DingDingApiResult deleteDepartment(String deptId) throws ApiException;
}
