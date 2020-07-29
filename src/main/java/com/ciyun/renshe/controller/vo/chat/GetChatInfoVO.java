package com.ciyun.renshe.controller.vo.chat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @Date 2020/4/17 16:43
 * @Author Admin
 * @Version 1.0
 */
@Getter
@Setter
@ApiModel("获取群下成员信息")
public class GetChatInfoVO {

    @ApiModelProperty("市Id")
    private Integer city;

    /**
     * 区Id
     */
    @ApiModelProperty("区Id")
    private Integer areaId;
    /**
     * 街道id
     */
    @ApiModelProperty("街道id")
    private Integer streetId;
    /**
     * 网格id
     */
    @ApiModelProperty("网格id")
    private Integer gridId;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;
    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    private String mobile;

    /**
     * 网格对应的群id
     */
    @ApiModelProperty("网格对应的群id")
    private String chatId;
}
