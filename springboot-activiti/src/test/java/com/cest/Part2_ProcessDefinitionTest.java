package com.cest;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 流程定义
 */
@Slf4j
public class Part2_ProcessDefinitionTest extends BaseTest {

    @Autowired
    private RepositoryService repositoryService;

    //查询流程定义
    @Test
    public void getDefinitions() {
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()
                .list();

        for(ProcessDefinition pd : list){
            System.out.println("------流程定义--------");
            System.out.println("Id:"+pd.getId());
            System.out.println("Name:"+pd.getName());
            System.out.println("Key:"+pd.getKey());
            System.out.println("ResourceName:"+pd.getResourceName());
            System.out.println("DeploymentId:"+pd.getDeploymentId());
            System.out.println("Version:"+pd.getVersion());
        }
    }

    //删除流程定义 如果是true 则是递归删除，会删除流程定义，流程实例以及各种历史实例……
    @Test
    public void delDefinitions() {
        repositoryService.deleteDeployment("df1d2e0c-86eb-11eb-8e7e-00ff2cb67c15",true);
        log.info("删除流程定义成功");
    }


}
