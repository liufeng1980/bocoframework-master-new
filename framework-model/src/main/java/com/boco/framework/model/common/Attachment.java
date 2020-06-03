package com.boco.framework.model.common;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: Liu Feng
 * @Date: 2020-5-29 17:01
 */

public class Attachment extends AuditBase implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -3521202851021715509L;

    /**
     * id
     */
    private Integer attachmentId = 0;

    /**
     * 主表Id
     */
    private String headId;

    /**
     * 类型 1 消息通知 2 情况上报
     */
    private Integer type;

    /**
     * 原始名
     */
    private String oldName;

    /**
     * 新名
     */
    private String newName;

    /**
     * 路径
     */
    private String filePath;

    /**
     * 文件大小
     */
    private Integer fileSize = 0;

    /**
     * 字典值（文件类型）DICVALUE
     */
    private Integer fileType = 0;

    /**
     * 文件类型名称
     */
    private String fileTypeName = "";

    /**
     * 字典分组(文件组)GROUPTYPE
     */
    private String groupType;

    /**
     * 创建部门Id
     */
    private String creationOrgId = "";

    /**
     * 创建机构名称
     */
    private String creationOrgName = "";

    /*
     * 投诉管理机构排序
     */
    private Integer orgOrder;


    private String creationUserId;

    /**
     * 上传时间
     */

    private Date uploadTime;

    /**
     * @return the id
     */
    public Integer getAttachmentId() {
        return attachmentId;
    }

    /**
     * @param attachmentId
     *            the attachmentId to set
     */
    public void setAttachmentId(Integer attachmentId) {
        Audit(this.attachmentId, attachmentId, "attachmentId");
        this.attachmentId = attachmentId;
    }

    /**
     * @return the 主表Id
     */
    public String getHeadId() {
        return headId;
    }

    /**
     * @param  headId 主表Id
     *            the headId to set
     */
    public void setHeadId(String headId) {
        Audit(this.headId, headId, "headId");
        this.headId = headId;
    }

    /**
     * @return the 类型1消息通知2情况上报
     */
    public Integer getType() {
        return type;
    }

    /**
     * @param  type 类型1消息通知2情况上报
     *            the type to set
     */
    public void setType(Integer type) {
        Audit(this.type, type, "type");
        this.type = type;
    }

    /**
     * @return the 原始名
     */
    public String getOldName() {
        return oldName;
    }

    /**
     * @param oldName 原始名
     *            the oldName to set
     */
    public void setOldName(String oldName) {
        Audit(this.oldName, oldName, "oldName");
        this.oldName = oldName;
    }

    /**
     * @return the 新名
     */
    public String getNewName() {
        return newName;
    }

    /**
     * @param newName 新名
     *            the newName to set
     */
    public void setNewName(String newName) {
        Audit(this.newName, newName, "newName");
        this.newName = newName;
    }

    /**
     * @return the 路径
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * @param  filePath 路径
     *            the filePath to set
     */
    public void setFilePath(String filePath) {
        Audit(this.filePath, filePath, "filePath");
        this.filePath = filePath;
    }

    /**
     * @return the 文件大小
     */
    public Integer getFileSize() {
        return fileSize;
    }

    /**
     * @param fileSize 文件大小
     *            the fileSize to set
     */
    public void setFileSize(Integer fileSize) {
        Audit(this.fileSize, fileSize, "fileSize");
        this.fileSize = fileSize;
    }

    /**
     * @return the 字典值（文件类型）DICVALUE
     */
    public Integer getFileType() {
        return fileType;
    }

    /**
     * @param fileType 字典值
     *            （文件类型）DICVALUE the fileType to set
     */
    public void setFileType(Integer fileType) {
        Audit(this.fileType, fileType, "fileType");
        this.fileType = fileType;
    }

    /**
     * @return the 字典分组(文件组)GROUPTYPE
     */
    public String getGroupType() {
        return groupType;
    }

    /**
     * @param groupType 字典分组
     *            (文件组)GROUPTYPE the groupType to set
     */
    public void setGroupType(String groupType) {
        Audit(this.groupType, groupType, "groupType");
        this.groupType = groupType;
    }

    /**
     * @return the 创建部门Id
     */
    public String getCreationOrgId() {
        return creationOrgId;
    }

    /**
     * @param  creationOrgId 创建部门Id
     *            the greationOrgId to set
     */
    public void setCreationOrgId(String creationOrgId) {
        Audit(this.creationOrgId, creationOrgId, "creationOrgId");
        this.creationOrgId = creationOrgId;
    }

    /**
     * @return the creationUserId
     */
    public String getCreationUserId() {
        return creationUserId;
    }

    /**
     * @param creationUserId
     *            the creationUserId to set
     */
    public void setCreationUserId(String creationUserId) {
        Audit(this.creationUserId, creationUserId, "creationUserId");
        this.creationUserId = creationUserId;
    }

    /**
     * @return the 上传时间
     */
    public Date getUploadTime() {
        return uploadTime;
    }

    /**
     * @param  uploadTime 上传时间
     *            the uploadTime to set
     */
    public void setUploadTime(Date uploadTime) {
        Audit(this.uploadTime, this.uploadTime, "uploadTime");
        this.uploadTime = uploadTime;
    }

    /**
     * 获取 创建机构名称
     *
     * @return creationOrgName 创建机构名称
     */
    public String getCreationOrgName() {
        return creationOrgName;
    }

    /**
     * 设置 创建机构名称
     *
     * @param creationOrgName
     *            创建机构名称
     */
    public void setCreationOrgName(String creationOrgName) {
        this.creationOrgName = creationOrgName;
    }

    /**
     * 获取 orgOrder
     *
     * @return orgOrder orgOrder
     */
    public Integer getOrgOrder() {
        return orgOrder;
    }

    /**
     * 设置 orgOrder
     *
     * @param orgOrder
     *            orgOrder
     */
    public void setOrgOrder(Integer orgOrder) {
        this.orgOrder = orgOrder;
    }

    /**
     * 获取 文件类型名称
     *
     * @return fileTypeName 文件类型名称
     */
    public String getFileTypeName() {
        return fileTypeName;
    }

    /**
     * 设置 文件类型名称
     *
     * @param fileTypeName
     *            文件类型名称
     */
    public void setFileTypeName(String fileTypeName) {
        this.fileTypeName = fileTypeName;
    }

}
