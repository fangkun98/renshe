package com.ciyun.renshe.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ciyun.renshe.common.Result;
import com.ciyun.renshe.controller.vo.area.UpdateGridNameByIdVO;
import com.ciyun.renshe.entity.Grid;

public interface GridService extends IService<Grid> {

    /**
     * 根据 id 修改网格名称
     *
     * @param gridNameVO
     * @return
     */
    Result updateGridNameById(UpdateGridNameByIdVO gridNameVO);
}
