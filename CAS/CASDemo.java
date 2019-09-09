package yangGeJava;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Ethan
 * @date 2019/9/9 10:21
 *
 * CAS：比较并交换
 * 底层使用一个Unsafe类。Unsafe类是CAS的核心，其方法都是native方法，即可以直接访问特定内存的数据，相当于C中的指针操作内存。
 * CAS底层使用的方法：unsafe.compareAndSwapInt(this, valueOffset, expect, update);
 * 其中valueOffset是内存偏移量，听过Unsafe类根据此内存地址获取数据
 * CAS 比较当前工作内存与的值与主内存中的值，如果相同则执行规定操作，否则继续比较值直到工作内存的值与主内存相同
 * 优点： 在不加锁的情况下，保证一致性，并发性加强
 * 缺点：1、循环时间长，开销大
 * 2、只能保证一个共享原子的原子性
 * 3、ABA问题
 */
public class CASDemo {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);
        System.out.println(atomicInteger.compareAndSet(5, 15) + "\t current data:" + atomicInteger.get());
        System.out.println(atomicInteger.compareAndSet(5, 115) + "\t current data:" + atomicInteger.get());
    }
}
