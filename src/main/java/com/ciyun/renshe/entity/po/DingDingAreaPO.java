package com.ciyun.renshe.entity.po;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Date 2020/4/14 17:18
 * @Author Admin
 * @Version 1.0
 */
@Getter
@Setter
public class DingDingAreaPO {

    private Integer areaId;

    private Integer code;

    private String name;

    private List<DingDingStreetPO> sub;
}
