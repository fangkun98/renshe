package com.ciyun.renshe.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ciyun.renshe.entity.OnLineTime;
import org.apache.ibatis.annotations.Param;

public interface OnLineTimeMapper extends BaseMapper<OnLineTime> {

    OnLineTime findOnLineTimeByUserId(@Param("userId") Integer userId, @Param("currentDate") String currentDate);
}