package com.ciyun.renshe.common.dingding.sdk;

import com.ciyun.renshe.common.dingding.DingDingUtil;
import com.ciyun.renshe.common.dingding.sdk.request.ChatCreateParam;
import com.ciyun.renshe.common.dingding.sdk.request.ChatUpdateParam;
import com.ciyun.renshe.common.dingding.sdk.request.DepartmentUpdateParam;
import com.ciyun.renshe.common.dingding.sdk.request.RoleAddParam;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @Date 2020/4/9 15:33
 * @Author Admin
 * @Version 1.0
 */
@Slf4j
public class DingDingSDKUtils {

    /**
     * 创建角色
     *
     * @param param
     * @return
     * @throws ApiException
     */
    public static OapiRoleAddRoleResponse addRole(RoleAddParam param) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/role/add_role");
        OapiRoleAddRoleRequest req = new OapiRoleAddRoleRequest();
        req.setRoleName(param.getRoleName());
        req.setGroupId(param.getGroupId());
        return client.execute(req, DingDingUtil.ACCESS_TOKEN);
    }

    /**
     * 获取管理员列表
     *
     * @return
     */
    public static OapiUserGetAdminResponse getUserAdminList() throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get_admin");
        OapiUserGetAdminRequest request = new OapiUserGetAdminRequest();
        request.setHttpMethod("GET");
        return client.execute(request, DingDingUtil.ACCESS_TOKEN);
    }

    /**
     * 修改 部门信息
     *
     * @param param
     * @return
     * @throws ApiException
     */
    public static OapiDepartmentUpdateResponse updateDepartment(DepartmentUpdateParam param) throws ApiException {

        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/update");
        OapiDepartmentUpdateRequest request = new OapiDepartmentUpdateRequest();
        request.setId(Long.parseLong(param.getId()));
        if (StringUtils.isNotBlank(param.getOrder())) {
            request.setOrder(param.getOrder());
        }
        if (StringUtils.isNotBlank(param.getName())) {
            request.setName(param.getName());
        }
        if (StringUtils.isNotBlank(param.getOrgDeptOwner())) {
            request.setOrgDeptOwner(param.getOrgDeptOwner());
        }
        return client.execute(request, DingDingUtil.ACCESS_TOKEN);
    }

    /**
     * 创建群
     *
     * @param param
     * @return
     * @throws ApiException
     */
    public static OapiChatCreateResponse createChat(ChatCreateParam param) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/chat/create");
        OapiChatCreateRequest request = new OapiChatCreateRequest();
        request.setName(param.getName());
        request.setOwner(param.getOwner());
        request.setUseridlist(param.getUseridlist());
        request.setShowHistoryType(1L);
        request.setManagementType(1L);
        return client.execute(request, DingDingUtil.ACCESS_TOKEN);
    }

    /**
     * 修改群信息
     *
     * @param param
     * @return
     * @throws ApiException
     */
    public static OapiChatUpdateResponse updateChat(ChatUpdateParam param) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/chat/update");
        OapiChatUpdateRequest request = new OapiChatUpdateRequest();

        request.setChatid(param.getChatId());

        if (StringUtils.isNotBlank(param.getName())) {
            request.setName(param.getName());
        }
        if (StringUtils.isNotBlank(param.getOwner())) {
            request.setOwner(param.getOwner());
        }
        if (param.getAddUserIdList() != null && !param.getAddUserIdList().isEmpty()) {
            request.setAddUseridlist(param.getAddUserIdList());
        }
        if (param.getDelUserIdList() != null && !param.getDelUserIdList().isEmpty()) {
            request.setDelUseridlist(param.getDelUserIdList());
        }
        if (param.getChatBannedType() != null) {
            request.setChatBannedType(param.getChatBannedType());
        }
        if (param.getSearchable() != null) {
            request.setSearchable(param.getSearchable());
        }
        if (param.getValidationType() != null) {
            request.setValidationType(param.getValidationType());
        }
        if (param.getMentionAllAuthority() != null) {
            request.setMentionAllAuthority(param.getMentionAllAuthority());
        }
        if (param.getShowHistoryType() != null) {
            request.setShowHistoryType(param.getShowHistoryType());
        }
        if (param.getManagementType() != null) {
            request.setManagementType(param.getManagementType());
        }

        return client.execute(request, DingDingUtil.ACCESS_TOKEN);
    }
}
