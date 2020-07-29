package com.ciyun.renshe.entity.po;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 网格员
 */
@Getter
@Setter
public class GridPO implements Serializable {

    private static final long serialVersionUID = 7572416510018715980L;

    private Integer id;

    private Integer gridId;

    private String name;

    private String chatId;

    private Integer streetId;

    //private Integer count;

}