package com.boco.framework.model.workflow.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Liu Feng
 * @Date: 2020-6-18 10:25
 */
@Data
@ApiModel("回访请求实体")
public class ReturnVisitRequest implements Serializable {
    private static final long serialVersionUID = 2529304865251258805L;
    @ApiModelProperty(notes = "1满意  2认可  3不满意")
    private Integer satisfactionStatus;

    @ApiModelProperty(notes = "意见")
    private String suggest;

    @ApiModelProperty("投诉主键")
    private Integer complaintId;

    @ApiModelProperty("投诉信息表主键")
    private Integer complaintInfoId;
}
