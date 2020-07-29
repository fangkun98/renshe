package com.ciyun.renshe.entity.po;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 网格下的人员
 */
@Getter
@Setter
public class GridInUserPO implements Serializable {

    private static final long serialVersionUID = 7572416510018715980L;
    private Integer id;

    private Integer gridId;

    private String name;

    private String chatId;

    private List<UserPO> children;

    //private Integer count;

}