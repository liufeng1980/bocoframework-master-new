package com.boco.sys.service.workflow.service;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Liu Feng
 * @Date: 2020-6-2 15:59
 */
@Service
public class WorkFlowService {
    Logger LOGGER = LoggerFactory.getLogger(WorkFlowService.class);

    @Autowired
    TaskService taskService;

    public Task queryTaskByInstanceId(String instanceId) {
        Task task = taskService.createTaskQuery().processInstanceId(instanceId).singleResult();
        if (task == null) {
            LOGGER.info("未查询到实例ID:   {}的任务", instanceId);
            return task;
        }
        return task;
    }
}
