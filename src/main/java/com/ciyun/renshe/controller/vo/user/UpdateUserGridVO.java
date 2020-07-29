package com.ciyun.renshe.controller.vo.user;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Date 2020/6/5 15:33
 * @Author kys
 * @Version 1.0
 */
@Data
public class UpdateUserGridVO {
    @NotNull(message = "userId 不能为空")
    private Integer userId;

    @NotNull(message = "gridId 不能为空")
    private Integer gridId;
}
