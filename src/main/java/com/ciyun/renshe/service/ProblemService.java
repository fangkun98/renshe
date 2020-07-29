package com.ciyun.renshe.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ciyun.renshe.common.Page;
import com.ciyun.renshe.common.PageResult;
import com.ciyun.renshe.controller.vo.problem.FindAdminProblemsVO;
import com.ciyun.renshe.entity.Problem;
import com.github.pagehelper.PageInfo;

/**
 * @Date 2020/4/3 13:43
 * @Author Admin
 * @Version 1.0
 */
public interface ProblemService extends IService<Problem> {

    /**
     * 查询问题列表
     *
     * @param isSolve
     * @return
     */
    PageInfo<Problem> findProblems(Page page, Integer isSolve, Integer userId);

    /**
     * 添加问题
     *
     * @param problem
     */
    void createProblem(Problem problem);

    /**
     * 修改问题
     *
     * @param problem
     */
    void updateProblem(Problem problem);

    /**
     * 根据 problemId 删除问题
     *
     * @param problemId
     */
    void deleteProblem(Integer problemId);

    /**
     * @param city
     * @param areaId
     * @param streetId
     * @param gridId
     * @param chatId
     * @param type     1 已反馈 0 未反馈 2去全部
     * @return
     */
    Integer findCount(String city, String areaId, String streetId, String gridId, String chatId, int type);

    /**
     * 后台查询对应的的问题列表
     *
     * @return
     */
    PageResult findAdminProblems(FindAdminProblemsVO findAdminProblemsVO);

    /**
     * 导出问题 Excel
     *
     * @param isSolve
     * @param content
     * @param startDate
     * @param endDate
     * @return
     */
    //List<ProblemExcelDTO> getProblemExcel(Integer isSolve, String content, String startDate, String endDate);
}



