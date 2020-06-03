package com.boco.sys.service.workflow.service;

import com.boco.framework.model.response.ResponseResult;
import com.boco.framework.model.workflow.Callflow;
import com.boco.framework.model.workflow.CallflowHis;
import com.boco.sys.service.workflow.dao.CallflowDao;
import com.boco.sys.service.workflow.dao.CallflowHisDao;
import com.boco.sys.service.workflow.model.ProcessAuditResult;
import org.activiti.api.task.model.builders.CandidateGroupsPayloadBuilder;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CallFlowService {

    Logger LOGGER = LoggerFactory.getLogger(CallFlowService.class);

    private static final String CALL_FLOW_PROCESS_NAME = "call_flow_process";
    private String businessKey = "1002";

    @Autowired
    ProcessEngine processEngine;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    CallflowDao callflowDao;

    @Autowired
    CallflowHisDao callflowHisDao;

    /**
     * 呼叫中心提交订单
     *
     * @return
     */
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public ResponseResult submitBill() {
        try {
            Callflow callflow = new Callflow();
            callflow.setContent("aaaaa");
            callflow.setCreateUser("zhangsan");
            callflow.setOrgId("1");
            callflowDao.insertSelective(callflow);

            CallflowHis callflowHis = new CallflowHis();
            callflowHis.setCallFlowKeyId(callflow.getKeyId());
            callflowHis.setProcessContent("bbbbbbbbbbbbbbbbbb");
            callflowHis.setProcessOrgId("1");
            callflowHis.setProcessUserId("111");
            callflowHis.setResultcode(1);
            callflowHisDao.insertSelective(callflowHis);

            String inputUser = "zhangsan";
            Map<String, Object> map = new HashMap<>();
            map.put("inputUser", inputUser);
            businessKey = callflow.getKeyId() + "";
            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(CALL_FLOW_PROCESS_NAME, businessKey, map);
            LOGGER.info("流程定义ID:{}", processInstance.getProcessDefinitionId());
            LOGGER.info("流程实例ID:{}", processInstance.getId());
            String instanceId = processInstance.getProcessInstanceId();
            Task task = queryTaskByInstanceId(instanceId);

            if (task != null) {
                taskService.complete(task.getId());
                printTask(task);
                Task task2 = queryTaskByInstanceId(instanceId);
                printTask(task2);
                List<IdentityLink> identityLinksForTask = taskService.getIdentityLinksForTask(task2.getId());
                if (identityLinksForTask != null && identityLinksForTask.size() > 0) {
                    if ("candidate".equals(identityLinksForTask.get(0).getType())) {
                        String groupid = identityLinksForTask.get(0).getGroupId();
                        if (!StringUtils.isEmpty(groupid)) {
                            CallflowHis callflowHis1 = new CallflowHis();
                            callflowHis1.setKeyId(callflowHis.getKeyId());
                            callflowHis1.setToOrgId(groupid);
                            callflowHisDao.updateByPrimaryKeySelective(callflowHis1);
                        }
                    }
                }
                for (IdentityLink identityLink : identityLinksForTask) {
                    printIdentityLink(identityLink);
                }
            }
        } catch (Exception e) {
            throw e;
        }

        return ResponseResult.SUCCESS();
    }

    private void printIdentityLink(IdentityLink identityLink) {
        LOGGER.info("getUserId={}", identityLink.getUserId());
        LOGGER.info("getGroupId={}", identityLink.getGroupId());
        LOGGER.info("getTaskId={}", identityLink.getTaskId());
        LOGGER.info("getType={}", identityLink.getType());
    }

    private void printTask(Task task) {
        LOGGER.info("任务id:{}", task.getId());
        LOGGER.info("任务名称:{}", task.getName());
        LOGGER.info("任务接收人:{}", task.getAssignee());
        LOGGER.info("task={}", task);
    }


    private Task queryTaskByInstanceId(String instanceId) {
        Task task = taskService.createTaskQuery().processInstanceId(instanceId).singleResult();
        if (task == null) {
            LOGGER.info("未查询到实例ID:   {}的任务", instanceId);
            return task;
        }
        return task;
    }


    /**
     * 2.运营科审核
     *
     * @return
     */
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public ResponseResult YunYingKeAudit(String businessKey) {
        List<Task> taskList = taskService.createTaskQuery()
                .taskCandidateGroup("yuyingke")
                .processInstanceBusinessKey(businessKey).list();

        if (taskList == null) {
            LOGGER.info("未查询到运营科任务");
            return ResponseResult.FAIL();
        }
        for (Task task : taskList) {
            LOGGER.info("运营科任务id:{}", task.getId());
            LOGGER.info("运营科任务名称:{}", task.getName());

            //拾取任务
            try {
                taskService.claim(task.getId(), "lisi");
                Map<String, Object> map = new HashMap<>();
                map.put("gaolu", "diyigaolu");
                ProcessAuditResult result = new ProcessAuditResult();
                result.setResultCode(1);
                map.put("result", result);
                taskService.complete(task.getId(), map);
                LOGGER.info("运营科处理完成");

                Integer keyid = Integer.parseInt(businessKey);

                CallflowHis callflowHis = new CallflowHis();
                callflowHis.setCallFlowKeyId(keyid);
                callflowHis.setProcessContent("bbbbbbbbbbbbbbbbbb");
                callflowHis.setProcessOrgId("yuyingke");
                callflowHis.setProcessUserId("111");
                callflowHis.setResultcode(1);
                callflowHis.setToOrgId("diyigaolu");
                callflowHisDao.insertSelective(callflowHis);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseResult.FAIL();
            }
        }
        return ResponseResult.SUCCESS();
    }


    /**
     * 高路公司审核
     */
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public ResponseResult gaoLuAudit(String businessKey) {
        List<Task> tasks = taskService.createTaskQuery()
                .processDefinitionKey(CALL_FLOW_PROCESS_NAME)
                .taskCandidateGroup("diyigaolu")
                .processInstanceBusinessKey(businessKey)
                .list();
        if (tasks == null) {
            LOGGER.info("未查询到高路公司审核任务");
            return ResponseResult.FAIL();
        }
        for (Task task : tasks) {
            try {
                taskService.claim(task.getId(), "wangwu");
                Map<String, Object> map = new HashMap<>();
                map.put("fengongsi", "diyifengongsi");
                //taskService.setVariable(task.getId(),"result",1);
                taskService.complete(task.getId(), map);
                LOGGER.info("高路公司审核");
                Integer keyid = Integer.parseInt(businessKey);
                CallflowHis callflowHis = new CallflowHis();
                callflowHis.setCallFlowKeyId(keyid);
                callflowHis.setProcessContent("bbbbbbbbbbbbbbbbbb");
                callflowHis.setProcessOrgId("diyigaolu");
                callflowHis.setProcessUserId("111");
                callflowHis.setResultcode(1);
                callflowHis.setToOrgId("diyifengongsi");
                callflowHisDao.insertSelective(callflowHis);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseResult.FAIL();
            }
        }
        return ResponseResult.SUCCESS();
    }

    /**
     * 4.分公司审核
     *
     * @return
     */
    public ResponseResult fenZhongXinAudit(String businessKey) {
        List<Task> tasks = taskService.createTaskQuery()
                .processDefinitionKey(CALL_FLOW_PROCESS_NAME)
                .taskCandidateGroup("diyifengongsi")
                .processInstanceBusinessKey(businessKey)
                .list();
        if (tasks == null) {
            LOGGER.info("未查询到分公司审核任务");
            return ResponseResult.FAIL();
        }
        for (Task task : tasks) {
            try {
                taskService.claim(task.getId(), "zhaoliu");
                Map<String, Object> map = new HashMap<>();
                ProcessAuditResult result = new ProcessAuditResult();
                result.setContineProcessCode(0);
                map.put("result", result);
                //map.put("gaolu", "diyigaolu");
                //taskService.setVariable(task.getId(),"result",1);
                taskService.complete(task.getId(), map);
                LOGGER.info("分公司审核");
                Integer keyid = Integer.parseInt(businessKey);
                CallflowHis callflowHis = new CallflowHis();
                callflowHis.setCallFlowKeyId(keyid);
                callflowHis.setProcessContent("bbbbbbbbbbbbbbbbbb");
                callflowHis.setProcessOrgId("diyifengongsi");
                callflowHis.setProcessUserId("111");
                callflowHis.setResultcode(1);
                callflowHis.setToOrgId("diyigaolu");
                callflowHisDao.insertSelective(callflowHis);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseResult.FAIL();
            }
        }
        return ResponseResult.SUCCESS();
    }

    /**
     * 5.高路公司的反馈
     *
     * @return
     */
    public ResponseResult gaoluFeedbackAudit(String businessKey) {
        List<Task> tasks = taskService.createTaskQuery()
                .processDefinitionKey(CALL_FLOW_PROCESS_NAME)
                .taskCandidateGroup("diyigaolu")
                .processInstanceBusinessKey(businessKey)
                .list();
        if (tasks == null) {
            LOGGER.info("未查询到高路公司的反馈任务");
            return ResponseResult.FAIL();
        }
        for (Task task : tasks) {
            try {
                taskService.claim(task.getId(), "wangwu");
                Map<String, Object> map = new HashMap<>();
                ProcessAuditResult result = new ProcessAuditResult();
                result.setResultCode(1);
                map.put("result", result);
                //taskService.setVariable(task.getId(),"result",1);
                taskService.complete(task.getId(), map);
                LOGGER.info("高路公司的反馈");
                String toOrgId = "";
                String instanceId = task.getProcessInstanceId();
                Task task2 = queryTaskByInstanceId(instanceId);
                List<IdentityLink> identityLinksForTask = taskService.getIdentityLinksForTask(task2.getId());
                if (identityLinksForTask != null && identityLinksForTask.size() > 0) {
                    if ("candidate".equals(identityLinksForTask.get(0).getType())) {
                        String groupid = identityLinksForTask.get(0).getGroupId();
                        if (!StringUtils.isEmpty(groupid)) {
                            toOrgId = groupid;
                        }
                    }
                }
                Integer keyid = Integer.parseInt(businessKey);
                CallflowHis callflowHis = new CallflowHis();
                callflowHis.setCallFlowKeyId(keyid);
                callflowHis.setProcessContent("bbbbbbbbbbbbbbbbbb");
                callflowHis.setProcessOrgId("diyigaolu");
                callflowHis.setProcessUserId("111");
                callflowHis.setResultcode(1);
                callflowHis.setToOrgId(toOrgId);
                callflowHisDao.insertSelective(callflowHis);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseResult.FAIL();
            }
        }
        return ResponseResult.SUCCESS();
    }
}
