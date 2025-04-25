package jdk.concurrent.producer_consumer;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

/**
 * 状态分裂：emptySlots和fullSlots由不同的锁保护，无法保证它们的总和始终为3
 * 缺乏全局一致性：没有任何机制确保"已生产的左括号数"与"已消费的右括号数"的差值正确
 */
public class Producer_Consumer_DualLocks_ParenthesesMismatch implements Producer_Consumer {

    public static void main(String[] args) {
        int threadNum = 2;
        Producer_Consumer producer_consumer = new Producer_Consumer_DualLocks_ParenthesesMismatch();
        for (int i = 0; i < threadNum; i++) {
            new Thread(new Producer(producer_consumer)).start();
        }
        for (int i = 0; i < threadNum; i++) {
            new Thread(new Consumer(producer_consumer)).start();
        }
    }


    private int emptySlots = 3;   // 最大深度3
    private int fullSlots = 0;    // 初始可用的已匹配左括号数量

    // 两个独立的锁分别管理空槽和已填充槽
    private final Lock emptyLock = new ReentrantLock();
    private final Condition emptyCondition = emptyLock.newCondition();

    private final Lock fullLock = new ReentrantLock();
    private final Condition fullCondition = fullLock.newCondition();

    @Override
    public void producer() throws InterruptedException {
        // 获取一个空槽
        emptyLock.lock();
        try {
            while (emptySlots <= 0) {
                emptyCondition.await();
            }
            emptySlots--;
        } finally {
            emptyLock.unlock();
        }

        // 打印左括号
        System.out.print("(");

        // 增加一个填充槽
        fullLock.lock();
        try {
            fullSlots++;
            fullCondition.signal();
        } finally {
            fullLock.unlock();
        }
    }

    @Override
    public void consumer() throws InterruptedException {
        // 获取一个填充槽
        fullLock.lock();
        try {
            while (fullSlots <= 0) {
                fullCondition.await();
            }
            fullSlots--;
        } finally {
            fullLock.unlock();
        }

        // 打印右括号
        System.out.print(")");

        // 释放一个空槽
        emptyLock.lock();
        try {
            emptySlots++;
            emptyCondition.signal();
        } finally {
            emptyLock.unlock();
        }
    }
}
