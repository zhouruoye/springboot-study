package com.cest.service;

import com.cest.config.AddMultiInstanceExecutionCmd;
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
}
