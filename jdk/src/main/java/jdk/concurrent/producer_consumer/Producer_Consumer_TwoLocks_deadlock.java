package jdk.concurrent.producer_consumer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * 嵌套锁获取导致的环路等待
 *
 */
public class Producer_Consumer_TwoLocks_deadlock implements Producer_Consumer {

    public static void main(String[] args) {
        int threadNum = 2;
        Producer_Consumer producer_consumer = new Producer_Consumer_TwoLocks_deadlock();
        for (int i = 0; i < threadNum; i++) {
            new Thread(new Producer(producer_consumer)).start();
        }
        for (int i = 0; i < threadNum; i++) {
            new Thread(new Consumer(producer_consumer)).start();
        }
    }

    private final int MAX_DEPTH = 3;
    private volatile int depth = 0;

    private final Lock producerLock = new ReentrantLock();
    private final Condition producerCondition = producerLock.newCondition();

    private final Lock consumerLock = new ReentrantLock();
    private final Condition consumerCondition = consumerLock.newCondition();

    @Override
    public void producer() throws InterruptedException {
        producerLock.lock();
        try {
            while (depth >= MAX_DEPTH) {
                producerCondition.await();
            }

            depth++;
            System.out.print("(");

            // 通知消费者有新的括号可以消费
            signalConsumers();
        } finally {
            producerLock.unlock();
        }
    }

    @Override
    public void consumer() throws InterruptedException {
        consumerLock.lock();
        try {
            while (depth <= 0) {
                consumerCondition.await();
            }

            depth--;
            System.out.print(")");

            // 通知生产者有新的空间可用
            signalProducers();
        } finally {
            consumerLock.unlock();
        }
    }

    private void signalConsumers() {
        consumerLock.lock();
        try {
            consumerCondition.signalAll();
        } finally {
            consumerLock.unlock();
        }
    }

    private void signalProducers() {
        producerLock.lock();
        try {
            producerCondition.signalAll();
        } finally {
            producerLock.unlock();
        }
    }
}