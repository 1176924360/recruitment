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
[demo1](volatile/MyData01.java)
