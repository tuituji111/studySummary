package threaddemo.backcall4future;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Callable+FutureTask
 * Callable+Future
 */
public class CallableTest {

    public static void main(String[] args) {
        //      //创建线程池
        //      ExecutorService es = Executors.newSingleThreadExecutor();
        //      //创建Callable对象任务
        //      CallableDemo calTask=new CallableDemo();
        //      //提交任务并获取执行结果
        //      Future<Integer> future =es.submit(calTask);
        //      //关闭线程池
        //      es.shutdown();


        List<FutureTask> futureTaskList = new ArrayList<>();
        //创建线程池
        //ExecutorService es = Executors.newSingleThreadExecutor();//《阿里巴巴Java开发手册》中强制线程池不允许使用 Executors 去创建，而是通过 new ThreadPoolExecutor 实例的方式，这样的处理方式让写的同学更加明确线程池的运行规则，规避资源耗尽的风险
        ExecutorService es = new ThreadPoolExecutor(3, 10, 60L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(10));

        for (int i = 0; i < 3; i++) {
            //创建Callable对象任务
            CallableDemo calTask = new CallableDemo();
            //创建FutureTask
            FutureTask<Integer> futureTask = new FutureTask<>(calTask);
            //执行任务
            es.submit(futureTask);
            futureTaskList.add(futureTask);
        }
        try {
            for (FutureTask futureTask : futureTaskList) {
                while (true){
                    System.out.println("主线程在执行其他任务");

                    //if (futureTask.get() != null) {
                    if (futureTask.isDone() && !futureTask.isCancelled()){
                        //输出获取到的结果
                        System.out.println("futureTask.get()-->" + futureTask.get() + "|" + Thread.currentThread().getName());
                        break;
                    } else {
                        //输出获取到的结果
                        System.out.println("futureTask.get()未获取到结果" + "|" + Thread.currentThread().getName());
                        Thread.sleep(1000);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭线程池
            es.shutdown();
        }

        System.out.println("主线程在执行完成");


        /*//创建Callable对象任务
        CallableDemo calTask = new CallableDemo();
        //创建FutureTask
        FutureTask<Integer> futureTask = new FutureTask<>(calTask);
        //执行任务
        es.submit(futureTask);

        try {
            Thread.sleep(2000);
            System.out.println("主线程在执行其他任务");

            if (futureTask.get() != null || futureTask.isDone() || futureTask.isCancelled()) {
                //输出获取到的结果
                System.out.println("futureTask.get()-->" + futureTask.get());
            } else {
                //输出获取到的结果
                System.out.println("futureTask.get()未获取到结果");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭线程池
            es.shutdown();
        }
        System.out.println("主线程在执行完成");*/
    }
}
