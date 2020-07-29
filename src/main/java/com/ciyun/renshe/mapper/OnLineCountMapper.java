package com.ciyun.renshe.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ciyun.renshe.entity.OnLineCount;

import java.util.List;
import java.util.Map;

public interface OnLineCountMapper extends BaseMapper<OnLineCount> {

    /**
     * 查询在线时长前十
     * @return
     */
    List<Map<String,Object> >chatManagerCount();
}