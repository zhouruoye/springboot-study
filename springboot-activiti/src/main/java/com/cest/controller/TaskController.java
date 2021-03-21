package com.cest.controller;

import com.cest.enums.ResponseCode;
import com.cest.security.SecurityUtil;
import com.cest.util.AjaxResponse;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.runtime.TaskRuntime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private TaskRuntime taskRuntime;

    @Autowired
    private ProcessRuntime processRuntime;

    //获取我的代办任务
    @GetMapping(value = "/getTasks")
    public AjaxResponse getTasks() {
        try {
            securityUtil.logInAs("bajie");
            Page<Task> tasks = taskRuntime.tasks(Pageable.of(0, 100));
            List<Task> list = tasks.getContent();

            List<HashMap<String, Object>> listMap = new ArrayList<HashMap<String, Object>>();

            for (Task tk : list) {
                ProcessInstance processInstance = processRuntime.processInstance(tk.getProcessInstanceId());
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("id", tk.getId());
                hashMap.put("name", tk.getName());
                hashMap.put("status", tk.getStatus());
                hashMap.put("createdDate", tk.getCreatedDate());
                if (tk.getAssignee() == null) {//执行人，null时前台显示未拾取
                    hashMap.put("assignee", "待拾取任务");
                } else {
                    hashMap.put("assignee", tk.getAssignee());//
                }
                hashMap.put("instanceName", processInstance.getName());
                listMap.add(hashMap);
            }
            return AjaxResponse.AjaxData(ResponseCode.SUCCESS.getCode(),
                    ResponseCode.SUCCESS.getDesc(), listMap);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResponse.AjaxData(ResponseCode.SUCCESS.getCode(),
                    ResponseCode.ERROR.getDesc(), e);
        }
    }

    //完成待办任务
    @GetMapping(value = "/completeTask")
    public AjaxResponse completeTask(@RequestParam("taskID") String taskID) {
        try {
            securityUtil.logInAs("bajie");
            Task task = taskRuntime.task(taskID);
            //如果执行人为空 则是候选人 则需要拾取任务
            if (task.getAssignee() == null) {
                taskRuntime.claim(TaskPayloadBuilder.claim()
                        .withTaskId(task.getId())
                        .build());
            }
            taskRuntime.complete(TaskPayloadBuilder
                    .complete()
                    .withTaskId(task.getId())
                    .build());
            return AjaxResponse.AjaxData(ResponseCode.SUCCESS.getCode(),
                    ResponseCode.SUCCESS.getDesc(), null);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResponse.AjaxData(ResponseCode.SUCCESS.getCode(),
                    ResponseCode.ERROR.getDesc(), e);
        }
    }
}
