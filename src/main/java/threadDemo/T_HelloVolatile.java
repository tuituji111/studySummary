package threadDemo;

import java.io.IOException;

public class T_HelloVolatile {

    private static /*volatile*/ boolean running = true;//volatile的作用/保持可见性/禁止指令重排


    private static void m(){
        System.out.println("m start");
        while (running){
            //System.out.println("Hello");//打印语句会触发成员变量的值同步回主内存
        }
        System.out.println("m end");
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        new Thread(T_HelloVolatile::m, "mtest").start();
        Thread.sleep(1000);
        T_HelloVolatile.running = false;
    }
}
