package com.cest;

import com.cest.security.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.activiti.api.model.shared.model.VariableInstance;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * activiti7 新特性
 */
@Slf4j
public class Part8_ProcessRuntime extends BaseTest {

    @Autowired
    private ProcessRuntime processRuntime;

    @Autowired
    private SecurityUtil securityUtil;

    //获取流程实例
    @Test
    public void getProcessInstance() {
        securityUtil.logInAs("bajie");
        Page<ProcessInstance> processInstancePage = processRuntime.processInstances(Pageable.of(0, 100));
        List<ProcessInstance> content = processInstancePage.getContent();
        for (ProcessInstance pr : content) {
            log.info("-----------------查询流程实例-------------------");
            log.info("getId:{}",pr.getId()); //实例id
            log.info("getName:{}",pr.getName()); //实例名称
            log.info("getProcessDefinitionId:{}",pr.getProcessDefinitionId()); //流程定义id
            log.info("getProcessDefinitionKey:{}",pr.getProcessDefinitionKey()); //流程定义key
            log.info("getBusinessKey:{}",pr.getBusinessKey()); //业务key
            log.info("getProcessDefinitionVersion:{}",pr.getProcessDefinitionVersion()); //流程定义版本
            log.info("getStartDate:{}",pr.getStartDate()); //实例开始时间
        }
    }

    //启动流程实例
    @Test
    public void startProcessInstance() {
        securityUtil.logInAs("bajie");
        ProcessInstance processInstance = processRuntime.start(ProcessPayloadBuilder
                .start()
                .withProcessDefinitionKey("exclusive01")
                .withName("第一个流程实例名称")
                //.withVariable("","")
                .withBusinessKey("自定义bKey")
                .build()
        );
    }

    //删除流程实例
    @Test
    public void delProcessInstance() {
        securityUtil.logInAs("bajie");
        ProcessInstance processInstance = processRuntime.delete(ProcessPayloadBuilder
                .delete()
                .withProcessInstanceId("9e147919-8705-11eb-b84c-00ff2cb67c15") //实例id
                .build());
        log.info("通过流程定义id删除实例");
    }


    //挂起流程实例
    @Test
    public void suspendProcessInstance() {
        securityUtil.logInAs("bajie");
        ProcessInstance processInstance = processRuntime.suspend(ProcessPayloadBuilder
                .suspend()
                .withProcessInstanceId("9e147919-8705-11eb-b84c-00ff2cb67c15")
                .build()
        );
    }

    //激活流程实例
    @Test
    public void resumeProcessInstance() {
        securityUtil.logInAs("bajie");
        ProcessInstance processInstance = processRuntime.resume(ProcessPayloadBuilder
                .resume()
                .withProcessInstanceId("9e147919-8705-11eb-b84c-00ff2cb67c15")
                .build()
        );
    }

    //流程实例参数
    @Test
    public void getVariables() {
        securityUtil.logInAs("bajie");
        List<VariableInstance> list = processRuntime.variables(ProcessPayloadBuilder
                .variables()
                .withProcessInstanceId("15fa0f89-86ee-11eb-a1cb-00ff2cb67c15")
                .build()
        );
        for(VariableInstance vi : list){
            System.out.println("-------------------");
            System.out.println("getName：" + vi.getName());
            System.out.println("getValue：" + vi.getValue());
            System.out.println("getTaskId：" + vi.getTaskId());
            System.out.println("getProcessInstanceId：" + vi.getProcessInstanceId());
        }
    }
}
