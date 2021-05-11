package com.cest;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 流程任务ACT_RU_TASK
 */
@Slf4j
public class Part4_TaskTest extends BaseTest{

    @Autowired
    private TaskService taskService;

    /**
     * 查询任务
     */
    @Test
    public void getTasks() {
        List<Task> list = taskService.createTaskQuery().list();

        log.info("--------------查询所有任务---------------");

        for (Task task : list) {
            log.info("getId:{}",task.getId());
            log.info("getName:{}",task.getName());
            log.info("getAssignee:{}",task.getAssignee()); //执行人
        }
    }

    /**
     * 查询我待办的任务
     */
    @Test
    public void getMyAssigneeTasks() {
        List<Task> list = taskService.createTaskQuery()
                .taskAssignee("wukong")
                .list();
        log.info("--------------------------查询我的待办任务------------------------");

        for(Task task : list){
            log.info("getId:{}",task.getId());
            log.info("getName:{}",task.getName());
            log.info("getAssignee:{}",task.getAssignee()); //执行人
        }
    }

    /**
     * 完成任务
     */
    @Test
    public void completeTaks() {
        log.info("--------------------------完成当前节点任务------------------------");
        taskService.complete("2c4db737-8623-11eb-8c54-00ff2cb67c15");
    }

    /**
     * 拾取任务 候选人任务
     */
    @Test
    public void claimTask(){
        Task task = taskService.createTaskQuery().taskId("b690a917-af20-11eb-90e0-00ff2cb67c15").singleResult();
        taskService.claim("b690a917-af20-11eb-90e0-00ff2cb67c15","bajie");
    }

    /**
     * 归还与交办任务
     */
    @Test
    public void setTaskAssignee(){
//        Task task = taskService.createTaskQuery().taskId("9007ce4f-8623-11eb-a086-00ff2cb67c15").singleResult();
        //taskService.setAssignee("9007ce4f-8623-11eb-a086-00ff2cb67c15","null");//归还候选任务 --- > 领取任务后 归还
        taskService.setAssignee("ad95854e-8d41-11eb-86f3-00ff2cb67c15","bajie");//交办
    }
}
