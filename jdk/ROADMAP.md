# JDK 基础复习路线图

> 8 大模块 · 55 章节 · 约 130 个待完成动手任务（TODO(human)）· 剩余预计 12-14 周

本路线图按 Learn by Doing 方式推进：每章先理解问题场景，再写可运行代码，最后沉淀 README、面试题和复盘笔记。

## 总进度

| 模块 | 进度 | 完成度 | 说明 |
|------|------|--------|------|
| 已完成 collection（集合源码） | 4/4 章 | 100% | 已有 `collection/README.md` |
| 进行中 features（JDK 8-21 新特性） | 11/14 章 | 79% | 还差 JDK 21 三个主题 |
| 待补 core-language（Java/JDK 基础语义） | 0/6 章 | 0% | 建议并发前补齐 |
| 当前重点 concurrent（并发） | 0/13 章 | 0% | 主攻面试高频 |
| 待学 jvm（虚拟机） | 0/7 章 | 0% | 运行时、GC、调优 |
| 待学 proxy/reflect（代理与反射） | 0/2 章 | 0% | Spring/AOP 前置能力 |
| 待学 design-patterns（设计模式） | 0/6 章 | 0% | 结合 JDK/Spring 场景 |
| 待学 io/nio（IO 与 NIO） | 0/3 章 | 0% | 网络编程基础 |

**总进度：15/55 章，约 27%**

---

## 推进规范

### 每章交付物

每完成一章，至少产出：

- 3 个以上 `TODO(human)` 动手任务
- 1 个可直接运行的 demo 或实验入口
- 1 份章节 README，包含概念图、关键代码、对比表、5-8 道面试题
- 1 段 Insight：说明本章最容易混淆或最值得记住的点

### 验收标准

每个任务完成时确认：

- 能用 Maven、IDE 或明确的 `java` 命令运行
- 输出能证明实验结论，而不是只打印“运行成功”
- 涉及 JVM 参数、JDK 版本、外部工具时，在 README 写清楚
- 性能压测类任务必须说明机器差异会影响结果，重点看趋势
- 危险实验（OOM、死锁、CPU 飙高）必须标注运行限制和停止方式

### 复习机制（防遗忘）

按艾宾浩斯曲线，learn by doing 学完的东西如果两周不碰，记忆留存率 < 30%。本路线图采用 **"模块结束即复习"** 的策略，而不是等到 W13-W14 集中"查漏补缺"。

**三层复习节奏**：

| 层级 | 触发时机 | 形式 | 时长 |
|------|---------|------|------|
| ① 即时回顾 | 每章完成时 | 在章节末尾追加"完成日期 + Insight + 卡壳点"一行 | 5 分钟 |
| ② 模块回顾 | 每个模块结束 | 用"问题驱动复习模式"自测（见 CLAUDE.md），填充模块尾部的"完成回顾表" | 1-2 小时 |
| ③ 跨模块回顾 | 每完成 2 个模块 | 把所有"卡壳点"汇总成"面试题串讲清单"，找跨模块知识关联 | 2-3 小时 |

**关键原则**：

- 卡壳点比 Insight 更值钱——它直接定位下次复习要重点看哪里
- 复习不是"重新读一遍"，而是"合上书自问自答"，能讲出来才算复习过
- 复习日发现还没掌握的，直接降级回 `[ ]` 未完成状态，不能自欺欺人
- 跨模块回顾的产出物 → 维护一个 `jdk/INTERVIEW.md`，沉淀真正的面试题库

### 推荐目录约定

```text
jdk/src/main/java/jdk/
├── collection/          # 已完成
├── features/            # JDK 8-21 新特性
├── core/                # Java/JDK 基础语义
├── concurrent/          # 并发
├── jvm/                 # JVM
├── proxy/               # 代理与反射
├── patterns/            # 设计模式
└── io/                  # IO/NIO
```

### 工具与依赖准备

| 场景 | 建议工具/依赖 | 用途 |
|------|---------------|------|
| 对象布局 | JOL | 观察对象头、字段布局、对齐填充 |
| 微基准 | JMH | 对比 AtomicLong、LongAdder、反射、MethodHandle |
| 字节码 | `javap -c -v` | 查看 monitorenter、invokedynamic、桥接方法 |
| GC 日志 | `-Xlog:gc*` | 观察 Young GC、G1、ZGC |
| 线程诊断 | `jstack` / `jcmd` | 排查死锁、线程阻塞 |
| 内存诊断 | `jmap` / MAT | dump 堆并定位泄漏 |
| 在线诊断 | Arthas | 观察线上方法、线程、类加载 |
| CGLIB | cglib | 对比 JDK 动态代理 |

---

## 模块一 · features JDK 8-21 新特性（补完剩余 3 章）

> 已有文件见 `src/main/java/jdk/features/README.md`，本模块主要补齐 JDK 21。

### F1 Lambda 表达式 — `features/jdk8/`
- [x] F1.1 四大函数式接口：Predicate / Function / Consumer / Supplier
- [x] F1.2 匿名内部类改写为 Lambda
- [x] F1.3 方法引用与构造器引用

### F2 Stream API — `features/jdk8/`
- [x] F2.1 filter / map / flatMap / sorted / distinct
- [x] F2.2 collect / groupingBy / partitioningBy
- [x] F2.3 惰性求值与并行流陷阱

### F3 Optional — `features/jdk8/`
- [x] F3.1 用 Optional 改造 null 检查
- [x] F3.2 orElse vs orElseGet
- [x] F3.3 map / flatMap 链式取值

### F4 var 类型推断 — `features/jdk10/`
- [x] F4.1 局部变量类型推断
- [x] F4.2 var 可用/不可用场景
- [x] F4.3 代码可读性边界

### F5 JDK 11 实用 API — `features/jdk11/`
- [x] F5.1 String 新方法
- [x] F5.2 List.of / Set.of / Map.of 不可变集合
- [x] F5.3 Optional 新方法

### F6 Switch 表达式 — `features/jdk14/`
- [x] F6.1 箭头语法消除穿透
- [x] F6.2 yield 返回值
- [x] F6.3 传统 switch vs 新 switch

### F7 Text Blocks — `features/jdk15/`
- [x] F7.1 多行 SQL / JSON 字符串
- [x] F7.2 缩进规则
- [x] F7.3 与普通字符串拼接对比

### F8 Record — `features/jdk16/`
- [x] F8.1 record 替代不可变 DTO
- [x] F8.2 紧凑构造函数
- [x] F8.3 record 的限制与适用场景

### F9 Pattern Matching for instanceof — `features/jdk16/`
- [x] F9.1 instanceof 后自动绑定变量
- [x] F9.2 作用域规则
- [x] F9.3 与传统强转写法对比

### F10 Sealed Classes — `features/jdk17/`
- [x] F10.1 sealed / permits / final / non-sealed
- [x] F10.2 封闭继承层级
- [x] F10.3 与 switch 穷尽性检查结合

### F11 Pattern Matching for switch — `features/jdk21/`
- [x] F11.1 类型模式匹配
- [x] F11.2 null case
- [x] F11.3 guarded pattern

### F12 Record Patterns — `features/jdk21/`
- [ ] F12.1 record 解构匹配
- [ ] F12.2 嵌套 record pattern
- [ ] F12.3 与 switch 组合实现结构化分发

### F13 Virtual Threads 概览 — `features/jdk21/`
- [ ] F13.1 Thread.ofVirtual 创建虚拟线程
- [ ] F13.2 Executors.newVirtualThreadPerTaskExecutor
- [ ] F13.3 与 concurrent/C12 深入实验互相引用

### F14 Sequenced Collections — `features/jdk21/`
- [ ] F14.1 SequencedCollection / SequencedSet / SequencedMap
- [ ] F14.2 reversed / first / last API
- [ ] F14.3 与 List / LinkedHashMap / TreeMap 的关系

### 完成回顾

> F1-F11 已完成 11 章，需要复习回填。F12-F14 完成后再追加。

<!-- TODO(human): 回填 F1-F11 的完成回顾表
要求：
1. 完成日期：YYYY-MM-DD 格式；记不清准确日期就写大致月份（如 2025-12）
2. 卡壳点：当时学的时候哪里最难理解 / 反复看了好几遍 / 至今仍然不太确定？（这一栏最重要，复习重点就看它）
3. 一句话 Insight：用一句话概括"如果只能记住一件事，是什么"
4. 完全没印象的章节就在卡壳点写"全忘了，需重学"，诚实记录比假装掌握更有价值
-->

| 章节 | 完成日期 | 卡壳点 | 一句话 Insight |
|------|---------|--------|----------------|
| F1 Lambda 表达式 | | | |
| F2 Stream API | | | |
| F3 Optional | | | |
| F4 var 类型推断 | | | |
| F5 JDK 11 实用 API | | | |
| F6 Switch 表达式 | | | |
| F7 Text Blocks | | | |
| F8 Record | | | |
| F9 Pattern Matching for instanceof | | | |
| F10 Sealed Classes | | | |
| F11 Pattern Matching for switch | | | |

---

## 模块二 · core-language Java/JDK 基础语义（1 周）

> 这块是“看起来基础，但面试最容易被追问细节”的部分，建议在并发前快速补齐。

### L1 Object 根类 — `core/object/`
- [ ] L1.1 equals 与 hashCode 契约实验
- [ ] L1.2 toString、getClass、clone 的使用边界
- [ ] L1.3 finalize 为什么不推荐

### L2 String 与包装类型 — `core/string/`
- [ ] L2.1 String 不可变与常量池
- [ ] L2.2 intern 行为实验
- [ ] L2.3 自动装箱拆箱与 Integer 缓存

### L3 泛型与类型擦除 — `core/generic/`
- [ ] L3.1 泛型擦除后字节码观察
- [ ] L3.2 extends / super 通配符
- [ ] L3.3 桥接方法 bridge method

### L4 异常体系 — `core/exception/`
- [ ] L4.1 checked vs unchecked exception
- [ ] L4.2 try-with-resources 与 suppressed exception
- [ ] L4.3 finally return 覆盖问题

### L5 枚举与注解 — `core/meta/`
- [ ] L5.1 enum 单例与反射防护
- [ ] L5.2 自定义注解与 Retention/Target
- [ ] L5.3 运行时读取注解并驱动逻辑

### L6 常用基础类 — `core/common/`
- [ ] L6.1 BigDecimal 精度与比较陷阱
- [ ] L6.2 LocalDateTime / Instant / ZoneId
- [ ] L6.3 Objects / Arrays / Collections 工具类

---

## 模块三 · concurrent 并发编程（4-5 周）

### C0 线程基础与协作 — `concurrent/thread/`
- [ ] C0.1 线程状态流转：NEW / RUNNABLE / BLOCKED / WAITING / TIMED_WAITING / TERMINATED
- [ ] C0.2 interrupt 语义：标记、中断阻塞、恢复标记
- [ ] C0.3 wait/notify/notifyAll 与 join
- [ ] C0.4 LockSupport.park/unpark 与许可模型

### C1 JMM 与可见性 — `concurrent/jmm/`
- [ ] C1.1 复现可见性 bug（共享标志位无 volatile，worker 不停）
- [ ] C1.2 用 volatile 修复可见性
- [ ] C1.3 happens-before 规则验证（程序次序 / volatile / 锁）
- [ ] C1.4 DCL 单例为何必须 volatile（指令重排序）

### C2 synchronized 锁升级 — `concurrent/sync/`
- [ ] C2.1 用 JOL 观察对象头（无锁 / 轻量级锁 / 重量级锁）
- [ ] C2.2 sync 方法 vs sync 块字节码对比（javap）
- [ ] C2.3 锁消除/锁粗化场景观察

### C3 CAS 与 Atomic — `concurrent/cas/`
- [ ] C3.1 AtomicInteger 自增（与 i++ 对比）
- [ ] C3.2 ABA 问题复现 + AtomicStampedReference 修复
- [ ] C3.3 LongAdder vs AtomicLong 高并发性能压测

### C4 AQS 与 ReentrantLock — `concurrent/aqs/`
- [ ] C4.1 手写简化版 AQS 的 acquire/release（独占模式）
- [ ] C4.2 公平锁 vs 非公平锁吞吐量对比
- [ ] C4.3 Condition 实现阻塞队列
- [ ] C4.4 tryLock 超时与中断响应

### C5 ReadWriteLock / StampedLock — `concurrent/rwlock/`
- [ ] C5.1 读写锁缓存场景（多读少写）
- [ ] C5.2 StampedLock 乐观读
- [ ] C5.3 读写锁的写饥饿/读饥饿现象复现

### C6 ThreadLocal 深入 — `concurrent/threadlocal/`
- [ ] C6.1 弱引用导致 key 回收实验
- [ ] C6.2 value 内存泄漏复现（线程池场景）
- [ ] C6.3 InheritableThreadLocal 父子线程传递

### C7 ThreadPoolExecutor — `concurrent/pool/`
- [ ] C7.1 7 大参数实验（核心线程 / 最大线程 / 队列 / 拒绝）
- [ ] C7.2 4 种拒绝策略对比
- [ ] C7.3 手写迷你线程池（Worker + 阻塞队列）
- [ ] C7.4 shutdown vs shutdownNow 区别

### C8 BlockingQueue 家族 — `concurrent/queue/`
- [ ] C8.1 ArrayBlockingQueue / LinkedBlockingQueue / SynchronousQueue 三者实验
- [ ] C8.2 手写有界阻塞队列（ReentrantLock + Condition）
- [ ] C8.3 PriorityBlockingQueue 应用

### C9 JUC 工具类 — `concurrent/utils/`
- [ ] C9.1 CountDownLatch 多线程等待
- [ ] C9.2 CyclicBarrier 多阶段同步
- [ ] C9.3 Semaphore 限流
- [ ] C9.4 Phaser 替代 CDL + CB

### C10 并发集合 — `concurrent/collections/`
- [ ] C10.1 ConcurrentHashMap put/get 核心流程复刻
- [ ] C10.2 ConcurrentHashMap 扩容协助迁移观察
- [ ] C10.3 CopyOnWriteArrayList 写时复制观察
- [ ] C10.4 ConcurrentLinkedQueue vs Collections.synchronizedXXX

### C11 CompletableFuture 完全体 — `concurrent/future/`（重组现有）
- [ ] C11.1 thenApply / thenAccept / thenRun 区别
- [ ] C11.2 异步编排（allOf / anyOf）
- [ ] C11.3 异常处理（exceptionally / handle / whenComplete）

### C12 虚拟线程（JDK 21） — `concurrent/virtual/`
- [ ] C12.1 创建虚拟线程并观察栈
- [ ] C12.2 虚拟线程 vs 平台线程吞吐量对比
- [ ] C12.3 pinning 陷阱复现（synchronized / native / foreign call）

---

## 模块四 · jvm 虚拟机（3-4 周）

### J1 内存结构 — `jvm/memory/`
- [ ] J1.1 StackOverflowError 复现
- [ ] J1.2 OOM: Java heap space
- [ ] J1.3 OOM: Metaspace
- [ ] J1.4 DirectMemory OOM

### J2 对象布局 — `jvm/object/`
- [ ] J2.1 JOL 输出对象头/实例数据/对齐
- [ ] J2.2 逃逸分析开/关对比（-XX:-DoEscapeAnalysis）
- [ ] J2.3 压缩指针实验（+/-UseCompressedOops）

### J3 类加载机制 — `jvm/classloader/`
- [ ] J3.1 类初始化顺序观察
- [ ] J3.2 自定义 ClassLoader 加载外部 class
- [ ] J3.3 SPI 如何绕开双亲委派（ServiceLoader）

### J4 GC 算法与引用 — `jvm/gc/`
- [ ] J4.1 强 / 软 / 弱 / 虚四种引用对比
- [ ] J4.2 Young GC 触发观察
- [ ] J4.3 大对象分配与老年代行为

### J5 垃圾收集器 — `jvm/collectors/`
- [ ] J5.1 阅读 CMS 历史日志，并与 G1 日志对比（CMS 需旧 JDK）
- [ ] J5.2 G1 关键调优参数
- [ ] J5.3 ZGC 停顿时间测量

### J6 字节码与 JIT — `jvm/bytecode/`
- [ ] J6.1 synchronized 的 monitorenter/monitorexit
- [ ] J6.2 String 拼接的 invokedynamic
- [ ] J6.3 Lambda 字节码与 invokedynamic

### J7 调优实战 — `jvm/tuning/`
- [ ] J7.1 jstack 排查死锁
- [ ] J7.2 jmap + MAT 找内存泄漏
- [ ] J7.3 Arthas 在线诊断慢方法

---

## 模块五 · proxy/reflect 代理与反射（1 周）

### P1 三种代理对比 — `proxy/`
- [ ] P1.1 静态代理实现
- [ ] P1.2 JDK 动态代理实现 InvocationHandler
- [ ] P1.3 CGLIB 代理无接口类
- [ ] P1.4 JDK vs CGLIB 创建+调用性能压测

### P2 反射与 MethodHandle — `proxy/reflect/`
- [ ] P2.1 反射调用 private 方法
- [ ] P2.2 setAccessible 性能影响压测
- [ ] P2.3 MethodHandle 替代反射
- [ ] P2.4 反射、MethodHandle、LambdaMetafactory 对比

---

## 模块六 · design-patterns 设计模式（1-2 周）

### D1 单例 5 种实现 — `patterns/singleton/`
- [ ] D1.1 饿汉式 / 懒汉式
- [ ] D1.2 DCL（关联 C1.4）
- [ ] D1.3 静态内部类 + 枚举（破坏反射攻击）

### D2 工厂 — `patterns/factory/`
- [ ] D2.1 简单工厂
- [ ] D2.2 工厂方法
- [ ] D2.3 抽象工厂

### D3 装饰器 — `patterns/decorator/`
- [ ] D3.1 装饰 IO 流（BufferedInputStream 案例）
- [ ] D3.2 vs 继承的对比
- [ ] D3.3 自定义装饰器链

### D4 责任链 — `patterns/chain/`
- [ ] D4.1 Filter / Interceptor 模式
- [ ] D4.2 Spring HandlerInterceptor 思路复刻
- [ ] D4.3 责任链 + 函数式接口（现代写法）

### D5 观察者 / 发布订阅 — `patterns/observer/`
- [ ] D5.1 观察者模式基础
- [ ] D5.2 vs 发布订阅
- [ ] D5.3 EventBus 简易实现

### D6 策略 — `patterns/strategy/`
- [ ] D6.1 策略替代 if-else
- [ ] D6.2 Map + 函数式接口写法
- [ ] D6.3 Spring 中的策略模式案例

---

## 模块七 · io/nio IO 与 NIO（1 周）

### I1 BIO — `io/bio/`
- [ ] I1.1 BIO Echo Server
- [ ] I1.2 多线程接多客户端
- [ ] I1.3 阻塞模型瓶颈观察

### I2 NIO 三大件 — `io/nio/`
- [ ] I2.1 Buffer 的 position/limit/capacity 实验
- [ ] I2.2 Channel 文件读写
- [ ] I2.3 Selector 单线程多连接

### I3 零拷贝 — `io/zerocopy/`
- [ ] I3.1 mmap 大文件
- [ ] I3.2 FileChannel.transferTo
- [ ] I3.3 与传统 IO 拷贝速度对比

---

## 模块八 · collection 集合源码（已完成）

> 已有 `collection/README.md`，后续只做复习和小修。

### K1 HashMap — `collection/`
- [x] K1.1 hash 扰动与索引计算
- [x] K1.2 扩容与高低位拆分
- [x] K1.3 JDK 7 vs JDK 8 对比

### K2 ArrayList — `collection/`
- [x] K2.1 动态数组结构
- [x] K2.2 1.5 倍扩容
- [x] K2.3 增删查复杂度

### K3 ConcurrentHashMap — `collection/`
- [x] K3.1 JDK 7 Segment vs JDK 8 CAS + synchronized
- [x] K3.2 get 为什么不用加锁
- [x] K3.3 put 简化实现

### K4 LinkedList — `collection/`
- [x] K4.1 双向链表结构
- [x] K4.2 头尾插入删除
- [x] K4.3 与 ArrayList 使用场景对比

### 完成回顾

> 复习时回填。模板见"推进规范 → 复习机制"。先填 features 模块，本表后续填。

| 章节 | 完成日期 | 卡壳点 | 一句话 Insight |
|------|---------|--------|----------------|
| K1 HashMap | | | |
| K2 ArrayList | | | |
| K3 ConcurrentHashMap | | | |
| K4 LinkedList | | | |

---

## 清理任务（与学习并行）

- [ ] 删除 `jdk/stream/` 目录（与 `features/jdk8/StreamAdvanced.java` 重复）
- [ ] 重写 `concurrent/LockTest.java`（移入 `concurrent/aqs/`）
- [ ] `concurrent/producer_consumer/` 加 README + 重组
- [ ] `concurrent/print_fish/` 移入 `producer_consumer/` 作为子例
- [ ] 将旧的散落 demo 按新目录归档，归档前先确认是否已有 README 覆盖
- [ ] 为 JOL/JMH/CGLIB/Arthas 相关任务补充 Maven profile 或 README 安装说明

---

## 周时间表

```text
W0      - core-language L1-L6 + features F12-F14 补完              [+ features 模块复习日 ✦]
W1-W2   - 并发 C0-C5      (线程基础 + JMM + sync + CAS + AQS + RWLock)
W3-W4   - 并发 C6-C9      (ThreadLocal + 线程池 + Queue + JUC)
W5      - 并发 C10-C12    (并发集合 + CompletableFuture + 虚拟线程) [+ concurrent 模块复习日 ✦✦ 跨模块串讲]
W6-W7   - JVM J1-J4       (内存 + 对象 + 类加载 + GC)
W8-W9   - JVM J5-J7       (收集器 + 字节码 + 调优实战)              [+ JVM 模块复习日 ✦]
W10     - 代理反射 P1-P2                                            [+ 模块复习日（含 core-language 回顾） ✦✦]
W11     - 设计模式 D1-D6                                            [+ 模块复习日 ✦]
W12     - IO/NIO I1-I3                                              [+ 模块复习日 ✦✦ 全栈跨模块串讲]
W13-W14 - 查漏补缺 + INTERVIEW.md 总复盘 + 面试题串讲
```

> 标注说明：`✦` = 模块回顾（1-2h，填完成回顾表）；`✦✦` = 跨模块回顾（2-3h，更新 INTERVIEW.md）

---

## 进度更新规范

每完成一个 TODO，把 `[ ]` 改为 `[x]`，并在章节末尾追加：

- 完成日期
- Insight 笔记一句话
- 遗留疑问（若有）
- 运行命令或验证方式

每完成一章，在对应模块目录下生成或更新 `README.md`，结构参照：

```markdown
# JDK [主题名称]

## 核心概念
[ASCII 图示 + 简要说明]

## 文件说明
| 文件 | 说明 |
|------|------|
| xxx.java | ... |

## 一、[知识点1]
### 对比表
### 代码示例

## 核心面试题
### 1. [问题]？
**答**: ...

## 运行演示
```
