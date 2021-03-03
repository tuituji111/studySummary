package threaddemo.backcall;

public class BackDemo {
    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                Teacher teacher = new Teacher(new Student());
                teacher.doEvent();
            }).start();
        }
    }
}
