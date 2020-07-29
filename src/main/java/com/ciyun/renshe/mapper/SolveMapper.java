package com.ciyun.renshe.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ciyun.renshe.entity.Solve;

import java.util.List;

public interface SolveMapper extends BaseMapper<Solve> {

    /**
     * 根据 问题id 查询对应的 反馈
     *
     * @param problemId
     * @return
     */
    List<Solve> findSolvesByProblemId(Integer problemId);
}