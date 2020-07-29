package com.ciyun.renshe.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ciyun.renshe.entity.Grid;
import com.ciyun.renshe.entity.po.GridInUserPO;
import com.ciyun.renshe.entity.po.GridPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GridMapper extends BaseMapper<Grid> {

    /**
     * 根据 streetId 查询 网格
     *
     * @param streetId
     * @return
     */
    List<GridPO> findGridByStreetId(Integer streetId);

    List<GridPO> findGridByStreetIds(@Param("streetIds") List<Integer> streetIds);

    List<GridInUserPO> findGridUserByStreetId(Integer streetId);

    List<GridInUserPO> findGridUserById(@Param("grids") List<Integer> grids);

    /**
     * 跟据 id 查询
     *
     * @param gridIds
     * @return
     */
    List<GridPO> findGridByGridIds(List<Integer> gridIds);
}