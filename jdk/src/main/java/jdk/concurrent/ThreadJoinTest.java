package jdk.concurrent;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 例子用于理解join
 */
public class ThreadJoinTest {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " run start");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " run end");
        },"new thread");
        thread.start();
        thread.join();
        //new thread线程关闭前会由jvm调用notifyAll方法，使主线程在等待中恢复
        System.out.println(Thread.currentThread().getName() + " run end");
    }
}
