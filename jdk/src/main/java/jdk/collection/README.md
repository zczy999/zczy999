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

---

## 八、HashMap 深度复习

### 8.1 容量与懒加载

| 创建方式 | 构造后 table | 构造后 threshold | 首次 put 后 length |
|---------|-------------|----------------|------------------|
| `new HashMap<>()` | null | 0 | 16 |
| `new HashMap<>(7)` | null | 8 | 8 |
| `new HashMap<>(16)` | null | 16 | 16 |
| `new HashMap<>(17)` | null | 32 | 32 |

**关键点**：
- HashMap **懒加载**：构造时不创建数组，第一次 put 才分配
- `threshold` 暂存"容量"，首次 resize 才修正为 `容量 × loadFactor`
- 阿里规约推荐：`new HashMap<>(expectedSize / 0.75 + 1)` 避免无谓扩容

### 8.2 自定义对象做 Key 的陷阱

```java
class User { String name; int age; }  // 不重写 hashCode/equals
HashMap<User, String> map = new HashMap<>();
User u1 = new User("Tom", 20);
map.put(u1, "A");

map.get(u1);                       // "A" ✓ (同一引用)
map.get(new User("Tom", 20));      // null ❌ (新对象 hashCode 不同)

u1.age = 30;
map.get(u1);                       // "A" (默认 hashCode 不依赖字段)
```

**结论**：
1. HashMap 的 key 必须**协同重写** hashCode 和 equals
2. HashMap 的 key 应该是**不可变**的（推荐 String、Integer 等）
3. 修改 key 字段会导致"找不到 value"（隐藏 bug）

### 8.3 扩容时的高低位拆分

JDK 8 扩容核心优化：**不重新计算 hash，只测一位 bit**。

```
oldCap = 16 = 0001 0000
       (e.hash & oldCap) == 0 → 低位链表，留在原位置
       (e.hash & oldCap) != 0 → 高位链表，移到 原位置 + oldCap
```

**示例**：原 tab[10] 上的链表（hash 低 4 位都是 1010）：

| Node | hash 二进制 | (hash & oldCap) | 新位置 |
|------|------------|-----------------|--------|
| A | `0001 1010` | 16 | tab[26] |
| B | `0010 1010` | 0 | tab[10] |
| C | `0011 1010` | 16 | tab[26] |
| D | `0100 1010` | 0 | tab[10] |

**原理**：扩容后 `n-1` 的 mask 多了一位 bit，这位 bit 正好是 `oldCap`。

### 8.4 负载因子 0.75 的依据

| 负载因子 | 查询冲突 | 内存浪费 |
|---------|---------|---------|
| 0.5 | 少 | 50% |
| 0.75 | 平衡 ⭐ | 33% |
| 1.0 | 多 | 0% |

**泊松分布依据**：负载因子 0.75 时，桶中节点数符合 λ=0.5 的泊松分布：

```
0 个节点: 60.65%
1 个节点: 30.33%
2 个节点: 7.58%
...
8 个节点: 0.00000006%   ← 几乎不可能！
```

→ 这就是为什么**树化阈值是 8**（防御性，正常情况几乎不可能触发）

### 8.5 LRU 缓存设计

```java
class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private final int capacity;
    public LRUCache(int capacity) {
        super(capacity, 0.75f, true);  // ★ accessOrder = true
        this.capacity = capacity;
    }
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > capacity;
    }
}
```

**为什么需要双向链表？**
- get 后要把节点移到尾部 → 删除中间节点
- 删除中间节点要 O(1) 拿到前驱 → **双向链表必需**（单向链表找前驱要 O(n)）

---

## 九、ArrayList 深度复习

### 9.1 三个生命周期阶段

```
new ArrayList<>()
    │
    │  懒加载：elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA (大小 0)
    ▼
第一次 add(e)
    │
    │  扩容到 DEFAULT_CAPACITY = 10
    ▼
size > capacity
    │
    │  newCap = oldCap + (oldCap >> 1)  (1.5 倍)
    ▼
扩容序列：10 → 15 → 22 → 33 → 49 → 73 → 109 → ...
```

**为什么 1.5 倍而不是 2 倍？**
- 累计释放的旧空间可以容纳下次扩容的新空间（内存复用友好）
- 空间浪费最多 33%（2 倍是 50%）

### 9.2 transient elementData 的序列化

```java
transient Object[] elementData;  // ★ transient
```

ArrayList 重写 `writeObject` / `readObject`，**只序列化 [0, size) 范围**：

```java
private void writeObject(ObjectOutputStream s) throws IOException {
    s.defaultWriteObject();
    s.writeInt(size);
    for (int i = 0; i < size; i++) {
        s.writeObject(elementData[i]);  // 不写 null 部分
    }
}
```

**收益**：容量远大于 size 时，避免序列化大量 null，节省网络/存储空间。

### 9.3 fail-fast 原理

```java
public abstract class AbstractList<E> {
    protected transient int modCount = 0;  // 修改计数器
}

private class Itr implements Iterator<E> {
    int expectedModCount = modCount;  // 创建时拷贝快照

    public E next() {
        if (modCount != expectedModCount)
            throw new ConcurrentModificationException();
        // ...
    }
}
```

**注意**：
- fail-fast **不是线程安全机制**，只是 bug 检测
- `iterator.remove()` 会同步更新 `expectedModCount`，所以不抛异常
- 推荐使用 `list.removeIf(...)` 替代手动迭代删除

### 9.4 Help GC 详解

```java
private void fastRemove(int index) {
    int numMoved = size - index - 1;
    if (numMoved > 0)
        System.arraycopy(elementData, index+1, elementData, index, numMoved);
    elementData[--size] = null;  // ★ Help GC
}
```

**为什么必须置 null？**

```
删除前: [A, B, C, D, E, null, null], size=5
arraycopy 后: [A, B, D, E, E, null, null]  ← E 出现两次！
置 null 后: [A, B, D, E, null, null, null], size=4
```

不置 null 的后果：数组仍引用对象 → **对象无法被 GC → 内存泄漏**。

**Effective Java 第 7 条**：消除过期对象引用（早期 JDK 的 `Stack.pop()` 就有这个 bug）

### 9.5 removeIf 的 O(n) 性能优化

| 方式 | 复杂度 | 10 万元素耗时 |
|------|--------|--------------|
| `iterator.remove()` | O(n²) | ~2000 ms |
| `list.removeIf(...)` | **O(n)** ⭐ | **~10 ms** |
| 倒序 `remove(i)` | O(n²) | ~800 ms |

**removeIf 实现**：用 BitSet 标记 → 一次性 compact → 置 null 部分 GC（两遍扫描 + 一次置 null）

### 9.6 ArrayList vs LinkedList 实战选型

| 场景 | 推荐 | 原因 |
|------|------|------|
| 队列（FIFO） | **ArrayDeque** | 循环数组，CPU 缓存友好 |
| 栈（LIFO） | **ArrayDeque** | 不要用 Stack（Vector 的锅） |
| 随机访问多 | ArrayList | O(1) vs O(n) |
| 频繁头尾操作 | ArrayDeque | 不是 LinkedList |
| LRU 缓存 | LinkedHashMap | 自带双向链表 |

**Doug Lea 的话**："In practice, LinkedList is almost never the right choice."

### 9.7 常见陷阱速查

| 陷阱 | 表现 | 解决方案 |
|------|------|---------|
| `Arrays.asList()` | 不能 add/remove，抛 UnsupportedOperationException | `new ArrayList<>(Arrays.asList(...))` |
| `subList()` | 是视图，修改会影响原 list；父结构性修改后 sub 抛 CME | 用 `new ArrayList<>(list.subList(...))` 拷贝 |
| `toArray()` 强转 | `(String[]) list.toArray()` 抛 ClassCastException | 用 `list.toArray(new String[0])` |
| `int[]` 传 asList | `Arrays.asList(int[])` size 是 1 | 用 `Arrays.stream(arr).boxed().collect(...)` |

---

## 十、ConcurrentHashMap 深度复习

### 10.1 并发策略演进

| 维度 | JDK 7 | JDK 8 |
|------|-------|-------|
| 锁机制 | ReentrantLock 分段锁 | CAS + synchronized |
| 锁粒度 | Segment（多个桶共享） | **单个桶头节点** |
| 并发度 | 固定 = Segment 数（默认 16） | **动态 = 桶数量** |
| 数据结构 | Segment[] + HashEntry[] + 链表 | Node[] + 链表 + 红黑树 |

**为什么 JDK 8 选 synchronized 而不是 ReentrantLock？**
- JDK 6 之后 synchronized 优化（偏向锁、轻量级锁）性能大幅提升
- 内存开销 0（直接复用 Node 对象头），ReentrantLock 每把锁约 48 字节
- 桶数量多时差异显著（16384 桶 × 48B ≈ 768KB 节省）

### 10.2 put 的分情况策略

```java
if ((f = tabAt(tab, i = (n - 1) & hash)) == null) {
    // ① 空桶 → CAS 无锁插入
    if (casTabAt(tab, i, null, new Node<>(hash, key, value, null))) break;
}
else if ((fh = f.hash) == MOVED) {
    // ② 扩容中 → 协助扩容
    tab = helpTransfer(tab, f);
}
else {
    // ③ 非空桶 → synchronized 锁头节点
    synchronized (f) {
        // 遍历链表/树，插入或更新
    }
}
```

**核心思想**：根据冲突概率选最优策略。空桶（高频）CAS 无锁，冲突桶（低频）才上锁。

### 10.3 get 为什么无锁安全？

```java
static class Node<K,V> {
    final int hash;       // ★ final，安全发布
    final K key;          // ★ final
    volatile V val;       // ★ volatile，保证可见性
    volatile Node<K,V> next;
}
```

**三大保证**：
1. `final` 字段：JMM 保证构造完成前对所有线程可见（不会读到半成品 Node）
2. `volatile` 字段：保证可见性，读到最新值
3. **弱一致性**：迭代/get 时新插入元素可能看不到，但**绝不会读到错误数据**

### 10.4 多线程协助扩容

```
线程 1 发起扩容（创建 nextTable，sizeCtl = -(stamp<<16 | 2)）
                          │
              ┌───────────┼───────────┐
              ▼           ▼           ▼
           线程 1       线程 2      线程 3
           CAS 抢任务   CAS 抢任务   CAS 抢任务
           [48,64)     [32,48)     [16,32)
              │           │           │
              ▼           ▼           ▼
           迁移桶 + 旧位置放 ForwardingNode
              │           │           │
              └───────────┼───────────┘
                          ▼
                  最后线程做收尾：table = nextTable
```

**ForwardingNode 的三个作用**：
1. **标记**：此桶已迁移（hash = MOVED = -1）
2. **桥梁**：转发 get 请求到 nextTable
3. **信号**：触发 put 线程协助扩容（helpTransfer）

### 10.5 sizeCtl 状态机

一个 int 编码 4 种状态，是 JDK 源码"位压缩"经典：

| sizeCtl 值 | 含义 |
|-----------|------|
| `0` | 默认（未初始化） |
| `-1` | 正在初始化 |
| 正数 `N` | 正常状态，N = 扩容阈值 |
| `-(stamp<<16 \| (N+1))` | 扩容中，N 个线程参与 |

- 高 16 位：`resizeStamp(n)`，防止两轮扩容窗口重叠
- 低 16 位：参与扩容的线程数 + 1（线程加入 +1，线程退出 -1）

### 10.6 null 限制的本质

**为什么 CHM 不允许 null？**

```java
chm.put("key", null);  // 抛 NPE

// 假设允许 null：
Object v = chm.get("key");
if (v == null) {
    // 二义性：key 不存在？value 是 null？
    // 单线程下可用 containsKey 区分
    // 多线程下 containsKey 和 get 之间可能被其他线程修改 → 无法可靠区分
}
```

**Doug Lea 的官方解释**：并发场景下 `containsKey + get` 不是原子的，无法消除 null 的二义性。

### 10.7 size() 的并发统计

```java
@Contended
static final class CounterCell {
    volatile long value;
}

private transient volatile long baseCount;
private transient volatile CounterCell[] counterCells;
```

**核心思路**：
1. 把"一个热点字段"拆成"多个分布式字段"（借鉴 LongAdder 设计）
2. 不同线程通过 `ThreadLocalRandom.getProbe()` 哈希到不同 cell
3. `@Contended` 填充缓存行，**防止伪共享**（False Sharing）

`size() = baseCount + sum(counterCells[i].value)` —— 弱一致性。

### 10.8 复合操作的原子性

```java
ConcurrentHashMap<String, Integer> chm = new ConcurrentHashMap<>();

// ❌ 不安全（read-modify-write 不原子）
Integer v = chm.get(key);
chm.put(key, v + 1);

// ❌ 不安全
chm.putIfAbsent(key, 1);
chm.put(key, chm.get(key) + 1);

// ✅ 安全（lambda 在 synchronized 块内执行）
chm.compute(key, (k, v) -> v == null ? 1 : v + 1);

// ✅ 安全（更简洁）
chm.merge(key, 1, Integer::sum);
```

**规则**：并发场景下使用 CHM 的**原子方法**（compute、merge、putIfAbsent、replace），**不要组合 get + put**。

### 10.9 computeIfAbsent 的死锁陷阱

```java
// JDK 8 上死循环
chm.computeIfAbsent("A", k -> {
    return chm.computeIfAbsent("B", k2 -> 2);  // 嵌套调用同一个 map
});
// JDK 9+ 改为抛 IllegalStateException
```

**原因**：`computeIfAbsent` 在 synchronized 块内执行 lambda，嵌套调用形成循环依赖。

**教训**：不要在 lambda 中再次操作同一个 map（compute/merge/computeIfAbsent 内禁止嵌套）。

---

## 十一、集合横向对比

### 11.1 HashMap vs Hashtable vs ConcurrentHashMap

| 维度 | HashMap | Hashtable | ConcurrentHashMap |
|------|---------|-----------|-------------------|
| 线程安全 | ❌ | ✅ 整表 synchronized | ✅ 锁单个桶 |
| null key/value | ✅ | ❌ NPE | ❌ NPE（二义性） |
| 继承 | AbstractMap | **Dictionary**（古老类） | AbstractMap |
| 迭代器 | fail-fast | fail-fast | weakly-consistent |
| 初始容量 | 16 | **11（质数）** | 16 |
| 扩容 | 2 倍 | 2n + 1 | 2 倍 |
| 推荐场景 | 单线程 | **已淘汰** | 多线程 |

### 11.2 ArrayList vs Vector vs CopyOnWriteArrayList

| 维度 | ArrayList | Vector | CopyOnWriteArrayList |
|------|-----------|--------|---------------------|
| 线程安全 | ❌ | ✅ 方法级 synchronized | ✅ 写时复制 |
| 读性能 | 快 | 慢（锁开销） | **最快**（无锁） |
| 写性能 | 快 | 慢 | **最慢**（每次复制） |
| 推荐场景 | 单线程 | 已淘汰 | **读多写少** |

**Vector 为什么淘汰？** 方法级 synchronized 是"伪线程安全"，**组合操作仍不原子**。

### 11.3 TreeMap vs HashMap

| 维度 | HashMap | TreeMap |
|------|---------|---------|
| 底层结构 | 哈希表 + 链表 + 红黑树 | 红黑树 |
| key 顺序 | 无序 | **有序**（Comparable/Comparator） |
| 查询 | O(1) | O(log n) |
| 范围查询 | 不支持 | **subMap、headMap、ceilingKey、floorKey** |
| 应用场景 | 通用 KV | 排行榜、范围查询、价格分布 |

### 11.4 fail-fast vs weakly-consistent

| 维度 | fail-fast | weakly-consistent |
|------|-----------|------------------|
| 行为 | 检测到结构性修改抛 CME | 不抛异常，容忍并发修改 |
| 实现 | modCount vs expectedModCount | volatile + snapshot |
| 典型集合 | ArrayList、HashMap | CHM、CopyOnWriteArrayList |
| 目的 | **暴露并发 bug** | **支持并发遍历** |

**fail-fast 不是线程安全机制**，只是 bug 检测（且只是"尽力检测"）。

### 11.5 "查询多用 ArrayList，增删多用 LinkedList" 的误区

**两个忽略的关键**：

1. **LinkedList "O(1) 增删" 的前提是已经定位到节点**
   - `list.remove(index)` 仍然是 O(n)（先 O(n) 定位 + O(1) 删除）
   - 只有配合 `iterator.remove()` 才是真正的 O(1)

2. **CPU 缓存友好性**
   - ArrayList：连续内存，cache 命中率高
   - LinkedList：节点分散，频繁 cache miss
   - **10 万元素遍历：ArrayList ~1ms，LinkedList ~50ms**（差 50 倍！）

**结论**：99% 场景用 ArrayList，少数场景用 ArrayDeque，**LinkedList 基本不用**。

---

## 十二、JDK 集合设计哲学

### 12.1 用约束换性能

- HashMap 容量必须 2 的幂 → 可用 `& (n-1)` 替代 `% n`（快 20-40 倍）
- CHM 节点 hash 非负 → `-1` 用作 ForwardingNode 标志位
- String 不可变 → 可缓存 hashCode、可常量池复用

### 12.2 用空间换时间

- 加载因子 0.75 → 25% 空间冗余换查询性能
- LinkedHashMap 双向链表 → 多用 prev/after 指针换有序遍历
- CounterCell + `@Contended` → 缓存行填充换计数性能

### 12.3 分情况选最优

- CHM：空桶 CAS / 非空桶 synchronized / 扩容 helpTransfer
- HashMap：短链表用链表 / 长链表用红黑树
- ArrayList.removeIf：标记 + compact 两遍扫描 O(n)

### 12.4 防御性编程

- **Help GC**：remove 后置 null 避免内存泄漏
- **fail-fast**：modCount 检测并发修改，早暴露 bug
- **树化阈值 8**：泊松分布几乎不可能达到，防御恶意 hash 攻击
- **CHM 安全发布**：final hash/key + volatile val/next

---

## 十三、知识全景图

### 13.1 整体架构

```
                            Iterable
                                │
                                ▼
                           Collection                              Map
                                │                                   │
              ┌─────────────────┼─────────────────┐                 │
              ▼                 ▼                 ▼                 ▼
            List              Set              Queue               Map
              │                 │                 │                 │
        ┌─────┴─────┐       ┌───┴───┐         ┌───┴───┐       ┌─────┼─────┬─────────┐
        ▼           ▼       ▼       ▼         ▼       ▼       ▼     ▼     ▼         ▼
   ⭐ArrayList  LinkedList HashSet TreeSet   ⭐CHM ArrayDeque ⭐HashMap LinkedHashMap TreeMap
```

### 13.2 选型决策树

```
                  你需要存什么？
                       │
            ┌──────────┴──────────┐
            ▼                     ▼
         键值对                  列表
            │                     │
   ┌────────┼────────┐    ┌──────┴──────┐
   ▼        ▼        ▼    ▼             ▼
 单线程  多线程   需要顺序 单线程        多线程
   │        │        │      │             │
HashMap   CHM   LinkedHashMap ArrayList  读多写少: CopyOnWriteArrayList
                /TreeMap                  其他: Collections.synchronizedList

         需要队列/栈？ → ArrayDeque（不要用 LinkedList/Stack/Vector）
```

### 13.3 一句话浓缩

| 集合 | 一句话浓缩 |
|------|----------|
| **HashMap** | 用约束(2^n) + 位运算 + 红黑树构造的高效哈希表 |
| **ArrayList** | 用懒加载 + 1.5 倍扩容 + Help GC 优化的动态数组 |
| **ConcurrentHashMap** | 用 CAS + synchronized + volatile 实现的并发哈希表 |
| **LinkedList** | 用双向链表实现，但已被 ArrayList/ArrayDeque 取代 |

### 13.4 高频考点矩阵

```
                  基础题            ★★★             ★★★★              ★★★★★
   HashMap     │ 默认容量?       │ hash 扰动?       │ 树化条件?         │ 扩容高低位拆分?    │
              │ 加载因子?       │ 容量 2 的幂?     │ 死循环原因?       │ tableSizeFor 原理? │
              │ put 流程?       │ key 重写要求?    │ 红黑树 vs AVL?    │ 多线程问题分析?    │
   ───────────┼────────────────┼─────────────────┼──────────────────┼───────────────────┤
   ArrayList  │ 默认容量?       │ 1.5 倍扩容?      │ subList 视图?     │ removeIf 性能?     │
              │ get O(1)?      │ Arrays.asList?  │ transient?       │ Help GC 原理?      │
   ───────────┼────────────────┼─────────────────┼──────────────────┼───────────────────┤
   CHM        │ 线程安全?       │ JDK7 vs 8?      │ get 无锁?         │ sizeCtl 状态机?    │
              │ null 限制?     │ CAS+sync?       │ ForwardingNode?  │ size() 实现?       │
              │                │                 │                  │ computeIfAbsent 坑?│
   ───────────┼────────────────┼─────────────────┼──────────────────┼───────────────────┤
   LinkedList │ 双向链表?       │ vs ArrayList?   │ node(i) 优化?    │ 为啥不推荐?        │
              │                │ 头尾 O(1)?      │ unlink 置 null?  │                   │
```

---

## 十四、复习笔记小贴士

### 14.1 阅读源码的方法

1. **带着问题读**：先问"为什么这么设计"，再看代码
2. **看常量找线索**：`DEFAULT_INITIAL_CAPACITY = 16`、`TREEIFY_THRESHOLD = 8` 这些数字背后都有故事
3. **看 transient / volatile / final 修饰符**：它们暗示了序列化策略、并发可见性、安全发布
4. **看 `// ...` 注释**：JDK 源码注释非常详细，特别是 HashMap、ConcurrentHashMap 顶部的注释是宝藏

### 14.2 容易混淆的点

| 容易混淆 | 区别 |
|---------|------|
| `DEFAULTCAPACITY_EMPTY_ELEMENTDATA` vs `EMPTY_ELEMENTDATA` | 前者无参构造用（首次扩容到 10），后者 `new ArrayList<>(0)` 用（首次扩容到 1） |
| `tableSizeFor(cap)` 中先 `cap - 1` | 防止 cap 本身是 2 的幂时被向上多算一倍 |
| `(hash & oldCap) == 0` vs `(n - 1) & hash` | 前者用于扩容判断高/低位，后者用于计算桶下标 |
| `modCount` vs `expectedModCount` | 前者在集合上（被修改时 +1），后者在迭代器上（创建时拷贝） |
| fail-fast vs fail-safe vs weakly-consistent | "尽力抛异常" vs "永不抛但读旧数据" vs "尽力反映新数据" |

### 14.3 面试官最爱追问的三连问

```
问：HashMap 默认容量？
答：16
追问：为什么是 16？
答：2 的幂，方便位运算
再追问：tableSizeFor 怎么实现的？
答：连续 |=  右移 OR，把最高位 1 后的所有位填 1，最后 +1
```

```
问：CHM 为什么不允许 null？
答：因为二义性
追问：HashMap 为什么允许？
答：单线程可用 containsKey 区分
再追问：CHM 用 containsKey 不行吗？
答：并发下 containsKey + get 不原子，被打断后无法可靠区分
```

```
问：ArrayList 删除元素后 size-- 不够吗？
答：不够，要置 null（Help GC）
追问：为什么 size-- 不能让对象被回收？
答：数组中的引用还在，对象仍可达
再追问：哪本书提到这个？
答：Effective Java 第 7 条"消除过期对象引用"，举例就是 Stack.pop
```

---

> **最终一句话**：JDK 集合是"软件工程艺术品"，每个常数都有依据，每个数据结构都有取舍。读它不是为了背 API，是为了**内化工程直觉**。
