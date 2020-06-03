package com.boco.sys.service.workflow.dao;

import com.boco.framework.model.workflow.JkptTsglAuditdetail;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface JkptTsglAuditdetailDao {
    int insertSelective(JkptTsglAuditdetail record);
}
