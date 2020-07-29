package com.ciyun.renshe.controller.vo.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 修改公司备注 接收类
 *
 * @Date 2020/6/5 10:40
 * @Author kys
 * @Version 1.0
 */
@Data
public class UpdateCompanyRemarkVO {

    /**
     * 用户id
     */
    @NotNull(message = "用户id不能为空")
    private Integer userId;

    /**
     * 备注信息
     */
    @NotBlank(message = "备注信息不能为空")
    private String remark;
}
