package com.boco.sys.service.workflow.dao;

import com.boco.framework.model.workflow.JkptTsglAuditdetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface JkptTsglAuditdetailDao {
    int insertSelective(JkptTsglAuditdetail record);

    int updateByPrimaryKeySelective(JkptTsglAuditdetail record);

    Integer queryMaxPkId(@Param("complaintId") Integer complaintId);
}
