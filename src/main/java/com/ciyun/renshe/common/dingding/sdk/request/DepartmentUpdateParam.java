package com.ciyun.renshe.common.dingding.sdk.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 更新部门 参数
 * @Date 2020/4/10 10:53
 * @Author Admin
 * @Version 1.0
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class DepartmentUpdateParam {

    /**
     * 部门名称
     */
    private String name;

    /**
     * 部门id
     */
    private String id;

    /**
     * 在父部门中的排序值，order值小的排序靠前
     */
    private String order;

    /**
     * 企业群群主的userid
     */
    private String orgDeptOwner;

}
