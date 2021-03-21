package com.cest.controller;

import com.cest.enums.ResponseCode;
import com.cest.pojo.UserInfo;
import com.cest.util.AjaxResponse;
import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/task")
public class HisController {

    @Autowired
    private HistoryService historyService;

    //用户历史任务
    @GetMapping(value = "/getInstancesByUserName")
    public AjaxResponse InstancesByUser() {
        try {
            List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery()
                        .orderByHistoricTaskInstanceEndTime().asc()
                        .taskAssignee("bajie")
                        .list();
            return AjaxResponse.AjaxData(ResponseCode.SUCCESS.getCode(),
                        ResponseCode.SUCCESS.getDesc(), historicTaskInstances);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResponse.AjaxData(ResponseCode.SUCCESS.getCode(),
                        ResponseCode.ERROR.getDesc(), e.toString());
        }
    }

    //任务实例历史
    @GetMapping(value = "/getInstancesByPiID")
    public AjaxResponse getInstancesByPiID(@RequestParam("piID") String piID) {
        try {
            List<HistoricTaskInstance> list = historyService
                .createHistoricTaskInstanceQuery()
                .orderByHistoricTaskInstanceEndTime().asc()
                .processInstanceId(piID)
                .list();
            return AjaxResponse.AjaxData(ResponseCode.SUCCESS.getCode(),
                        ResponseCode.SUCCESS.getDesc(), list);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResponse.AjaxData(ResponseCode.SUCCESS.getCode(),
                        ResponseCode.ERROR.getDesc(), e.toString());
        }
    }

    //流程图高亮
    @GetMapping("/gethighLine")
    public AjaxResponse gethighLine(@RequestParam("instanceId") String instanceId, @AuthenticationPrincipal UserInfo userInfo) {
        return AjaxResponse.AjaxData(ResponseCode.SUCCESS.getCode(),
                    ResponseCode.SUCCESS.getDesc(), null);
    }
}
