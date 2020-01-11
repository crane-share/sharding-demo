package com.crane.sharding.test;

/**
 * @author syhe
 * @Title: SynchronizedTest
 * @ProjectName sharding-demo
 * @Description: TODO 同步锁
 * @date 2020-01-1114:08
 */
public class SynchronizedTest extends Thread{

    private String key;
    public  SynchronizedTest (String key){
        this.key = key;
    }

    public static void main(String[] args) {
        Thread t1 = new SynchronizedTest("crane1");
        Thread t2 = new SynchronizedTest("crane1");
        Thread t3 = new SynchronizedTest("crane1");
        Thread t4 = new SynchronizedTest("crane1");
        t1.setName("t1");
        t2.setName("t2");
        t3.setName("t3");
        t4.setName("t4");
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }

    @Override
    public void run() {
        synchronized (key){
            try {
                Thread.sleep(2000);
                System.out.println(key+ ": 来了"+ Thread.currentThread().getName() + "时间" + System.currentTimeMillis());
            }catch (Exception e){

            }
        }
    }
}
