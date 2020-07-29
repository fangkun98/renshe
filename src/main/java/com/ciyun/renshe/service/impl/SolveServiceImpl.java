package com.ciyun.renshe.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ciyun.renshe.common.dingding.message.DDSendMessageUtils;
import com.ciyun.renshe.common.dingding.message.MessageTypeConvert;
import com.ciyun.renshe.common.dingding.message.param.MessageBody;
import com.ciyun.renshe.common.dingding.message.type.Text;
import com.ciyun.renshe.common.dingding.sdk.request.MessageCorpParam;
import com.ciyun.renshe.entity.Problem;
import com.ciyun.renshe.entity.Solve;
import com.ciyun.renshe.entity.User;
import com.ciyun.renshe.mapper.ProblemMapper;
import com.ciyun.renshe.mapper.SolveMapper;
import com.ciyun.renshe.mapper.UserMapper;
import com.ciyun.renshe.service.SolveService;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.taobao.api.ApiException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class SolveServiceImpl extends ServiceImpl<SolveMapper, Solve> implements SolveService {

    private SolveMapper solveMapper;
    private ProblemMapper problemMapper;
    private UserMapper userMapper;

    public SolveServiceImpl(SolveMapper solveMapper, ProblemMapper problemMapper, UserMapper userMapper) {
        this.solveMapper = solveMapper;
        this.problemMapper = problemMapper;
        this.userMapper = userMapper;
    }

    /**
     * 根据问题id 查询对应的 反馈
     *
     * @param problemId
     * @return
     */
    @Override
    public List<Solve> findSolvesByProblemId(Integer problemId) {
        List<Solve> solves = solveMapper.findSolvesByProblemId(problemId);
        return solves;
    }

    /**
     * 添加反馈
     *
     * @param solve
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createSolve(Solve solve) {
        solve.setFlag(1).setSolveTime(new Date());
        solveMapper.insert(solve);
        // 给提出问题的人发送通知
        Problem p1 = problemMapper.selectById(solve.getProblemId());
        Integer problemUserId = p1.getProblemUserId();
        User user = userMapper.selectById(problemUserId);
        String ddUserId = user.getDdUserId();

        MessageCorpParam param = new MessageCorpParam();
        param.setUserIdList(ddUserId);

        MessageBody messageBody = new MessageBody();
        messageBody.setMsgType(1);
        Text text = new Text();
        text.setContent("你提问的问题已经得到解答，请前往小程序查看");
        messageBody.setText(text);

        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = MessageTypeConvert.messageCorpconvert(messageBody);
        param.setMsg(msg);
        try {
            DDSendMessageUtils.messageCorpconversationAsync(param);
        } catch (ApiException e) {
            e.printStackTrace();
        }

        // 将问题修改为未读
        Problem problem = new Problem();
        problem.setProblemId(solve.getProblemId());
        problem.setIsSolve(1);
        problem.setIsLook(0);
        // 将对应的问题修改为已反馈
        problemMapper.updateById(problem);
    }
}
