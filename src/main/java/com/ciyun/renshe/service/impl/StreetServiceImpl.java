package com.ciyun.renshe.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ciyun.renshe.common.MessageInfo;
import com.ciyun.renshe.common.Result;
import com.ciyun.renshe.entity.Street;
import com.ciyun.renshe.entity.po.GridPO;
import com.ciyun.renshe.mapper.GridMapper;
import com.ciyun.renshe.mapper.StreetMapper;
import com.ciyun.renshe.service.StreetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class StreetServiceImpl extends ServiceImpl<StreetMapper, Street> implements StreetService {

    private StreetMapper streetMapper;
    private GridMapper gridMapper;

    @Autowired
    public StreetServiceImpl(StreetMapper streetMapper, GridMapper gridMapper) {
        this.streetMapper = streetMapper;
        this.gridMapper = gridMapper;
    }

    @Override
    public List<Street> findAllStreet(Integer areaId) {
        QueryWrapper<Street> streetQueryWrapper = new QueryWrapper<>();
        if (areaId != null && areaId != 0) {
            streetQueryWrapper.eq("area_id", areaId);
        }

        return streetMapper.selectList(streetQueryWrapper);
    }

    /**
     * 根据街道id查询街道下的所有网格
     *
     * @param streetId
     * @return
     */
    @Override
    public Result findGridIdByStreetId(Integer streetId) {
        Result result = new Result(MessageInfo.GET_INFO.getInfo());
        List<GridPO> gridByStreetId = gridMapper.findGridByStreetId(streetId);
        return result.setData(gridByStreetId);
    }
}
