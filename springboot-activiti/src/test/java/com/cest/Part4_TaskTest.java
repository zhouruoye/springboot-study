package com.cest;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 流程任务
 */
@Slf4j
public class Part4_TaskTest extends BaseTest{

    @Autowired
    private RuntimeService runtimeService;

    /**
     * 初始化流程实例--开启流程  ACT_RU_EXECUTION_流程实例表  ACT_RU_IDENTITYLINK_执行人参与表
     */
    @Test
    public void initProcessInstance() {
        //1、获取页面表单填报的内容，请假时间，请假事由，String fromData
        //2、fromData 写入业务表，返回业务表主键ID==businessKey
        //3、把业务数据与Activiti7流程数据关联
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("Part1_Deployment","bKey002");
        System.out.println("流程实例ID："+processInstance.getProcessDefinitionId());
    }
}
