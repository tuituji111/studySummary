package threaddemo;

import threaddemo.utils.Consumer;
import threaddemo.utils.Producer;
import threaddemo.utils.Wotou;

public class T_P_C {
    private int index = 0;
    Wotou[] arrWt = new Wotou[6];

    public synchronized void push(Wotou wotou){
        while(index == arrWt.length){//用while代替if，catch住后继续回来判断while判断，防止catch后继续往下执行造成线程不一致的问题，
            try {
                this.wait();//调用当前push方法的生产者线程进入等待
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        this.notify();//唤醒正在等待的消费者线程
        arrWt[index] = wotou;
        index++;
    }

    public synchronized Wotou pop(){
        while(index == 0){
            try {
                this.wait();//调用当前pop的消费者线程进入等待
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        this.notify();//唤醒正在等待的生产者线程
        index--;
        return arrWt[index];
    }

    public static void main(String[] args){
        T_P_C box = new T_P_C();
        Producer p = new Producer(box);
        //new Thread(pruducer::p, "pruducer").start();

        Consumer c = new Consumer(box);
        //new Thread(cussume::c, "cussume").start();

        //最好保证生产者和消费者等量，或者保证wotou总数量相等，否则会有一直等待的情况，
        new Thread(p).start();
        new Thread(c).start();
        new Thread(c).start();
        new Thread(c).start();
    }
}
