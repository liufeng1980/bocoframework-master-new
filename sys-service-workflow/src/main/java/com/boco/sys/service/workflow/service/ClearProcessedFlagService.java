package com.boco.sys.service.workflow.service;

import com.boco.sys.service.workflow.dao.JkptTsglAuditinfoExtDao;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.runtime.Execution;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * @Author: Liu Feng
 * @Date: 2020-6-23 14:23
 */
@Service("clearProcessedFlagService")
public class ClearProcessedFlagService implements Serializable {
    @Autowired
    JkptTsglAuditinfoExtDao auditinfoExtDao;
    public void process(Execution exe){
        ExecutionEntityImpl executionEntity = (ExecutionEntityImpl) exe;

        String businessKey = executionEntity.getProcessInstanceBusinessKey();
        Integer complaintId = Integer.parseInt(businessKey);
        auditinfoExtDao.clearProcessedFlag(complaintId);
    }
}
