package com.ciyun.renshe.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ciyun.renshe.common.MessageInfo;
import com.ciyun.renshe.common.Result;
import com.ciyun.renshe.common.dingding.DDErrResult;
import com.ciyun.renshe.common.dingding.sdk.DingDingSDKUtils;
import com.ciyun.renshe.common.dingding.sdk.request.ChatUpdateParam;
import com.ciyun.renshe.controller.vo.area.UpdateGridNameByIdVO;
import com.ciyun.renshe.entity.Chat;
import com.ciyun.renshe.entity.ChatAt;
import com.ciyun.renshe.entity.ChatAtRecord;
import com.ciyun.renshe.entity.Grid;
import com.ciyun.renshe.mapper.ChatAtMapper;
import com.ciyun.renshe.mapper.ChatAtRecordMapper;
import com.ciyun.renshe.mapper.ChatMapper;
import com.ciyun.renshe.mapper.GridMapper;
import com.ciyun.renshe.service.GridService;
import com.dingtalk.api.response.OapiChatUpdateResponse;
import com.taobao.api.ApiException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Slf4j
@Service
@AllArgsConstructor
public class GridServiceImpl extends ServiceImpl<GridMapper, Grid> implements GridService {

    private final GridMapper gridMapper;
    private final ChatMapper chatMapper;
    private final ChatAtMapper chatAtMapper;
    private final ChatAtRecordMapper chatAtRecordMapper;

    /**
     * 根据 id 修改网格名称
     *
     * @param gridNameVO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result updateGridNameById(UpdateGridNameByIdVO gridNameVO) {

        Result result = new Result(MessageInfo.UPDATE_INFO.getInfo());

        Grid grid = new Grid();
        grid.setGridId(gridNameVO.getGridId());
        grid.setGridName(gridNameVO.getGridName());
        gridMapper.updateById(grid);

        Grid gridInfo = gridMapper.selectById(gridNameVO.getGridId());
        String chatId = gridInfo.getChatId();

        // 获取群具体信息
        Chat chatInfo = chatMapper.selectOne(Wrappers.lambdaQuery(Chat.class)
                .eq(Chat::getChatId, chatId));
        // 获取到未修改之前的群名称
        String beforeChatName = chatInfo.getName();

        // 修改钉钉群的名称
        ChatUpdateParam param = new ChatUpdateParam();
        param.setChatId(chatId);
        param.setName(gridNameVO.getGridName());
        try {
            OapiChatUpdateResponse response = DingDingSDKUtils.updateChat(param);
            if (!response.getErrcode().equals(0L)) {
                return DDErrResult.dataError(result, response.getErrmsg());
            }
        } catch (ApiException e) {
            log.warn(e.getErrMsg());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            return DDErrResult.connException(result);
        }

        // 将群名称修改
        Chat chat = new Chat();
        chat.setName(gridNameVO.getGridName());
        // 将名称进行修改
        chatMapper.update(chat, Wrappers.lambdaQuery(Chat.class)
                .eq(Chat::getChatId, chatId));

        // 修改@群主列表中的
        ChatAt chatAt = new ChatAt();
        chatAt.setConversationTitle(gridNameVO.getGridName());
        chatAtMapper.update(chatAt, Wrappers.lambdaQuery(ChatAt.class)
                .eq(ChatAt::getConversationTitle, beforeChatName));

        ChatAtRecord chatAtRecord = new ChatAtRecord();
        chatAtRecord.setChatName(gridNameVO.getGridName());
        chatAtRecordMapper.update(chatAtRecord, Wrappers.lambdaQuery(ChatAtRecord.class)
                .eq(ChatAtRecord::getChatName, beforeChatName));

        return result;
    }
}
