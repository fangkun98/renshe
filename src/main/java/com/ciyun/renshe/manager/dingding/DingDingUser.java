package com.ciyun.renshe.manager.dingding;

import com.ciyun.renshe.manager.dingding.dto.DingDingApiResult;
import com.ciyun.renshe.manager.dingding.dto.user.UpdateUserDeptParam;
import com.taobao.api.ApiException;

import java.util.List;

/**
 * @author kys
 * @version 1.0
 * @date 2020/7/7 14:08
 */
public interface DingDingUser {

    /**
     * 修改用户信息
     *
     * @param param
     * @return
     */
    DingDingApiResult updateUser(UpdateUserDeptParam param) throws ApiException;

    /**
     * 修改成员的部门
     *
     * @return
     */
    DingDingApiResult updateUserDept(String userId, List<Long> department) throws ApiException;

    /**
     * 修改成员的部门，只指定一个部门
     *
     * @param userId
     * @param department
     * @return
     * @throws ApiException
     */
    DingDingApiResult updateUserDept(String userId, Long department) throws ApiException;

}
