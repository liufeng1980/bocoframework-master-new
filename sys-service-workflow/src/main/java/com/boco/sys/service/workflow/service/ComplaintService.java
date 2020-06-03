package com.boco.sys.service.workflow.service;

import com.boco.framework.model.common.JkptCommParamdic;
import com.boco.framework.model.response.ResponseResult;
import com.boco.framework.model.workflow.JkptTsglAuditdetail;
import com.boco.framework.model.workflow.JkptTsglAuditinfo;
import com.boco.framework.model.workflow.request.Complaint;
import com.boco.framework.model.workflow.request.ComplaintPage;
import com.boco.framework.model.workflow.request.ComplaintResult;
import com.boco.framework.model.workflow.response.AddFormResponse;
import com.boco.framework.model.workflow.response.ComplaintByTelResponse;
import com.boco.framework.model.workflow.response.JkptTsglOrgRelationExt;
import com.boco.sys.service.workflow.dao.ComplaintDao;
import com.boco.sys.service.workflow.dao.JkptCommParamdicDao;
import com.boco.sys.service.workflow.dao.JkptTsglAuditdetailDao;
import com.boco.sys.service.workflow.dao.JkptTsglAuditinfoDao;
import com.boco.utils.SysOauth2Util;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Liu Feng
 * @Date: 2020-5-29 10:33
 */
@Service
public class ComplaintService {
    @Autowired
    JkptCommParamdicDao paramdicDao;

    @Autowired
    ComplaintDao complaintDao;

    @Autowired
    JkptTsglAuditdetailDao tsglAuditdetailDao;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    JkptTsglAuditinfoDao auditinfoDao;

    @Autowired
    TaskService taskService;

    @Autowired
    WorkFlowService workFlowService;

    private static final String PROCESS_NAME = "complaint_process";

    /**
     * 初始化添加界面
     * @return
     */
    public ResponseResult<AddFormResponse> initAddComplaintPage(){
        AddFormResponse addFormResponse = new AddFormResponse();
        List<JkptCommParamdic> parentParams
                = paramdicDao.getParamDicList("ComplaintType", "0");
        addFormResponse.setComplaintTypesParents(parentParams);

        List<JkptCommParamdic> subParams
                = paramdicDao.getParamDicList("ComplaintType","");
        addFormResponse.setComplaintTypesSubs(subParams);

        List<JkptCommParamdic> complaintLevels
                = paramdicDao.getParamDicListByGroupType("ComplaintLevel");
        addFormResponse.setComplaintLevels(complaintLevels);

        List<JkptCommParamdic> whetherOrNotParams
                = paramdicDao.getParamDicListByGroupType("WhetherOrNot");
        addFormResponse.setWeatherOrNot(whetherOrNotParams);

        List<JkptCommParamdic> fileTypes
                = paramdicDao.getParamDicListByGroupType("ComplaintFileType");
        addFormResponse.setComplaintFileTypes(fileTypes);

        List<JkptCommParamdic> idTypes
                = paramdicDao.getParamDicListByGroupType("IdType");
        addFormResponse.setIdTypes(idTypes);

        List<JkptCommParamdic> payTypes
                = paramdicDao.getParamDicListByGroupType("ComplaintPayType");
        addFormResponse.setComplaintEtcPayTypes(payTypes);

        List<JkptCommParamdic> etcAgencyParams
                = paramdicDao.getParamDicListByGroupType("EtcAgency");
        addFormResponse.setComplaintEtcPayTypes(etcAgencyParams);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = formatter.format(new Date());
        addFormResponse.setEventTime(now);
        addFormResponse.setEtcAcceptTime(now);
        addFormResponse.setEtcEventTime(now);

        return   ResponseResult.SUCCESS(addFormResponse);
    }

    /**
     * 按电话或车牌查询列表
     * @param searchInput
     * @return
     */
    public ResponseResult<List<ComplaintByTelResponse>> getComplaintByPlateNumOrTel(String searchInput) {
        List<ComplaintByTelResponse> complaintByPlateNumOrTel = complaintDao.getComplaintByPlateNumOrTel(searchInput);
        return ResponseResult.SUCCESS(complaintByPlateNumOrTel);
    }


    /**
     * 获取可以选择的机构列表
     * @param senderOrgId  发送机构id
     * @param complaintParentType 投诉父类型
     * @return
     */
    public ResponseResult<List<JkptTsglOrgRelationExt>> getReceiveOrgidList(String senderOrgId,String complaintParentType) {
        List<JkptTsglOrgRelationExt> receiveOrgidList = null;
        try{
            receiveOrgidList = complaintDao.getReceiveOrgidList(senderOrgId, complaintParentType);
        }
        catch (Exception e){
            return  ResponseResult.FAIL(e.getMessage());
        }

        return ResponseResult.SUCCESS(receiveOrgidList);
    }

    /**
     * 添加投诉
     * @param userJwt
     * @param complaint
     * @return
     */
    @Transactional
    public ResponseResult addComplaint(SysOauth2Util.UserJwt userJwt, Complaint complaint) throws Exception {
        try{
            complaint.setCreationOrgId(userJwt.getOrgid());
            complaint.setCreationUserId(userJwt.getLoginid());
            complaint.setPlateNumber(complaint.getPlateNumber().toUpperCase());
            ComplaintResult complaintResult  = new ComplaintResult();
            BeanUtils.copyProperties(complaint,complaintResult);
            complaintDao.addComplain(complaintResult);

            Integer complaintId = complaintResult.getPkid();
            String bussinessKey = complaintId +"";

            JkptTsglAuditinfo auditinfo = new JkptTsglAuditinfo();
            auditinfo.setFkid(complaintId);
            auditinfo.setSendorgid(complaint.getCreationOrgId());
            auditinfo.setIsreject("0");
            auditinfo.setReceiveorgid(complaint.getCurrentOrgId());
            auditinfo.setPasspath(1);
            auditinfo.setSenduserid(complaint.getCreationUserId());
            auditinfo.setCurrentstatusdesc("投诉待" + complaint.getCurrentOrgId() +"响应");
            auditinfoDao.insertSelective(auditinfo);

            Map<String, Object> map = new HashMap<>();
            map.put("inputUser", complaint.getCreationUserId());
            map.put("createOrgId",userJwt.getOrgid());
            map.put("complaintType",complaint.getComplaintType());
            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(PROCESS_NAME,bussinessKey,map);
            Task task = workFlowService.queryTaskByInstanceId(processInstance.getId());
            if(task == null){
                return ResponseResult.FAIL("工作流中未查询到提交任务");
            }
            Integer fkid = auditinfo.getPkid();
            JkptTsglAuditdetail auditdetail = new JkptTsglAuditdetail();
            auditdetail.setFkid(fkid);
            auditdetail.setComplaintid(complaintId);
            auditdetail.setStatusinfo(task.getName());
            auditdetail.setSenduserid(complaint.getCreationUserId());
            auditdetail.setPasspath(1);
            auditdetail.setReceiveorgid(complaint.getCreationOrgId());
            auditdetail.setStatus(0);
            auditdetail.setSendorgid(complaint.getCreationOrgId());
            auditdetail.setIsreject("0");
            auditdetail.setIsshow("0");
            auditdetail.setReceiveuserid(complaint.getCreationUserId());
            tsglAuditdetailDao.insertSelective(auditdetail);
            taskService.complete(task.getId());
            Task task2 = workFlowService.queryTaskByInstanceId(processInstance.getId());
            if(task2 == null){
                return ResponseResult.FAIL("工作流中未查询到提交任务2");
            }
            JkptTsglAuditdetail auditdetail2 = new JkptTsglAuditdetail();
            auditdetail2.setFkid(fkid);
            auditdetail2.setComplaintid(complaintId);
            auditdetail2.setStatusinfo(task2.getName());
            auditdetail2.setSenduserid(complaint.getCreationUserId());
            auditdetail2.setPasspath(1);
            auditdetail2.setReceiveorgid(complaint.getCurrentOrgId());
            auditdetail2.setStatus(1);
            auditdetail2.setSendorgid(complaint.getCreationOrgId());
            auditdetail2.setIsreject("0");
            auditdetail2.setIsshow("0");
            tsglAuditdetailDao.insertSelective(auditdetail2);

            if (complaintResult.getPReturnVal().equals("1")) {
                return ResponseResult.SUCCESS();
            } else {
                return ResponseResult.FAIL(complaintResult.getPMsg());
            }
        }
        catch (Exception e){
            throw new Exception();
            //return ResponseResult.FAIL(e.getMessage());
        }
    }


    /**
     * 查询分页信息
     * @param userJwt
     * @param complaintPage
     */
    public PageInfo<Complaint> getPage(SysOauth2Util.UserJwt userJwt, ComplaintPage complaintPage) {
        PageHelper.startPage(complaintPage.getCurrentPage(),complaintPage.getPageSize());
        complaintPage.setLoginUserOrgId(userJwt.getOrgid());
        List<Complaint> complaints = complaintDao.getList(complaintPage);
        PageInfo<Complaint> pageInfo = new PageInfo<>(complaints);
        return pageInfo;
    }
}
