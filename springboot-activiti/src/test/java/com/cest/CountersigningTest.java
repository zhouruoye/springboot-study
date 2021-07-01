package com.cest;

import com.cest.service.CountersigningService;
import com.cest.service.TaskOpService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.Execution;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CountersigningTest extends BaseTest{


    @Autowired
    private CountersigningService countersigningService;

    @Autowired
    private TaskOpService taskOpService;

    @Autowired
    private RuntimeService runtimeService;

    /**
     *  测试会签减签
     */
    @Test
    public void testRemoveCountersigningTask() {
        List<String> assigneeList = new ArrayList<>();
        assigneeList.add("bajie");
        countersigningService.removeCountersigningTask("50fcdf6cad664d12b352421bf30b4824", assigneeList);
    }


    /**
     *  测试会签加签
     */
    @Test
    public void testAddCountersigningTask() {

        List<String> assigneeList = new ArrayList<>();
        assigneeList.add("bajie");
        countersigningService.addCountersigningTask("b81becab-b3cc-11eb-bc16-00ff2cb67c15", assigneeList,"bajie");
    }


    /**
     *  测试多实例会签减签
     */
    @Test
    public void testRemoveMultiInstanceCmdTask() {
        countersigningService.removeMultiInstanceCmdTask("50fcdf6cad664d12b352421bf30b4824", true);
    }


    /**
     *  测试多实例会签加签
     */
    @Test
    public void testAddMultiInstanceCmdTask() {
        countersigningService.addMultiInstanceCmdTask("50fcdf6cad664d12b352421bf30b4824", "bajie");
    }


    @Test
    public void skipActivitiPoint() {
        taskOpService.withdraw("1bf6bffa628c4adeb382e3003161f8c4","Activity_09kpd56");
    }


    @Test
    public void msgBack() {
        Execution exec = runtimeService.createExecutionQuery()
                .messageEventSubscriptionName("Event_1vkhme1")
                .processInstanceId("8c129089f51546ef84fe539bb3429ba6")
                .singleResult();
        runtimeService.messageEventReceived("Event_1vkhme1",exec.getId());
    }


    /**
     *  调用子流程跳转到主流程节点
     */
    @Test
    public void testMoveSubOutCommand() {
        countersigningService.moveSubOutCommand("e6ff3ee229664e3c9df28686e91bf46c", "raiseRequests");
    }

}
