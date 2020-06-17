package com.boco.sys.service.workflow.dao;

import org.apache.ibatis.annotations.Param;

/**
 * @Author: Liu Feng
 * @Date: 2020-6-12 15:01
 */
public interface JkptFlowRejectDao {
    int queryCount(@Param("complaintId") Integer complaintId,
                   @Param("receiverOrgId") String receiverOrgId);

    int insert(@Param("complaintId") Integer complaintId,
               @Param("receiverOrgId") String receiverOrgId);
}
