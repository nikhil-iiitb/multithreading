package com.ex.threadpool.ex;

import java.util.concurrent.LinkedBlockingQueue;

public class ThreadpoolRunnable implements Runnable{

    private Thread thread = null;
    private LinkedBlockingQueue taskQ = null;
    private boolean isShutdown = false;

    public ThreadpoolRunnable(LinkedBlockingQueue taskQ) {
        this.taskQ = taskQ;
    }

    @Override
    public void run() {
        this.thread = Thread.currentThread();
        while (!isShutdown){
            try{
                Runnable runnable = (Runnable) taskQ.take();
                runnable.run();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public synchronized void doStop(){
        isShutdown = true;
        this.thread.interrupt();
    }

    public synchronized boolean isShutdown(){
        return isShutdown;
    }
}
