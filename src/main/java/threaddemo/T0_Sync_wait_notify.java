package threaddemo;

/**
 * 这里没法控制哪个线程先执行
 */

public class T0_Sync_wait_notify {
    public static void main(String[] args){
        final Object o = new Object();

        char[] aI = "1234567".toCharArray();
        char[] aC = "ABCDEFG".toCharArray();

        new Thread(() -> {
            synchronized (o){
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
