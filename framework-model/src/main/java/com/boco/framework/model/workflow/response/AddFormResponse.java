package com.boco.framework.model.workflow.response;

import com.boco.framework.model.common.JkptCommParamdic;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel("添加表单相关参数")
public class AddFormResponse implements Serializable {
    @ApiModelProperty("投诉等级字典")
    private List<JkptCommParamdic> complaintLevels;

    @ApiModelProperty("父投诉类型字典")
    private List<JkptCommParamdic> complaintTypesParents;

    @ApiModelProperty("子投诉类型字典")
    private List<JkptCommParamdic> complaintTypesSubs;

    @ApiModelProperty("是否字典")
    private List<JkptCommParamdic> weatherOrNot;

    @ApiModelProperty("上传资料类型字典")
    private List<JkptCommParamdic> complaintFileTypes;

    @ApiModelProperty("证件类型字典")
    private List<JkptCommParamdic> idTypes;

    @ApiModelProperty("投诉ETC支付方式字典")
    private List<JkptCommParamdic> complaintEtcPayTypes;

    @ApiModelProperty("etc代理机构字典")
    private List<JkptCommParamdic> etcAgents;

    @ApiModelProperty("事件时间 yyyy-MM-dd HH:mm:ss")
    private String eventTime;

    @ApiModelProperty("发生时间 yyyy-MM-dd HH:mm:ss")
    private String etcEventTime;

    @ApiModelProperty("受理时间 yyyy-MM-dd HH:mm:ss")
    private String etcAcceptTime;

    @ApiModelProperty("是否能添加")
    private Boolean canAdd;
}
