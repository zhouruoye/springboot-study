package com.cest.controller;

import com.cest.enums.ResponseCode;
import com.cest.util.AjaxResponse;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipInputStream;

@Slf4j
@RestController
@RequestMapping("/processDefinition")
public class ProcessDefinitionController {

    //流程部署
    @Autowired
    private RepositoryService repositoryService;

    //BPMN上传
    @PostMapping(value = "/uploadStreamAndDeployment")
    public AjaxResponse uploadStreamAndDeployment(@RequestParam("multipartFile") MultipartFile multipartFile) {
        try {
            //获取上传的文件名
            String originalFilename = multipartFile.getOriginalFilename();
            //获取文件的扩展名
            String extension = FilenameUtils.getExtension(originalFilename);

            InputStream inputStream = multipartFile.getInputStream();

            Deployment deployment = null;
            if ("bpmn".equalsIgnoreCase(extension)) {
                deployment = repositoryService.createDeployment()
                        .addInputStream(originalFilename, inputStream)
                        .name("流程部署bpmn")
                        .deploy();
            } else if ("zip".equalsIgnoreCase(extension)) {
                ZipInputStream zipInputStream = new ZipInputStream(inputStream);
                deployment = repositoryService.createDeployment()
                        .addZipInputStream(zipInputStream)
                        .name("流程部署zip")
                        .deploy();
            } else {
                return AjaxResponse.AjaxData(ResponseCode.ERROR.getCode(),
                        "部署流程失败", "error");
            }
            return AjaxResponse.AjaxData(ResponseCode.SUCCESS.getCode(),
                    ResponseCode.SUCCESS.getDesc(), deployment.getId() + ";" + originalFilename);
        } catch (Exception e) {
            return AjaxResponse.AjaxData(ResponseCode.ERROR.getCode(),
                    "部署流程失败", e.toString());
        }
    }

    //流程部署查询
    @GetMapping(value = "/getDeployment")
    public AjaxResponse getDeployment() {

        try {
            List<HashMap<String, Object>> listMap = new ArrayList<HashMap<String, Object>>();

            List<Deployment> deployments = repositoryService.createDeploymentQuery()
                    .orderByDeploymenTime()
                    .desc()
                    .list();

            for (Deployment dep : deployments) {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("id", dep.getId());
                hashMap.put("name", dep.getName());
                hashMap.put("deploymentTime", dep.getDeploymentTime());
                listMap.add(hashMap);
            }

            return AjaxResponse.AjaxData(ResponseCode.SUCCESS.getCode(),
                    ResponseCode.SUCCESS.getDesc(), listMap);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("流程部署查询失败:{}", e);
            return AjaxResponse.AjaxData(ResponseCode.ERROR.getCode(),
                    "查询失败", e.toString());
        }
    }

    //流程定义查询
    @GetMapping(value = "/getDefinitions")
    public AjaxResponse getDefinitions() {
        try {
            List<HashMap<String, Object>> listMap = new ArrayList<HashMap<String, Object>>();

            List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()
                    .orderByProcessDefinitionVersion()
                    .desc()
                    .list();

//            list.sort((x, y) -> x.getVersion() - y.getVersion());

            for (ProcessDefinition pd : list) {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("processDefinitionID", pd.getId());
                hashMap.put("name", pd.getName());
                hashMap.put("key", pd.getKey());
                hashMap.put("resourceName", pd.getResourceName());
                hashMap.put("deploymentID", pd.getDeploymentId());
                hashMap.put("version", pd.getVersion());
                listMap.add(hashMap);
            }

            return AjaxResponse.AjaxData(ResponseCode.SUCCESS.getCode(),
                    ResponseCode.SUCCESS.getDesc(), listMap);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("流程部署查询失败:{}", e);
            return AjaxResponse.AjaxData(ResponseCode.ERROR.getCode(),
                    "获取流程定义失败", e.toString());
        }
    }

    //获取流程定义XML查询
    @GetMapping(value = "/getDefinitionXML")
    public void getDefinitionXML(HttpServletResponse response,
                                 @RequestParam("deploymentId") String deploymentID,
                                 @RequestParam("resourceName") String resourceName) {
        try {
            InputStream resourceAsStream = repositoryService.getResourceAsStream(deploymentID, resourceName);
            int count = resourceAsStream.available();

            byte[] bytes = new byte[count];
            response.setContentType("text/xml");
            OutputStream outputStream = response.getOutputStream();
            while (resourceAsStream.read(bytes) != -1) {
                outputStream.write(bytes);
            }
            resourceAsStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //流程定义查询
    @GetMapping(value = "/delDefinition")
    public AjaxResponse delDefinition(@RequestParam("deploymentID") String deploymentID) {
        try {
            repositoryService.deleteDeployment(deploymentID, true);
            return AjaxResponse.AjaxData(ResponseCode.SUCCESS.getCode(),
                    ResponseCode.SUCCESS.getDesc(), deploymentID);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("删除流程定义失败:{}", e);
            return AjaxResponse.AjaxData(ResponseCode.ERROR.getCode(),
                    "删除流程定义失败", e.toString());
        }
    }

}
