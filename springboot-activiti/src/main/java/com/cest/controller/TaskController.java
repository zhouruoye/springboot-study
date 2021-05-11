package com.cest.controller;

import com.cest.enums.ResponseCode;
import com.cest.mapper.ActivitiMapper;
import com.cest.pojo.UserInfo;
import com.cest.security.SecurityUtil;
import com.cest.util.AjaxResponse;
import com.cest.util.GlobalConfig;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.bpmn.model.FormProperty;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.RepositoryService;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private TaskRuntime taskRuntime;

    @Autowired
    private ProcessRuntime processRuntime;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ActivitiMapper activitiMapper;

    //获取我的代办任务
    @GetMapping(value = "/getTasks")
    public AjaxResponse getTasks() {
        if(GlobalConfig.Test) {
            securityUtil.logInAs("bajie");
        }else {
            securityUtil.logInAs(SecurityContextHolder.getContext().getAuthentication().getName());
        }

        try {
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
    public AjaxResponse completeTask(@RequestParam("taskID") String taskID,
                                     @RequestParam(value = "variable",required = false) String variable,
                                     @AuthenticationPrincipal UserInfo userInfo) {
        Map<String, Object> variables = new HashMap<>();
        if (StringUtils.isNotBlank(variable)) {
            if(variable.indexOf("_") == -1) {
                return AjaxResponse.AjaxData(ResponseCode.ERROR.getCode(),
                        "传入参数异常", variable);
            }
            String[] split = variable.split(";");
            for (String param : split) {
                String[] params = param.split("_");
                variables.put(params[0], params[1]);
            }
        }
        if(GlobalConfig.Test) {
            securityUtil.logInAs("bajie");
        }else {
            securityUtil.logInAs(SecurityContextHolder.getContext().getAuthentication().getName());
        }

        try {
            Task task = taskRuntime.task(taskID);
            //如果执行人为空 则是候选人 则需要拾取任务
            if (task.getAssignee() == null) {
                taskRuntime.claim(TaskPayloadBuilder.claim()
                        .withTaskId(task.getId())
                        .withAssignee(userInfo.getUsername())
                        .build());
            }
            Task complete = taskRuntime.complete(TaskPayloadBuilder
                    .complete()
                    .withTaskId(task.getId())
                    .withVariables(variables)
                    .build());
            return AjaxResponse.AjaxData(ResponseCode.SUCCESS.getCode(),
                    ResponseCode.SUCCESS.getDesc(), null);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResponse.AjaxData(ResponseCode.SUCCESS.getCode(),
                    ResponseCode.ERROR.getDesc(), e);
        }
    }

    //渲染表单
    @GetMapping(value = "/formDataShow")
    public AjaxResponse formDataShow(@RequestParam("taskID") String taskID) {
        try {
            if (GlobalConfig.Test) {
                securityUtil.logInAs("bajie");
            }
            Task task = taskRuntime.task(taskID);

//-----------------------构建表单控件历史数据字典------------------------------------------------
            //本实例所有保存的表单数据HashMap，为了快速读取控件以前环节存储的值
            HashMap<String, String> controlistMap = new HashMap<>();
            //本实例所有保存的表单数据
            List<HashMap<String, Object>> tempControlList = activitiMapper.selectFormData(task.getProcessInstanceId());

            for (HashMap ls : tempControlList) {
                //String Control_ID = ls.get("Control_ID_").toString();
                //String Control_VALUE = ls.get("Control_VALUE_").toString();
                controlistMap.put(ls.get("Control_ID_").toString(), ls.get("Control_VALUE_").toString());
            }
            //String controlistMapValue = controlistMap.get("控件ID");
            //controlistMap.containsKey()

            //

/*  ------------------------------------------------------------------------------
            FormProperty_0ueitp2-_!类型-_!名称-_!默认值-_!是否参数
            例子：
            FormProperty_0lovri0-_!string-_!姓名-_!请输入姓名-_!f
            FormProperty_1iu6onu-_!int-_!年龄-_!请输入年龄-_!s

            默认值：无、字符常量、FormProperty_开头定义过的控件ID
            是否参数：f为不是参数，s是字符，t是时间(不需要int，因为这里int等价于string)
            注：类型是可以获取到的，但是为了统一配置原则，都配置到
            */

            //注意!!!!!!!!:表单Key必须要任务编号一模一样，因为参数需要任务key，但是无法获取，只能获取表单key“task.getFormKey()”当做任务key
            UserTask userTask = (UserTask) repositoryService.getBpmnModel(task.getProcessDefinitionId())
                    .getFlowElement(task.getFormKey());

            if (userTask == null) {
                return AjaxResponse.AjaxData(ResponseCode.SUCCESS.getCode(),
                        ResponseCode.SUCCESS.getDesc(), "无表单");
            }
            List<FormProperty> formProperties = userTask.getFormProperties();
            List<HashMap<String, Object>> listMap = new ArrayList<HashMap<String, Object>>();
            for (FormProperty fp : formProperties) {
                String[] splitFP = fp.getId().split("-_!");

                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("id", splitFP[0]);
                hashMap.put("controlType", splitFP[1]);
                hashMap.put("controlLable", splitFP[2]);


                //默认值如果是表单控件ID
                if (splitFP[3].startsWith("FormProperty_")) {
                    //控件ID存在
                    if (controlistMap.containsKey(splitFP[3])) {
                        hashMap.put("controlDefValue", controlistMap.get(splitFP[3]));
                    } else {
                        //控件ID不存在
                        hashMap.put("controlDefValue", "读取失败，检查" + splitFP[0] + "配置");
                    }
                } else {
                    //默认值如果不是表单控件ID则写入默认值
                    hashMap.put("controlDefValue", splitFP[3]);
                }


                hashMap.put("controlIsParam", splitFP[4]);
                listMap.add(hashMap);
            }

            return AjaxResponse.AjaxData(ResponseCode.SUCCESS.getCode(),
                    ResponseCode.SUCCESS.getDesc(), listMap);
        } catch (Exception e) {
            return AjaxResponse.AjaxData(ResponseCode.ERROR.getCode(),
                    "失败", e.toString());
        }
    }

    //保存表单
    @PostMapping(value = "/formDataSave")
    public AjaxResponse formDataSave(@RequestParam("taskID") String taskID,
                                     @RequestParam("formData") String formData) {
        try {
            if (GlobalConfig.Test) {
                securityUtil.logInAs("bajie");
            }

            Task task = taskRuntime.task(taskID);

            //formData:控件id-_!控件值-_!是否参数!_!控件id-_!控件值-_!是否参数
            //FormProperty_0lovri0-_!不是参数-_!f!_!FormProperty_1iu6onu-_!数字参数-_!s


            HashMap<String, Object> variables = new HashMap<String, Object>();
            Boolean hasVariables = false;//没有任何参数


            List<HashMap<String, Object>> listMap = new ArrayList<>();

            //前端传来的字符串，拆分成每个控件
            String[] formDataList = formData.split("!_!");//
            for (String controlItem : formDataList) {
                String[] formDataItem = controlItem.split("-_!");

                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("PROC_DEF_ID_", task.getProcessDefinitionId());
                hashMap.put("PROC_INST_ID_", task.getProcessInstanceId());
                hashMap.put("FORM_KEY_", task.getFormKey());
                hashMap.put("Control_ID_", formDataItem[0]);
                hashMap.put("Control_VALUE_", formDataItem[1]);
                listMap.add(hashMap);

                //构建参数集合
                switch (formDataItem[2]) {
                    case "f":
                        System.out.println("控件值不作为参数");
                        break;
                    case "s":
                        variables.put(formDataItem[0], formDataItem[1]);
                        hasVariables = true;
                        break;
                    case "t":
                        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        variables.put(formDataItem[0], timeFormat.parse(formDataItem[2]));
                        hasVariables = true;
                        break;
                    case "b":
                        variables.put(formDataItem[0], BooleanUtils.toBoolean(formDataItem[2]));
                        hasVariables = true;
                        break;
                    default:
                        System.out.println("控件参数类型配置错误：" + formDataItem[0] + "的参数类型不存在，" + formDataItem[2]);
                }
            }//for结束

            if (hasVariables) {
                //带参数完成任务
                taskRuntime.complete(TaskPayloadBuilder.complete().withTaskId(taskID)
                        .withVariables(variables)
                        .build());
            } else {
                taskRuntime.complete(TaskPayloadBuilder.complete().withTaskId(taskID)
                        .build());
            }

            //写入数据库
            int result = activitiMapper.insertFormData(listMap);

            return AjaxResponse.AjaxData(ResponseCode.SUCCESS.getCode(),
                    ResponseCode.SUCCESS.getDesc(), listMap);
        } catch (Exception e) {
            return AjaxResponse.AjaxData(ResponseCode.ERROR.getCode(),
                    "失败", e.toString());
        }
    }
}
