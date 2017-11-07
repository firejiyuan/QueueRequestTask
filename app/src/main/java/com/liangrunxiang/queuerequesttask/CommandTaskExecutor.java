package com.liangrunxiang.queuerequesttask;

/**
 * 执行串口命令的任务执行器
 *
 * @author liujianping
 *
 * @date 2017/10/25
 */

public class CommandTaskExecutor extends TaskExecutor {

    private static CommandTaskExecutor executor;

    /**
     * 单例模式
     *
     * @return
     */
    public static CommandTaskExecutor getInstance(){
        if (executor == null)
        {
            synchronized (CommandTaskExecutor.class)
            {
                if (executor == null)
                {
                    executor = new CommandTaskExecutor();
                }
            }
        }

        return executor;
    }

    private CommandTaskExecutor(){
        super();//不能少，否则任务队列为空
    }

    @Override
    protected int createTaskQueue() {
        //此任务队列只允许同时执行一个任务，防止串口命令执行失败
        return 1;
    }
}
