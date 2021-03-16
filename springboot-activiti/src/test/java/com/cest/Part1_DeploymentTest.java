package com.cest;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

/**
 * 流程部署测试
 */
@Slf4j
public class Part1_DeploymentTest extends BaseTest {

    @Autowired
    private RepositoryService repositoryService;

    @Test
    public void testHello() {
        log.info("hello");
    }


    //通过bpmn部署流程 -- 同时创建ACT_RE_PROCDEF--流程定义表 ACT_RE_DEPLOYMENT--流程部署表
    @Test
    public void initDeploymentByBPMN() {
        String filename="BPMN/Part1_Deployment.bpmn";
        // String pngname="BPMN/Part1_Deployment.png";
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource(filename)
                //.addClasspathResource(pngname)//图片
                .name("流程部署测试申请task1")
                .deploy();
        System.out.println(deployment.getName());
    }


    //通过zip部署流程
    @Test
    public void initDeploymentByZip() {
        String filename="BPMN/Part1_DeploymentV2.zip";

        InputStream resourceAsStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream(filename);

        ZipInputStream zipInputStream = new ZipInputStream(resourceAsStream);

        Deployment deployment = repositoryService.createDeployment()
                .addZipInputStream(zipInputStream)
                .name("流程部署zip")
                .deploy();

        System.out.println(deployment.getName());
    }

    //部署流程查询 -- 集合
    @Test
    public void getDeploymentWithList() {
        List<Deployment> list = repositoryService.createDeploymentQuery()
                .listPage(1,4);
//                .list();

        for(Deployment dep : list){
            log.info("Id："+dep.getId());
            log.info("Name："+dep.getName());
            log.info("DeploymentTime："+dep.getDeploymentTime());
            log.info("Key："+dep.getKey());
        }
    }

    //部署流程查询 -- 条件查询
    @Test
    public void getDeploymentByCondition() {
        List<Deployment> list = repositoryService.createDeploymentQuery()
                .deploymentId("b050f43c-2ef6-11eb-a33f-00ff56d6be42")
                .list();

        for(Deployment dep : list){
            log.info("Id："+dep.getId());
            log.info("Name："+dep.getName());
            log.info("DeploymentTime："+dep.getDeploymentTime());
            log.info("Key："+dep.getKey());
        }
    }
}
