package com.ciyun.renshe.service;

import com.ciyun.renshe.common.Page;
import com.ciyun.renshe.common.Result;
import com.ciyun.renshe.controller.vo.enterprisescale.UpdateCompanyTagVO;
import com.ciyun.renshe.entity.EnterpriseScale;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ciyun.renshe.entity.po.EnterpriseScalePO;

import java.util.List;
import java.util.Map;

public interface EnterpriseScaleService extends IService<EnterpriseScale> {

    /**
     * 查询所有企业规模列表
     *
     * @return
     */
    List<EnterpriseScale> findAllEnterpriseScale(Integer state);

    /**
     * 分类查询所有企业规模列表
     *
     * @return
     */
    Map<String, Object> findAllByType();

    /**
     * 分类查询所有企业规模列表
     *
     * @param state
     * @return
     */
    List<EnterpriseScalePO> findAllEnterpriseScaleList(Integer state);

    /**
     * 根据公司名称查询全部标签
     *
     * @param companyName
     * @return
     */
    Result findTagByCompanyName(String companyName);

    /**
     * 查询全部标签
     *
     * @param page
     * @param pageType
     * @return
     */
    Result findAllTag(Page page, Integer pageType);

    /**
     * 修改公司的标签
     *
     * @param updateCompanyTagVO
     * @return
     */
    Result updateCompanyTag(UpdateCompanyTagVO updateCompanyTagVO);
}




