package com.boco.framework.model.workflow.request;

import lombok.Data;

/**
 * @Author: Liu Feng
 * @Date: 2020-5-29 17:18
 */
@Data
public class ComplaintResult extends Complaint {
    /**
     * 调用包返回标记：1-成功,0-失败
     */
    private String pReturnVal = "1";
    /**
     * 异常信息
     */
    private String pMsg = "";
}
