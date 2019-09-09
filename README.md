# recruitment
为秋招准备的资料

## 卖票系统(Sync.java)
使用volatile与synchronized实现线程同步

## 可重入锁(LockDemo.java)
对ReentrantLock的联系，lock对于synchronized来说，比较灵活

## 线程池(FixPoolDemo.java)
线程池有4种：
1、SingleThreadExecutor 只有一个线程的线程池
2、FixedThreadPool 线程固定大小的线程池
3、CachedThreadPool 可缓存的线程池
4、ScheduledThreadPool 一个大小无限的线程池

## 自定义线程池(CustomThreadPoolExecutor.java)
使用ThreadPoolExecutor创建线程池，ThreadPoolExecutor的构造函数需要的参数：corePoolSize，maximumPoolSize，keepAliveTime，unit，workQueue，handler

corePoolSize：线程池核心线程的个数
maximumPoolSize：池中允许的最大线程数
keepAliveTime：当线程数大于核心时，空闲线程在终止之前等待新任务的最长时间
unit：keepAliveTime的时间单位
workQueue：工作队列，保存开始工作的任务
handler：当达到了线程边界和队列容量，无法及时处理时，reject task使用的处理程序

非阻塞线程池：当线程数量大于边界就报错
阻塞线程池：当线程数量大于临界值放入阻塞队列

## 计数器(CountDownLatchTest.java)
一个线程被挂起，当其他线程结束时，这个挂起的线程才会继续执行
在创建CountDownLatch对象时，需要传入一个count值，当count为0时，被挂起的线程会执行。核心方法为await()与countdown()，await()方法使需要等待的线程阻塞，countdown()方法在其他线程结束使进行count-1操作。

## 循环栅栏(CyclicBarrierTest.java)
当所有线程执行完后，再一起往后执行，先执行的线程会被挂起

## 信号量
将一批线程进行同步


## volatile
[demo1](volatile/MyData01.java)<br/>
此demo使用了volatile解决了内存可见性的问题，当共享变量没有用volatile修饰时，线程AAA将共享变量改变成60后，main线程无法察觉<br/>
[demo2](volatile/MyData02.java)<br/>
问题：volatile不可以保证原子性，当用20个线程将number++1000次时，最终得到的值与预期值不符合
当各个线程将主内存中的值拷贝到自己的工作内存中，当他们都想将处理后的值写入到主内存时，由于一个线程写的时候，其他线程被挂起。在写完时，被挂起的线程继续往主内存写，此时导致写回到主内存的数据没有+1，出现数据丢失的现象
解决：1、可以使用synchronized修饰，使其同步，但是这个方法太重了 2、使用JUC下的Atomic包

## CAS
[CASDemo](CAS/CASDemo.java)<br/>
CAS：比较并交换底层使用一个Unsafe类。Unsafe类是CAS的核心，其方法都是native方法，即可以直接访问特定内存的数据，相当于C中的指针操作内存。
CAS底层使用的方法：unsafe.compareAndSwapInt(this, valueOffset, expect, update);
其中valueOffset是内存偏移量，Unsafe类根据此内存地址获取数据
CAS 比较当前工作内存与的值与主内存中的值，如果相同则执行规定操作，否则继续比较值直到工作内存的值与主内存相同
* 优点： 在不加锁的情况下，保证一致性，并发性加强
* 缺点：
1. 循环时间长，开销大
2. 只能保证一个共享原子的原子性
3. ABA问题<br/>

[AtomicReferenceDemo ](CAS/AtomicReferenceDemo.java)<br/>
CAS会导致ABA问题需要使用原子引用更新使用AtomicStampedReference版本号原子引用

[ABADemo](CAS/ABADemo.java)<br/>
ABA问题的解决  使用AtomicStampedReference

## container
[safeContainer](container/safeContainer.java)<br/>
java.util.ConcurrentModificationException产生了此问题，并发修改异常导致原因：并发修改导致，很多线程抢夺写资源，<br/>
导致出现异常解决办法：
1. 使用Vector，其底层使用synchronized
2. 使用Collections.synchronizedList(new ArrayList<>());
3. 使用CopyOnWriteArrayList<>(),实现写时复制：CopyOnWriteArrayList底层使用volatile修饰一个数组，当在添加数据时，使用ReentrantLock锁锁住线程，
copy一份与原先list相同的数据到数组中(使用Arrays.copyOf)，最后加入元素，使用setArray方法，将原来的list指向这个数组

## lock
[ReenterLockDemo](lock/ReenterLockDemo.java)<br/>
公平锁：队列先来后到，多个线程按照申请锁的顺序取锁<br/>
非公平锁：允许加塞，不按顺序。在高并发的情况下可能会出现优先级反转和饥饿现象<br/>
可重入锁：同一个线程外层函数获得锁，内层递归函数仍可以获取该锁，锁会自动释放。线程可以进入任意一个已经拥有锁的代码块<br/>

[SpinLockDemo](lock/SpinLockDemo.java)<br/>
自旋锁：尝试获取锁的线程不会立即阻塞，还是采用循环的方式获取锁，好处是减小上下位的切换，缺点是会消耗CPU
底层是采用Unsafe类与CAS思想，通过CAS操作完成自旋锁

[ReadWriteLockDemo](lock/ReadWriteLockDemo.java)<br/>
读写锁:读锁是共享锁，写锁是独占锁，只有读读的时候可以共享

[CountDownLatchDemo](lock/CountDownLatchDemo.java)<br/>
只有当其他线程完成时，本线程才可以开始让一些线程阻塞直到另一些线程完成一系列操作后才被唤醒<br/>
countDownLatch有两个方法，一个await()，调用的线程会阻塞，其他线程调用countDown()将计数器减一。当计数器变成0时，被await()阻塞的线程会被唤起

[CyclicBarrierDemo](lock/CyclicBarrierDemo.java)<br/>
当一组线程到达一个屏障时被阻塞，直到最后一个线程到达屏障，所有被屏障拦截的线程会继续干活，线程进入屏障使用await()方法

[SemaphoreDemo](lock/SemaphoreDemo.java)<br/>
信号量：多个线程抢多个资源使用这个<br/>
当信号量资源变成1时，退化为synchronized
