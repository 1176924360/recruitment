package yangGeJava;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Ethan
 * @date 2019/9/9 14:53
 *
 * 读写锁
 * 读锁是共享锁，写锁是独占锁，只有读读的时候可以共享
 */

class  MyCache{
    //使map内存可见
    private volatile Map<String,Object> map = new HashMap<>();
    private ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    public void put(String key, Object value){
        reentrantReadWriteLock.writeLock().lock();
        try{
            System.out.println(Thread.currentThread().getName()+"\t 正在写入：" + key);
            TimeUnit.MILLISECONDS.sleep(100);
            map.put(key, value);
            System.out.println(Thread.currentThread().getName()+"\t 写入完成");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            reentrantReadWriteLock.writeLock().unlock();
        }
    }

    public void get(String key){
        reentrantReadWriteLock.readLock().lock();
        try{
            System.out.println(Thread.currentThread().getName()+"\t 正在读取：" + key);
            TimeUnit.MILLISECONDS.sleep(100);
            Object values = map.get(key);
            System.out.println(Thread.currentThread().getName()+"\t 读取完成：" + values);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            reentrantReadWriteLock.readLock().unlock();
        }
    }
}

public class ReadWriteLockDemo {
    public static void main(String[] args) {
        MyCache myCache = new MyCache();
        for (int i = 0; i < 5; i++) {
            final int temp = i;
            new Thread(() -> {
                myCache.put(temp + "", temp + "");
            }, String.valueOf(i)).start();
        }
        for (int i = 0; i < 5; i++) {
            final int temp = i;
            new Thread(() ->{
                myCache.get(temp+"");
            }, String.valueOf(i)).start();
        }
    }
}
