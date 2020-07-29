package com.ciyun.renshe.service;

import com.ciyun.renshe.common.Page;
import com.ciyun.renshe.common.PageResult;
import com.ciyun.renshe.common.Result;
import com.ciyun.renshe.entity.Notice;
import com.baomidou.mybatisplus.extension.service.IService;

public interface NoticeService extends IService<Notice> {

    /**
     * 根据类型查询通知公告或者政策解读
     *
     * @param page
     * @param userId
     * @param type
     * @return
     */
    PageResult findNoticeByType(Page page, Integer userId, Integer type);

    /**
     * 后台根据类型查询通知公告或者政策解读
     *
     * @param page
     * @param userId
     * @param type
     * @return
     */
    PageResult findAdminNoticeByType(Page page, Integer userId, Integer roleId, Integer type);

    /**
     * 更新点赞数或者阅读数
     *
     * @param noticeId
     * @param like
     * @param readingNumber
     */
    void readingAndLikes(Integer noticeId, Integer like, Integer readingNumber);

    Notice findNoticeById(Integer noticeId);

    /**
     * 查询全部消息
     *
     * @param cityId
     * @param areaId
     * @param streetId
     * @param gridId
     * @return
     */
    Result findMessageCount(Integer cityId, Integer areaId, Integer streetId, Integer gridId);

    /**
     * 定时统计未读人数
     */
    void countNumberOfUnread();

    String messagePercentage(String city, String areaId, String streetId, String gridId, String chatId);
}











