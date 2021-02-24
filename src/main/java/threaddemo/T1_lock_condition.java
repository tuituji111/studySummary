package threaddemo;

/**
 * 多个condition，将生产者和消费者分别放到不同的队列中进行等待，
 * 然后唤醒指定队列中的生产者、消费者，这样比sychronized的notifyAll的效率会高一些
 */

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class T1_lock_condition {

    public static void main(String[] args){
        char[] aI = "1234567".toCharArray();
        char[] aC = "ABCDEFG".toCharArray();

        Lock lock = new ReentrantLock();
        // 这里的condition表示加锁的队列，可以指定唤醒某一个condition队列里面的线程，这里是两个线程交替打印，
        // 如果需要三个线程交替打印就再加一个condition，这用sychronized不容易实现，sychronized只有一个队列
        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();

        new Thread(() -> {
            try{
                lock.lock(); // synchronized

                for(char c : aI){
                    System.out.print(c);
                    condition2.signal(); // notify，叫醒condition2队列中的等待线程
                    condition1.await(); // wait，当前线程进入到condition1队列
                }

                condition2.signal(); // notify，叫醒condition2队列中的等待线程
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }


        }, "t1").start();

        new Thread(() -> {
            try {
                lock.lock(); // synchronized

                for(char c : aC){
                    System.out.print(c);
                    condition1.signal(); // notify，叫醒condition1队列中的等待线程
                    condition2.await(); // wait，当前线程进入到condition2队列
                }

                condition1.signal(); // notify，叫醒condition1队列中的等待线程
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }

        }, "t2").start();
    }
}
