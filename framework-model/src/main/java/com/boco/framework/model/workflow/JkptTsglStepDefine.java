package com.boco.framework.model.workflow;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: Liu Feng
 * @Date: 2020-6-10 13:55
 */
@Data
public class JkptTsglStepDefine implements Serializable {
    /**
     * 发送方
     */
    private String sender;

    /**
     * 通过接收方
     */
    private String successreceiver;

    /**
     * 通过状态
     */
    private Integer successstatus;

    /**
     * 审核步骤
     */
    private Integer step;

    /**
     * 审核待下一步处理描述信息
     */
    private String successdescribe;

    /**
     * 被驳回描述信息
     */
    private String rejectdescribe;

    /**
     * 通过路径：1-投诉，2-处理，3-回访
     */
    private Integer passpath;

    /**
     * 被驳回接收方
     */
    private String rejectreceiver;

    /**
     * 被驳回状态
     */
    private Integer rejectstatus;

    /**
     * 审核通过描述信息
     */
    private String dealsucessdescribe;

    private String remark1;

    private String remark2;

    private String remark3;

    private String remark4;

    private Integer remark5;

    private Integer remark6;

    private Date remark7;

    private Date remark8;

    /**
     * 审核驳回描述信息
     */
    private String dealrejectdescribe;

    /**
     * 投诉父类型
     */
    private String complaintparenttype;
}
