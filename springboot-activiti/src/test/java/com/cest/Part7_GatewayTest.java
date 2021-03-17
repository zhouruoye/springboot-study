package com.cest;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * 网关 并行、包容、排他
 */
@Slf4j
public class Part7_GatewayTest extends BaseTest {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    //启动流程实例带参数，执行执行人 --> ${zhixingren}
    @Test
    public void initProcessInstanceWithArgs() {
        Map<String, Object> variables = new HashMap<>();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("exclusive01","wangguan002",variables);
        System.out.println("流程实例ID："+processInstance.getProcessDefinitionId());
    }

    //完成任务带参数，指定流程变量测试
    @Test
    public void completeTaskWithArgs() {
        Map<String, Object> variables = new HashMap<>();
        variables.put("day","2");
        taskService.complete("8eb669d3-86f9-11eb-88ce-00ff2cb67c15",variables);
        log.info("完成任务");
    }
}
