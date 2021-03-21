package com.cest.controller;

import com.cest.enums.ResponseCode;
import com.cest.util.AjaxResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
public class TaskController {

    //获取我的代办任务
    @GetMapping(value = "/getTasks")
    public AjaxResponse getTasks() {
        return AjaxResponse.AjaxData(ResponseCode.SUCCESS.getCode(),
                    ResponseCode.SUCCESS.getDesc(), null);
    }

     //完成待办任务
    @GetMapping(value = "/completeTask")
    public AjaxResponse completeTask(@RequestParam("taskID") String taskID) {
        return AjaxResponse.AjaxData(ResponseCode.SUCCESS.getCode(),
                    ResponseCode.SUCCESS.getDesc(), null);
    }

    //启动流程
    @GetMapping(value = "/startProcess4")
    public AjaxResponse startProcess3(@RequestParam("processDefinitionKey") String processDefinitionKey,
                                      @RequestParam("instanceName") String instanceName,
                                      @RequestParam("instanceVariable") String instanceVariable) {
        return AjaxResponse.AjaxData(ResponseCode.SUCCESS.getCode(),
                    ResponseCode.SUCCESS.getDesc(), null);
    }
}
