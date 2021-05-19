package com.cest.service;

import com.cest.config.DeleteTaskCmd;
import com.cest.config.SetFLowNodeAndGoCmd;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class TaskOpService {

    @Autowired
    private org.activiti.engine.TaskService taskService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ManagementService managementService;

    //当前任务Id 要跳转的节点targetNodeId
    public void withdraw(String taskId, String targetNodeId) {
        log.info("========开始withdraw========");
        // 当前任务
        Task currentTask = taskService.createTaskQuery().taskId(taskId).singleResult();
        // 获取流程定义
        org.activiti.bpmn.model.Process process = repositoryService.getBpmnModel(currentTask.getProcessDefinitionId())
                .getMainProcess();
        // 获取目标节点定义
        FlowNode targetNode = (FlowNode) process.getFlowElement(targetNodeId);
        // 删除当前运行任务
        String executionEntityId = managementService.executeCommand(new DeleteTaskCmd(currentTask.getId()));
        // 流程执行到来源节点
        managementService.executeCommand(new SetFLowNodeAndGoCmd(targetNode, executionEntityId));
        log.info("========结束withdraw========");
    }
}
