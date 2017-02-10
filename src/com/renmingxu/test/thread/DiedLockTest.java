package com.renmingxu.test.thread;

/**
 * Created by renmingxu on 2017/2/8.
 */
public class DiedLockTest {
    public static void main(String args[]) throws InterruptedException {
        String a = "a";
        String b = "b";
        Thread ta = new Thread(){
            public void run() {
                synchronized (a){
                    try {

                        System.out.println("This is " + a + " " + System.nanoTime());
                        Thread.sleep(1000);
                        synchronized (b){
                            System.out.println("Get " + b + " in " + a);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("ta exit");
                }
            }
        };
        Thread tb = new Thread(){
            public void run() {
                synchronized (b){
                    try {
                        System.out.println("This is " + b +  " " + System.nanoTime());
                        Thread.sleep(1000);
                        synchronized (a){
                            System.out.println("Get " + a + " in " + b);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("tb exit");
                }
            }
        };
        ta.start();
        tb.start();
        Thread.sleep(1500);
        ta.interrupt();
    }


}
