package com.liangrunxiang.queuerequesttask;

/**
 * 任务接口
 *
 * @author liujianping
 *
 * @date 2017/10/25
 */

public interface ITask {


    /**
     * 执行任务
     *
     * @param listener 任务监听器，如果在execute方法里，把工作做完了，需要执行一下listener.ended方法
     */
    void execute(ITaskListener listener);

    /**
     * 获取任务id
     *
     * @return
     */
    long getId();

    /**
     * 任务执行完成，此方法在listener.ended后调用
     */
    void completed();
}
