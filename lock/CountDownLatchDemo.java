package yangGeJava;

import java.util.concurrent.CountDownLatch;

/**
 * @author Ethan
 * @date 2019/9/9 15:08
  * 只有当其他线程完成时，本线程才可以开始
 * 让一些线程阻塞直到另一些线程完成一系列操作后才被唤醒
 * countDownLatch有两个方法，一个await()，调用的线程会阻塞，其他线程调用countDown()将计数器减一。
 * 当计数器变成0时，被await()阻塞的线程会被唤起
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
