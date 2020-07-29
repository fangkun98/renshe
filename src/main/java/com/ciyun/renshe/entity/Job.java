package com.ciyun.renshe.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "com-ciyun-renshe-entity-Job")
@Getter
@Setter
@ToString
@TableName(value = "sys_job")
public class Job {
    public static final String COL_UPDATE_TIME = "update_time";
    @TableId(value = "job_id", type = IdType.AUTO)
    @ApiModelProperty(value = "")
    private Integer jobId;

    /**
     * bean 名称
     */
    @TableField(value = "bean_name")
    @ApiModelProperty(value = "bean 名称")
    private String beanName;

    /**
     * 方法名称
     */
    @TableField(value = "method_name")
    @ApiModelProperty(value = "方法名称")
    private String methodName;

    /**
     * 方法参数
     */
    @TableField(value = "method_params")
    @ApiModelProperty(value = "方法参数")
    private String methodParams;

    /**
     * cron 表达式
     */
    @TableField(value = "cron_expression")
    @ApiModelProperty(value = "cron 表达式")
    private String cronExpression;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 1 正常 0暂停
     */
    @TableField(value = "job_status")
    @ApiModelProperty(value = "1 正常 0暂停")
    private Integer jobStatus;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 执行时间
     */
    @TableField(value = "execute_time")
    @ApiModelProperty(value = "执行时间")
    private Date executeTime;

    /**
     * 发送的消息记录id
     */
    @TableField(value = "notice_id")
    @ApiModelProperty(value = "发送的消息记录id")
    private Integer noticeId;

    public static final String COL_JOB_ID = "job_id";

    public static final String COL_BEAN_NAME = "bean_name";

    public static final String COL_METHOD_NAME = "method_name";

    public static final String COL_METHOD_PARAMS = "method_params";

    public static final String COL_CRON_EXPRESSION = "cron_expression";

    public static final String COL_REMARK = "remark";

    public static final String COL_JOB_STATUS = "job_status";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_EXECUTE_TIME = "execute_time";

    public static final String COL_NOTICE_ID = "notice_id";
}