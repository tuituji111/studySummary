package threaddemo;

import java.util.concurrent.CountDownLatch;

/**
 * 这里可以控制哪个线程先执行
 */

public class T1_Sync_wait_notify {

    // 用开关和CountDownLatch都可以实现控制
    //private static volatile boolean t2Started = false;
    private static CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args){
        final Object o = new Object();

        char[] aI = "1234567".toCharArray();
        char[] aC = "ABCDEFG".toCharArray();

        new Thread(() -> {
            //后开始的执行await
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (o){

                // 后开始的加这一个while判断
                /*while (!t2Started){
                    try {
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }*/

                for(char c : aI){
                    System.out.print(c);
                    try {
                        o.notify();
                        o.wait(); //这里两行不能交换位置，等待之后就无法唤醒其他线程了
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                o.notify(); //这里如果不加notify,则永远会有一个线程处于等待状态
            }
        }, "t1").start();

        new Thread(() -> {
            synchronized (o){
                for(char c : aC){
                    System.out.print(c);
                    latch.countDown(); // 先开始的执行countDown
                    //t2Started = true;// 先开始的打开轮训开关
                    try {
                        o.notify();
                        o.wait(); //这里两行不能交换位置，等待之后就无法唤醒其他线程了
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                o.notify(); //这里如果不加notify,则永远会有一个线程处于等待状态
            }
        }, "t2").start();
    }
}
