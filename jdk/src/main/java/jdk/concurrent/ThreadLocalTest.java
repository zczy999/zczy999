package jdk.concurrent;

import java.util.concurrent.atomic.AtomicInteger;

public class ThreadLocalTest {
    // 创建一个 ThreadLocal 对象，用于在每个线程的 ThreadLocalMap 中存储线程 ID
    private static final ThreadLocal<Integer> threadId = new ThreadLocal<>();

    // 线程 ID 计数器
    private static AtomicInteger idCounter = new AtomicInteger(0);

    // 获取当前线程的 ID
    public static int getThreadId() {
        // 从当前线程的 ThreadLocalMap 中获取线程 ID
        Integer id = threadId.get();

        // 如果线程 ID 不存在，生成一个新的 ID 并将其存储在 ThreadLocalMap 中
        if (id == null) {
            id = idCounter.getAndIncrement();
            threadId.set(id);
        }

        return id;
    }

    public static void main(String[] args) {
        Runnable task = () -> {
            System.out.println("Thread " + Thread.currentThread().getName() + " ID: " + getThreadId());
        };

        // 创建并启动多个线程
        for (int i = 0; i < 5; i++) {
            new Thread(task).start();
        }
    }
}
