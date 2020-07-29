package com.ciyun.renshe.controller.vo.enterprisescale;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @Date 2020/5/21 13:15
 * @Author Admin
 * @Version 1.0
 */
@Getter
@Setter
public class UpdateCompanyTagVO {

    @NotEmpty(message = "选择标签不能为空")
    private List<String> tagList;

    @NotBlank(message = "公司不能为空")
    private String company;
}
