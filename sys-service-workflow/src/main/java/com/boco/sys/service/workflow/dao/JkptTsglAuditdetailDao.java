package com.boco.sys.service.workflow.dao;

import com.boco.framework.model.workflow.JkptTsglAuditdetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface JkptTsglAuditdetailDao {
    int insertSelective(JkptTsglAuditdetail record);

    int updateByPrimaryKeySelective(JkptTsglAuditdetail record);

    Integer queryMaxPkId(@Param("complaintId") Integer complaintId);

    JkptTsglAuditdetail selectByPrimaryKey(@Param("pkid") Integer pkid);

    int updateAuditDetail(@Param("suggest") String suggest,
                          @Param("statusInfo") String statusInfo,
                          @Param("receiverUserId") String receiverUserId,
                          @Param("newStatus") Integer newStatus,
                          @Param("detailId") Integer detailId);
}
