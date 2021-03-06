package com.ciyun.renshe.common.dingding.sdk.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 创建群
 *
 * @Date 2020/4/9 15:37
 * @Author Admin
 * @Version 1.0
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class ChatCreateParam {
    /**
     * 群名称，长度限制为1~20个字符
     */
    private String name;
    /**
     * 群主userId，员工唯一标识ID；必须为该会话useridlist的成员之一
     */
    private String owner;
    /**
     * 群成员列表，每次最多支持40人，群人数上限为1000
     */
    private List<String> useridlist;
    /**
     * 新成员是否可查看聊天历史消息（新成员入群是否可查看最近100条聊天记录），
     * <p>
     * 0代表否；1代表是；不传默认为否
     */
    private Integer showHistoryType;
    /**
     * 群可搜索，0-默认，不可搜索，1-可搜索
     */
    private Integer searchable;
    /**
     * 入群验证，0：不入群验证（默认） 1：入群验证
     */
    private Integer validationType;
    /**
     * @ all 权限，0-默认，所有人，1-仅群主可@ all
     */
    private Integer mentionAllAuthority;
    /**
     * 群禁言，0-默认，不禁言，1-全员禁言
     */
    private Integer chatBannedType;
    /**
     * 管理类型，0-默认，所有人可管理，1-仅群主可管理
     */
    private Integer managementType;

}
