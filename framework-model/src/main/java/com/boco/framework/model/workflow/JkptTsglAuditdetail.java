package com.boco.framework.model.workflow;

import lombok.Data;

import java.util.Date;

/**
 * @Author: Liu Feng
 * @Date: 2020-6-2 15:38
 */
@Data
public class JkptTsglAuditdetail {
    /**
     * 主键，采用序列
     */
    private Integer pkid;

    /**
     * JKPT_TSGL_AUDITINFO表主键
     */
    private Integer fkid;

    /**
     * 状态描述
     */
    private String statusinfo;

    /**
     * 发送人工号
     */
    private String senduserid;

    /**
     * 处理时间
     */
    private Date dealtime;

    /**
     * 通过路径：1-投诉，2-处理，3-回访
     */
    private Integer passpath;

    /**
     * 接收方工号
     */
    private String receiveuserid;

    /**
     * 备用字段
     */
    private String remark1;

    private String remark2;

    private String remark3;

    private String remark4;

    /**
     * 已使用：N表示被重新流转次数
     */
    private Integer remark5;

    private Integer remark6;

    private Date remark7;

    private Date remark8;

    /**
     * 接收机构
     */
    private String receiveorgid;

    /**
     * 处理前状态
     */
    private Integer status;

    /**
     * 发送机构
     */
    private String sendorgid;

    /**
     * 处理后状态，99-(归档、放入回收站,98-不满意)
     */
    private Integer newstatus;

    /**
     * 是否为驳回 1=驳回 其他为非驳回
     */
    private String isreject;

    /**
     * 不满意次数，从0依次递增
     */
    private Integer frequency;

    /**
     * 导出是否显示：1-显示，0-不显示
     */
    private String isshow;

    /**
     * jkpt_tsgl_complaint表主键 投诉id
     */
    private Integer complaintid;

    /**
     * 发送方处理意见及要求
     */
    private String suggestion;
}
