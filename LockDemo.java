package threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Ethan
 * @date 2019/8/28 14:22
 */
public class LockDemo {
    Lock lock = new ReentrantLock();
    public void readFile(String fileMessage){
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "得到了锁，正在读取文件……");
            for (int i = 0; i < fileMessage.length(); i++){
                System.out.print(fileMessage.charAt(i));
            }
            System.out.println();
            System.out.println("文件读取完毕！");
        }finally {
            System.out.println(Thread.currentThread().getName() + "释放了锁！");
            lock.unlock();
        }
    }

    public void demo(final String fileMessage){
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < 20; i++){
            service.execute(new Runnable() {
                @Override
                public void run() {
                    readFile(fileMessage);
                    try {
                        Thread.sleep(20);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            });
        }
        service.shutdown();
    }

    public static void main(String[] args) {
        LockDemo lockDemo = new LockDemo();
        lockDemo.demo("1111111");
    }
}
