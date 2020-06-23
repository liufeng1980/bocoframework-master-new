package com.boco.sys.service.workflow.service;

import com.alibaba.fastjson.JSON;
import com.boco.framework.model.response.ResponseResult;
import com.boco.framework.model.workflow.JkptTsglAuditdetail;
import com.boco.framework.model.workflow.JkptTsglAuditinfo;
import com.boco.framework.model.workflow.request.ReturnVisitRequest;
import com.boco.sys.service.workflow.dao.ComplaintDao;
import com.boco.sys.service.workflow.dao.JkptTsglAuditdetailDao;
import com.boco.sys.service.workflow.dao.JkptTsglAuditinfoDao;
import com.boco.sys.service.workflow.dao.JkptTsglAuditinfoExtDao;
import com.boco.utils.SysOauth2Util;
import groovy.json.JsonParser;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: Liu Feng
 * @Date: 2020-6-18 17:12
 */
@Service("dissatisfiedServiceTaskProcessor")
public class DissatisfiedServiceTaskProcessor implements Serializable {
    Logger logger = LoggerFactory.getLogger(DissatisfiedServiceTaskProcessor.class);
    @Autowired
    JkptTsglAuditinfoExtDao auditinfoExtDao;
    @Autowired
    RuntimeService runtimeService;
    @Autowired
    TaskService taskService;

    @Autowired
    JkptTsglAuditinfoDao auditinfoDao;

    @Autowired
    ComplaintDao complaintDao;

    @Autowired
    JkptTsglAuditdetailDao tsglAuditdetailDao;

    @Autowired
    JkptTsglAuditdetailDao auditdetailDao;

    private static final String PROCESS_NAME = "complaint_process";
    public void process(Execution exe){
        String suggest ="用户未完成，用户不满意";
        ExecutionEntityImpl executionEntity = (ExecutionEntityImpl)exe;
        Object user = executionEntity.getVariable("user");
        SysOauth2Util.UserJwt userJwt = null;
        if(user != null){
            userJwt = JSON.parseObject(user.toString(),SysOauth2Util.UserJwt.class);
            logger.info("userJwt:{}",userJwt);
        }
        ReturnVisitRequest returnVisitRequest = null;
        Object data = executionEntity.getVariable("data");
        if(data != null){
            returnVisitRequest = JSON.parseObject(data.toString(),ReturnVisitRequest.class);
        }

        JkptTsglAuditinfo auditinfo
                = auditinfoDao.selectByPrimaryKey(returnVisitRequest.getComplaintInfoId());
        if(auditinfo == null){
            new Exception("未查询到投诉详情");
        }
        if(auditinfo.getFrequency() >= 2){
            suggest ="投诉已被处理3次，自动完结";
            complaintDao.updateComplaintReturnVisitInfo(returnVisitRequest.getComplaintId(),
                    returnVisitRequest.getComplaintInfoId(),
                    returnVisitRequest.getSatisfactionStatus(),
                    userJwt.getOrgid());
            complaintDao.updateComplaintInfoReturnVisitInfo(returnVisitRequest.getComplaintInfoId(),
                    returnVisitRequest.getSatisfactionStatus(),
                    userJwt.getOrgid(),
                    userJwt.getUserId(),
                    98,
                    suggest);
            Integer detailId = tsglAuditdetailDao.queryMaxPkId(returnVisitRequest.getComplaintId());
            JkptTsglAuditdetail detail = new JkptTsglAuditdetail();
            detail.setPkid(detailId);
            detail.setDealtime(new Date());
            detail.setSuggestion(suggest);
            detail.setNewstatus(98);
            detail.setReceiveuserid(userJwt.getUserId());
            detail.setStatusinfo(suggest);
            tsglAuditdetailDao.updateByPrimaryKeySelective(detail);
            executionEntity.setVariable("isEnd",1);
            return;
        }

        Integer maxDetailPkId = auditdetailDao.queryMaxPkId(returnVisitRequest.getComplaintId());
        String statusinfo1 ="";
        Object objStatus = executionEntity.getVariable("statusInfo1");
        if(objStatus != null){
            statusinfo1 = objStatus.toString();
        }
        auditdetailDao.updateAuditDetail(returnVisitRequest.getSuggest(),
                statusinfo1,
                userJwt.getUserId(),
                99,maxDetailPkId);

        auditinfoExtDao.updateUnStatisfiyStatusWithAuditInfo(returnVisitRequest.getComplaintInfoId(),
                returnVisitRequest.getSuggest(),suggest,returnVisitRequest.getSatisfactionStatus(),
                userJwt.getUserId());
        int frequency = auditinfo.getFrequency() + 1;

        Integer maxPkId = auditdetailDao.queryMaxPkId(returnVisitRequest.getComplaintId());
        JkptTsglAuditdetail auditdetail = auditdetailDao.selectByPrimaryKey(maxPkId);
        JkptTsglAuditinfo tsglAuditinfo = new JkptTsglAuditinfo();
        tsglAuditinfo.setFkid(returnVisitRequest.getComplaintId());
        tsglAuditinfo.setFrequency(frequency);
        tsglAuditinfo.setSendorgid(userJwt.getOrgid());
        tsglAuditinfo.setReceiveorgid(auditdetail.getSendorgid());
        tsglAuditinfo.setSenduserid(userJwt.getUserId());
        auditinfoDao.insertSelective(tsglAuditinfo);

        JkptTsglAuditdetail insertAuditDetail = new JkptTsglAuditdetail();
        insertAuditDetail.setFkid(tsglAuditinfo.getPkid());
        insertAuditDetail.setComplaintid(returnVisitRequest.getComplaintId());
        insertAuditDetail.setFrequency(frequency);
        insertAuditDetail.setStatusinfo("投诉已受理");
        insertAuditDetail.setSenduserid(userJwt.getUserId());
        insertAuditDetail.setPasspath(1);
        insertAuditDetail.setReceiveorgid(auditinfo.getReceiveorgid());
        insertAuditDetail.setStatus(1);
        insertAuditDetail.setSendorgid(auditinfo.getReceiveorgid());
        insertAuditDetail.setIsreject("0");
        insertAuditDetail.setIsshow("0");
        insertAuditDetail.setReceiveuserid(userJwt.getUserId());
        auditdetailDao.insertSelective(insertAuditDetail);

        JkptTsglAuditdetail detail = new JkptTsglAuditdetail();
        detail.setFkid(tsglAuditinfo.getPkid());
        detail.setComplaintid(returnVisitRequest.getComplaintId());
        detail.setFrequency(frequency);
        String statusinfo ="";
        Object statusInfo1 = executionEntity.getVariable("statusInfo2");
        if(statusInfo1 != null){
            statusinfo = statusInfo1.toString();
        }
        detail.setStatusinfo(statusinfo);
        detail.setSenduserid(userJwt.getUserId());
        detail.setPasspath(1);
        detail.setReceiveorgid(auditdetail.getSendorgid());
        Integer  intStatus = 0;
        Object status = executionEntity.getVariable("status");
        if(status != null){
            intStatus = Integer.parseInt(status.toString());
        }
        detail.setStatus(intStatus);
        detail.setSendorgid(auditinfo.getReceiveorgid());
        detail.setIsreject("0");
        detail.setIsshow("0");
        auditdetailDao.insertSelective(detail);

        complaintDao.updateCurrentOrgId(returnVisitRequest.getComplaintId(),auditdetail.getSendorgid());

        executionEntity.setVariable("isEnd",0);
    }
}
