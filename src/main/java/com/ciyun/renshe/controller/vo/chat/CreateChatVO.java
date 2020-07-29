package com.ciyun.renshe.controller.vo.chat;

import com.ciyun.renshe.controller.vo.permission.PermissionData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
@ApiModel("创建群所需结构")
public class CreateChatVO {

    /**
     * 群名称
     */
    @NotBlank(message = "群名称不能为空")
    @ApiModelProperty(value = "群名称，必传")
    private String name;

    /**
     * 群主userId，员工唯一标识ID；必须为该会话useridlist的成员之一
     */
    @NotBlank(message = "群主userId 不能为空")
    @ApiModelProperty(value = "群主userId，员工唯一标识ID；必须为该会话useridlist的成员之一")
    private String owner;

    /**
     * 群成员列表，每次最多支持40人，群人数上限为1000
     */
    @NotEmpty(message = "群成员列表不能为空")
    @ApiModelProperty(value = "群成员列表，每次最多支持40人，群人数上限为1000")
    private List<String> userIdList;

    @ApiModelProperty(value = "群成员 userIds 列表，字符串使用英文逗号分割")
    private String userIds;

    /**
     * 群类型
     */
    @NotNull(message = "群类型不能为空")
    @ApiModelProperty("群类型，必传，1 区域群 2 行业群")
    private Integer type;

    @ApiModelProperty("规模id")
    private Integer esId;

    @ApiModelProperty("行业id")
    private Integer industryId;

    private PermissionData permission;


}
