package yangGeJava;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Ethan
 * @date 2019/9/9 10:39
 *
 * CAS会导致ABA问题
 * 需要使用原子引用更新
 * 使用AtomicStampedReference版本号原子引用
 */
class User{
    User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    String name;
    int age;
}
public class AtomicReferenceDemo {
    public static void main(String[] args) {
        AtomicReference<User> atf = new AtomicReference<>();
        User u1 = new User("z3", 10);
        User u2 = new User("li4", 14);
        atf.set(u1);
        System.out.println(atf.compareAndSet(u1, u2)+"\t"+atf.get().toString());
    }
}
