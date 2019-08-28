package threadPool;

import java.util.concurrent.*;

/**
 * @author Ethan
 * @date 2019/8/28 14:52
 */
public class CyclicBarrierTest {
    public static void main(String[] args) {
        int n = 4;
        final CyclicBarrier barrier = new CyclicBarrier(n);
        final CyclicBarrier barrier1 = new CyclicBarrier(n, new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        });
        ExecutorService service = Executors.newFixedThreadPool(n);
        for (int i = 0; i < n; i++){
            service.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(Thread.currentThread().getName() + "正在写入数据");
                        Thread.sleep(1000);
                        System.out.println(Thread.currentThread().getName() + "执行完毕");
                        barrier1.await(200, TimeUnit.MILLISECONDS);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }catch (BrokenBarrierException e){
                        e.printStackTrace();
                    } catch (TimeoutException e) {
                        e.printStackTrace();
                    }
                    System.out.println("所有线程写入完毕，继续处理其他任务...");
                }
            });
        }
    }
}
