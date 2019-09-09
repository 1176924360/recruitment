package yangGeJava;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Ethan
 * @date 2019/9/9 9:18
 */

class MyData02{
    /**
     * 问题：volatile不可以保证原子性，当用20个线程将number++1000次时，最终得到的值与预期值不符合
     * 当各个线程将主内存中的值拷贝到自己的工作内存中，当他们都想将处理后的值写入到主内存时，由于一个线程写的时候，其他线程被挂起。在写完时，被挂起的线程继续往主内存写，此时导致写回到主内存的数据没有+1，出现数据丢失的现象
     * 解决：1、可以使用synchronized修饰，使其同步，但是这个方法太重了
     * 2、使用JUC下的Atomic包
     */
    volatile int number = 0;
    public synchronized void addPlus(){
        number++;
    }

    /**
     * 使用AtomicInteger保证原子性
     */
    AtomicInteger atomicInteger = new AtomicInteger();
    public void addAtomic(){
        atomicInteger.getAndIncrement();
    }
}

public class VolatileDemo {
    public static void main(String[] args) {
        MyData myData = new MyData();
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    //使用volatile不保证原子性
                    myData.addPlus();
                    //使用AtomicInteger保证原子性
                    myData.addAtomic();
                }
            }, String.valueOf(i)).start();
        }

        //当活跃线程大于2个时(main线程与GC线程)，将在此等待
        while (Thread.activeCount() > 2){
            Thread.yield();
        }

        System.out.println(Thread.currentThread().getName() + "\t finally number value:" + myData.number);
        System.out.println(Thread.currentThread().getName() + "\t finally number value:" + myData.atomicInteger);

    }
}
