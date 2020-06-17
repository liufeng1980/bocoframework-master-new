package com.boco.sys.service.workflow.dao;

import com.boco.framework.model.workflow.JkptTsglAuditinfo;
import com.boco.framework.model.workflow.response.JkptTsglAuditInfoResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface JkptTsglAuditinfoExtDao {
    JkptTsglAuditInfoResponse queryComplaintAuditInfo(@Param("auditInfoId") Integer auditInfoId);
}
