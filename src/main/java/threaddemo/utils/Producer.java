package threaddemo.utils;

import threaddemo.T_P_C;

public class Producer implements Runnable{
    private T_P_C box = null;

    public Producer(T_P_C box){
        this.box = box;
    }

    @Override
    public void run() {
        for (int i = 0;i < 60; i++){//最好保证生产者和消费者等量，或者保证wotou总数量相等，否则会有一直等待的情况，
            Wotou wt = new Wotou(i);
            box.push(wt);
            System.out.println("生产了：" + i);

            try {
                //Thread.sleep((long) (1000 * Math.random()));
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
