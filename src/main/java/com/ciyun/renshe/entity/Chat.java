package com.ciyun.renshe.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@ApiModel(value = "com-ciyun-renshe-entity-Chat")
@Getter
@Setter
@ToString
@TableName(value = "dd_chat")
public class Chat {
    @TableId(value = "chat_id", type = IdType.INPUT)
    @ApiModelProperty(value = "")
    private String chatId;

    /**
     * 群名称
     */
    @TableField(value = "name")
    @ApiModelProperty(value = "群名称")
    private String name;

    /**
     * 群主userId，员工唯一标识ID；必须为该会话useridlist的成员之一
     */
    @TableField(value = "owner")
    @ApiModelProperty(value = "群主userId，员工唯一标识ID；必须为该会话useridlist的成员之一")
    private String owner;

    /**
     * 群成员列表，每次最多支持40人，群人数上限为1000
     */
    @TableField(value = "user_id_list")
    @ApiModelProperty(value = "群成员列表，每次最多支持40人，群人数上限为1000")
    private String userIdList;

    /**
     * 新成员是否可查看聊天历史消息（新成员入群是否可查看最近100条聊天记录），
     * <p>
     * 0代表否；1代表是；不传默认为否
     */
    @TableField(value = "show_history_type")
    @ApiModelProperty(value = "新成员是否可查看聊天历史消息（新成员入群是否可查看最近100条聊天记录），,,0代表否；1代表是；不传默认为否")
    private Integer showHistoryType;

    /**
     * 群可搜索，0-默认，不可搜索，1-可搜索
     */
    @TableField(value = "search_able")
    @ApiModelProperty(value = "群可搜索，0-默认，不可搜索，1-可搜索")
    private Integer searchAble;

    /**
     * 入群验证，0：不入群验证（默认） 1：入群验证
     */
    @TableField(value = "validation_type")
    @ApiModelProperty(value = "入群验证，0：不入群验证（默认） 1：入群验证")
    private Integer validationType;

    /**
     * @ all 权限，0-默认，所有人，1-仅群主可@ all
     */
    @TableField(value = "mention_all_authority")
    @ApiModelProperty(value = "@ all 权限，0-默认，所有人，1-仅群主可@ all")
    private Integer mentionAllAuthority;

    /**
     * 群禁言，0-默认，不禁言，1-全员禁言
     */
    @TableField(value = "chat_banned_type")
    @ApiModelProperty(value = "群禁言，0-默认，不禁言，1-全员禁言")
    private Integer chatBannedType;

    /**
     * 管理类型，0-默认，所有人可管理，1-仅群主可管理
     */
    @TableField(value = "management_type")
    @ApiModelProperty(value = "管理类型，0-默认，所有人可管理，1-仅群主可管理")
    private Integer managementType;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 1有效 0无效
     */
    @TableField(value = "flag")
    @ApiModelProperty(value = "1有效 0无效")
    private Integer flag;

    /**
     * 管理员id
     */
    @TableField(value = "manager_user")
    @ApiModelProperty(value = "管理员id")
    private String managerUser;

    /**
     * 群所在的区
     */
    @TableField(value = "position")
    @ApiModelProperty(value = "群所在的区")
    private String position;

    /**
     * 1 区域群 2 行业群 3 规模群 4大群
     */
    @TableField(value = "type")
    @ApiModelProperty(value = "1 区域群 2 行业群 3 规模群 4大群")
    private Integer type;

    /**
     * 规模id
     */
    @TableField(value = "es_id")
    @ApiModelProperty(value = "规模id")
    private Integer esId;

    /**
     * 行业id
     */
    @TableField(value = "industry_id")
    @ApiModelProperty(value = "行业id")
    private Integer industryId;

    /**
     * 临时群属于哪个区
     */
    @TableField(value = "area_id")
    @ApiModelProperty(value = "临时群属于哪个区")
    private Integer areaId;

    /**
     * 部门id
     */
    @TableField(value = "dept_id")
    @ApiModelProperty(value = "部门id")
    private Integer deptId;

    public static final String COL_CHAT_ID = "chat_id";

    public static final String COL_NAME = "name";

    public static final String COL_OWNER = "owner";

    public static final String COL_USER_ID_LIST = "user_id_list";

    public static final String COL_SHOW_HISTORY_TYPE = "show_history_type";

    public static final String COL_SEARCH_ABLE = "search_able";

    public static final String COL_VALIDATION_TYPE = "validation_type";

    public static final String COL_MENTION_ALL_AUTHORITY = "mention_all_authority";

    public static final String COL_CHAT_BANNED_TYPE = "chat_banned_type";

    public static final String COL_MANAGEMENT_TYPE = "management_type";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_FLAG = "flag";

    public static final String COL_MANAGER_USER = "manager_user";

    public static final String COL_POSITION = "position";

    public static final String COL_TYPE = "type";

    public static final String COL_ES_ID = "es_id";

    public static final String COL_INDUSTRY_ID = "industry_id";

    public static final String COL_AREA_ID = "area_id";

    public static final String COL_DEPT_ID = "dept_id";
}