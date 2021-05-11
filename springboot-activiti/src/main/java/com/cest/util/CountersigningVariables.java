package com.cest.util;

/**
 * 三个竖线并行任务 三个横线串行任务
 */
public interface CountersigningVariables {

    /**
     *  默认审核人
     */
    String ASSIGNEE_USER = "assignee";

    /**
     *  审核人集合
     */
    String ASSIGNEE_LIST = "assigneeList";

    /**
     *  会签实例总数
     */
    String NUMBER_OF_INSTANCES = "nrOfInstances";

    /**
     *  当前还没有完成的实例
     */
    String NUMBER_OF_ACTIVE_INSTANCES = "nrOfActiveInstances";

    /**
     *  已完成的会签实例总数
     */
    String NUMBER_OF_COMPLETED_INSTANCES = "nrOfCompletedInstances";

    /**
     *  会签任务表示
     *  collectionElementIndexVariable
     */
    String LOOP_COUNTER = "loopCounter";
}
