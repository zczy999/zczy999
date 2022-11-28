package jdk.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class CountDownLatchTest {
    static AtomicInteger ai = new AtomicInteger(0);
    static CountDownLatch countDownLatch = new CountDownLatch(3);

    public static void main(String[] args) throws InterruptedException {
//        CountDownLatch countDownLatch = new CountDownLatch(2);

        Thread a = new Thread(() -> {
            for(int i = 0; i < 5 ; i++) {
                ai.incrementAndGet(); //返回的增加1之后的值
            }
            countDownLatch.countDown();
        });
        Thread b = new Thread(() -> {
            for(int i = 0; i < 7 ; i++) {
                ai.incrementAndGet(); //返回的增加1之后的值
            }
            countDownLatch.countDown();
        });
        a.start();
        b.start();
//        Thread.sleep(100); //为了保障 a b线程执行完毕之后，才继续执行后续的代码、
        countDownLatch.await();
        System.out.println(ai.get()); // 获取ai的值
    }
}
