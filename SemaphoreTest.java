package threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author Ethan
 * @date 2019/8/28 15:09
 */
public class SemaphoreTest {
    public static void main(String[] args) {
        int n = 8;
        final Semaphore semaphore = new Semaphore(5);
        ExecutorService service = Executors.newFixedThreadPool(n);
        for (int i = 0; i < n; i++){
            service.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire();
                        System.out.println("工人"+ Thread.currentThread().getName() +"占用一个机器在生产...");
                        Thread.sleep(2000);
                        System.out.println("工人"+ Thread.currentThread().getName() +"释放出机器");
                        semaphore.release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
