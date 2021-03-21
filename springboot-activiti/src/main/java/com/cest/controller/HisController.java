package com.cest.controller;

import com.cest.enums.ResponseCode;
import com.cest.pojo.UserInfo;
import com.cest.util.AjaxResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
public class HisController {

    //用户历史
    @GetMapping(value = "/getInstancesByUserName")
    public AjaxResponse InstancesByUser() {
        return AjaxResponse.AjaxData(ResponseCode.SUCCESS.getCode(),
                    ResponseCode.SUCCESS.getDesc(), null);
    }

    //任务实例历史
    @GetMapping(value = "/getInstancesByPiID")
    public AjaxResponse getInstancesByPiID(@RequestParam("piID") String piID) {
        return AjaxResponse.AjaxData(ResponseCode.SUCCESS.getCode(),
                    ResponseCode.SUCCESS.getDesc(), null);
    }

    //流程图高亮
    @GetMapping("/gethighLine")
    public AjaxResponse gethighLine(@RequestParam("instanceId") String instanceId, @AuthenticationPrincipal UserInfo userInfo) {
        return AjaxResponse.AjaxData(ResponseCode.SUCCESS.getCode(),
                    ResponseCode.SUCCESS.getDesc(), null);
    }
}
