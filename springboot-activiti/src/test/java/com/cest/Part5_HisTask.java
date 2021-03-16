package com.cest;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 历史任务查询
 */
@Slf4j
public class Part5_HisTask extends BaseTest {

    @Autowired
    private HistoryService historyService;

    //根据用户名查询历史记录
    @Test
    public void HistoricTaskInstanceByUser(){
        List<HistoricTaskInstance> list = historyService
                .createHistoricTaskInstanceQuery()
                .orderByHistoricTaskInstanceEndTime().asc()
                .taskAssignee("bajie")
                .list();
        for(HistoricTaskInstance hi : list){
            System.out.println("Id："+ hi.getId());
            System.out.println("ProcessInstanceId："+ hi.getProcessInstanceId());
            System.out.println("Name："+ hi.getName());
        }
    }

    //根据流程实例ID查询历史
    @Test
    public void HistoricTaskInstanceByPiID(){
        List<HistoricTaskInstance> list = historyService
                .createHistoricTaskInstanceQuery()
                .orderByHistoricTaskInstanceEndTime().asc()
                .processInstanceId("2c49bf93-8623-11eb-8c54-00ff2cb67c15")
                .list();
        for(HistoricTaskInstance hi : list){
            System.out.println("Id："+ hi.getId());
            System.out.println("ProcessInstanceId："+ hi.getProcessInstanceId());
            System.out.println("Name："+ hi.getName());

        }
    }
}
