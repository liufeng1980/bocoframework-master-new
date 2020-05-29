package com.boco.framework.model.workflow;

import lombok.Data;

import java.util.Date;

@Data
public class Callflow {
    /**
     * 主键
     */
    private Integer keyId;

    private Integer currentHisId;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 机构
     */
    private String orgId;

    /**
     * 投诉内容
     */
    private String content;

    private static final long serialVersionUID = 1L;
}
