package com.ciyun.renshe.controller.vo.manager;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @Date 2020/5/12 14:30
 * @Author Admin
 * @Version 1.0
 */
@Getter
@Setter
@ApiModel("保存管理员")
public class SaveAdminVO {

    @NotEmpty(message = "用户不能为空")
    @ApiModelProperty("用户集合")
    private List<SaveAdmin> admins;
}
