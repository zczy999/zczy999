package jdk.collection;

import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 * 简化版 ConcurrentHashMap 实现（JDK 8 风格）
 * <p>
 * 学习目标：
 * 1. 理解 CAS 无锁插入空桶
 * 2. 理解 synchronized 锁桶头节点
 * 3. 理解与 HashMap 的区别
 * <p>
 * 简化点：
 * - 不实现红黑树转换
 * - 不实现扩容（固定容量）
 * <p>
 * JDK 8 的核心策略：
 * - 空桶：CAS 无锁插入
 * - 非空桶：synchronized 锁住头节点
 */
public class SimpleConcurrentHashMap<K, V> {

    // ==================== 核心参数 ====================

    /**
     * 默认容量
     */
    private static final int DEFAULT_CAPACITY = 16;

    // ==================== 内部节点 ====================

    static class Node<K, V> {
        final int hash;
        final K key;
        volatile V value;      // volatile 保证可见性
        volatile Node<K, V> next;

        Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    // ==================== 成员变量 ====================

    /**
     * 使用 AtomicReferenceArray 支持 CAS 操作
     * 相当于 volatile Node[] + CAS
     */
    private final AtomicReferenceArray<Node<K, V>> table;

    // ==================== 构造方法 ====================

    public SimpleConcurrentHashMap() {
        this(DEFAULT_CAPACITY);
    }

    public SimpleConcurrentHashMap(int capacity) {
        this.table = new AtomicReferenceArray<>(capacity);
    }

    // ==================== 核心方法 ====================

    /**
     * hash 扰动函数（与 HashMap 类似）
     */
    static int spread(int h) {
        return (h ^ (h >>> 16)) & 0x7fffffff;  // 保证非负
    }

    /**
     * put 方法 - 体现 CAS + synchronized 的并发策略
     */
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new NullPointerException();
        }

        int hash = spread(key.hashCode());
        int index = hash & (table.length() - 1);

        for (; ; ) {
            Node<K, V> curNode = table.get(index);
            if (curNode == null) {
                // 情况1：空桶，CAS 插入
                Node<K, V> newNode = new Node<>(hash, key, value, null);
                if (table.compareAndSet(index, null, newNode)) {
                    return null;  // 插入成功
                }
                // CAS 失败，继续自旋
            } else {
                // 情况2：非空桶，synchronized 锁住头节点
                synchronized (curNode) {
                    // 双重检查
                    if (table.get(index) == curNode) {
                        // 检查头节点是否就是目标 key
                        if (curNode.hash == hash && curNode.key.equals(key)) {
                            V oldValue = curNode.value;
                            curNode.value = value;
                            return oldValue;
                        }
                        // 遍历链表
                        Node<K, V> prev = curNode;
                        Node<K, V> node = curNode.next;
                        while (node != null) {
                            if (node.hash == hash && node.key.equals(key)) {
                                V oldValue = node.value;
                                node.value = value;
                                return oldValue;  // 更新成功
                            }
                            prev = node;
                            node = node.next;
                        }
                        // 没找到，尾插新节点
                        prev.next = new Node<>(hash, key, value, null);
                        return null;  // 插入成功
                    }
                    // 双重检查失败，继续自旋
                }
            }
        }

    }

    /**
     * get 方法 - 无锁读取
     * <p>
     * 为什么 get 不需要加锁？
     * - Node 的 value 和 next 都是 volatile
     * - volatile 保证了可见性，能读到最新值
     * - 即使读取过程中有写入，也不会读到"半成品"
     */
    public V get(Object key) {
        int hash = spread(key.hashCode());
        int index = hash & (table.length() - 1);  // 位运算比 % 更快

        Node<K, V> node = table.get(index);

        while (node != null) {
            if (node.hash == hash && node.key.equals(key)) {
                return node.value;
            }
            node = node.next;
        }
        return null;
    }

    /**
     * 打印内部结构（调试用）
     */
    public void printStructure() {
        System.out.println("========== ConcurrentHashMap 内部结构 ==========");
        System.out.println("容量: " + table.length());

        for (int i = 0; i < table.length(); i++) {
            Node<K, V> node = table.get(i);
            if (node != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("桶[").append(i).append("]: ");
                while (node != null) {
                    sb.append(node.key).append("=").append(node.value).append(" → ");
                    node = node.next;
                }
                sb.append("null");
                System.out.println(sb);
            }
        }
        System.out.println("================================================");
    }

    // ==================== 测试 ====================

    public static void main(String[] args) throws InterruptedException {
        SimpleConcurrentHashMap<String, Integer> map = new SimpleConcurrentHashMap<>();

        // 单线程测试
        System.out.println("===== 单线程测试 =====");
        map.put("apple", 1);
        map.put("banana", 2);
        map.put("cherry", 3);
        map.printStructure();

        System.out.println("get('apple') = " + map.get("apple"));
        System.out.println("get('banana') = " + map.get("banana"));

        // 多线程测试
        System.out.println("\n===== 多线程测试 =====");
        SimpleConcurrentHashMap<Integer, Integer> concurrentMap = new SimpleConcurrentHashMap<>();

        int threadCount = 4;
        int itemsPerThread = 100;
        Thread[] threads = new Thread[threadCount];

        for (int t = 0; t < threadCount; t++) {
            final int threadId = t;
            threads[t] = new Thread(() -> {
                for (int i = 0; i < itemsPerThread; i++) {
                    int key = threadId * itemsPerThread + i;
                    concurrentMap.put(key, key * 10);
                }
            });
        }

        long start = System.currentTimeMillis();
        for (Thread thread : threads) thread.start();
        for (Thread thread : threads) thread.join();
        long end = System.currentTimeMillis();

        System.out.println("插入 " + (threadCount * itemsPerThread) + " 个元素");
        System.out.println("耗时: " + (end - start) + " ms");

        // 验证数据正确性
        int count = 0;
        for (int i = 0; i < threadCount * itemsPerThread; i++) {
            if (concurrentMap.get(i) != null) count++;
        }
        System.out.println("成功读取: " + count + " 个元素");
    }
}
