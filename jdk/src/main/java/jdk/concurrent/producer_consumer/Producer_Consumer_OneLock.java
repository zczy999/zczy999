package jdk.concurrent.producer_consumer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock 实现生产者消费者模式
 */
public class Producer_Consumer_OneLock implements Producer_Consumer {

    public static void main(String[] args) {
        int threadNum = 2;
        Producer_Consumer_OneLock producer_consumer = new Producer_Consumer_OneLock();
        for (int i = 0; i < threadNum; i++) {
            new Thread(new Producer(producer_consumer)).start();
        }
        for (int i = 0; i < threadNum; i++) {
            new Thread(new Consumer(producer_consumer)).start();
        }


    }

    private int depth = 0; // Current nesting depth
    private final int MAX_DEPTH = 3; // Maximum nesting depth

    private Lock lock = new ReentrantLock(true);

    private Condition emptyCondition = lock.newCondition();
    private Condition fullCondition = lock.newCondition();

    @Override
    public void producer() throws InterruptedException {
        lock.lock();
        try {
            while (depth >= MAX_DEPTH) {
                fullCondition.await(); // Wait if depth is at max
            }
            System.out.print("(");
            depth++;
            emptyCondition.signalAll(); // Notify consumer that there's an unmatched '('
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void consumer() throws InterruptedException {
        lock.lock();
        try {
            while (depth <= 0) {
                emptyCondition.await(); // Wait if no unmatched '(' exists
            }
            System.out.print(")");
            depth--;
            fullCondition.signalAll(); // Notify producer that space is available
        } finally {
            lock.unlock();
        }
    }
}
