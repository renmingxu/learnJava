package com.renmingxu.test.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by renmingxu on 2017/2/8.
 */
public class FixedThreadPoolExecutorTest {
    public static void main(String[] args) {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 10; i++) {
            final int index = i;
            fixedThreadPool.execute(new Thread(String.valueOf(i)) {
                public void run() {
                    try {
                        System.out.println(index);
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();

                    }
                }
            });
        }
        fixedThreadPool.execute(new Runnable() {
            public void run() {
                System.out.println(fixedThreadPool.isShutdown());
                fixedThreadPool.shutdown();
            }
        });
        //fixedThreadPool.shutdownNow();
        System.out.println("Main exit");
    }
}
