package com.ciyun.renshe.manager.dingding.dto.deptartment;

import lombok.Data;

/**
 * 更新部门参数类
 * <p>
 * 各字段见 https://ding-doc.dingtalk.com/doc#/serverapi2/dubakq/ed9c05fc
 *
 * @author kys
 * @version 1.0
 * @date 2020/7/7 13:25
 */
@Data
public class UpdateDepartmentParam {

    private Boolean autoAddUser;

    private Boolean createDeptGroup;

    private Boolean deptHiding;

    private String deptManagerUseridList;

    private String deptPerimits;

    private String deptPermits;

    private Boolean groupContainHiddenDept;

    private Boolean groupContainOuterDept;

    private Boolean groupContainSubDept;

    private Long id;

    private String lang;

    private String name;

    private String order;

    private String orgDeptOwner;

    private Boolean outerDept;

    private Boolean outerDeptOnlySelf;

    private String outerPermitDepts;

    private String outerPermitUsers;

    private String parentid;

    private String sourceIdentifier;

    private String userPerimits;

    private String userPermits;

}
