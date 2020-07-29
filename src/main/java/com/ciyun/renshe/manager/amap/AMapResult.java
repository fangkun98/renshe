package com.ciyun.renshe.manager.amap;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Date 2020/4/22 22:11
 * @Author Admin
 * @Version 1.0
 */
@Getter
@Setter
public class AMapResult {

    private String status;

    private String info;

    private String infocode;

    private String count;

    private List<Info> geocodes;

}


