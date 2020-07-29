package com.ciyun.renshe.entity.po;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class StreetUserPO implements Serializable {


    private Integer id;

    private Integer streetId;

    private String name;

    private Integer areaId;

    /**
     * 街道对应的网格
     */
    private List<GridInUserPO> children;
}