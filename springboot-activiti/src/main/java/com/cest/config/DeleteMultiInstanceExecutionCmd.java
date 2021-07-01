package com.cest.config;

import com.cest.mapper.ActivitiMapper;
import com.cest.util.AbstractCountersignCmd;
import com.cest.util.CountersigningVariables;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityManager;
import org.activiti.engine.impl.persistence.entity.TaskEntityImpl;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class DeleteMultiInstanceExecutionCmd extends AbstractCountersignCmd implements Command<String>, CountersigningVariables {
    /**
     * 当前任务ID
     */
    private String taskId;

    /**
     * 审核人
     */
    private List<String> assigneeList;


    @Autowired
    private ActivitiMapper activitiMapper;

    public DeleteMultiInstanceExecutionCmd(String taskId, List<String> assigneeList) {

        super();

        if (ObjectUtils.isEmpty(assigneeList)) {
            throw new RuntimeException("assigneeList 不能为空!");
        }

        this.taskId = taskId;
        this.assigneeList = assigneeList;
    }

    @Override
    public String execute(CommandContext commandContext) {

        TaskEntityImpl task = (TaskEntityImpl) taskService.createTaskQuery().taskId(taskId).singleResult();

        BpmnModel bpmnModel = repositoryService.getBpmnModel(task.getProcessDefinitionId());
        Process process = bpmnModel.getProcesses().get(0);

        UserTask userTask = (UserTask) process.getFlowElement(task.getTaskDefinitionKey());

        if (userTask.getLoopCharacteristics() == null) {
            log.info("task:[" + task.getId() + "] 不是会签节任务");
        }




//        String executionIdByTaskId = activitiMapper.getExecutionIdByTaskId(taskId);
        String executionIdByTaskId = "56064d11584d47ebb1ea4714350dfe0d";

        /**
         *  获取任务完成数
         */
        int nrOfCompletedInstances = (int) runtimeService.getVariable(executionIdByTaskId, NUMBER_OF_COMPLETED_INSTANCES);

        /**
         *  转换判断标识
         */
        Set<String> assigneeSet = new HashSet<>(assigneeList);
        ExecutionEntityManager executionEntityManager = Context.getCommandContext().getExecutionEntityManager();

        Object behavior = userTask.getBehavior();
        /**
         *  进行并行任务 减签
         */
        log.info("task:[" + task.getId() + "] 并行会签 减签 任务");

        /**
         *  当前任务列表
         */
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(task.getProcessInstance().getProcessInstanceId()).list();

        List<Task> removeTaskList = new ArrayList<>(assigneeSet.size());
        List<Task> existTaskList = new ArrayList<>(taskList.size() - assigneeSet.size());

        taskList.forEach(obj -> {

            if (assigneeSet.contains(obj.getAssignee())) {
                removeTaskList.add(obj);

                ExecutionEntityImpl temp = (ExecutionEntityImpl) runtimeService.createExecutionQuery().executionId(obj.getExecutionId()).singleResult();
                executionEntityManager.deleteExecutionAndRelatedData(temp, "会签减签", true);

            } else {
                existTaskList.add(obj);
            }
        });

        /**
         *  修改已完成任务变量,增加被删减任务
         */
        runtimeService.setVariable(executionIdByTaskId, NUMBER_OF_COMPLETED_INSTANCES, nrOfCompletedInstances + removeTaskList.size());
        return "减签成功";
    }
}
