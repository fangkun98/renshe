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
public class AreaPO {

    private Integer areaId;

    private Integer id;

    private String name;

    private List<StreetPO> children;
}
