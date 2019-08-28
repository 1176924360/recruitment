package threadPool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.TimeUnit.MINUTES;

/**
 * 自主定制非阻塞线程池
 * ThreadPoolExecutor: corePoolSize,maximumPoolSize,keepAliveTime,unit,workQueue,threadFactory,handler
 * @author Ethan
 * @date 2019/8/28 13:33
 */
public class CustomThreadPoolExecutor {
    private ThreadPoolExecutor pool = null;
    /**
     * 初始方法
     */
    public void init(){
        pool = new ThreadPoolExecutor(10, 30, 30, MINUTES,
                new ArrayBlockingQueue<Runnable>(10), new CustomThreadFactory(),
                new CustomRejectedExecutionHandler());
    }

    public void destroy(){
        if (pool != null)
            pool.shutdown();
    }

    public ExecutorService getCustomThreadPoolExecutor(){
        return this.pool;
    }

    //异常处理
    private class CustomRejectedExecutionHandler implements RejectedExecutionHandler{

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.out.println("error...................");
            try {
                executor.getQueue().put(r);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    //线程工厂
    private class CustomThreadFactory implements ThreadFactory {
        private AtomicInteger count = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            String name = CustomThreadFactory.class.getSimpleName() + count.addAndGet(1);
            System.out.println(name);
            thread.setName(name);
            return thread;
        }
    }

    public static void main(String[] args) {
        CustomThreadPoolExecutor exc = new CustomThreadPoolExecutor();

        exc.init();
        ExecutorService pool = exc.getCustomThreadPoolExecutor();
        for (int i = 0; i < 100; i++){
            System.out.println("提交第"+i+"个任务");
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(">>>task is running========");
                        Thread.sleep(3000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
