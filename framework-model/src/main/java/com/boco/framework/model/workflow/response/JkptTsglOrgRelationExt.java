package com.boco.framework.model.workflow.response;

import com.boco.framework.model.workflow.JkptTsglOrgrelation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: Liu Feng
 * @Date: 2020-5-29 11:38
 */
@Data
@ApiModel("机构流转表")
public class JkptTsglOrgRelationExt extends JkptTsglOrgrelation {
    @ApiModelProperty("被分配权限机构名称")
    private String allotOrgName;
}
