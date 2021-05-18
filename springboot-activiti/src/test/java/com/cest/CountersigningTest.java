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
        assigneeList.add("bajie");

        countersigningService.removeCountersigningTask("b81b9e89-b3cc-11eb-bc16-00ff2cb67c15", assigneeList);
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
        taskOpService.withdraw("b9ea0403-b7bf-11eb-be51-00ff2cb67c15","Activity_1mrbp7d");
    }
}
