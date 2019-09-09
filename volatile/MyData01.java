package yangGeJava;

import java.util.concurrent.TimeUnit;

/**
 * @author Ethan
 * @date 2019/9/9 9:18
 */

class MyData01{
    //当number不被修饰为volatile，其对内存不可见，当有两个线程时(一个main线程，一个AAA线程)，AAA线程将number
    //变成60后，main线程无法得知此信息
    int number = 0;
    public void addTo60(){
        number = 60;
    }
}

public class VolatileDemo {
    public static void main(String[] args) {
        final MyData myData = new MyData();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t come in");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myData.addTo60();
            System.out.println(Thread.currentThread().getName() + "\t update number value:" + myData.number);
        }, "AAA").start();

        while (myData.number == 0){

        }

        System.out.println(Thread.currentThread().getName() + "\t mission is over");

//        for (int i = 0; i < 20; i++) {
//            new Thread(() -> {
//                for (int j = 0; j < 10; j++) {
//                    myData.addPlus();
//                }
//            }, String.valueOf(i)).start();
//            System.out.println(Thread.currentThread().getName() + "\t finally number value:" + myData.number);
//        }
//        System.out.println(Thread.currentThread().getName() + "\t finally number value:" + myData.number);
    }
}
