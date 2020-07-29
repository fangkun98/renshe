package com.ciyun.renshe.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ciyun.renshe.entity.Solve;

import java.util.List;

public interface SolveService extends IService<Solve> {

    /**
     * 根据问题id 查询对应的 反馈
     *
     * @param problemId
     * @return
     */
    List<Solve> findSolvesByProblemId(Integer problemId);

    /**
     * 添加反馈
     *
     * @param solve
     */
    void createSolve(Solve solve);

}
