package threaddemo;

/**
 * 一个condition
 */

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class T0_lock_condition {

    public static void main(String[] args){
        char[] aI = "1234567".toCharArray();
        char[] aC = "ABCDEFG".toCharArray();

        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        new Thread(() -> {
            try{
                lock.lock(); // synchronized

                for(char c : aI){
                    System.out.print(c);
                    condition.signal(); // notify
                    condition.await(); // wait
                }

                condition.signal(); // notify
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
                    condition.signal(); // notify
                    condition.await(); // wait
                }

                condition.signal(); // notify
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }

        }, "t2").start();
    }
}
