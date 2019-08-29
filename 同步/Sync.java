package recruitment;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Ethan
 * @date 2019/8/28 10:11
 */
public class Sync {
    int i = 0;
    public static void main(String[] args) {
        int num = 0;
        MyThread myThread = new MyThread();
        for (int i = 0; i < 10; i++){
            Thread thread = new Thread(myThread);
            thread.setName(i + "");
            thread.start();
        }
    }
}
class MyThread implements Runnable{
    private volatile int tickets = 100;

    @Override
    public void run() {
        while (tickets > 0){
            synchronized (this) {
                System.out.println(Thread.currentThread().getName() + ":剩余" + tickets-- + "张票");
            }
        }
    }
}
