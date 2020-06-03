package com.boco.framework.model.workflow;

import lombok.Data;

import java.util.Date;

/**
 * @Author: Liu Feng
 * @Date: 2020-6-2 16:39
 */
@Data
public class JkptTsglAuditinfo {
    /**
     * JKPT_TSGL_COMPLAINT表主键
     */
    private Integer fkid;

    /**
     * 不满意次数，从0依次递增
     */
    private Integer frequency;

    /**
     * 当前处理状态，99-(归档、放入回收站,98-不满意)
     */
    private Integer currentstatus;

    /**
     * 当前处理意见
     */
    private String currentsuggestion;

    /**
     * 是否归档：1-是，0-否
     */
    private String isarchive;

    /**
     * 是否是最新投诉：1-是，0-否
     */
    private String iscurrent;

    /**
     * 处理结果：1-满意，2-认可，3-不满意
     */
    private String dealresult;

    /**
     * 已使用：其他功能按钮，1-分中心受理
     */
    private String remark1;

    private String remark2;

    private String remark3;

    private String remark4;

    /**
     * 已使用：N表示被重新流转次数
     */
    private Integer remark5;

    /**
     * 已使用：用于判断是否重复提交
     */
    private Integer remark6;

    private Date remark7;

    private Date remark8;

    /**
     * 是否放入回收站：1-是，0-否
     */
    private String isrecyclebin;

    /**
     * 发送机构
     */
    private String sendorgid;

    /**
     * 主键，采用序列
     */
    private Integer pkid;

    /**
     * 是否为驳回 1=驳回 其他为非驳回
     */
    private String isreject;

    private Date creationtime;

    /**
     * 所用天数
     */
    private Integer useddays;

    /**
     * 归档时间
     */
    private Date archivetime;

    /**
     * 回收站时间
     */
    private Date recyclebintime;

    /**
     * 接收机构
     */
    private String receiveorgid;

    /**
     * 通过路径：1-投诉，2-处理，3-回访
     */
    private Integer passpath;

    /**
     * 发送人
     */
    private String senduserid;

    /**
     * 当前状态描述
     */
    private String currentstatusdesc;
}
