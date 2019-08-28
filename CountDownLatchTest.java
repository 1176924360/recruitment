package threadPool;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Ethan
 * @date 2019/8/28 14:35
 */
public class CountDownLatchTest {
    public static void main(String[] args) {
        final CountDownLatch latch = new CountDownLatch(10);
        ExecutorService service = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++){
            service.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(Thread.currentThread().getName() + "正在执行");
                        Thread.sleep(10);
                        System.out.println(Thread.currentThread().getName() + "执行完毕");
                        latch.countDown();
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            });
        }
        service.shutdown();
        try {
            System.out.println("等待10个子线程执行完毕...");
            latch.await();
            System.out.println("10个子线程已经执行完毕");
            System.out.println("继续执行主线程");
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
