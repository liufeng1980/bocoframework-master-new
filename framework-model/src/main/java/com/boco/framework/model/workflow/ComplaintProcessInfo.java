package com.boco.framework.model.workflow;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Liu Feng
 * @Date: 2020-6-3 15:35
 */
@Data
@ApiModel("处理过程")
public class ComplaintProcessInfo implements Serializable {
    /**
     * 状态描述：审核通过，驳回等
     */
    @ApiModelProperty("状态描述：审核通过，驳回等")
    private String statusInfo = "";
    /**
     * 处理时间
     */
    @ApiModelProperty("处理时间")
    private String dealTime;
    /**
     * 接收机构Id
     */
    @ApiModelProperty("接收机构Id")
    private String receiveOrgId;
    /**
     * 接收人工号
     */
    @ApiModelProperty("接收人工号")
    private String receiveUserId = "";
    /**
     *
     */
    @ApiModelProperty("")
    private String receiveLoginId;
    /**
     * 接收机构名称
     */
    @ApiModelProperty("接收机构名称")
    private String orgName;
    /**
     * 发送方处理意见及要求
     */
    @ApiModelProperty("发送方处理意见及要求")
    private String suggestion = "";

    /**
     * 投诉管理审核信息表主键
     */
    @ApiModelProperty("投诉管理审核信息表主键")
    private Integer fkid;

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private Integer pkId;
}
