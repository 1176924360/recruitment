package yangGeJava;

import java.util.concurrent.CountDownLatch;

/**
 * @author Ethan
 * @date 2019/9/9 15:08
 * 只有当其他线程完成时，本线程才可以开始
 */
public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t over");
                countDownLatch.countDown();
            }, String.valueOf(i)).start();
        }

        countDownLatch.await();
        System.out.println(Thread.currentThread().getName()+"\t 最后一个over");
    }
}
