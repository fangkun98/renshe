package com.ciyun.renshe.common.dingding.sdk;

import com.ciyun.renshe.common.dingding.DingDingUtil;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiDepartmentListIdsRequest;
import com.dingtalk.api.request.OapiDepartmentListRequest;
import com.dingtalk.api.response.OapiDepartmentListIdsResponse;
import com.dingtalk.api.response.OapiDepartmentListResponse;
import com.taobao.api.ApiException;
import org.apache.commons.lang3.StringUtils;

/**
 * 钉钉 操作部门接口
 *
 * @Date 2020/4/11 14:43
 * @Author Admin
 * @Version 1.0
 */
public class DeptSDKUtil {

    /**
     * 获取部门列表
     *
     * @param deptId
     * @return
     */
    public static OapiDepartmentListResponse departmentList(String deptId) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list");
        OapiDepartmentListRequest request = new OapiDepartmentListRequest();
        if (StringUtils.isNotBlank(deptId)) {
            request.setId(deptId);
        }
        request.setFetchChild(false);
        request.setHttpMethod("GET");
        return client.execute(request, DingDingUtil.ACCESS_TOKEN);
    }

    /**
     * 获取子部门ID列表
     *
     * @return
     */
    public static OapiDepartmentListIdsResponse getDepartmentById(String id) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list_ids");
        OapiDepartmentListIdsRequest request = new OapiDepartmentListIdsRequest();
        request.setId(id);
        request.setHttpMethod("GET");
        return client.execute(request, DingDingUtil.ACCESS_TOKEN);
    }
}
