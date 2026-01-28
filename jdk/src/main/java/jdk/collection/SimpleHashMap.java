package jdk.collection;

/**
 * 简化版 HashMap 实现
 * <p>
 * 学习目标：
 * 1. 理解 hash() 扰动函数的作用
 * 2. 理解 put() 的完整流程
 * 3. 理解 resize() 扩容机制
 * <p>
 * 数据结构：数组 + 链表（简化版不实现红黑树）
 * <p>
 * table[]
 * ┌───┬───┬───┬───┬───┬───┬───┬───┐
 * │ 0 │ 1 │ 2 │ 3 │ 4 │ 5 │ 6 │ 7 │
 * └─┬─┴───┴─┬─┴───┴───┴─┬─┴───┴───┘
 * │       │           │
 * ▼       ▼           ▼
 * ┌───┐   ┌───┐       ┌───┐
 * │ A │   │ C │       │ E │
 * └─┬─┘   └───┘       └─┬─┘
 * ▼                   ▼
 * ┌───┐               ┌───┐
 * │ B │               │ F │
 * └───┘               └───┘
 */
public class SimpleHashMap<K, V> {

    // ==================== 核心参数 ====================

    /**
     * 默认初始容量：16（必须是2的幂）
     */
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // 16

    /**
     * 默认负载因子：0.75
     */
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /**
     * 最大容量
     */
    static final int MAXIMUM_CAPACITY = 1 << 30;

    // ==================== 内部节点 ====================

    /**
     * 链表节点（对应 JDK 中的 HashMap.Node）
     */
    static class Node<K, V> {
        final int hash;    // key 的 hash 值（缓存起来避免重复计算）
        final K key;
        V value;
        Node<K, V> next;   // 链表的下一个节点

        Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }
    }

    // ==================== 成员变量 ====================

    /**
     * 存储数据的数组（桶数组）
     */
    Node<K, V>[] table;

    /**
     * 当前元素数量
     */
    int size;

    /**
     * 扩容阈值 = capacity × loadFactor
     */
    int threshold;

    /**
     * 负载因子
     */
    final float loadFactor;

    // ==================== 构造方法 ====================

    public SimpleHashMap() {
        this.loadFactor = DEFAULT_LOAD_FACTOR;
    }

    public SimpleHashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    public SimpleHashMap(int initialCapacity, float loadFactor) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
        }
        if (initialCapacity > MAXIMUM_CAPACITY) {
            initialCapacity = MAXIMUM_CAPACITY;
        }
        if (loadFactor <= 0 || Float.isNaN(loadFactor)) {
            throw new IllegalArgumentException("Illegal load factor: " + loadFactor);
        }
        this.loadFactor = loadFactor;
        // tableSizeFor 确保容量是 2 的幂
        this.threshold = tableSizeFor(initialCapacity);
    }

    /**
     * 返回大于等于 cap 的最小 2 的幂
     * 例如：cap=10 → 返回 16，cap=17 → 返回 32
     * <p>
     * 原理：通过位运算将最高位 1 之后的所有位都变成 1，然后 +1
     * 例如：10 = 0000 1010
     * 0000 1111  （所有位变1）
     * 0001 0000  （+1 得到 16）
     */
    static int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    // ==================== 核心方法 ====================

    /**
     * 【任务1】hash 扰动函数
     * <p>
     * 为什么需要扰动？
     * - 直接用 hashCode() 作为下标会有问题
     * - 假设 table.length = 16，计算下标：index = hash & (16-1) = hash & 0x0F
     * - 这只用到了 hash 的低 4 位！高位完全被忽略了
     * <p>
     * 解决方案：让高位也参与运算
     * - 将 hashCode 的高 16 位与低 16 位异或
     * - 这样下标计算时，高位信息也能影响结果
     * <p>
     * 示例：
     * hashCode = 0b 1010_1010_1010_1010_0000_0000_0000_0101
     * 高16位    = 0b 0000_0000_0000_0000_1010_1010_1010_1010
     * 异或结果  = 0b 1010_1010_1010_1010_1010_1010_1010_1111
     * <p>
     * 原本下标 = 0101 & 1111 = 5
     * 扰动后   = 1111 & 1111 = 15  ← 高位影响了结果！
     */
    static int hash(Object key) {
        if (key == null) {
            return 0;
        }
        int h = key.hashCode();
        return h ^ (h >>> 16);
    }

    /**
     * 存入键值对
     */
    public V put(K key, V value) {
        return putVal(hash(key), key, value);
    }

    /**
     * 【任务2】put 的核心实现
     */
    V putVal(int hash, K key, V value) {
        Node<K, V>[] tab;
        Node<K, V> p;
        int n, i;

        // Step 1: 如果 table 为空，先初始化
        if ((tab = table) == null || (n = tab.length) == 0) {
            tab = resize();
            n = tab.length;
        }

        // Step 2: 计算桶下标，检查该位置是否为空
        // 关键公式：i = (n - 1) & hash
        // 这就是为什么容量必须是 2 的幂！等价于 hash % n，但位运算更快
        i = (n - 1) & hash;
        p = tab[i];

        if (p == null) {
            // 桶为空，直接放入新节点
            tab[i] = new Node<>(hash, key, value, null);
        } else {
            // 桶不为空，需要遍历链表
            Node<K, V> e = null;
            K k;

            // 检查第一个节点是否就是要找的 key
            if (p.hash == hash && ((k = p.key) == key || (key != null && key.equals(k)))) {
                e = p;
            } else {
                // 遍历链表查找
                while (true) {
                    if ((e = p.next) == null) {
                        // 到达链表末尾，插入新节点（JDK8 尾插法）
                        p.next = new Node<>(hash, key, value, null);
                        break;
                    }
                    // 找到相同的 key
                    if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k)))) {
                        break;
                    }
                    p = e;
                }
            }

            // 如果找到了已存在的 key，替换 value
            if (e != null) {
                V oldValue = e.value;
                e.value = value;
                return oldValue;
            }
        }

        // Step 3: 检查是否需要扩容
        if (++size > threshold) {
            resize();
        }
        return null;
    }

    /**
     * 获取值
     */
    public V get(Object key) {
        Node<K, V> e = getNode(hash(key), key);
        return e == null ? null : e.value;
    }

    /**
     * 查找节点
     */
    Node<K, V> getNode(int hash, Object key) {
        Node<K, V>[] tab;
        Node<K, V> first, e;
        int n;
        K k;

        if ((tab = table) != null && (n = tab.length) > 0 &&
                (first = tab[(n - 1) & hash]) != null) {

            // 检查第一个节点
            if (first.hash == hash &&
                    ((k = first.key) == key || (key != null && key.equals(k)))) {
                return first;
            }

            // 遍历链表
            if ((e = first.next) != null) {
                do {
                    if (e.hash == hash &&
                            ((k = e.key) == key || (key != null && key.equals(k)))) {
                        return e;
                    }
                } while ((e = e.next) != null);
            }
        }
        return null;
    }

    /**
     * 删除指定 key 的键值对
     */
    public V remove(Object key) {
        Node<K, V> e = removeNode(hash(key), key);
        return e == null ? null : e.value;
    }

    /**
     * 【任务2】删除节点的核心实现
     */
    Node<K, V> removeNode(int hash, Object key) {

        if (table == null) {
            return null;
        }

        int n = table.length;
        int i = (n - 1) & hash;
        Node<K, V> first = table[i];

        if (first == null) {
            return null;
        }

        // 情况1：头节点就是目标
        if (first.hash == hash && (first.key == key || key.equals(first.key))) {
            table[i] = first.next;
            size--;
            return first;
        }

        // 情况2：遍历链表查找
        Node<K, V> prev = first;
        Node<K, V> cur = first.next;

        while (cur != null) {
            if (cur.hash == hash && (cur.key == key || key.equals(cur.key))) {
                prev.next = cur.next;  // 删除 cur
                size--;
                return cur;
            }
            prev = cur;
            cur = cur.next;
        }

        // 没找到
        return null;
    }

    /**
     * 【任务3】扩容方法
     * <p>
     * 扩容时机：size > threshold（元素数量超过阈值）
     * 扩容策略：容量翻倍（newCap = oldCap << 1）
     * <p>
     * 重新分布元素：
     * - 旧容量：16，下标计算 hash & 0x0F (0000 1111)
     * - 新容量：32，下标计算 hash & 0x1F (0001 1111)
     * - 区别就在第 5 位（从右数）
     * <p>
     * 巧妙的优化（JDK8）：
     * - 如果 hash 的第 5 位是 0，新下标 = 旧下标
     * - 如果 hash 的第 5 位是 1，新下标 = 旧下标 + 旧容量
     * <p>
     * 示例：
     * hash = 0b ...0101 (5)   oldIndex = 5, newIndex = 5      (第5位是0)
     * hash = 0b ..10101 (21)  oldIndex = 5, newIndex = 5 + 16 = 21 (第5位是1)
     */
    @SuppressWarnings("unchecked")
    Node<K, V>[] resize() {
        Node<K, V>[] oldTab = table;
        int oldCap = (oldTab == null) ? 0 : oldTab.length;
        int oldThr = threshold;
        int newCap, newThr = 0;

        // 计算新容量和新阈值
        if (oldCap > 0) {
            if (oldCap >= MAXIMUM_CAPACITY) {
                threshold = Integer.MAX_VALUE;
                return oldTab;
            }
            // 容量翻倍
            newCap = oldCap << 1;
            if (newCap < MAXIMUM_CAPACITY && oldCap >= DEFAULT_INITIAL_CAPACITY) {
                newThr = oldThr << 1; // 阈值也翻倍
            }
        } else if (oldThr > 0) {
            // 初始化时指定了容量
            newCap = oldThr;
        } else {
            // 默认初始化
            newCap = DEFAULT_INITIAL_CAPACITY;
            newThr = (int) (DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
        }

        if (newThr == 0) {
            float ft = (float) newCap * loadFactor;
            newThr = (newCap < MAXIMUM_CAPACITY && ft < (float) MAXIMUM_CAPACITY ?
                    (int) ft : Integer.MAX_VALUE);
        }
        threshold = newThr;

        // 创建新数组
        Node<K, V>[] newTab = (Node<K, V>[]) new Node[newCap];
        table = newTab;

        // 迁移旧数据
        if (oldTab != null) {
            for (int j = 0; j < oldCap; ++j) {
                Node<K, V> e;
                if ((e = oldTab[j]) != null) {
                    oldTab[j] = null; // help GC

                    if (e.next == null) {
                        // 只有一个节点，直接计算新位置
                        newTab[e.hash & (newCap - 1)] = e;
                    } else {
                        // 链表需要拆分成两个链表
                        // loHead: 留在原位置的链表
                        // hiHead: 移动到 (原位置 + oldCap) 的链表
                        Node<K, V> loHead = null, loTail = null;
                        Node<K, V> hiHead = null, hiTail = null;
                        Node<K, V> next;

                        do {
                            next = e.next;
                            // 关键：用 hash & oldCap 判断应该放在哪个链表
                            // 如果结果是 0，留在原位置；否则移动到新位置
                            if ((e.hash & oldCap) == 0) {
                                if (loTail == null) {
                                    loHead = e;
                                } else {
                                    loTail.next = e;
                                }
                                loTail = e;
                            } else {
                                if (hiTail == null) {
                                    hiHead = e;
                                } else {
                                    hiTail.next = e;
                                }
                                hiTail = e;
                            }
                        } while ((e = next) != null);

                        if (loTail != null) {
                            loTail.next = null;
                            newTab[j] = loHead;
                        }
                        if (hiTail != null) {
                            hiTail.next = null;
                            newTab[j + oldCap] = hiHead;
                        }
                    }
                }
            }
        }
        return newTab;
    }

    // ==================== 辅助方法 ====================

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean containsKey(Object key) {
        return getNode(hash(key), key) != null;
    }

    /**
     * 打印内部结构（调试用）
     */
    public void printStructure() {
        System.out.println("========== HashMap 内部结构 ==========");
        System.out.println("容量: " + (table == null ? 0 : table.length));
        System.out.println("元素数量: " + size);
        System.out.println("扩容阈值: " + threshold);
        System.out.println();

        if (table != null) {
            for (int i = 0; i < table.length; i++) {
                Node<K, V> node = table[i];
                if (node != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("桶[").append(i).append("]: ");
                    while (node != null) {
                        sb.append(node).append(" → ");
                        node = node.next;
                    }
                    sb.append("null");
                    System.out.println(sb);
                }
            }
        }
        System.out.println("======================================");
    }

    // ==================== 测试 ====================

    public static void main(String[] args) {
        SimpleHashMap<String, Integer> map = new SimpleHashMap<>();

        // 测试 put
        map.put("apple", 1);
        map.put("banana", 2);
        map.put("cherry", 3);

        System.out.println("===== 初始状态 =====");
        map.printStructure();

        // 测试 get
        System.out.println("get('apple') = " + map.get("apple"));
        System.out.println("get('banana') = " + map.get("banana"));

        // 测试 remove
        System.out.println("\n===== 测试 remove =====");
        System.out.println("remove('banana') = " + map.remove("banana"));
        System.out.println("remove('notexist') = " + map.remove("notexist"));
        System.out.println("get('banana') after remove = " + map.get("banana"));

        System.out.println("\n===== 删除后状态 =====");
        map.printStructure();

        // 测试扩容
        System.out.println("\n===== 测试扩容 =====");
        for (int i = 0; i < 15; i++) {
            map.put("key" + i, i);
        }
        map.printStructure();
    }
}
