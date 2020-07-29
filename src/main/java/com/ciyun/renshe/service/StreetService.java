package com.ciyun.renshe.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ciyun.renshe.common.Result;
import com.ciyun.renshe.entity.Street;

import java.util.List;

public interface StreetService extends IService<Street> {

    List<Street> findAllStreet(Integer areaId);

    /**
     * 根据街道id查询街道下的所有网格
     *
     * @param streetId
     * @return
     */
    Result findGridIdByStreetId(Integer streetId);
}
