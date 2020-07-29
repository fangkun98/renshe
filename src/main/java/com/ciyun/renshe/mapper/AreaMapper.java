package com.ciyun.renshe.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ciyun.renshe.entity.Area;
import com.ciyun.renshe.entity.po.AreaPO;
import com.ciyun.renshe.entity.po.AreaUserPO;
import com.ciyun.renshe.entity.po.DingDingAreaPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AreaMapper extends BaseMapper<Area> {

    /**
     * 根据 id 查询区
     *
     * @param id
     * @return
     */
    Area findAreaById(Integer id);

    /**
     * 查询全部区
     *
     * @return
     */
    List<AreaPO> findAllArea(@Param("areaId") Integer areaId);

    /**
     * @param areaIds
     * @return
     */
    List<AreaPO> findAllAreaByAreaIds(@Param("areaIds") List<Integer> areaIds);

    /**
     * 查询区下面所有的人员
     *
     * @return
     */
    List<AreaUserPO> findAllAreaInUser(@Param("areaIds") List<Integer> areaIds);

    /**
     * @return
     */
    List<DingDingAreaPO> findAllAreaInfo();
}