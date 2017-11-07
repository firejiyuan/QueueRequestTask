package com.liangrunxiang.queuerequesttask;

/**
 * 此类保留了execute抽象方法，在添加Task的地方实现
 *
 * @author liujianping
 *
 * @date 2017/10/25
 */

public abstract class AbsTaskImpl implements ITask {

    /**
     * 任务id
     */
    private long id;

    public AbsTaskImpl() {
        this.id = System.currentTimeMillis();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * 执行完成，预留的接口
     */
    public void completed(){
        //如果在这里实现表示，任务队列里的所有任务执行完成后，都会执行相同的操作
        //如果需要实现各任务在执行完后有不同的操作，则需在new AbsTaskImpl里重写此方法，并且不能调用super.completed()
    }

}
