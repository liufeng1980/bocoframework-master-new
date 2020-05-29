package com.boco.sys.service.workflow.dao;

import com.boco.framework.model.workflow.JkptTsglOrgrelation;
import com.boco.framework.model.workflow.response.ComplaintByTelResponse;
import com.boco.framework.model.workflow.response.JkptTsglOrgRelationExt;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ComplaintDao {
    List<ComplaintByTelResponse> getComplaintByPlateNumOrTel(@Param("searchInput") String searchInput);

    List<JkptTsglOrgRelationExt> getReceiveOrgidList(@Param("senderOrgId") String senderOrgId, @Param("complaintParentType") String complaintParentType );
}
