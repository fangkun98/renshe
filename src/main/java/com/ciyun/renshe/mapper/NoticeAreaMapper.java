package com.ciyun.renshe.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ciyun.renshe.entity.NoticeArea;

import java.util.List;

public interface NoticeAreaMapper extends BaseMapper<NoticeArea> {

    /**
     * 根据 用户id 查询出用户所在的网格
     *
     * @param userId
     * @return
     */
    List<Integer> selectGridIdByUserId(Integer userId);
}