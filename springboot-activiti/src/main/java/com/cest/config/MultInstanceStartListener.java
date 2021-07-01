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
//        execution.setVariable("assigneeList",list);
        execution.setVariable("temp_groupId",list);
    }

    //人 角色 机构 业务方  查待办 在办   返回count
    //人 业务方  查待办 在办   返回count
}
