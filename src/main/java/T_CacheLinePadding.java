import sun.misc.Contended;

import java.util.concurrent.CountDownLatch;

public class T_CacheLinePadding {
    public static long COUNT = 10_0000_0000L;

    //@Contended //只有1.8起作用，保证x位于单独一个内存行中，运行时需要加参数：-XX:-RestrictContended
    private static class T{
        //private long p1,p2,p3,p4,p5,p6,p7;//一个内存行64个字节
        //@Contended
        public long x = 0L;
        //private long p9,p10,p11,p12,p13,p14,p15;//
        //@Contended
        public long y = 0L;

    }

    public static T[] arr = new T[2];

    static{
        arr[0] = new T();
        arr[1] = new T();
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);//用法?

        Thread t1 = new Thread(()->{
            for(long j=0;j<COUNT;j++){
                arr[0].x = j;
            }

            latch.countDown();
        });

        Thread t2 = new Thread(()->{
            for(long j=0;j<COUNT;j++){
                arr[0].y = j;//此时在一个内存行上的概率较高
                //arr[1].y = j;//此时在一个内存行上的概率很低
                //arr[0].x = j;//此时不管怎么优化，貌似始终在一个内存行上
            }

            latch.countDown();
        });

        final long start = System.nanoTime();
        t1.start();
        t2.start();
        latch.await();
        System.out.println((System.nanoTime() - start)/100_0000);
    }
}
