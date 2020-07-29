package com.ciyun.renshe.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 分页对象
 *
 * @Date 2019/11/8 14:29
 * @Author Admin
 * @Version 1.0
 */
@ApiModel("分页对象")
@Setter
@ToString
@AllArgsConstructor
public class Page implements Serializable {

    private static final long serialVersionUID = -1797428036828236109L;
    /**
     * 页码
     */
    @ApiModelProperty("开始页码")
    private Long pageNum;
    /**
     * 页大小
     */
    @ApiModelProperty("页大小")
    private Long pageSize;

    public Long getPageNum() {
        if (pageNum == null) {
            pageNum = 1L;
        }
        return pageNum;
    }

    public Long getPageSize() {
        if (pageSize == null) {
            pageSize = 10L;
        }
        return pageSize;
    }
}
