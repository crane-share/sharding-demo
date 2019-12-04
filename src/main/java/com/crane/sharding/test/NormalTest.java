package com.crane.sharding.test;

import groovy.transform.Synchronized;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author syhe
 * @Title: NormalTest
 * @ProjectName sharding-demo
 * @Description: TODO 普通验证
 * @date 2019-11-2209:39
 */
public class NormalTest {

    private  static AtomicInteger num= new AtomicInteger(0);
    private  static volatile int numint= 0;
    private  static Semaphore semaphore_main = new Semaphore(1);
    private  static Semaphore semaphore_son = new Semaphore(20);

    public static void main(String[] args) throws Exception{

        for (int i = 0; i <20 ; i++) {
            new Thread(()->{
                try{
                    semaphore_son.acquire();
                }catch (Exception e){
                    e.printStackTrace();
                }
                for (int j = 0; j <1000 ; j++) {
                    incr();
                }
            }).start();
        }
        semaphore_main.acquire();
        System.out.println(semaphore_son.availablePermits());
        if(semaphore_son.availablePermits()>=2){
            semaphore_main.release();
        }
        try {
            semaphore_main.tryAcquire(2, TimeUnit.SECONDS);
            System.out.println("num:"+num);// <=20000
            System.out.println("numint:"+numint);// <=20000
            semaphore_main.release();
            semaphore_son.release(20);
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    private static  void  incr(){
        num.incrementAndGet();
        numint++;
    }

}
