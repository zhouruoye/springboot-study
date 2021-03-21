package com.cest.controller;

import com.cest.enums.ResponseCode;
import com.cest.pojo.UserInfo;
import com.cest.security.SecurityUtil;
import com.cest.util.AjaxResponse;
import lombok.extern.slf4j.Slf4j;
import org.activiti.api.model.shared.model.VariableInstance;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/processInstance")
public class ProcessInstanceController {

    @Autowired
    private ProcessRuntime processRuntime;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private RepositoryService repositoryService;

    //获取流程实例
    @GetMapping(value = "/getInstances")
    public AjaxResponse getInstances(@AuthenticationPrincipal UserInfo userInfo) {
        try {
            securityUtil.logInAs("bajie");
            Page<ProcessInstance> processInstancePage = processRuntime.processInstances(Pageable.of(0, 100));
            List<ProcessInstance> content = processInstancePage.getContent();
            List<HashMap<String, Object>> listMap = new ArrayList<HashMap<String, Object>>();
            for (ProcessInstance pi : content) {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("id", pi.getId());
                hashMap.put("name", pi.getName());
                hashMap.put("status", pi.getStatus());
                hashMap.put("processDefinitionId", pi.getProcessDefinitionId());
                hashMap.put("processDefinitionKey", pi.getProcessDefinitionKey());
                hashMap.put("startDate", pi.getStartDate());
                hashMap.put("processDefinitionVersion", pi.getProcessDefinitionVersion());
                //因为processRuntime.processDefinition("流程部署ID")查询的结果没有部署流程与部署ID，所以用repositoryService查询
                ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()
                        .processDefinitionId(pi.getProcessDefinitionId())
                        .singleResult();
                hashMap.put("resourceName", pd.getResourceName());
                hashMap.put("deploymentId", pd.getDeploymentId());
                listMap.add(hashMap);
            }

            return AjaxResponse.AjaxData(ResponseCode.SUCCESS.getCode(),
                    ResponseCode.SUCCESS.getDesc(), listMap);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResponse.AjaxData(ResponseCode.ERROR.getCode(),
                    "流程部署失败", e.toString());
        }
    }

    //启动流程实例
    @GetMapping(value = "/startProcess")
    public AjaxResponse startProcess(@RequestParam("processDefinitionKey") String processDefinitionKey,
                                     @RequestParam("instanceName") String instanceName) {
        try {
            securityUtil.logInAs("bajie");
            ProcessInstance processInstance = processRuntime.start(ProcessPayloadBuilder
                    .start()
                    .withProcessDefinitionKey(processDefinitionKey)
                    .withName(instanceName)
                    .withBusinessKey("自定义Key")
                    .build());
            return AjaxResponse.AjaxData(ResponseCode.SUCCESS.getCode(),
                    ResponseCode.SUCCESS.getDesc(), processInstance.getName() + "；" + processInstance.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResponse.AjaxData(ResponseCode.ERROR.getCode(),
                    "流程启动失败", e.toString());
        }
    }

    //删除流程实例
    @GetMapping(value = "/delInstance")
    public AjaxResponse delInstance(@RequestParam("instanceID") String instanceID) {
        try {
            securityUtil.logInAs("bajie");
            ProcessInstance processInstance = processRuntime.delete(ProcessPayloadBuilder
                    .delete()
                    .withProcessInstanceId(instanceID) //实例id
                    .build());
            return AjaxResponse.AjaxData(ResponseCode.SUCCESS.getCode(),
                    ResponseCode.SUCCESS.getDesc(), processInstance.getName());
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResponse.AjaxData(ResponseCode.ERROR.getCode(),
                    "流程删除失败", e.toString());
        }
    }

    //挂起流程实例
    @GetMapping(value = "/suspendInstance")
    public AjaxResponse suspendInstance(@RequestParam("instanceID") String instanceID) {
        try {
            securityUtil.logInAs("bajie");
            ProcessInstance processInstance = processRuntime.suspend(ProcessPayloadBuilder
                    .suspend()
                    .withProcessInstanceId(instanceID) //实例id
                    .build());
            return AjaxResponse.AjaxData(ResponseCode.SUCCESS.getCode(),
                    ResponseCode.SUCCESS.getDesc(), processInstance.getName());
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResponse.AjaxData(ResponseCode.ERROR.getCode(),
                    "流程挂起失败", e.toString());
        }
    }

    //激活流程实例
    @GetMapping(value = "/resumeInstance")
    public AjaxResponse resumeInstance(@RequestParam("instanceID") String instanceID) {
        try {
            securityUtil.logInAs("bajie");
            ProcessInstance processInstance = processRuntime.resume(ProcessPayloadBuilder
                    .resume()
                    .withProcessInstanceId(instanceID) //实例id
                    .build());
            return AjaxResponse.AjaxData(ResponseCode.SUCCESS.getCode(),
                    ResponseCode.SUCCESS.getDesc(), processInstance.getName());
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResponse.AjaxData(ResponseCode.ERROR.getCode(),
                    "流程激活失败", e.toString());
        }
    }

    //获取参数
    @GetMapping(value = "/variables")
    public AjaxResponse variables(@RequestParam("instanceID") String instanceID) {
        try {
            securityUtil.logInAs("bajie");
            List<VariableInstance> list = processRuntime.variables(ProcessPayloadBuilder
                .variables()
                .withProcessInstanceId(instanceID)
                .build());
            return AjaxResponse.AjaxData(ResponseCode.SUCCESS.getCode(),
                    ResponseCode.SUCCESS.getDesc(), list);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResponse.AjaxData(ResponseCode.ERROR.getCode(),
                    "获取参数失败", e.toString());
        }
    }
}
