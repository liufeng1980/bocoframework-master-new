package com.boco.framework.model.workflow.response;

import com.boco.framework.model.common.JkptCommParamdic;
import com.boco.framework.model.workflow.ComplaintProcessInfo;
import com.boco.framework.model.workflow.request.Complaint;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Liu Feng
 * @Date: 2020-6-3 15:42
 */
@Data
@ApiModel("页面详情")
public class DetailFormResponse implements Serializable {
    private Integer isCreatOrgGroupType;

    @ApiModelProperty("当前时间")
    private String visitTime;

    @ApiModelProperty("投诉信息")
    private Complaint complaint;

    @ApiModelProperty("审核信息")
    private JkptTsglAuditInfoResponse auditInfo;

    @ApiModelProperty("文件类型字典")
    private List<JkptCommParamdic> dicFileTypes;

    @ApiModelProperty("处理历史")
    private List<ComplaintProcessInfo> processInfos;

    @ApiModelProperty("机构关系")
    private List<JkptTsglOrgRelationExt> orgRelationExtList;

    @ApiModelProperty("审核列表id")
    private List<Integer> auditIdList;

    @ApiModelProperty("刷新目标")
    private String refrushTarget;
}
