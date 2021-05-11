package com.cest;

import com.cest.service.CountersigningService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CountersigningTest extends BaseTest{


    @Autowired
    private CountersigningService countersigningService;

    /**
     *  测试会签减签
     */
    @Test
    public void testRemoveCountersigningTask() {
        List<String> assigneeList = new ArrayList<>();
        assigneeList.add("bajie");

        countersigningService.removeCountersigningTask("b02543b5-b228-11eb-ab53-00ff2cb67c15", assigneeList);
    }


    /**
     *  测试会签加签
     */
    @Test
    public void testAddCountersigningTask() {

        List<String> assigneeList = new ArrayList<>();
        assigneeList.add("bajie");
        countersigningService.addCountersigningTask("e8e5088f-b225-11eb-89dc-00ff2cb67c15", assigneeList,"bajie");
    }
}
