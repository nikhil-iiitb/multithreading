package com.ex.threadpool.ex;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class Threadpool {
    private LinkedBlockingQueue taskQ = null;
    private List<ThreadpoolRunnable> runnableList = new ArrayList<>();
    private boolean isShutdown = false;

    public Threadpool(int noOfThreads, int maxNoOfTasks) {
        taskQ = new LinkedBlockingQueue(maxNoOfTasks);

        for(int i=0; i<noOfThreads; i++){
            ThreadpoolRunnable poolThread = new ThreadpoolRunnable(taskQ);
            runnableList.add(poolThread);
        }

        for(ThreadpoolRunnable poolThread : runnableList){
            new Thread(poolThread).start();
        }
    }

    public synchronized void execute(Runnable task) throws Exception{
        if(this.isShutdown) throw new IllegalStateException("Threadpool stopped");

        this.taskQ.offer(task);
    }

    public synchronized void shutdown(){
        this.isShutdown = true;
        for (ThreadpoolRunnable runnable : runnableList)
            runnable.doStop();
    }

    public synchronized void waitUntilAllTaskFinished(){
        while (this.taskQ.size()>0){
            try{
                Thread.sleep(1);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
