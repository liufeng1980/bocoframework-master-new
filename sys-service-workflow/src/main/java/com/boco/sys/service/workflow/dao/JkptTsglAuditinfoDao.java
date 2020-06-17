package com.boco.sys.service.workflow.dao;

import com.boco.framework.model.workflow.JkptTsglAuditinfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface JkptTsglAuditinfoDao {
    int insertSelective(JkptTsglAuditinfo record);
    JkptTsglAuditinfo selectByPrimaryKey(@Param("pkid") Integer pkid);

    int updateByPrimaryKeySelective(JkptTsglAuditinfo record);
}
