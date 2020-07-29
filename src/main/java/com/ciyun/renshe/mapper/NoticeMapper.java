package com.ciyun.renshe.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ciyun.renshe.entity.Notice;import com.ciyun.renshe.entity.po.NoticePO;import org.apache.ibatis.annotations.Param;import java.util.List;

public interface NoticeMapper extends BaseMapper<Notice> {
    List<Notice> findNoticeByType(@Param("userId") Integer userId,
                                  @Param("gridList") List<Integer> gridList,
                                  @Param("type") Integer type);

    List<Notice> getMessageHistory2DD(List<Integer> noticeIds);

    List<Integer> countNumberOfUnread();

    List<NoticePO> getMessageHistory(@Param("title") String title, @Param("chatOrUser") Integer chatOrUser, @Param("startDate") String startDate,
                                     @Param("endDate") String endDate, @Param("type") Integer type);
}