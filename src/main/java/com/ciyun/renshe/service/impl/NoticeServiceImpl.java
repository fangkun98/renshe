package com.ciyun.renshe.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ciyun.renshe.common.MessageInfo;
import com.ciyun.renshe.common.Page;
import com.ciyun.renshe.common.PageResult;
import com.ciyun.renshe.common.Result;
import com.ciyun.renshe.common.dingding.sdk.ChatSDKUtil;
import com.ciyun.renshe.entity.*;
import com.ciyun.renshe.entity.po.AreaPO;
import com.ciyun.renshe.entity.po.GridPO;
import com.ciyun.renshe.entity.po.StreetPO;
import com.ciyun.renshe.mapper.*;
import com.ciyun.renshe.service.NoticeService;
import com.dingtalk.api.response.OapiChatGetReadListResponse;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taobao.api.ApiException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@NoArgsConstructor
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

    private AreaPermissionsMapper areaPermissionsMapper;
    private NoticeMapper noticeMapper;
    private NoticeAreaMapper noticeAreaMapper;
    private AreaMapper areaMapper;
    private StreetMapper streetMapper;
    private GridMapper gridMapper;
    private NoticeChatMapper noticeChatMapper;

    @Autowired
    public NoticeServiceImpl(AreaPermissionsMapper areaPermissionsMapper, NoticeMapper noticeMapper, NoticeAreaMapper noticeAreaMapper, AreaMapper areaMapper, StreetMapper streetMapper, GridMapper gridMapper, NoticeChatMapper noticeChatMapper) {
        this.areaPermissionsMapper = areaPermissionsMapper;
        this.noticeMapper = noticeMapper;
        this.noticeAreaMapper = noticeAreaMapper;
        this.areaMapper = areaMapper;
        this.streetMapper = streetMapper;
        this.gridMapper = gridMapper;
        this.noticeChatMapper = noticeChatMapper;
    }

    /**
     * 根据类型查询通知公告或者政策解读
     *
     * @param page
     * @param userId
     * @param type
     * @return
     */
    @Override
    public PageResult findNoticeByType(Page page, Integer userId, Integer type) {

        /*List<AreaPermissions> areaPermissions = areaPermissionsMapper.selectList(Wrappers.<AreaPermissions>lambdaQuery()
                .eq(AreaPermissions::getUserId, userId)
                .eq(AreaPermissions::getFlag, 1));*/
        // 如果为空说明为普通用户
        /*if (areaPermissions.isEmpty()) {

            noticeMapper.findNoticeByType(userId)
        }*/
        /*List<NoticeArea> areaList = noticeAreaMapper.selectList(Wrappers.
                <NoticeArea>lambdaQuery()
                .eq(NoticeArea::getUserId, userId)
                .isNotNull(NoticeArea::getGridId)
                .groupBy(NoticeArea::getAreaId));
        List<Integer> gridList = new ArrayList<>(areaList.size());
        if (!areaList.isEmpty()) {
            areaList.forEach(noticeArea -> gridList.add(noticeArea.getGridId()));
        }*/

        log.info("userId:{}", userId);

        // 根据 用户id 查询出用户所在的网格
        List<Integer> gridIdList = noticeAreaMapper.selectGridIdByUserId(userId);
        log.info("gridIdList{}:", JSON.toJSONString(gridIdList));

        PageInfo<Object> pageInfo = PageHelper.startPage(page.getPageNum().intValue(), page.getPageSize().intValue())
                .doSelectPageInfo(() -> noticeMapper.findNoticeByType(userId, gridIdList, type));

        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 后台根据类型查询通知公告或者政策解读
     *
     * @param page
     * @param userId
     * @param type
     * @return
     */
    @Override
    public PageResult findAdminNoticeByType(Page page, Integer userId, Integer roleId, Integer type) {

        //areaPermissionsMapper.selectList();
        // 如果为超级管理员或者市管理员，查询全部通知公告
        if (roleId.equals(1) || roleId.equals(2)) {
            PageInfo<Notice> pageInfo = PageHelper.startPage(page.getPageNum().intValue(), page.getPageSize().intValue())
                    .doSelectPageInfo(() -> noticeMapper.selectList(Wrappers.<Notice>lambdaQuery()
                            .eq(Notice::getFlag, 1).eq(Notice::getType, type).orderByDesc(Notice::getCreateTime)));
            return new PageResult(pageInfo.getTotal(), pageInfo.getList());
        }

        // 区管理员
        if (roleId.equals(3)) {
            List<AreaPermissions> areaPermissions = areaPermissionsMapper.selectList(Wrappers.<AreaPermissions>lambdaQuery()
                    .eq(AreaPermissions::getUserId, userId).eq(AreaPermissions::getAdminType, roleId));
            List<Integer> areaIds = new ArrayList<>();
            if (!areaPermissions.isEmpty()) {
                areaPermissions.forEach(area -> areaIds.add(area.getAreaId()));
            }

            List<Integer> gridIds = new ArrayList<>(10);
            for (Integer areaId : areaIds) {
                // 查询区下的所有网格
                // 查询管理员所有区权限
                List<AreaPO> allArea = areaMapper.findAllArea(areaId);
                if (!allArea.isEmpty()) {
                    AreaPO areaPO = allArea.get(0);
                    List<StreetPO> children = areaPO.getChildren();
                    for (StreetPO child : children) {
                        List<GridPO> gridPOList = child.getChildren();
                        for (GridPO gridPO : gridPOList) {
                            gridIds.add(gridPO.getGridId());
                        }
                    }
                }
            }

            PageInfo<Notice> pageInfo = PageHelper.startPage(page.getPageNum().intValue(), page.getPageSize().intValue())
                    .doSelectPageInfo(() -> noticeMapper.findNoticeByType(userId, gridIds, type));

            return new PageResult(pageInfo.getTotal(), pageInfo.getList());
        }

        // 如果为街道管理员
        if (roleId.equals(4)) {
            // 查询出街道下的所有网格
            List<AreaPermissions> areaPermissions = areaPermissionsMapper.selectList(Wrappers.<AreaPermissions>lambdaQuery()
                    .eq(AreaPermissions::getUserId, userId).eq(AreaPermissions::getAdminType, roleId));
            List<Integer> streetIds = new ArrayList<>();
            if (!areaPermissions.isEmpty()) {
                areaPermissions.forEach(area -> streetIds.add(area.getStreetId()));

                List<StreetPO> streets = streetMapper.findStreetsByStreetIds(streetIds);
                List<Integer> gridIds = new ArrayList<>(10);
                for (StreetPO street : streets) {
                    List<GridPO> gridPOS = street.getChildren();
                    for (GridPO gridPO : gridPOS) {
                        gridIds.add(gridPO.getGridId());
                    }
                }

                PageInfo<Notice> pageInfo = PageHelper.startPage(page.getPageNum().intValue(), page.getPageSize().intValue())
                        .doSelectPageInfo(() -> noticeMapper.findNoticeByType(userId, gridIds, type));

                return new PageResult(pageInfo.getTotal(), pageInfo.getList());
            }
        }

        if (roleId.equals(5)) {
            PageInfo<Notice> pageInfo = PageHelper.startPage(page.getPageNum().intValue(), page.getPageSize().intValue())
                    .doSelectPageInfo(() -> noticeMapper.findNoticeByType(userId, new ArrayList<>(), type));
            return new PageResult(pageInfo.getTotal(), pageInfo.getList());
        }

        return new PageResult(0L, new ArrayList<>());
    }

    /**
     * 更新点赞数或者阅读数
     *
     * @param noticeId
     * @param like
     * @param readingNumber
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void readingAndLikes(Integer noticeId, Integer like, Integer readingNumber) {
        Notice notice = noticeMapper.selectById(noticeId);
        if (like != null) {
            Integer likes = notice.getLikes();
            likes = likes + 1;
            notice.setLikes(likes);
        }
        if (readingNumber != null) {
            Integer reading = notice.getReadingNumber();
            reading = reading + 1;
            notice.setReadingNumber(reading);
        }
        noticeMapper.updateById(notice);
    }

    /**
     * 根据id查询公告
     * @param noticeId  企业id
     * @return
     */

    @Override
    public Notice findNoticeById(Integer noticeId) {
        Notice notice = noticeMapper.selectById(noticeId);
        notice.setReadingNumber(notice.getReadingNumber() + 1);
        noticeMapper.updateById(notice);
        return noticeMapper.selectById(noticeId);
    }

    /**
     * 查询全部消息
     *
     * @param cityId
     * @param areaId
     * @param streetId
     * @param gridId
     * @return
     */
    @Override
    public Result findMessageCount(Integer cityId, Integer areaId, Integer streetId, Integer gridId) {
        Result result = new Result(MessageInfo.GET_INFO.getInfo());

        // 如果市不为空，查询全部数据
        if (cityId != null) {
            Integer count = noticeMapper.selectCount(Wrappers.<Notice>lambdaQuery()
                    .eq(Notice::getFlag, 1));
            return result.setData(count);
        }

        // 如果区不为空
        if (areaId != null) {
            // 先查询同一个区下所有街道
            List<Street> streets = streetMapper.selectList(Wrappers.<Street>lambdaQuery()
                    .select(Street::getStreetId).eq(Street::getAreaId, areaId));
            List<Integer> streetIds = new ArrayList<>(streets.size());
            streets.forEach(street -> streetIds.add(street.getStreetId()));

            if (CollectionUtil.isNotEmpty(streetIds)) {
                // 查询街道下的所有网格
                List<Grid> grids = gridMapper.selectList(Wrappers.<Grid>lambdaQuery().select(Grid::getChatId)
                        .in(Grid::getStreetId, streetIds).eq(Grid::getFlag, 1));

                if (CollectionUtil.isNotEmpty(grids)) {
                    List<String> chatIds = new ArrayList<>(grids.size());
                    grids.forEach(grid -> chatIds.add(grid.getChatId()));

                    List<NoticeChat> noticeChats = noticeChatMapper.selectList(Wrappers.<NoticeChat>lambdaQuery()
                            .select(NoticeChat::getNoticeId)
                            .in(NoticeChat::getChatId, chatIds));

                    if (CollectionUtil.isNotEmpty(noticeChats)) {
                        List<Integer> noticeIds = new ArrayList<>(noticeChats.size());
                        noticeChats.forEach(noticeChat -> noticeIds.add(noticeChat.getNoticeId()));

                        // 查询全部消息
                        Integer count = noticeMapper.selectCount(Wrappers.<Notice>lambdaQuery()
                                .in(Notice::getNoticeId, noticeIds));
                        return result.setData(count);
                    }

                }

            }

        }

        // 如果街道不为空
        if (streetId != null) {
            // 查询街道下的所有网格
            List<Grid> grids = gridMapper.selectList(Wrappers.<Grid>lambdaQuery().select(Grid::getChatId)
                    .eq(Grid::getStreetId, streetId).eq(Grid::getFlag, 1));

            if (CollectionUtil.isNotEmpty(grids)) {
                List<String> chatIds = new ArrayList<>(grids.size());
                grids.forEach(grid -> chatIds.add(grid.getChatId()));

                List<NoticeChat> noticeChats = noticeChatMapper.selectList(Wrappers.<NoticeChat>lambdaQuery()
                        .select(NoticeChat::getNoticeId)
                        .in(NoticeChat::getChatId, chatIds));

                if (CollectionUtil.isNotEmpty(noticeChats)) {
                    List<Integer> noticeIds = new ArrayList<>(noticeChats.size());
                    noticeChats.forEach(noticeChat -> noticeIds.add(noticeChat.getNoticeId()));

                    // 查询全部消息
                    Integer count = noticeMapper.selectCount(Wrappers.<Notice>lambdaQuery()
                            .in(Notice::getNoticeId, noticeIds));
                    return result.setData(count);

                }
            }
        }

        // 如果网格不为空
        if (gridId != null) {
            // 查询街道下的所有网格
            /*List<Grid> grids = gridMapper.selectList(Wrappers.<Grid>lambdaQuery().select(Grid::getChatId)
                    .eq(Grid::getStreetId, streetId).eq(Grid::getFlag, 1));*/

            Grid grid = gridMapper.selectById(gridId);

            /*List<String> chatIds = new ArrayList<>(grids.size());
            grids.forEach(grid -> chatIds.add(grid.getChatId()));*/

            List<NoticeChat> noticeChats = noticeChatMapper.selectList(Wrappers.<NoticeChat>lambdaQuery()
                    .select(NoticeChat::getNoticeId)
                    .eq(NoticeChat::getChatId, grid.getChatId()));

            if (CollectionUtil.isNotEmpty(noticeChats)) {
                List<Integer> noticeIds = new ArrayList<>(noticeChats.size());
                noticeChats.forEach(noticeChat -> noticeIds.add(noticeChat.getNoticeId()));

                // 查询全部消息
                Integer count = noticeMapper.selectCount(Wrappers.<Notice>lambdaQuery()
                        .in(Notice::getNoticeId, noticeIds));
                return result.setData(count);
            }

        }

        return result.setData(0);
    }

    /**
     * 定时统计未读人数
     */
    @Override
    public void countNumberOfUnread() {
        List<Integer> noticeIds = noticeMapper.countNumberOfUnread();

        if (CollectionUtil.isNotEmpty(noticeIds)) {
            List<NoticeChat> noticeChats = noticeChatMapper.selectList(Wrappers.<NoticeChat>lambdaQuery()
                    .select(NoticeChat::getMsgId)
                    .in(NoticeChat::getNoticeId, noticeIds)
                    .eq(NoticeChat::getIsAllRead, 0));

            List<String> msgIds = new ArrayList<>(noticeChats.size());
            noticeChats.forEach(noticeChat -> msgIds.add(noticeChat.getMsgId()));

            for (String msgId : msgIds) {
                // ChatSDKUtil.getReadList();
            }
        }

    }

    // 查询未读人员列表
    public List<String> findUnReadList(String msgId) {

        //AtomicInteger atomicInteger = new AtomicInteger(100);

        List<String> readUserList = new ArrayList<>(10);

        boolean flag = true;

        while (flag) {
            try {
                OapiChatGetReadListResponse response = ChatSDKUtil.getReadList(msgId, 0L, 100L);
                if (response.getErrcode().equals(0L)) {
                    if (CollectionUtil.isNotEmpty(response.getReadUserIdList())) {
                        readUserList.addAll(response.getReadUserIdList());
                    }
                    if (response.getNextCursor() != null) {
                        ChatSDKUtil.getReadList(msgId, response.getNextCursor(), 100L);
                    } else {
                        flag = false;
                    }
                } else {
                    log.error(response.getErrmsg());
                }

            } catch (ApiException e) {
                e.printStackTrace();
                log.error("连接钉钉查询已读人员列表出现异常");
            }
        }

        return readUserList;
    }

    @Override
    public String messagePercentage(String city, String areaId, String streetId, String gridId, String chatId) {

        if (StringUtils.isNotBlank(city)) {
            // 查询全部的消息已读条数
            List<NoticeChat> noticeChats = noticeChatMapper.selectList(Wrappers
                    .<NoticeChat>lambdaQuery()
                    .isNotNull(NoticeChat::getChatId));
            return sumReadNumAndUnReadNum(noticeChats);
        }

        // 查询某个区下的消息送达率
        if (StringUtils.isNotBlank(areaId)) {
            List<Street> streets = streetMapper.selectList(Wrappers.lambdaQuery(Street.class)
                    .select()
                    .eq(Street::getAreaId, areaId));
            List<Integer> streetIdList = new ArrayList<>(streets.size());

            if (CollectionUtil.isNotEmpty(streetIdList)) {
                List<Grid> grids = gridMapper.selectList(Wrappers.lambdaQuery(Grid.class)
                        .eq(Grid::getFlag, 1)
                        .in(Grid::getStreetId, streetIdList));
                List<String> chatList = new ArrayList<>(grids.size());
                grids.forEach(grid -> chatList.add(grid.getChatId()));
                if (CollectionUtil.isNotEmpty(chatList)) {
                    List<NoticeChat> noticeChats = noticeChatMapper.selectList(Wrappers.lambdaQuery(NoticeChat.class)
                            .in(NoticeChat::getChatId, chatList));
                    return this.sumReadNumAndUnReadNum(noticeChats);
                } else {
                    return "100%";
                }
            }
        }

        if (StringUtils.isNotBlank(streetId)) {
            List<Grid> grids = gridMapper.selectList(Wrappers.lambdaQuery(Grid.class)
                    .eq(Grid::getStreetId, streetId)
                    .eq(Grid::getFlag, 1));
            List<String> chatList = new ArrayList<>(grids.size());
            grids.forEach(grid -> chatList.add(grid.getChatId()));
            if (CollectionUtil.isNotEmpty(chatList)) {
                List<NoticeChat> noticeChats = noticeChatMapper.selectList(Wrappers.lambdaQuery(NoticeChat.class)
                        .in(NoticeChat::getChatId, chatList));
                return this.sumReadNumAndUnReadNum(noticeChats);
            } else {
                return "100%";
            }
        }

        if (StringUtils.isNotBlank(chatId)) {
            List<NoticeChat> noticeChats = noticeChatMapper.selectList(Wrappers.lambdaQuery(NoticeChat.class)
                    .eq(NoticeChat::getChatId, chatId));
            return this.sumReadNumAndUnReadNum(noticeChats);
        }
        return "100%";
    }

    private String sumReadNumAndUnReadNum(List<NoticeChat> noticeChats) {
        // 全部的消息总数
        int readCount = 0;

        int unreadCount = 0;

        for (NoticeChat noticeChat : noticeChats) {
            // 如果已读人数不为空
            if (noticeChat.getReadNum() != null) {
                readCount = readCount + noticeChat.getReadNum();
            }

            if (noticeChat.getUnreadNum() != null) {
                unreadCount = unreadCount + noticeChat.getUnreadNum();
            }
        }

        if (readCount == 0 && unreadCount == 0) {
            return "100%";
        } else {
            // 已读人数 / 总人数
            double v = ((double) readCount / (unreadCount + readCount));
            BigDecimal bigDecimal = new BigDecimal(v);

            NumberFormat percentInstance = NumberFormat.getPercentInstance();
            // 百分比 2 位
            percentInstance.setMaximumFractionDigits(2);
            String format = percentInstance.format(bigDecimal);
            return format;
        }

    }
}











