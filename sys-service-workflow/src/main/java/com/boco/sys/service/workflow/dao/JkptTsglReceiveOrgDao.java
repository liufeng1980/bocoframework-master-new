package com.boco.sys.service.workflow.dao;

/**
 * @Author: Liu Feng
 * @Date: 2020-6-12 15:14
 */
public interface JkptTsglReceiveOrgDao {
    int queryCount(Integer complaintId,String receiverOrgId);
    int insert(Integer complaintId,String receiverOrgId);
}
