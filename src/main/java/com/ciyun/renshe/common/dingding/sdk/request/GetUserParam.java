package com.ciyun.renshe.common.dingding.sdk.request;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @Date 2020/4/11 15:19
 * @Author Admin
 * @Version 1.0
 */
@Getter
@Setter
@Accessors(chain = true)
public class GetUserParam {

    /**
     * 获取的部门id
     */
    private Long departmentId;

    /**
     * 支持分页查询，与size参数同时设置时才生效，此参数代表偏移量
     */
    private Long offset;

    /**
     * 支持分页查询，与offset参数同时设置时才生效，此参数代表分页大小，最大100
     */
    private Long size;

    /**
     * 支持分页查询，部门成员的排序规则，默认不传是按自定义排序；
     * <p>
     * entry_asc：代表按照进入部门的时间升序，
     * <p>
     * entry_desc：代表按照进入部门的时间降序，
     * <p>
     * modify_asc：代表按照部门信息修改时间升序，
     * <p>
     * modify_desc：代表按照部门信息修改时间降序，
     * <p>
     * custom：代表用户定义(未定义时按照拼音)排序
     */
    private String order;
}
