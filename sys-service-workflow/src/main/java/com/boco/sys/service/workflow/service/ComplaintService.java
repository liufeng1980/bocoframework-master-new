package com.boco.sys.service.workflow.service;

import com.boco.framework.model.common.JkptCommParamdic;
import com.boco.framework.model.response.ResponseResult;
import com.boco.framework.model.workflow.ComplaintProcessInfo;
import com.boco.framework.model.workflow.JkptTsglAuditdetail;
import com.boco.framework.model.workflow.JkptTsglAuditinfo;
import com.boco.framework.model.workflow.request.*;
import com.boco.framework.model.workflow.response.*;
import com.boco.sys.service.workflow.dao.*;
import com.boco.utils.SysOauth2Util;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.activiti.bpmn.model.FormProperty;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipInputStream;

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
    JkptTsglAuditinfoExtDao auditinfoExtDao;

    @Autowired
    TaskService taskService;

    @Autowired
    WorkFlowService workFlowService;

    @Autowired
    JkptFlowRejectDao flowRejectDao;

    @Autowired
    JkptTsglReceiveOrgDao receiveOrgDao;

    private static final String PROCESS_NAME = "complaint_process";

    private static Logger LOGGER = LoggerFactory.getLogger(ComplaintService.class);

    public ResponseResult deployTest(){
       //1.创建ProcessEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        //2.得到RepositoryService实例
        RepositoryService repositoryService = processEngine.getRepositoryService();

        //3.转化出ZipInputStream流对象 D:\Code\Boco\bocoframework-master\sys-service-workflow\src\main\resources\processes\complaint_process.bpmn20.xml
       // InputStream is = ComplaintService.class.getClassLoader()
        //        .getResourceAsStream("processes/test.bpmn20.xml");

        //将 inputstream流转化为ZipInputStream流
        //ZipInputStream zipInputStream = new ZipInputStream(is);

        //3.进行部署
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("processes//test.bpmn20.xml")
                .name("布署投诉")
                .deploy();

        return ResponseResult.SUCCESS();
    }

    public ResponseResult deployComplaint(){
        //1.创建ProcessEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        //2.得到RepositoryService实例
        RepositoryService repositoryService = processEngine.getRepositoryService();

        //3.转化出ZipInputStream流对象 D:\Code\Boco\bocoframework-master\sys-service-workflow\src\main\resources\processes\complaint_process.bpmn20.xml
        //InputStream is = ComplaintService.class.getClassLoader().getResourceAsStream("processes/complaint_process.bpmn20.xml");

        //将 inputstream流转化为ZipInputStream流
        //ZipInputStream zipInputStream = new ZipInputStream(is);

        //3.进行部署
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("processes//complaint_process.bpmn20.xml")
                .name("布署投诉")
                .deploy();

        return ResponseResult.SUCCESS();
    }



    /**
     * 初始化添加界面
     *
     * @return
     */
    public ResponseResult<AddFormResponse> initAddComplaintPage() {
        AddFormResponse addFormResponse = new AddFormResponse();
        List<JkptCommParamdic> parentParams
                = paramdicDao.getParamDicList("ComplaintType", "0");
        addFormResponse.setComplaintTypesParents(parentParams);

        List<JkptCommParamdic> subParams
                = paramdicDao.getParamDicList("ComplaintType", "");
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

        return ResponseResult.SUCCESS(addFormResponse);
    }

    /**
     * 按电话或车牌查询列表
     *
     * @param searchInput
     * @return
     */
    public ResponseResult<List<ComplaintByTelResponse>> getComplaintByPlateNumOrTel(String searchInput) {
        List<ComplaintByTelResponse> complaintByPlateNumOrTel = complaintDao.getComplaintByPlateNumOrTel(searchInput);
        return ResponseResult.SUCCESS(complaintByPlateNumOrTel);
    }


    /**
     * 获取可以选择的机构列表
     *
     * @param senderOrgId         发送机构id
     * @param complaintParentType 投诉父类型
     * @return
     */
    public ResponseResult<List<JkptTsglOrgRelationExt>> getReceiveOrgidList(String senderOrgId, String complaintParentType) {
        List<JkptTsglOrgRelationExt> receiveOrgidList = null;
        try {
            receiveOrgidList = complaintDao.getReceiveOrgidList(senderOrgId, complaintParentType);
        } catch (Exception e) {
            return ResponseResult.FAIL(e.getMessage());
        }

        return ResponseResult.SUCCESS(receiveOrgidList);
    }

    /**
     * 添加投诉
     *
     * @param userJwt
     * @param complaint
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult addComplaint(SysOauth2Util.UserJwt userJwt, Complaint complaint) throws Exception {
        try {
            complaint.setCreationOrgId(userJwt.getOrgid());
            complaint.setCreationUserId(userJwt.getLoginid());
            complaint.setPlateNumber(complaint.getPlateNumber().toUpperCase());
            ComplaintResult complaintResult = new ComplaintResult();
            BeanUtils.copyProperties(complaint, complaintResult);
            complaintDao.addComplain(complaintResult);

            Integer complaintId = complaintResult.getPkid();
            String bussinessKey = complaintId + "";

            JkptTsglAuditinfo auditinfo = new JkptTsglAuditinfo();
            auditinfo.setFkid(complaintId);
            auditinfo.setSendorgid(complaint.getCreationOrgId());
            auditinfo.setIsreject("0");
            auditinfo.setReceiveorgid(complaint.getCurrentOrgId());
            auditinfo.setPasspath(1);
            auditinfo.setSenduserid(complaint.getCreationUserId());
            auditinfo.setCurrentstatusdesc("投诉待" + complaint.getCurrentOrgId() + "响应");
            auditinfoDao.insertSelective(auditinfo);

            Map<String, Object> map = new HashMap<>();
            map.put("inputUser", complaint.getCreationUserId());
            map.put("createOrgId", userJwt.getOrgid());
            map.put("complaintType", complaint.getComplaintType());
            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(PROCESS_NAME, bussinessKey, map);
            String instanceId = processInstance.getProcessInstanceId();
            LOGGER.info("查询流程实例Id={}",instanceId);
            Task task = workFlowService.queryTaskByInstanceId(instanceId);
            if (task == null) {
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
            try {
                taskService.complete(task.getId());
            }
            catch (Exception e){
                throw e;
            }

            LOGGER.info("查询流程实例2  Id={}",instanceId);
            Task task2 = workFlowService.queryTaskByInstanceId(instanceId);
            if (task2 == null) {
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
        } catch (Exception e) {
            throw new Exception();
            //return ResponseResult.FAIL(e.getMessage());
        }
    }


    /**
     * 查询分页信息
     *
     * @param userJwt
     * @param complaintPage
     */
    public PageInfo<Complaint> getPage(SysOauth2Util.UserJwt userJwt, ComplaintPage complaintPage) {
        PageHelper.startPage(complaintPage.getCurrentPage(), complaintPage.getPageSize());
        complaintPage.setLoginUserOrgId(userJwt.getOrgid());
        List<Complaint> complaints = complaintDao.getList(complaintPage);
        PageInfo<Complaint> pageInfo = new PageInfo<>(complaints);
        return pageInfo;
    }

    /**
     * 初始化详情
     * @param userJwt
     * @param detailFormRequest
     */
    public ResponseResult<DetailFormResponse> initDetailPage(SysOauth2Util.UserJwt userJwt,
                                                             DetailFormRequest detailFormRequest) {
        if (detailFormRequest.getAuditInfoId()== 0) {
            return ResponseResult.FAIL("审核信息id不能为0");
        }
        JkptTsglAuditInfoResponse auditInfo = auditinfoExtDao.queryComplaintAuditInfo(detailFormRequest.getAuditInfoId());
        // 投诉主表详细信息
        Complaint complaint = complaintDao.getComplaintByComplaintId(auditInfo.getFkid());
        // 获取待审核机构列表
        List<JkptTsglOrgRelationExt> orgRelationList
                = complaintDao.getReceiveOrgidList(userJwt.getOrgid(), complaint.getParentType());

        Integer isCreatOrgGroupType = 0;
        if (orgRelationList.size() > 0) {
            String creatOrgGroupType = complaintDao.getGroupType(
                    complaint.getCreationOrgId(), complaint.getParentType());
            //创建机构和用户机构相同
            if (creatOrgGroupType.equals(orgRelationList.get(0).getGrouptype())) {
                isCreatOrgGroupType = 1;
                if (auditInfo.getReceiveorgid().equals(userJwt.getOrgid())
                        && auditInfo.getIsarchive().equals("0")
                        && auditInfo.getIsrecyclebin().equals("0")) {
                    // 判断记录是否被驳回
                    if (auditInfo.getIsreject().equals("1")) {
                        auditInfo.setJsDealStatus("2");
                        auditInfo.setIsShowReAdd("1");
                    } else {
                        // 显示处理意见
                        auditInfo.setIsShowAudit("1");
                        auditInfo.setAuditTitle("用户对处理结果是否满意");
                        auditInfo.setJsDealStatus("1");
                    }
                } else {
                    // 查看流转信息
                    auditInfo.setJsDealStatus("99");
                }
            } else {
                isCreatOrgGroupType = 0;
                switch (orgRelationList.get(0).getGrouptype()) {
                    // 运营科
                    case "OperationDept":
                        // 监控部
                    case "MonitorDept":
                        // 高路总中心
                    case "GaoLuHearCenter":
                        // 判断记录是查看还是其他
                        if (auditInfo.getReceiveorgid().equals(userJwt.getOrgid())) {
                            // 判断记录是否被驳回
                            if (auditInfo.getIsreject().equals("1")) {
                                auditInfo.setIsShowAudit("1");
                                auditInfo.setAuditTitle("处理结果审核");
                                auditInfo.setJsDealStatus("1");
                            } else {
                                // PassPath：1-投诉待处理，2-处理结果待审核
                                if (auditInfo.getPasspath().equals("1")) {
                                    auditInfo.setIsShowAudit("1");
                                    auditInfo.setAuditTitle("投诉审核");
                                    auditInfo.setJsDealStatus("1");
                                } else if (auditInfo.getPasspath().equals("2")
                                        || auditInfo.getPasspath().equals("3")) {
                                    auditInfo.setJsDealStatus("2");
                                    auditInfo.setIsShowAudit("1");
                                    auditInfo.setAuditTitle("投诉审核");
                                }
                            }
                        }else{
                            // 查看流转信息
                            auditInfo.setJsDealStatus("99");
                        }
                        break;
                    // 分中心
                    case "SubCenter":
                        // 判断记录是查看还是其他
                        if (auditInfo.getReceiveorgid().equals(userJwt.getOrgid())) {
                            auditInfo.setIsShowAudit("1");
                            auditInfo.setAuditTitle("投诉结果反馈");
                            auditInfo.setJsDealStatus("1");
                        } else {
                            // 查看流转信息
                            auditInfo.setJsDealStatus("99");
                        }
                        break;
                    // etc发展部
                    case "ETCDept":
                        // 判断记录是查看还是其他
                        if (auditInfo.getReceiveorgid().equals(userJwt.getOrgid())) {
                            auditInfo.setIsShowAudit("1");
                            auditInfo.setAuditTitle("投诉结果反馈");
                            auditInfo.setJsDealStatus("1");
                        } else {
                            // 查看流转信息
                            auditInfo.setJsDealStatus("99");
                        }
                        break;
                }
            }
        }
        List<ComplaintProcessInfo> processInfoList = complaintDao
                .queryComplaintProcessHisById(detailFormRequest.getComplaintId());
        List<Integer> auditIdList = new ArrayList<Integer>();
        for (ComplaintProcessInfo info : processInfoList) {
            if (auditIdList.contains(info.getFkid()) == false) {
                auditIdList.add(info.getFkid());
            }
        }

        DetailFormResponse detailFormResponse = new DetailFormResponse();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        detailFormResponse.setVisitTime(formatter.format(new Date()));
        detailFormResponse.setIsCreatOrgGroupType(isCreatOrgGroupType);
        List<JkptCommParamdic> paramdics
                = paramdicDao.getParamDicListByGroupType("ComplaintFileType");
        detailFormResponse.setDicFileTypes(paramdics);

        detailFormResponse.setComplaint(complaint);
        detailFormResponse.setAuditInfo(auditInfo);
        detailFormResponse.setOrgRelationExtList(orgRelationList);
        detailFormResponse.setProcessInfos(processInfoList);
        detailFormResponse.setAuditIdList(auditIdList);
        detailFormResponse.setRefrushTarget(detailFormRequest.getRefrushTarget());

        return ResponseResult.SUCCESS(detailFormResponse);
    }

    /**
     *
     * @param processDetailRequest
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult audit(SysOauth2Util.UserJwt userJwt,ProcessDetailRequest processDetailRequest){
        String bussinessKey = processDetailRequest.getComplaintId()+"";

        Task task = workFlowService.queryTaskByBusinessKey(bussinessKey);
        if (task == null) {
            LOGGER.info("工作流中未查询businessKey={}的任务",bussinessKey);
            return ResponseResult.FAIL("工作流中未查询相关任务");
        }
        String formKey = task.getFormKey();
        String sourceOrg = queryValueFromFormKey(formKey, "sourceOrg");
        Map<String,Object> map = new HashMap<>();
        //map.put("receiveOrgId",processDetailRequest.getReceiveOrgId());

        if(processDetailRequest.getSubmitType() == 1){
            map.put("sourceOrg",sourceOrg);
            //map.put("pass",1);
            taskService.setVariable(task.getId(),"receiveOrgId",processDetailRequest.getReceiveOrgId());
            taskService.setVariable(task.getId(),"sourceOrg",sourceOrg);
            taskService.setVariable(task.getId(),"pass",1);
        }
        else if(processDetailRequest.getSubmitType() == 2){
            //  sourceOrg转成功后变量内更改
            //map.put("pass",2);
            //taskService.setVariable(task.getId(),"receiveOrgId",processDetailRequest.getReceiveOrgId());
            taskService.setVariable(task.getId(),"pass",2);
        }

        //taskService.setVariables(task.getId(),map);
        Object pass = taskService.getVariable(task.getId(), "pass");
        LOGGER.info("task id={},pass={}",task.getId(),pass);
        taskService.complete(task.getId());

        Task task2 = workFlowService.queryTaskByBusinessKey(bussinessKey);
        if (task2 == null) {
            LOGGER.info("工作流中未查询businessKey={}的任务",bussinessKey);
            return ResponseResult.FAIL("工作流中未查询相关任务");
        }
        Object pass2 = taskService.getVariable(task2.getId(), "pass");
        LOGGER.info("task id={},pass={}",task2.getId(),pass2);
//        if("2".equals(processDetailRequest.getSubmitType())){  //驳回
//            taskService.setVariable(task2.getId(),"sourceOrg",sourceOrg);
//        }
        //Complaint complaint = complaintDao.getComplaintByComplaintId(processDetailRequest.getComplaintId());
        //驳回
        if(processDetailRequest.getSubmitType() == 2){
            Integer detailMaxPkid = tsglAuditdetailDao.queryMaxPkId(processDetailRequest.getComplaintId());
            JkptTsglAuditdetail jkptTsglAuditdetail = new JkptTsglAuditdetail();
            jkptTsglAuditdetail.setPkid(detailMaxPkid);
            String statusInfoDesc = getStringFromTaskVariables(task2, "statusInfo1");
            jkptTsglAuditdetail.setStatusinfo(statusInfoDesc);
            jkptTsglAuditdetail.setSuggestion(processDetailRequest.getSuggestion());
            jkptTsglAuditdetail.setReceiveuserid(userJwt.getUserId());
            jkptTsglAuditdetail.setReceiveorgid(userJwt.getOrgid());
            //TODO 未设置状态
            //jkptTsglAuditdetail.setNewstatus();
            jkptTsglAuditdetail.setIsreject("1");
            tsglAuditdetailDao.updateByPrimaryKeySelective(jkptTsglAuditdetail);

            JkptTsglAuditdetail insertDetail = new JkptTsglAuditdetail();
            insertDetail.setFkid(processDetailRequest.getComplaintInfoId());
            String statusInfoDesc2 = getStringFromTaskVariables(task2, "statusInfo2");
            insertDetail.setStatusinfo(statusInfoDesc2);
            insertDetail.setSenduserid(userJwt.getUserId());
            Integer intPass = getIntegerFromTaskVariable(task2,"passPath");
            insertDetail.setPasspath(intPass);
            Integer intStatus = getIntegerFromTaskVariable(task2, "status");
            insertDetail.setStatus(intStatus);
            insertDetail.setSendorgid(userJwt.getOrgid());
            String receiverOrg = queryTaskReceiverOrg(task2.getId());
            insertDetail.setReceiveorgid(receiverOrg);
            JkptTsglAuditinfo auditinfo = auditinfoDao.selectByPrimaryKey(processDetailRequest.getComplaintInfoId());
            if(auditinfo != null){
                insertDetail.setFrequency(auditinfo.getFrequency());
            }
            insertDetail.setComplaintid(processDetailRequest.getComplaintId());
            insertDetail.setIsreject("1");
            tsglAuditdetailDao.insertSelective(insertDetail);

            JkptTsglAuditinfo updateAuditInfo = new JkptTsglAuditinfo();
            updateAuditInfo.setPkid(processDetailRequest.getComplaintInfoId());
            updateAuditInfo.setCurrentstatus(intStatus);
            updateAuditInfo.setCurrentstatusdesc(statusInfoDesc2);
            updateAuditInfo.setCurrentsuggestion(processDetailRequest.getSuggestion());
            updateAuditInfo.setSenduserid(userJwt.getUserId());
            updateAuditInfo.setSendorgid(userJwt.getOrgid());
            updateAuditInfo.setReceiveorgid(receiverOrg);
            updateAuditInfo.setPasspath(intPass);
            updateAuditInfo.setIsreject("1");
            auditinfoDao.updateByPrimaryKeySelective(updateAuditInfo);
            complaintDao.updateCurrentOrgId(processDetailRequest.getComplaintId(),receiverOrg);
            int cnt = flowRejectDao.queryCount(processDetailRequest.getComplaintId(),receiverOrg);
            if(cnt == 0){
                flowRejectDao.insert(processDetailRequest.getComplaintId(),receiverOrg);
            }
            int recCount = receiveOrgDao.queryCount(processDetailRequest.getComplaintId(),receiverOrg);
            if(recCount == 0){
                receiveOrgDao.insert(processDetailRequest.getComplaintId(),receiverOrg);
            }
        }
        else if(processDetailRequest.getSubmitType() == 1){
            Integer detailMaxPkid = tsglAuditdetailDao.queryMaxPkId(processDetailRequest.getComplaintId());
            JkptTsglAuditdetail jkptTsglAuditdetail = new JkptTsglAuditdetail();
            jkptTsglAuditdetail.setPkid(detailMaxPkid);
            String statusInfoDesc = getStringFromTaskVariables(task2, "statusInfo1");
            jkptTsglAuditdetail.setStatusinfo(statusInfoDesc);
            jkptTsglAuditdetail.setSuggestion(processDetailRequest.getSuggestion());
            jkptTsglAuditdetail.setReceiveuserid(userJwt.getUserId());
            jkptTsglAuditdetail.setReceiveorgid(userJwt.getOrgid());
            //TODO 未设置状态
            //jkptTsglAuditdetail.setNewstatus();
            tsglAuditdetailDao.updateByPrimaryKeySelective(jkptTsglAuditdetail);


            JkptTsglAuditdetail insertDetail = new JkptTsglAuditdetail();
            insertDetail.setFkid(processDetailRequest.getComplaintInfoId());
            String statusInfoDesc2 = getStringFromTaskVariables(task2, "statusInfo2");
            insertDetail.setStatusinfo(statusInfoDesc2);
            insertDetail.setSenduserid(userJwt.getUserId());
            Integer intPass = getIntegerFromTaskVariable(task2,"passPath");
            insertDetail.setPasspath(intPass);
            Integer intStatus = getIntegerFromTaskVariable(task2, "status");
            insertDetail.setStatus(intStatus);
            insertDetail.setSendorgid(userJwt.getOrgid());
            String receiverOrg = queryTaskReceiverOrg(task2.getId());
            insertDetail.setReceiveorgid(receiverOrg);
            JkptTsglAuditinfo auditinfo = auditinfoDao.selectByPrimaryKey(processDetailRequest.getComplaintInfoId());
            if(auditinfo != null){
                insertDetail.setFrequency(auditinfo.getFrequency());
            }
            insertDetail.setComplaintid(processDetailRequest.getComplaintId());
            tsglAuditdetailDao.insertSelective(insertDetail);

            JkptTsglAuditinfo updateAuditInfo = new JkptTsglAuditinfo();
            updateAuditInfo.setPkid(processDetailRequest.getComplaintInfoId());
            updateAuditInfo.setCurrentstatus(intStatus);
            updateAuditInfo.setCurrentstatusdesc(statusInfoDesc2);
            updateAuditInfo.setCurrentsuggestion(processDetailRequest.getSuggestion());
            updateAuditInfo.setSenduserid(userJwt.getUserId());
            updateAuditInfo.setSendorgid(userJwt.getOrgid());
            updateAuditInfo.setReceiveorgid(receiverOrg);
            updateAuditInfo.setPasspath(intPass);
            updateAuditInfo.setIsreject("0");
            auditinfoDao.updateByPrimaryKeySelective(updateAuditInfo);

            //更新当前待处理机构
            complaintDao.updateCurrentOrgId(processDetailRequest.getComplaintId(),receiverOrg);
            int cnt = flowRejectDao.queryCount(processDetailRequest.getComplaintId(),receiverOrg);
            if(cnt == 0){
                flowRejectDao.insert(processDetailRequest.getComplaintId(),receiverOrg);
            }
            int recCount = receiveOrgDao.queryCount(processDetailRequest.getComplaintId(),receiverOrg);
            if(recCount == 0){
                receiveOrgDao.insert(processDetailRequest.getComplaintId(),receiverOrg);
            }
        }

        return null;
    }

    private String getStringFromTaskVariables(Task task2, String statusInfo22) {
        Object statusInfo2 = queryTaskVariable(task2.getId(), statusInfo22);
        return statusInfo2 == null ? "" : statusInfo2.toString();
    }

    private Integer getIntegerFromTaskVariable(Task task2, String key) {
        Object status = queryTaskVariable(task2.getId(),key);
        Integer intStatus = null;
        if(status != null){
            try{
                intStatus = Integer.parseInt(status.toString());
            }
            catch (Exception e){
                LOGGER.info("任务{}中没有变量{}",task2.getId(),key);
            }
        }
        return intStatus;
    }

    public String queryTaskReceiverOrg(String taskId){
        List<IdentityLink> identityLinks = taskService.getIdentityLinksForTask(taskId);
        if(identityLinks == null || identityLinks.size() == 0 ){
            return "";
        }
        String toOrgId = "";
        if ("candidate".equals(identityLinks.get(0).getType())) {
            String groupid = identityLinks.get(0).getGroupId();
            if (!StringUtils.isEmpty(groupid)) {
                toOrgId = groupid;
            }
        }
        return toOrgId;
    }

    public Object queryTaskVariable(String taskId,String variableName){
        Object value = taskService.getVariable(taskId, variableName);
        return value;
    }

    public void test(String bussinessKey){
        ProcessInstance instance = runtimeService.startProcessInstanceByKey("test");
        Task task = workFlowService.queryTaskByInstanceId(instance.getProcessInstanceId());
        String a = (String) taskService.getVariable(task.getId(),"myVar");
        LOGGER.info("var={}",a);
    }

    public void test2(String bussinessKey) {
        Map<String, Object> map = new HashMap<>();
        map.put("inputUser", "12122");
        map.put("createOrgId", "0110000");
        map.put("complaintType", "101");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(PROCESS_NAME, bussinessKey, map);
        String instanceId = processInstance.getProcessInstanceId();
        LOGGER.info("查询流程实例Id={}",instanceId);
        Task task = workFlowService.queryTaskByInstanceId(instanceId);
        if (task == null) {
            //return ResponseResult.FAIL("工作流中未查询到提交任务");
            LOGGER.info("工作流中未查询到提交任务");
            return;
        }
        try {
            taskService.complete(task.getId());
        }
        catch (Exception e){
            throw e;
        }

        LOGGER.info("查询流程实例2  Id={}",instanceId);
        Task task2 = workFlowService.queryTaskByInstanceId(instanceId);
        if (task2 == null) {
            LOGGER.info("工作流中未查询到提交任务2");
        }

    }

    private String queryValueFromFormKey(String formKey,String key){
        if(StringUtils.isEmpty(formKey)){
            return "";
        }
        String[] splits = formKey.split("&");
        if(splits == null || splits.length == 0){
            return "";
        }
        String result ="";
        for (int i = 0; i < splits.length; i++) {
            String split = splits[i];
            Integer subKeyIndex =split.indexOf("=");
            if(subKeyIndex == -1){
                continue;
            }
            String subKey = split.substring(0, subKeyIndex);
            if(StringUtils.isEmpty(subKey)){
                continue;
            }
            subKey = subKey.trim();
            if(key.equals(subKey)){
                result = split.substring(subKeyIndex+1);
                break;
            }
        }
        return result;
    }
}
