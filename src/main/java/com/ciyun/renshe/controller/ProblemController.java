package com.ciyun.renshe.controller;

import com.ciyun.renshe.common.*;
import com.ciyun.renshe.controller.vo.problem.CreateProblemVo;
import com.ciyun.renshe.controller.vo.problem.FindAdminProblemsVO;
import com.ciyun.renshe.controller.vo.problem.UpdateProblemVo;
import com.ciyun.renshe.controller.vo.solve.CreateSolveVo;
import com.ciyun.renshe.entity.Problem;
import com.ciyun.renshe.entity.Solve;
import com.ciyun.renshe.service.ProblemService;
import com.ciyun.renshe.service.SolveService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 问题相关 controller
 *
 * @Date 2020/4/3 14:14
 * @Author Admin
 * @Version 1.0
 */
@Api(tags = "问题解答模块")
@Validated
@CrossOrigin
@RestController
@RequestMapping("/problem")
public class ProblemController {

    private ProblemService problemService;
    private SolveService solveService;

    public ProblemController(ProblemService problemService, SolveService solveService) {
        this.problemService = problemService;
        this.solveService = solveService;
    }

    /**
     * 查询对应的的问题列表
     *
     * @param page
     * @param isSolve 1 已经反馈 0 未反馈
     * @return
     */
    @GetMapping
    @ApiOperation(value = "查询对应的的问题列表,返回值字段查看", response = Result.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isSolve", value = "是否已经反馈,1已反馈，0未反馈，不传查全部", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "userId", value = "用户id", required = false, paramType = "query", dataType = "int")
    })
    public Result<PageResult> findProblems(Page page, Integer isSolve, Integer userId) {
        PageInfo<Problem> problems = problemService.findProblems(page, isSolve, userId);
        return new Result<>(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(),
                new PageResult(problems.getTotal(), problems.getList()));
    }

    @PostMapping("/admin")
    @ApiOperation(value = "后台查询对应的的问题列表", response = Result.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isSolve", value = "是否已经反馈,1已反馈，0未反馈，不传查全部", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageNum", value = "起始页", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "页大小", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "startDate", value = "开始时间", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "endDate", value = "结束时间", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "content", value = "内容", required = false, paramType = "query", dataType = "String")
    })
    public Result<PageResult> findAdminProblems(@RequestBody FindAdminProblemsVO findAdminProblemsVO) {
        PageResult pageResult = problemService.findAdminProblems(findAdminProblemsVO);
        return new Result<>(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(), pageResult);
    }

    /**
     * 提交问题
     *
     * @param problemVo
     * @return
     */
    @PostMapping
    @ApiOperation(value = "提交问题")
    public Result createProblem(@RequestBody @Valid CreateProblemVo problemVo) {
        Problem problem = new Problem();
        BeanUtils.copyProperties(problemVo, problem);
        problemService.createProblem(problem);
        return new Result<>(true, StatusCode.OK, MessageInfo.ADD_INFO.getInfo());
    }

    /**
     * 修改问题
     *
     * @param updateProblemVo
     * @return
     */
    @PostMapping("/update")
    @ApiOperation(value = "修改问题")
    public Result updateProblem(@RequestBody UpdateProblemVo updateProblemVo) {
        Problem problem = new Problem();
        BeanUtils.copyProperties(updateProblemVo, problem);
        problemService.updateProblem(problem);
        return new Result<>(true, StatusCode.OK, MessageInfo.UPDATE_INFO.getInfo());
    }

    /**
     * 根据 problemId 删除对应的 问题
     *
     * @param problemId
     * @return
     */
    @GetMapping("/delete")
    @ApiOperation("根据 problemId 删除对应的问题")
    @ApiImplicitParam(name = "problemId", value = "问题对应的 problemId", required = true, paramType = "query", dataType = "int")
    public Result deleteProblem(@RequestParam Integer problemId) {
        problemService.deleteProblem(problemId);
        return new Result<>(true, StatusCode.OK, MessageInfo.DELETE_INFO.getInfo());
    }
//TODO
    /**
     * 添加反馈
     *
     * @param createSolveVo
     * @return
     */
    @PostMapping("/solve")
    @ApiOperation(value = "添加反馈")
    public Result createSolve(@RequestBody @Valid CreateSolveVo createSolveVo) {
        Solve solve = new Solve();
        BeanUtils.copyProperties(createSolveVo, solve);
        solveService.createSolve(solve);
        return new Result(true, StatusCode.OK, MessageInfo.ADD_INFO.getInfo());
    }

    /**
     * 根据 问题id 查询 对应的反馈
     *
     * @param problemId
     * @return
     */
    @GetMapping("/solve/problemId")
    @ApiOperation(value = " 根据 problemId 查询对应的反馈")
    @ApiImplicitParam(name = "problemId", value = "问题对应的 problemId", required = true, paramType = "query", dataType = "int")
    public Result<Map> findSolveByProblemId(@RequestParam Integer problemId) {
        List<Solve> solves = solveService.findSolvesByProblemId(problemId);
        Problem problem = problemService.getById(problemId);
        Map<String, Object> result = new HashMap<>(4);
        result.put("problem", problem);
        result.put("solves", solves);
        return new Result<>(true, StatusCode.OK, MessageInfo.GET_INFO.getInfo(), result);
    }
}
