package yangGeJava;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Ethan
 * @date 2019/9/9 11:23
 */
public class ContainerNotSafeDemo {
    //集合类不安全的例子
    public static void main(String[] args) {
         //list
        List<String> list = new ArrayList<>();
        Vector<String> vector = new Vector<>();
        List<Object> synchronizedList = Collections.synchronizedList(new ArrayList<>());
        CopyOnWriteArrayList<String> writeArrayList = new CopyOnWriteArrayList<>();
        //set
        Set<String> set = new HashSet<>();
        Set<String> synchronizedSet = Collections.synchronizedSet(new HashSet<>());
        CopyOnWriteArraySet<String> writeArraySet = new CopyOnWriteArraySet<>();
        //map
        Map<String, String> map = new HashMap<>();
        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();
        for (int i = 0; i < 30; i++) {
            /**
             * java.util.ConcurrentModificationException产生了此问题，并发修改异常
             * 导致原因：并发修改导致，很多线程抢夺写资源，导致出现异常
             * 解决办法：1、使用Vector，其底层使用synchronized
             * 2、使用Collections.synchronizedList(new ArrayList<>());
             * 3、使用CopyOnWriteArrayList<>(),实现写时复制：
             *  CopyOnWriteArrayList底层使用volatile修饰一个数组，当在添加数据时，使用ReentrantLock锁锁住线程，copy一份与原先list相同的数据到数组中(使用Arrays.copyOf)，最后加入元素，使用setArray方法，将原来的list指向这个数组
             *  
             */
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list);
            }, String.valueOf(i)).start();
            new Thread(() -> {
                vector.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(vector);
            }, String.valueOf(i)).start();
            new Thread(() -> {
                synchronizedList.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(synchronizedList);
            }, "t4"+String.valueOf(i)).start();
            new Thread(() -> {
                writeArrayList.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(writeArrayList);
            }, "t4"+String.valueOf(i)).start();
        }
    }
}
