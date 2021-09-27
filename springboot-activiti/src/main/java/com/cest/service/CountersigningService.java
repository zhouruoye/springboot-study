package com.cest.service;

import com.cest.config.AddMultiInstanceCmd;
import com.cest.config.AddMultiInstanceExecutionCmd;
import com.cest.config.DeleteMultiInstanceCmd;
import com.cest.config.DeleteMultiInstanceExecutionCmd;
import org.activiti.engine.ManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class CountersigningService  {


    @Autowired
    private ManagementService managementService;

    /**
     * 会签加签
     * @param taskId
     * @param assigneeList
     */
    @Transactional(rollbackFor = Exception.class)
    public void addCountersigningTask(String taskId, List<String> assigneeList, String assignee) {

        managementService.executeCommand(new AddMultiInstanceExecutionCmd(taskId, assigneeList, assignee));

    }

    /**
     * 会签减签
     * @param taskId
     * @param assigneeList
     */
    @Transactional(rollbackFor = Exception.class)
    public void removeCountersigningTask(String taskId, List<String> assigneeList) {

        managementService.executeCommand(new DeleteMultiInstanceExecutionCmd(taskId, assigneeList));

    }


    /**
     * 多实例子流程加签
     * @param taskId
     * @param assignee
     */
    @Transactional(rollbackFor = Exception.class)
    public void addMultiInstanceCmdTask(String taskId, String assignee) {

        managementService.executeCommand(new AddMultiInstanceCmd(taskId, assignee));

    }

    /**
     * 多实例子流程减签
     * @param taskId
     * @param isNormalComplete
     */
    @Transactional(rollbackFor = Exception.class)
    public void removeMultiInstanceCmdTask(String taskId, boolean isNormalComplete) {
        managementService.executeCommand(new DeleteMultiInstanceCmd(taskId, isNormalComplete));
    }

    /**
     * 子流程跳入父流程
     * @param taskId
     * @param targetNodeId
     */
    @Transactional(rollbackFor = Exception.class)
    public void moveSubOutCommand(String taskId, String targetNodeId) {
    }
}
