package com.ciyun.renshe.manager.dingding.impl;

import com.ciyun.renshe.common.dingding.DingDingUtil;
import com.ciyun.renshe.manager.dingding.DingDingDepartment;
import com.ciyun.renshe.manager.dingding.dto.DingDingApiResult;
import com.ciyun.renshe.manager.dingding.dto.deptartment.CreateDepartmentParam;
import com.ciyun.renshe.manager.dingding.dto.deptartment.UpdateDepartmentParam;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiDepartmentCreateRequest;
import com.dingtalk.api.request.OapiDepartmentDeleteRequest;
import com.dingtalk.api.request.OapiDepartmentUpdateRequest;
import com.dingtalk.api.response.OapiDepartmentCreateResponse;
import com.dingtalk.api.response.OapiDepartmentDeleteResponse;
import com.dingtalk.api.response.OapiDepartmentUpdateResponse;
import com.taobao.api.ApiException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * @author kys
 * @version 1.0
 * @date 2020/7/7 13:32
 */
@Service
public class DingDingDepartmentImpl implements DingDingDepartment {

    /**
     * 创建部门
     *
     * @param param
     * @return
     */
    @Override
    public DingDingApiResult createDepartment(CreateDepartmentParam param) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/create");
        OapiDepartmentCreateRequest request = new OapiDepartmentCreateRequest();
        BeanUtils.copyProperties(param, request);
        OapiDepartmentCreateResponse response = client.execute(request, DingDingUtil.ACCESS_TOKEN);
        DingDingApiResult departmentResult = new DingDingApiResult();
        BeanUtils.copyProperties(response, departmentResult);
        return departmentResult;
    }

    /**
     * 更新部门详情
     *
     * @param param
     * @return
     */
    @Override
    public DingDingApiResult updateDepartment(UpdateDepartmentParam param) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/update");
        OapiDepartmentUpdateRequest request = new OapiDepartmentUpdateRequest();
        BeanUtils.copyProperties(param, request);
        OapiDepartmentUpdateResponse response = client.execute(request, DingDingUtil.ACCESS_TOKEN);
        DingDingApiResult departmentResult = new DingDingApiResult();
        BeanUtils.copyProperties(response, departmentResult);
        return departmentResult;
    }

    /**
     * 更新部门为隐藏部门
     *
     * @param deptId     部门id
     * @param deptHiding 是否隐藏部门，true表示隐藏，false表示显示
     * @return
     */
    @Override
    public DingDingApiResult updateDepartmentHiding(Long deptId, Boolean deptHiding) throws ApiException {
        UpdateDepartmentParam param = new UpdateDepartmentParam();
        param.setId(deptId);
        param.setDeptHiding(deptHiding);
        return updateDepartment(param);
    }

    /**
     * 删除部门
     *
     * @param deptId
     * @return
     */
    @Override
    public DingDingApiResult deleteDepartment(String deptId) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/delete");
        OapiDepartmentDeleteRequest request = new OapiDepartmentDeleteRequest();
        request.setId(deptId);
        request.setHttpMethod("GET");
        OapiDepartmentDeleteResponse response = client.execute(request, DingDingUtil.ACCESS_TOKEN);
        DingDingApiResult departmentResult = new DingDingApiResult();
        BeanUtils.copyProperties(response, departmentResult);
        return departmentResult;
    }
}
