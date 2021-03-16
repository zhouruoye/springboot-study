package com.cest;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 流程实例 和 流程定义是多对1的关系
 */
@Slf4j
public class Part3_ProcessInstanceTest extends BaseTest{

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
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("mytask2","bKey004");
        System.out.println("流程实例ID："+processInstance.getProcessDefinitionId());
    }

    /**
     * 流程实例
     */
    @Test
    public void getProcessInstance() {
        List<ProcessInstance> list = runtimeService.createProcessInstanceQuery().list();

        for (ProcessInstance processInstance : list) {
            System.out.println("流程实例");
            System.out.println("getId:" + processInstance.getId());  //实例id
            System.out.println("getProcessInstanceId:" + processInstance.getProcessInstanceId()); //实例id
            System.out.println("getProcessDefinitionId:" + processInstance.getProcessDefinitionId()); //流程定义id
            System.out.println("getDeploymentId:" + processInstance.getDeploymentId());
            System.out.println("isEnded:" + processInstance.isEnded()); //是否结束
            System.out.println("isSuspended:" + processInstance.isSuspended()); //是否被挂起
        }
        
    }

    /**
     * 暂停与激活流程实例
     */
    @Test
    public void activitiProcessInstance() {
//        log.info("挂起流程实例");
//        runtimeService.suspendProcessInstanceById("957864fa-85fc-11eb-9519-00ff2cb67c15");

        log.info("激活流程实例");
        runtimeService.activateProcessInstanceById("957864fa-85fc-11eb-9519-00ff2cb67c15");
    }

    /**
     * 删除流程实例
     */
    @Test
    public void delProcessInstance() {
        runtimeService.deleteProcessInstance("33889811-861f-11eb-b4c2-00ff2cb67c15","删除测试");
    }
}
