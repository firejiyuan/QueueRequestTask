package com.liangrunxiang.queuerequesttask;

import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * 任务队列
 * <p>
 * 创建任务队列的入口放在{@link TaskExecutor}里面
 *
 * @author liujianping
 * @date 2017/10/25
 */

public class TaskQueue {

    private static final String TAG = TaskQueue.class.getSimpleName();

    /**
     * 正在待待的任务
     */
    private LinkedList<ITask> waitQueue = new LinkedList<>();

    /**
     * 正在执行的任务
     */
    private ArrayList<ITask> executeQueue = new ArrayList<>();

    /**
     * 允许同时执行的任务阀值
     */
    private int allowedSize;

    /**
     * 是否需要中止执行
     */
    private boolean needStop;

    /**
     * 构造任务队列
     *
     * @param allowedSize 同时执行的任务数量
     */
    TaskQueue(int allowedSize) {
        this.allowedSize = allowedSize;
    }

    /**
     * 添加任务
     *
     * @param task 新任务
     */
    void addTask(ITask task) {
        Log.d(TAG, "-- 添加任务 ");
        //添加到等待队列中
        waitQueue.add(task);

        //判断正在执行的队列是否为空，
        synchronized (executeQueue) {

            //同时执行的任务数量不允许超过阀值
            if (executeQueue.size() < allowedSize) {
                work();
            }
        }
    }

    void work() {
        synchronized (waitQueue) {
            while (!needStop && !waitQueue.isEmpty()) {
                //从等待队列中移除第一个
                ITask task = waitQueue.removeFirst();

                Log.i(TAG, "-- 累计 " + waitQueue.size() + " 个等待中");

                //添加到正在执行的队列中
                executeQueue.add(task);
                Log.i(TAG, "-- 有 " + executeQueue.size() + " 个任务正在执行");

                //执行任务， 一个任务对应一个任务监听器
                task.execute(new TaskListenerImpl(task));
            }
        }
    }

    /**
     * 停止队列所有任务
     */
    void stop() {
        needStop = true;
    }

    /**
     * 停止队列所有任务，并清空所有状态的任务
     */
    void clean() {
        needStop = true;
        executeQueue.clear();
        waitQueue.clear();
        needStop = false;
    }

    class TaskListenerImpl implements ITaskListener {

        ITask task;

        public TaskListenerImpl(ITask task) {
            this.task = task;
        }

        @Override
        public void ended() {
            long taskId = task.getId();
            Log.w(TAG, "-- 任务[" + taskId + "] 执行完成#");

            task.completed();//预留接口，在此任务执行完后，需要作其他的工作，如：sleep(1000)。

            //从正在执行的任务中移除已执行完成的任务
            executeQueue.remove(task);

            Log.d(TAG, "-- 准备执行下一个 >>");

            //继续执行剩下的任务
            work();

            if (executeQueue.isEmpty()) {
                Log.e(TAG, "-- ### 所有任务执行完毕 ###");
                return;
            }
        }
    }


}
