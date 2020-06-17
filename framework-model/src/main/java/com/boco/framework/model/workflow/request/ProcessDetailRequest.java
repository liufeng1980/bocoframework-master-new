package com.boco.framework.model.workflow.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Liu Feng
 * @Date: 2020-6-10 10:18
 */
@Data
@ApiModel("处理信息实体")
public class ProcessDetailRequest implements Serializable {
    /**
     * 处理部门
     */
    @ApiModelProperty("处理部门")
    String dealDeptType;
    @ApiModelProperty("投诉管理审核信息表id")
    Integer complaintInfoId;
    /**
     * 投诉id
     */
    @ApiModelProperty("投诉id")
    Integer complaintId;

    /**
     * 意见建议
     */
    @ApiModelProperty("意见建议")
    String suggestion;
    /**
     * 接收部门
     */
    @ApiModelProperty("接收部门")
    String receiveOrgId;
    /***
     * 提交类型：1-通过，2-驳回，3-（处理机构是呼叫中心-不满意，处理机构是分中心-受理），4-放入回收站，5-处理完成
     */
    @ApiModelProperty("提交类型：1-通过，2-驳回，3-（处理机构是呼叫中心-不满意，处理机构是分中心-受理），4-放入回收站，5-处理完成")
    Integer submitType;

    /**
     *重新流转次数
     */
    @ApiModelProperty("重新流转次数")
    String reCirculation;
}
