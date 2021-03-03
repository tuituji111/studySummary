package threaddemo.backcall;

public class Student {

    //学生做课堂练习,并告诉老师
    public void doPractice(BackInterface BackInterface) {
        System.out.println(Thread.currentThread().getName() + "学生做练习...");
        System.out.println(Thread.currentThread().getName() + "学生告诉老师做完练习...");
        BackInterface.backMethod();
    }
}
