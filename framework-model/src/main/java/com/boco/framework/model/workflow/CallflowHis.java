package com.boco.framework.model.workflow;

import lombok.Data;

import java.util.Date;

@Data
public class CallflowHis {
    /**
     * 注键
     */
    private Integer keyId;

    /**
     * 呼叫流转id
     */
    private Integer callFlowKeyId;

    /**
     * 处理机构
     */
    private String processOrgId;

    /**
     * 处理内容
     */
    private String processContent;

    /**
     * 处理人
     */
    private String processUserId;

    /**
     * 处理结果
     */
    private Integer resultcode;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 提交到机构
     */
    private String toOrgId;

    private static final long serialVersionUID = 1L;
}
