package com.ciyun.renshe.controller.vo.chat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 创建群结构
 *
 * @Date 2020/4/8 16:44
 * @Author Admin
 * @Version 1.0
 */
@Valid
@Getter
@Setter
@ToString
@ApiModel("修改群所需结构")
public class UpdateChatVO {

    @NotBlank(message = "群会话id 不能为空")
    @ApiModelProperty(value = "群会话id")
    private String chatId;

    /**
     * 群名称
     */
    @ApiModelProperty(value = "群名称")
    private String name;

    /**
     * 群主userId，员工唯一标识ID；必须为该会话useridlist的成员之一
     */
    @ApiModelProperty(value = "群主userId，员工唯一标识ID；必须为该会话useridlist的成员之一")
    private String owner;

    /**
     * 群成员列表，每次最多支持40人，群人数上限为1000
     */
    @ApiModelProperty(value = "群成员列表，每次最多支持40人，群人数上限为1000")
    private List<String> userIdList;


    /**
     * @ all 权限，0-默认，所有人，1-仅群主可@ all
     */
    @ApiModelProperty(value = "@ all 权限，0-默认，所有人，1-仅群主可@ all")
    private Integer mentionAllAuthority;

    /**
     * 群禁言，0-默认，不禁言，1-全员禁言
     */
    @ApiModelProperty(value = "群禁言，0-默认，不禁言，1-全员禁言")
    private Integer chatBannedType;


    @ApiModelProperty(value = "群成员 userIds 列表，字符串使用英文逗号分割")
    private String userIds;

}
