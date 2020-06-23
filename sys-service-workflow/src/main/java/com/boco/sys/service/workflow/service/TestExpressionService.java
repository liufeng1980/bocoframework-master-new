package com.boco.sys.service.workflow.service;

import com.boco.sys.service.workflow.dao.JkptTsglAuditinfoDao;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * @Author: Liu Feng
 * @Date: 2020-6-18 16:03
 */
@Service("testExpressionService")
public class TestExpressionService implements Serializable {
    Logger logger = LoggerFactory.getLogger(TestExpressionService.class);

    @Autowired
    JkptTsglAuditinfoDao dao;

    @Autowired
    RuntimeService runtimeService;
    @Autowired
    TaskService taskService;
    public void test(Execution exe){
        //System.out.println("使用Java Bean的print方法：" + exe.getId());
        logger.info("使用Java Bean的print方法：{}",exe.getId());
        logger.info("get business key ={}",exe.getProcessInstanceId());
        logger.info("taskservice={}",taskService);
        logger.info("dao={}",dao);
        String instanceId = exe.getProcessInstanceId();
        Task task =
                taskService.createTaskQuery().processInstanceId(instanceId)
                        .processDefinitionKey("test").singleResult();
        if(task == null){

            return;
        }
    }
}
