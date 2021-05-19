package com.cest;

import com.cest.service.CountersigningService;
import com.cest.service.TaskOpService;
import lombok.extern.slf4j.Slf4j;
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


    /**
     *  测试会签减签
     */
    @Test
    public void testRemoveCountersigningTask() {
        List<String> assigneeList = new ArrayList<>();
        assigneeList.add("salaboy");
        countersigningService.removeCountersigningTask("e2e13ede-b857-11eb-805f-00ff2cb67c15", assigneeList);
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


    @Test
    public void skipActivitiPoint() {
        taskOpService.withdraw("ed6a29ac-b83f-11eb-9c1a-00ff2cb67c15","zfsq01");
    }
}
