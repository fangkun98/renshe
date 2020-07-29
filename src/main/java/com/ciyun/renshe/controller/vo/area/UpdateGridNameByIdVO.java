package com.ciyun.renshe.controller.vo.area;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Date 2020/5/25 10:17
 * @Author Admin
 * @Version 1.0
 */
@Data
public class UpdateGridNameByIdVO {
    @NotNull(message = "网格id不为空为")
    private Integer gridId;
    @NotBlank(message = "网格名称不能为空")
    private String gridName;
}
