package yangGeJava;

/**
 * @author Ethan
 * @date 2019/9/9 14:27
 *
 * 公平锁：队列先来后到，多个线程按照申请锁的顺序取锁
 * 非公平锁：允许加塞，不按顺序。在高并发的情况下可能会出现优先级反转和饥饿现象
 * 可重入锁：同一个线程外层函数获得锁，内层递归函数仍可以获取该锁，锁会自动释放。线程可以进入任意一个已经拥有锁的代码块
 *
 */
class Phone{
    public synchronized void sendSMS(){
        System.out.println(Thread.currentThread().getName()+"\t invoked sendSMS()");
        sendEmail();
    }

    private synchronized void sendEmail() {
        System.out.println(Thread.currentThread().getName()+"\t invoked sendEmail()");
    }
}

public class ReenterLockDemo {
    public static void main(String[] args) {
        Phone phone = new Phone();
        new Thread(() -> {
            phone.sendSMS();
        }, "t1").start();
        new Thread(() -> {
            phone.sendSMS();
        }, "t2").start();
    }

}
