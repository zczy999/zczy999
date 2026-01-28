# JDK 集合框架源码学习

> 遵循 28 原则，掌握核心 20%，覆盖 80% 使用场景

## 📚 整体架构

```
                          Collection                              Map
                              │                                    │
          ┌───────────────────┼───────────────────┐                │
          ▼                   ▼                   ▼                ▼
        List                 Set                Queue             Map
          │                   │                   │                │
    ┌─────┴─────┐         ┌───┴───┐               │         ┌──────┴──────┐
    ▼           ▼         ▼       ▼               ▼         ▼             ▼
⭐ArrayList  LinkedList  HashSet  TreeSet   PriorityQueue  ⭐HashMap   TreeMap
  (核心1)     (核心4)      │                               (核心2)
                           │
                           └──── 底层是 HashMap

                    ⭐ConcurrentHashMap (核心3) - 线程安全的 HashMap
```

## 📁 文件说明

| 文件 | 说明 |
|------|------|
| `SimpleHashMap.java` | HashMap 简化实现：hash扰动、put/get、扩容、删除 |
| `SimpleArrayList.java` | ArrayList 简化实现：动态数组、1.5倍扩容、元素移动 |
| `SimpleConcurrentHashMap.java` | ConcurrentHashMap 简化实现：CAS + synchronized |
| `SimpleLinkedList.java` | LinkedList 简化实现：双向链表、头尾O(1)操作 |

---

## 一、HashMap

### 1.1 数据结构

```
JDK 8: 数组 + 链表 + 红黑树

table[]
┌───┬───┬───┬───┬───┬───┬───┬───┐
│ 0 │ 1 │ 2 │ 3 │ 4 │ 5 │ 6 │ 7 │
└─┬─┴───┴─┬─┴───┴───┴─┬─┴───┴───┘
  │       │           │
  ▼       ▼           ▼
┌───┐   ┌───┐      ┌─────┐
│ A │   │ C │      │ Red │  ← 链表长度≥8 且 容量≥64 时转红黑树
└─┬─┘   └─┬─┘      │Black│
  ▼       ▼        │Tree │
┌───┐   ┌───┐      └─────┘
│ B │   │ D │
└───┘   └───┘
```

### 1.2 核心参数

| 参数 | 默认值 | 含义 |
|------|--------|------|
| `DEFAULT_INITIAL_CAPACITY` | 16 | 初始容量（必须是2的幂） |
| `DEFAULT_LOAD_FACTOR` | 0.75 | 负载因子 |
| `TREEIFY_THRESHOLD` | 8 | 链表转红黑树阈值 |
| `UNTREEIFY_THRESHOLD` | 6 | 红黑树退化为链表阈值 |
| `MIN_TREEIFY_CAPACITY` | 64 | 树化最小容量 |

### 1.3 核心源码

#### hash 扰动函数

```java
static int hash(Object key) {
    if (key == null) {
        return 0;
    }
    int h = key.hashCode();
    return h ^ (h >>> 16);  // 高16位与低16位异或
}
```

**为什么需要扰动？**
- 计算下标：`index = hash & (n-1)`，只用到低位
- 扰动让高位信息也参与运算，减少哈希冲突

**为什么用异或？**
- AND 倾向于产生 0（信息丢失）
- OR 倾向于产生 1（信息饱和）
- XOR 结果均匀分布，保留两边信息

#### 为什么容量必须是 2 的幂？

```
hash & (n-1) 等价于 hash % n，但位运算更快

n = 16 = 0001 0000
n-1 = 15 = 0000 1111  ← 低位全是 1

hash & 0000 1111 = 保留 hash 的低 4 位 = hash % 16
```

### 1.4 JDK 7 vs JDK 8

| 对比项 | JDK 7 | JDK 8 |
|--------|-------|-------|
| 数据结构 | 数组 + 链表 | 数组 + 链表 + 红黑树 |
| 插入方式 | 头插法 | 尾插法 |
| 扩容转移 | 重新计算 hash | 高低位拆分（更高效） |
| hash 扰动 | 4 次扰动 | 1 次扰动 |
| 并发问题 | 头插法可能死循环 ⚠️ | 尾插法不会死循环 |

---

## 二、ArrayList

### 2.1 数据结构

```
elementData[] (Object 数组)
┌─────┬─────┬─────┬─────┬─────┬─────┬─────┬─────┬─────┬─────┐
│  A  │  B  │  C  │  D  │  E  │null │null │null │null │null │
└─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┘
  0     1     2     3     4     5     6     7     8     9
└─────────────────────────┘└─────────────────────────────────┘
         size = 5                      空闲空间
└────────────────────────────────────────────────────────────┘
                     capacity = 10
```

### 2.2 扩容机制

```java
private void grow(int minCapacity) {
    int oldCapacity = elementData.length;
    int newCapacity = oldCapacity + (oldCapacity >> 1);  // 1.5 倍
    if (newCapacity - minCapacity < 0) {
        newCapacity = minCapacity;  // 保底
    }
    elementData = Arrays.copyOf(elementData, newCapacity);
}
```

**扩容序列**：10 → 15 → 22 → 33 → 49 → 73 → ...

**为什么是 1.5 倍？**
- 2 倍：空间浪费最多 50%
- 1.5 倍：空间浪费最多 33%，平衡时间和空间

### 2.3 时间复杂度

| 操作 | 时间复杂度 | 说明 |
|------|-----------|------|
| get(index) | O(1) | 数组随机访问 |
| add(E) | O(1)* | 尾部添加（均摊） |
| add(index, E) | O(n) | 需要移动后面的元素 |
| remove(index) | O(n) | 需要移动后面的元素 |

---

## 三、ConcurrentHashMap

### 3.1 JDK 7 vs JDK 8 架构

```
JDK 7: 分段锁（Segment）
┌─────────┬─────────┬─────────┬─────────┐
│ Seg[0]  │ Seg[1]  │ Seg[2]  │ Seg[3]  │  每个 Segment 独立加锁
│   🔒    │   🔒    │   🔒    │   🔒    │  并发度 = Segment 数量（默认16）
└─────────┴─────────┴─────────┴─────────┘

JDK 8: CAS + synchronized（锁桶头节点）
┌───┬───┬───┬───┬───┬───┬───┬───┐
│🔒 │   │🔒 │   │   │🔒 │   │   │  只锁冲突的桶
└───┴───┴───┴───┴───┴───┴───┴───┘  并发度 = 桶数量
```

### 3.2 核心策略

```java
public V put(K key, V value) {
    int hash = spread(key.hashCode());
    int index = hash & (table.length() - 1);

    for (;;) {  // 自旋
        Node<K,V> f = table.get(index);

        if (f == null) {
            // 空桶：CAS 无锁插入（快速路径）
            if (table.compareAndSet(index, null, newNode)) {
                return null;
            }
            // CAS 失败，继续自旋
        } else {
            // 非空桶：synchronized 锁住头节点
            synchronized (f) {
                if (table.get(index) == f) {  // 双重检查
                    // 遍历链表，插入或更新
                }
            }
        }
    }
}
```

### 3.3 为什么 get() 不需要加锁？

```java
static class Node<K,V> {
    final int hash;
    final K key;
    volatile V value;      // ← volatile 保证可见性
    volatile Node<K,V> next;
}
```

- Node 的 value 和 next 都是 volatile
- volatile 保证读操作能看到最新值
- 不会读到"半成品"数据

### 3.4 双重检查模式

```java
if (condition) {                    // 第一次检查（无锁）
    synchronized (lock) {
        if (condition) {            // 第二次检查（有锁）
            // 执行操作
        }
    }
}
```

**应用场景**：
- 单例模式（DCL）
- ConcurrentHashMap 的 put
- 缓存失效重建
- 懒加载资源

---

## 四、LinkedList

### 4.1 数据结构：双向链表

```
  first                                        last
    │                                            │
    ▼                                            ▼
 ┌──────┐     ┌──────┐     ┌──────┐     ┌──────┐
 │ null │ ←── │ prev │ ←── │ prev │ ←── │ prev │
 │  A   │     │  B   │     │  C   │     │  D   │
 │ next │ ──→ │ next │ ──→ │ next │ ──→ │ null │
 └──────┘     └──────┘     └──────┘     └──────┘
```

### 4.2 核心操作

```java
// 头部插入 O(1)
private void linkFirst(E e) {
    final Node<E> f = first;
    final Node<E> newNode = new Node<>(null, e, f);
    first = newNode;
    if (f == null) {
        last = newNode;
    } else {
        f.prev = newNode;
    }
    size++;
}

// get(index) 优化：判断前半/后半
Node<E> node(int index) {
    if (index < (size >> 1)) {
        // 前半：从 first 正向遍历
    } else {
        // 后半：从 last 反向遍历
    }
}
```

### 4.3 ArrayList vs LinkedList

| 操作 | ArrayList | LinkedList |
|------|-----------|------------|
| get(index) | O(1) ✅ | O(n) ❌ |
| add(尾部) | O(1)* | O(1) ✅ |
| add(头部) | O(n) ❌ | O(1) ✅ |
| remove(头部) | O(n) ❌ | O(1) ✅ |
| 内存占用 | 小 ✅ | 大 ❌ |

**结论**：
- 大多数场景用 ArrayList（随机访问多）
- 频繁头部插入/删除用 LinkedList（如队列）

---

## 五、核心面试题

### HashMap

**Q1: HashMap 的 put 流程？**

1. 计算 hash：`h ^ (h >>> 16)`
2. 如果 table 为空，resize() 初始化
3. 计算桶下标：`(n-1) & hash`
4. 如果桶为空，直接放入新节点
5. 如果桶不为空：
   - 第一个节点 key 相同 → 更新 value
   - 是红黑树 → 树节点插入
   - 是链表 → 遍历，找到相同 key 则更新，否则尾插
6. 链表长度 ≥ 8 且容量 ≥ 64 → 转红黑树
7. 如果 size > threshold，resize() 扩容

**Q2: 为什么容量必须是 2 的幂？**

让 `hash & (n-1)` 等价于 `hash % n`，位运算比取模快 20-40 倍。

**Q3: JDK 7 的 HashMap 多线程有什么问题？**

头插法扩容时可能形成环形链表，导致 get() 死循环。JDK 8 改用尾插法解决了这个问题。

**Q4: HashMap 允许 null key 吗？**

允许。null key 的 hash 值固定为 0，永远放在 table[0] 位置。

### ConcurrentHashMap

**Q5: ConcurrentHashMap 如何保证线程安全？**

JDK 8 采用 CAS + synchronized：
- 空桶：CAS 无锁插入
- 非空桶：synchronized 锁住头节点
- Node 的 value 和 next 都是 volatile，保证可见性

**Q6: ConcurrentHashMap 的 get() 需要加锁吗？**

不需要。因为 Node 的 value 和 next 都是 volatile，保证了可见性。

**Q7: ConcurrentHashMap 允许 null key/value 吗？**

都不允许。因为无法区分"key 不存在"和"value 是 null"。

**Q8: JDK 7 和 JDK 8 的 ConcurrentHashMap 有什么区别？**

| 对比项 | JDK 7 | JDK 8 |
|--------|-------|-------|
| 锁机制 | ReentrantLock（分段锁） | CAS + synchronized |
| 锁粒度 | Segment（多个桶） | 单个桶 |
| 并发度 | 固定（默认16） | 动态（桶数量） |
| 数据结构 | 数组+链表 | 数组+链表+红黑树 |

### ArrayList

**Q9: ArrayList 的扩容机制？**

新容量 = 旧容量 × 1.5（`oldCapacity + (oldCapacity >> 1)`）

**Q10: ArrayList 和 Vector 的区别？**

| 对比项 | ArrayList | Vector |
|--------|-----------|--------|
| 线程安全 | 否 | 是（synchronized） |
| 扩容倍数 | 1.5 倍 | 2 倍 |
| 性能 | 高 | 低 |

### LinkedList

**Q11: ArrayList 和 LinkedList 如何选择？**

- 随机访问多 → ArrayList（O(1) vs O(n)）
- 频繁头部插入/删除 → LinkedList（O(1) vs O(n)）
- 内存敏感 → ArrayList（节点开销小）

**Q12: LinkedList 是单向还是双向链表？**

双向链表。每个节点有 prev 和 next 指针，支持双向遍历。好处：
- 删除节点不需要从头遍历找前驱
- get(index) 可以根据 index 在前半/后半选择从头或尾遍历

---

## 六、运行演示

```bash
# 编译
javac -d target src/main/java/jdk/collection/*.java

# 运行 HashMap
java -cp target jdk.collection.SimpleHashMap

# 运行 ArrayList
java -cp target jdk.collection.SimpleArrayList

# 运行 ConcurrentHashMap
java -cp target jdk.collection.SimpleConcurrentHashMap

# 运行 LinkedList
java -cp target jdk.collection.SimpleLinkedList
```

---

## 七、学习路线建议

```
1️⃣ HashMap      → 面试必问，理解哈希表核心原理
2️⃣ ArrayList    → 最常用，理解动态数组扩容
3️⃣ ConcurrentHashMap → 并发场景必备
4️⃣ LinkedList   → 对比 ArrayList 理解更深刻
```

掌握这 4 个，覆盖 80% 的使用场景和面试问题！
