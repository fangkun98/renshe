package com.ciyun.renshe.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ciyun.renshe.common.Page;
import com.ciyun.renshe.common.PageResult;
import com.ciyun.renshe.controller.vo.problem.FindAdminProblemsVO;
import com.ciyun.renshe.entity.*;
import com.ciyun.renshe.mapper.*;
import com.ciyun.renshe.service.ProblemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ProblemServiceImpl extends ServiceImpl<ProblemMapper, Problem> implements ProblemService {

    @Autowired
    private ProblemMapper problemMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserChatMapper userChatMapper;
    @Autowired
    private GridMapper gridMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private AreaPermissionsMapper areaPermissionsMapper;
    @Autowired
    private SolveMapper solveMapper;

    /**
     * 查询问题列表
     *
     * @param isSolve
     * @return
     */
    @Override
    public PageInfo<Problem> findProblems(Page page, Integer isSolve, Integer userId) {

        Problem problem = new Problem();
        problem.setIsLook(1);
        // 设置为已读
        problemMapper.update(problem, Wrappers.<Problem>lambdaQuery()
                .eq(Problem::getProblemUserId, userId));

        QueryWrapper<Problem> queryWrapper = new QueryWrapper<>();

        if (isSolve != null) {
            queryWrapper.eq("is_solve", isSolve);
        }

        if (userId != null) {
            queryWrapper.eq("problem_user_id", userId);
        }

        queryWrapper.eq("flag", 1);
        //queryWrapper.

        queryWrapper.orderByDesc("create_time");
        problemMapper.selectList(queryWrapper);

        PageInfo<Problem> problems = PageHelper.startPage(page.getPageNum().intValue(), page.getPageSize().intValue())
                .doSelectPageInfo(() -> problemMapper.selectList(queryWrapper));
        return problems;
    }

    /**
     * 后台查询对应的的问题列表
     *
     * @param findAdminProblemsVO
     * @return
     */
    @Override
    public PageResult findAdminProblems(FindAdminProblemsVO findAdminProblemsVO) {

        QueryWrapper<Problem> queryWrapper = new QueryWrapper<>();
        String content = findAdminProblemsVO.getContent();
        String endDate = findAdminProblemsVO.getEndDate();
        String startDate = findAdminProblemsVO.getStartDate();
        Integer isSolve = findAdminProblemsVO.getIsSolve();
        Integer pageNum = findAdminProblemsVO.getPageNum();
        Integer pageSize = findAdminProblemsVO.getPageSize();

        List<Integer> roleIdList = findAdminProblemsVO.getPermission().getRoleIds();
        List<Integer> areaIds = findAdminProblemsVO.getPermission().getAreaIds();
        List<Integer> streetIds = findAdminProblemsVO.getPermission().getStreetIds();
        List<Integer> grids = findAdminProblemsVO.getPermission().getGrids();

        // 如果不包含 1 或者 2
        if (!(roleIdList.contains(1) || roleIdList.contains(2))) {
            if (CollectionUtil.isNotEmpty(areaIds)) {
                queryWrapper.in("area_id", areaIds);
            } else if (CollectionUtil.isNotEmpty(streetIds)) {
                queryWrapper.in("street_id", streetIds);
            } /*else if (CollectionUtil.isNotEmpty(grids)) {
                queryWrapper.in("grid_id", grids);
            }*/
        }

        // 问题内容不为空
        if (StringUtils.isNotBlank(content)) {
            queryWrapper.like("problem_content", content);
        }

        if (isSolve != null) {
            queryWrapper.eq("is_solve", isSolve);
        }

        /*if (userId != null) {
            queryWrapper.eq("problem_user_id", userId);
        }*/
        if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
            queryWrapper.ge("create_time", startDate + " 00:00:00");
            queryWrapper.le("create_time", endDate + " 23:59:59");
        }
        if (StringUtils.isNotBlank(startDate)) {
            queryWrapper.ge("create_time", startDate + " 00:00:00");
        }
        if (StringUtils.isNotBlank(endDate)) {
            queryWrapper.le("create_time", endDate + " 23:59:59");
        }

        queryWrapper.eq("flag", 1);

        queryWrapper.orderByAsc("is_solve");
        queryWrapper.orderByDesc("create_time");
        //problemMapper.selectList(queryWrapper);

        PageInfo<Problem> problems = PageHelper.startPage(pageNum, pageSize)
                .doSelectPageInfo(() -> problemMapper.selectList(queryWrapper));

        List<Problem> list = problems.getList();
        for (Problem problem : list) {
            Integer problemUserId = problem.getProblemUserId();
            User user = userMapper.selectById(problemUserId);
            if (user != null) {
                problem.setCompanyName(user.getCompanyName());
            } else {
                problem.setCompanyName("");
            }
            if (problem.getIsSolve().equals(1)) {
                List<Solve> solvesByProblemId = solveMapper.findSolvesByProblemId(problem.getProblemId());
                if (CollectionUtil.isNotEmpty(solvesByProblemId)) {
                    Solve solve = solvesByProblemId.get(0);
                    Integer solveUserId = solve.getSolveUserId();
                    User solveUser = userMapper.selectById(solveUserId);
                    if (solveUser != null) {
                        problem.setSolveName(solveUser.getName());
                    } else {
                        problem.setSolveName("");
                    }
                }
            } else {
                problem.setSolveName("");
            }
        }

        return new PageResult(problems.getTotal(), list);
    }

    /**
     * 添加问题
     *
     * @param problem
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createProblem(Problem problem) {
        Integer problemUserId = problem.getProblemUserId();
        User user = userMapper.selectById(problemUserId);
        List<UserChat> userChats = userChatMapper.selectList(Wrappers.<UserChat>lambdaQuery()
                .eq(UserChat::getUserId, user.getUserId()));
        if (!userChats.isEmpty()) {
            List<Grid> grids = gridMapper.selectList(Wrappers.<Grid>lambdaQuery()
                    .eq(Grid::getChatId, userChats.get(0).getChatId()));

            if (!grids.isEmpty()) {
                problem.setGridId(grids.get(0).getGridId());
            }
        }
        problem.setAreaId(user.getAreaId());
        problem.setStreetId(user.getStreetId());
        problem.setCreateTime(new Date());
        problem.setFlag(1);
        problem.setIsLook(0);
        problem.setIsSolve(0);
        problemMapper.insert(problem);
    }

    /**
     * 修改问题
     *
     * @param problem
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateProblem(Problem problem) {
        problemMapper.updateById(problem);
    }

    /**
     * 根据 problemId 删除问题
     *
     * @param problemId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteProblem(Integer problemId) {
        Problem problem = new Problem();
        problem.setProblemId(problemId);
        problem.setFlag(0);
        problemMapper.updateById(problem);
    }

    /**
     * @param city
     * @param areaId
     * @param streetId
     * @param gridId
     * @param chatId
     * @param type     1 已反馈 0 未反馈 2去全部
     * @return
     */
    @Override
    public Integer findCount(String city, String areaId, String streetId, String gridId, String chatId, int type) {
        return problemMapper.findCount(city, areaId, streetId, gridId, chatId, type);
    }

    /**
     * 导出问题 Excel
     *
     * @param isSolve
     * @param content
     * @param startDate
     * @param endDate
     * @return
     */
    /*@Override
    public List<ProblemExcelDTO> getProblemExcel(Integer isSolve, String content, String startDate, String endDate) {
        QueryWrapper<Problem> queryWrapper = new QueryWrapper<>();
        if (isSolve != null) {
            queryWrapper.eq("is_solve", isSolve);
        }
        if (StringUtils.isNotBlank(content)) {
            queryWrapper.like("problem_content", content);
        }
        // 开始时间和结束时间都不为空
        if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
            startDate = startDate + " 00:00:00";
            endDate = endDate + "23:59:59";
            queryWrapper.ge("create_time", startDate);
            queryWrapper.le("create_time", endDate);
        }

        // 开始时间不为空，结束时间为空
        if (StringUtils.isNotBlank(startDate) && StringUtils.isBlank(endDate)) {
            startDate = startDate + " 00:00:00";
            queryWrapper.ge("create_time", startDate);
        }

        // 结束时间不为空，开始时间为空
        if (StringUtils.isBlank(startDate) && StringUtils.isNotBlank(endDate)) {
            endDate = endDate + "23:59:59";
            queryWrapper.le("create_time", endDate);
        }

        queryWrapper.eq("flag", 1);
        queryWrapper.orderByDesc("problem_id");
        List<Problem> problems = problemMapper.selectList(queryWrapper);

        List<ProblemExcelDTO> problemExcelDTOS = new ArrayList<>(problems.size());
        for (Problem problem : problems) {
            ProblemExcelDTO problemExcelDTO = new ProblemExcelDTO();
            BeanUtils.copyProperties(problem, problemExcelDTO);
            if (problem.getIsSolve() != null && problem.getIsSolve().equals(1)) {
                problemExcelDTO.setIsSolve("已反馈");
            } else {
                problemExcelDTO.setIsSolve("未反馈");
            }
            problemExcelDTOS.add(problemExcelDTO);

        }
        return problemExcelDTOS;
    }*/
}


