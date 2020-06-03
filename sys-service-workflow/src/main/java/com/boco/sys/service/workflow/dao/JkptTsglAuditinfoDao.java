package com.boco.sys.service.workflow.dao;

import com.boco.framework.model.workflow.JkptTsglAuditinfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface JkptTsglAuditinfoDao {
    int insertSelective(JkptTsglAuditinfo record);
}
