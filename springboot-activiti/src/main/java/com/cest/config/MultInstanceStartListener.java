package com.cest.config;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

import java.util.ArrayList;
import java.util.List;

public class MultInstanceStartListener implements ExecutionListener
{
    @Override
    public void notify(DelegateExecution execution) {
        List<String> list = new ArrayList<>();
        list.add("bajie");
        list.add("wukong");
        list.add("salaboy");
        execution.setVariable("assigneeList",list);
    }
}
