package com.cest.controller;

import com.cest.enums.ResponseCode;
import com.cest.util.AjaxResponse;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/processDefinition")
public class ProcessDefinitionController {

    //BPMN上传
    @PostMapping(value = "/uploadStreamAndDeployment")
    public AjaxResponse uploadStreamAndDeployment(@RequestParam("multipartFile") MultipartFile multipartFile) {
        try {
            //获取上传的文件名
            String originalFilename = multipartFile.getOriginalFilename();
            //获取文件的扩展名
            String extension = FilenameUtils.getExtension(originalFilename);

            return AjaxResponse.AjaxData(ResponseCode.SUCCESS.getCode(),
                    ResponseCode.SUCCESS.getDesc(), "");
        } catch (Exception e) {
            return AjaxResponse.AjaxData(ResponseCode.ERROR.getCode(),
                    "部署流程失败", e.toString());
        }
    }

}
