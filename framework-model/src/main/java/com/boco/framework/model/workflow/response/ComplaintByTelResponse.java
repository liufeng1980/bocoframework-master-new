package com.boco.framework.model.workflow.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: Liu Feng
 * @Date: 2020-5-29 11:12
 */
@Data
@ApiModel("根据车牌或者联系电话获取投诉列表结果")
public class ComplaintByTelResponse {
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("事件描述")
    private String eventDesc;
}
