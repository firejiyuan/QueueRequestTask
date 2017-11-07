package com.liangrunxiang.queuerequesttask;

/**
 * 任务执行器
 *
 * @author liujianping
 * @date 2017/10/25
 */

public abstract class TaskExecutor {

    private TaskQueue queue;

    protected TaskExecutor() {
        create();
    }

    private void create() {
        queue = new TaskQueue(createTaskQueue());
    }

    /**
     * 创建任务队列
     *
     * @return 可同时执行的任务数量
     */
    protected abstract int createTaskQueue();

    /**
     * 添加任务
     *
     * @param task
     */
    public void addTask(ITask task) {
        if (queue != null) {
            queue.addTask(task);
        }
    }

    public void clean() {
        if (queue != null) {
            queue.clean();
        }
    }

    public void stop() {
        if (queue != null) {
            queue.stop();
        }
    }


}
