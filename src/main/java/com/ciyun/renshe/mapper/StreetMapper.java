package com.ciyun.renshe.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ciyun.renshe.entity.Street;
import com.ciyun.renshe.entity.po.DingDingStreetPO;
import com.ciyun.renshe.entity.po.StreetPO;
import com.ciyun.renshe.entity.po.StreetUserPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StreetMapper extends BaseMapper<Street> {

    /**
     * 根据区id 查询 对应的街道
     *
     * @param areaId
     * @return
     */
    List<StreetPO> findStreetsByAreaId(Integer areaId);

    List<DingDingStreetPO> findDingDingStreetsByAreaId(Integer areaId);

    /**
     * 根据街道id 查询全部 网格
     *
     * @param streetId
     * @return
     */
    StreetPO findStreetsByStreetId(Integer streetId);

    /**
     * 根据区id 查询 对应的街道
     *
     * @param streetIds
     * @return
     */
    List<StreetPO> findStreetsByStreetIds(@Param("streetIds") List<Integer> streetIds);

    /**
     * Integer areaId
     *
     * @return
     */
    List<StreetUserPO> findStreetsUserByAreaId(Integer areaId);

    List<StreetUserPO> findStreetsUserByStreetId(Integer streetId);
}