package com.cest.config;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class CounterListener implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        delegateTask.getVariable("assign");
    }
}
