package com.ciyun.renshe.manager.dingding.impl;

import com.ciyun.renshe.common.dingding.DingDingUtil;
import com.ciyun.renshe.manager.dingding.DingDingUser;
import com.ciyun.renshe.manager.dingding.dto.DingDingApiResult;
import com.ciyun.renshe.manager.dingding.dto.user.UpdateUserDeptParam;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiUserUpdateRequest;
import com.dingtalk.api.response.OapiUserUpdateResponse;
import com.taobao.api.ApiException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kys
 * @version 1.0
 * @date 2020/7/7 14:16
 */
@Service
public class DingDingUserImpl implements DingDingUser {

    /**
     * 修改用户信息
     *
     * @param param
     * @return
     */
    @Override
    public DingDingApiResult updateUser(UpdateUserDeptParam param) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/update");
        OapiUserUpdateRequest request = new OapiUserUpdateRequest();
        BeanUtils.copyProperties(param, request);
        OapiUserUpdateResponse response = client.execute(request, DingDingUtil.ACCESS_TOKEN);
        DingDingApiResult dingDingApiResult = new DingDingApiResult();
        BeanUtils.copyProperties(response, dingDingApiResult);
        return dingDingApiResult;
    }

    /**
     * 修改成员的部门
     *
     * @param userId
     * @param department
     * @return
     */
    @Override
    public DingDingApiResult updateUserDept(String userId, List<Long> department) throws ApiException {
        UpdateUserDeptParam param = new UpdateUserDeptParam();
        param.setUserid(userId);
        param.setDepartment(department);
        return updateUser(param);
    }

    /**
     * 修改成员的部门，只指定一个部门
     *
     * @param userId
     * @param department
     * @return
     * @throws ApiException
     */
    @Override
    public DingDingApiResult updateUserDept(String userId, Long department) throws ApiException {
        UpdateUserDeptParam param = new UpdateUserDeptParam();
        param.setUserid(userId);
        List<Long> departmentList = new ArrayList<>(1);
        departmentList.add(department);
        param.setDepartment(departmentList);
        return updateUser(param);
    }
}
