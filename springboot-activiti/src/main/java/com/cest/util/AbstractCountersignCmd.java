package com.cest.util;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;

public class AbstractCountersignCmd {

    protected RuntimeService runtimeService;

    protected TaskService taskService;

    protected RepositoryService repositoryService;

    public AbstractCountersignCmd(){

        runtimeService = SpringContextUtils.getBean(RuntimeService.class);
        taskService = SpringContextUtils.getBean(TaskService.class);
        repositoryService = SpringContextUtils.getBean(RepositoryService.class);
    }
}
