package com.boco.framework.model.workflow.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @Author: Liu Feng
 * @Date: 2020-6-3 16:11
 */
@Data
@ApiModel("详情页请求参数")
public class DetailFormRequest {
    @ApiModelProperty("审核表id")
    private Integer auditInfoId;
    @ApiModelProperty("投诉id")
    private Integer complaintId;

    @ApiModelProperty("刷新目标")
    private String refrushTarget;


}
