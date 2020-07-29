package com.ciyun.renshe.manager.renshe.dto;

import lombok.Data;

/**
 * 调用人社返回信息
 *
 * @author kys
 * @version 1.0
 * @date 2020/7/9 9:39
 */
@Data
public class DsInfoDTO {

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 公司地址
     */
    private String address;

    /**
     * 单位编码
     */
    private String unitNo;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 区id
     */
    private String areaId;
}
