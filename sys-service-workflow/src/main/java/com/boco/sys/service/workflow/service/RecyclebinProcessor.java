package com.boco.sys.service.workflow.service;

import com.boco.framework.model.workflow.JkptTsglAuditinfo;
import com.boco.framework.model.workflow.request.ReturnVisitRequest;
import com.boco.sys.service.workflow.dao.ComplaintDao;
import com.boco.sys.service.workflow.dao.JkptTsglAuditdetailDao;
import com.boco.sys.service.workflow.dao.JkptTsglAuditinfoDao;
import com.boco.sys.service.workflow.dao.JkptTsglAuditinfoExtDao;
import com.boco.utils.SysOauth2Util;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.runtime.Execution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * @Author: Liu Feng
 * @Date: 2020-6-19 14:19
 */
@Service("recyclebinProcessor")
public class RecyclebinProcessor implements Serializable {
    @Autowired
    ComplaintDao complaintDao;

    @Autowired
    JkptTsglAuditinfoExtDao auditinfoExtDao;

    @Autowired
    JkptTsglAuditinfoDao auditinfoDao;
    @Autowired
    JkptTsglAuditdetailDao auditdetailDao;

    public void process(Execution exe) {
        ExecutionEntityImpl executionEntity = (ExecutionEntityImpl) exe;

        ReturnVisitRequest data
                = executionEntity.getVariable("data", ReturnVisitRequest.class);
        SysOauth2Util.UserJwt userJwt
                = executionEntity.getVariable("user", SysOauth2Util.UserJwt.class);
        complaintDao.updateRecyclebin(data.getComplaintId());

        String statusDesc ="无效投诉";
        JkptTsglAuditinfo auditinfo = auditinfoDao.selectByPrimaryKey(data.getComplaintInfoId());
        auditinfoExtDao.updateAuditinfoRecyclebinStatus(data.getComplaintInfoId(),
                userJwt.getUserId(),
                auditinfo.getReceiveorgid(),
                data.getSuggest(),
                statusDesc
        );
        Integer maxPkId = auditdetailDao.queryMaxPkId(data.getComplaintId());
        auditdetailDao.updateAuditDetail(data.getSuggest(),
                statusDesc,
                userJwt.getUserId(),
                99,maxPkId);

    }
}
