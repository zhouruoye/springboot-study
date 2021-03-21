package com.cest.controller;

import com.cest.enums.ResponseCode;
import com.cest.pojo.UserInfo;
import com.cest.util.AjaxResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/processInstance")
public class ProcessInstanceController {

    //获取流程实例
    @GetMapping(value = "/getInstances")
    public AjaxResponse getInstances(@AuthenticationPrincipal UserInfo userInfo) {
        return AjaxResponse.AjaxData(ResponseCode.SUCCESS.getCode(),
                    ResponseCode.SUCCESS.getDesc(),null);
    }


    //启动流程实例
    @GetMapping(value = "/startProcess")
    public AjaxResponse startProcess(@RequestParam("processDefinitionKey") String processDefinitionKey,
                                     @RequestParam("instanceName") String instanceName) {
        return AjaxResponse.AjaxData(ResponseCode.SUCCESS.getCode(),
                    ResponseCode.SUCCESS.getDesc(),null);
    }

    //删除流程实例
    @GetMapping(value = "/delInstance")
    public AjaxResponse delInstance(@RequestParam("instanceID") String instanceID) {
        return AjaxResponse.AjaxData(ResponseCode.SUCCESS.getCode(),
                    ResponseCode.SUCCESS.getDesc(),null);
    }

    //挂起流程实例
    @GetMapping(value = "/suspendInstance")
    public AjaxResponse suspendInstance(@RequestParam("instanceID") String instanceID) {
        return AjaxResponse.AjaxData(ResponseCode.SUCCESS.getCode(),
                    ResponseCode.SUCCESS.getDesc(),null);
    }

    //激活流程实例
    @GetMapping(value = "/resumeInstance")
    public AjaxResponse resumeInstance(@RequestParam("instanceID") String instanceID) {
        return AjaxResponse.AjaxData(ResponseCode.SUCCESS.getCode(),
                    ResponseCode.SUCCESS.getDesc(),null);
    }

    //获取参数
    @GetMapping(value = "/variables")
    public AjaxResponse variables(@RequestParam("instanceID") String instanceID) {
        return AjaxResponse.AjaxData(ResponseCode.SUCCESS.getCode(),
                    ResponseCode.SUCCESS.getDesc(),null);
    }
}
