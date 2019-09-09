package yangGeJava;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author Ethan
 * @date 2019/9/9 10:37
 *
 * ABA问题的解决  使用AtomicStampedReference
 */
public class ABADemo {

    //原子引用的整形数，初始值为100
    static AtomicReference<Integer> atomicReference = new AtomicReference<>(100);
    //时间戳引用，相当于版本号，初始值为100，初始版本号是1
    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100, 1);
    public static void main(String[] args) {
        new Thread(() -> {
            //产生ABA问题
            atomicReference.compareAndSet(100, 101);
            atomicReference.compareAndSet(101, 100);
        }, "t1").start();

        new Thread(() -> {
            try {
                //睡1秒，完成上面100->101,101->100的ABA情景
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //修改成功,产生ABA问题
            System.out.println(atomicReference.compareAndSet(100, 2019) + "\t" + atomicReference.get());
        }, "t2").start();

        //睡2秒，完成上面的线程
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + "\t 第一次版本号：" + stamp);

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            atomicStampedReference.compareAndSet(100,101, atomicStampedReference.getStamp(), atomicStampedReference.getStamp()+1);
            System.out.println(Thread.currentThread().getName() + "\t 第二次版本号：" + atomicStampedReference.getStamp());
            atomicStampedReference.compareAndSet(101,100, atomicStampedReference.getStamp(), atomicStampedReference.getStamp()+1);
            System.out.println(Thread.currentThread().getName() + "\t 第三次版本号：" + atomicStampedReference.getStamp());
        }, "t3").start();

        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + "\t 第一次版本号：" + stamp);

            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean result = atomicStampedReference.compareAndSet(100, 101, stamp, stamp+1);
            System.out.println(Thread.currentThread().getName()+"\t修改是否成功"+result+"\t当前版本号："+atomicStampedReference.getStamp());
            System.out.println(Thread.currentThread().getName()+"\t当前值："+atomicStampedReference.getReference());
        }, "t4").start();
    }

}
