package com.ciyun.renshe.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ciyun.renshe.entity.EnterpriseScale;import com.ciyun.renshe.entity.po.EnterpriseScalePO;import java.util.List;

public interface EnterpriseScaleMapper extends BaseMapper<EnterpriseScale> {
    List<EnterpriseScalePO> findAllEnterpriseScaleList(Integer state);
}