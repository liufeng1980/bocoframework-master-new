package com.boco.framework.model.workflow;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Liu Feng
 * @Date: 2020-5-27 17:20
 */
@Data
@ApiModel("附件")
public class UploadDocumentItem implements Serializable {
    private Integer attachmentId = 0;
    private String newFileName;
    private String oldFileName;
    private String uploadTime;
    private String filePath;
    private Long fileSize;
    private String creationOrgId = "";
    private Integer fileType = 0;
    /**
     * 文件类型名称
     */
    @ApiModelProperty("文件类型名称")
    private String fileTypeName = "";
    /**
     * 创建机构名称
     */
    @ApiModelProperty("创建机构名称")
    private String creationOrgName = "";
    /**
     * 上传人
     */
    @ApiModelProperty("上传人")
    private String createUserID;
    private String personName;
}
