package com.boco.framework.model.workflow.response;

import com.boco.framework.model.workflow.JkptTsglAuditinfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: Liu Feng
 * @Date: 2020-6-3 14:18
 */
@Data
@ApiModel("投诉审批信息")
public class JkptTsglAuditInfoResponse extends JkptTsglAuditinfo {
    /**
     * 发送机构名称
     */
    @ApiModelProperty("发送机构名称")
    private String sendOrgName;

    /**
     * 接收机构名称
     */
    @ApiModelProperty("接收机构名称")
    private String receiveOrgName;

    @ApiModelProperty(notes = "投诉发起机构组：1=待处理，2=驳回 " +
            "中转组(OperationDept,MonitorDept,GaoLuHearCenter)：" +
            "OperationDept:1=驳回" +
            "MonitorDept:jsDealStatus=1 and passpath=1 下行待处理，流转机构可以选择流转的下属机构" +
            "GaoLuHearCenter:jsDealStatus=1 and passpath=2/3 上行待处理，不可以选择流转的下属机构")
    private String jsDealStatus = "";


    /**
     * 是否显示重新流转
     */
    @ApiModelProperty("是否显示重新流转")
    private String isShowReAdd = "0";

    /**
     * 是否显示处理意见信息
     */
    @ApiModelProperty("是否显示处理意见信息")
    private String isShowAudit = "0";

    /**
     * 处理意见标题
     */
    private String auditTitle = "";

}
