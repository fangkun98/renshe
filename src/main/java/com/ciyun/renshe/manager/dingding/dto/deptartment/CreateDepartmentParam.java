package com.ciyun.renshe.manager.dingding.dto.deptartment;

import lombok.Data;

/**
 * 创建部门参数
 * <p>
 * https://ding-doc.dingtalk.com/doc#/serverapi2/dubakq/ed9c05fc
 *
 * @author kys
 * @version 1.0
 * @date 2020/7/7 13:39
 */
@Data
public class CreateDepartmentParam {

    private Boolean createDeptGroup;
    private Boolean deptHiding;
    private String deptPerimits;
    private String deptPermits;
    private String name;
    private String order;
    private Boolean outerDept;
    private Boolean outerDeptOnlySelf;
    private String outerPermitDepts;
    private String outerPermitUsers;
    private Boolean parentBalanceFirst;
    private String parentid;
    private Boolean shareBalance;
    private String sourceIdentifier;
    private String userPerimits;
    private String userPermits;
}
