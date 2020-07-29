package com.ciyun.renshe.manager.dingding.dto.user;

import lombok.Data;

import java.util.List;

/**
 * 用户修改部门功能
 * <p>
 * https://ding-doc.dingtalk.com/doc#/serverapi2/ege851/1ce6da36
 *
 * @author kys
 * @version 1.0
 * @date 2020/7/7 14:11
 */
@Data
public class UpdateUserDeptParam {

    private List<Long> department;
    private String email;
    private String extattr;
    private Long hiredDate;
    private Boolean isHide;
    private Boolean isSenior;
    private String jobnumber;
    private String lang;
    private String managerUserid;
    private String mobile;
    private String name;
    private String orderInDepts;
    private String orgEmail;
    private String position;
    private String remark;
    private String tel;
    private String userid;
    private String workPlace;

}
