package threaddemo.backcall;

public class Teacher implements BackInterface {

    //因为老师要告诉学生做课堂练习,所以这个传一个学生的引用
    private Student student;

    public Teacher(Student student) {
        this.student = student;
    }

    //老师告诉学生做课堂练习
    public void doEvent() {
        System.out.println(Thread.currentThread().getName() + "老师让学生做练习...");
        student.doPractice(this);
    }

    //用于接收接口回调的消息
    @Override
    public void backMethod() {
        System.out.println(Thread.currentThread().getName() + "老师接收到学生做完课堂练习的消息");
    }

}