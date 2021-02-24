package threaddemo;

import java.util.concurrent.locks.LockSupport;

public class T_LockSupport {
    static Thread t1 = null,t2=null;

    public static void main(String[] args){
        char[] aI = "1234567".toCharArray();
        char[] aC = "ABCDEFG".toCharArray();

        t1 = new Thread(() -> {
            for(char c : aI){
                System.out.print(c);
                LockSupport.unpark(t2); //唤醒t2
                LockSupport.park(); //当前线程t1阻塞
            }
        }, "t1");

        t2 = new Thread(() -> {
            for(char c : aC){
                LockSupport.park();
                System.out.print(c);
                LockSupport.unpark(t1);
            }
        }, "t2");

        t1.start();
        t2.start();
    }
}
