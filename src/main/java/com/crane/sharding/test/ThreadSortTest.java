package com.crane.sharding.test;

import lombok.Data;

import java.util.concurrent.Semaphore;

/**
 * @author syhe
 * @Title: ThreadSortTest
 * @ProjectName snt-autobots
 * @Description: TODO 线程有序测试
 * @date 2019-11-2011:00
 */
@Data
public class ThreadSortTest implements Runnable{
    /*countDown方式控制线程有序
    private  static  CountDownLatch2 first = new CountDownLatch2(1);
    private  static  CountDownLatch2 second = new CountDownLatch2(1);
    private int i;
    @Override
    public void run() {
        for (int k=0;k<100;k++) {
            i++;
            System.out.println(Thread.currentThread().getName()+"_"+i);
        }
        second.countDown();
    }
    public static void main(String[] args) throws Exception{
        Thread thread1 = new Thread(new ThreadSortTest());
        Thread thread2 = new Thread(new ThreadSortTest());
        first.countDown();
        thread1.start();
        second.await();
        thread2.start();
    }*/

    //信号量方式控制线程有序
    private static Semaphore semaphore = new Semaphore(1,true);
    private int i;
    @Override
    public void run() {
        try {
            semaphore.acquire();
        }catch (Exception e){
            e.printStackTrace();
        }
        for (int k=0;k<100;k++) {
            i++;
            System.out.println(Thread.currentThread().getName()+"_"+i);
        }
        semaphore.release();
    }
    public static void main(String[] args){
        Thread thread1 = new Thread(new ThreadSortTest());
        Thread thread2 = new Thread(new ThreadSortTest());
        thread1.start();
        thread2.start();
    }
}
