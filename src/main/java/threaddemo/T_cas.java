package threaddemo;

/**
 * 自旋锁，空转
 */

public class T_cas {
    enum ReadyToRun {T1, T2}

    static volatile ReadyToRun r = ReadyToRun.T1;

    public static void main(String[] args){
        char[] aI = "1234567".toCharArray();
        char[] aC = "ABCDEFG".toCharArray();

        new Thread(() -> {
            for(char c : aI){
                while (r != ReadyToRun.T1){} //空转自旋
                System.out.print(c);
                r = ReadyToRun.T2;
            }
        }, "t1").start();

        new Thread(() -> {
            for(char c : aC){
                while (r != ReadyToRun.T2){} //空转自旋
                System.out.print(c);
                r = ReadyToRun.T1;
            }
        }, "t2").start();
    }
}
