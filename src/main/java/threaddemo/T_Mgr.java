package threaddemo;

public class T_Mgr {
    //private static threadDemo.T_Mgr INSTANCE = new threadDemo.T_Mgr();//不管用不用就直接new一个对象，比较浪费空间资源
    //private static threadDemo.T_Mgr INSTANCE = null;
    private static volatile T_Mgr INSTANCE = null;// 保持可见性，禁止初始化对象时的指令重排

    private T_Mgr(){}

    //加锁处理线程一致性问题
    //public synchronized static threadDemo.T_Mgr getInstance(){ //假如这里业务逻辑代码很复杂，就要考虑缩小上锁粒度的问题，否则会降低效率
    public static T_Mgr getInstance(){
        //业务逻辑代码

        // 为空时才new操作，节省空间资源，如果这里不加空判断，会导致所有线程过来时都进行下面的加锁操作，造成资源浪费
        // 两个空判断叫DCL,double check lock
        if (null == INSTANCE) {

            synchronized (T_Mgr.class) {
                // 如果这里不加空判断，仍然会有线程一致性问题
                if (null == INSTANCE) {
                    try {
                        Thread.sleep(1);//不加sleep的话cpu处理的会很均匀，发现不了一致性的问题
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    INSTANCE = new T_Mgr();
                }
            }
        }
        return INSTANCE;
    }

    public void m(){
        System.out.println("m");
    }

    public static void main(String[] args){
        /*threadDemo.T_Mgr m1 = threadDemo.T_Mgr.getInstance();
        threadDemo.T_Mgr m2 = threadDemo.T_Mgr.getInstance();
        System.out.println(m1 == m2);*/
        for(int i = 0;i<100;i++){
            new Thread(()->{
                System.out.println(T_Mgr.getInstance().hashCode());
            }).start();
        }
    }
}
