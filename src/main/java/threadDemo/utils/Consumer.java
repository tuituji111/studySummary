package threadDemo.utils;

import threadDemo.T_P_C;

public class Consumer implements Runnable{
    private T_P_C box = null;

    public Consumer(T_P_C box){
        this.box = box;
    }

    @Override
    public void run() {
        for (int i = 0;i < 20; i++){
            Wotou wt = box.pop();
            System.out.println("消费了：" + wt.id);

            try {
                Thread.sleep((long) (1000 * Math.random()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
