# JDK 8-21 核心特性学习

> 基于二八原则，聚焦最实用的 20% 特性

## 📊 学习进度

| 版本 | 特性 | 状态 | 文件 |
|------|------|------|------|
| JDK 8 | Lambda 表达式 | ✅ 完成 | `jdk8/LambdaBasics.java` |
| JDK 8 | Stream API | ✅ 完成 | `jdk8/StreamAdvanced.java` |
| JDK 8 | Optional | ✅ 完成 | `jdk8/OptionalDemo.java` |
| JDK 10 | var 类型推断 | ✅ 完成 | `jdk10/VarDemo.java` |
| JDK 11 | 新增实用方法 | ✅ 完成 | `jdk11/JDK11Features.java` |
| JDK 14 | Switch 表达式 | ✅ 完成 | `jdk14/SwitchExpression.java` |
| JDK 15 | 文本块 | ✅ 完成 | `jdk15/TextBlocks.java` |
| JDK 16 | Record | ✅ 完成 | `jdk16/RecordAndPatternMatching.java` |
| JDK 16 | Pattern Matching | ✅ 完成 | `jdk16/RecordAndPatternMatching.java` |
| JDK 17 | Sealed Classes | ✅ 完成 | `jdk17/SealedClasses.java` |
| JDK 21 | Pattern Matching for switch | ✅ 完成 | `jdk21/PatternMatchingSwitch.java` |
| JDK 21 | Record Patterns | ⏳ 待学习 | - |
| JDK 21 | Virtual Threads | ⏳ 待学习 | - |
| JDK 21 | Sequenced Collections | ⏳ 待学习 | - |

**总进度: █████████████░░░ 80% (11/14)**

---

## 一、JDK 8: Lambda + Stream + Optional

### 1.1 Lambda 表达式

**核心概念**：把行为当作参数传递

```java
// 匿名内部类 → Lambda
Comparator<String> c1 = new Comparator<>() {
    public int compare(String a, String b) { return a.length() - b.length(); }
};
Comparator<String> c2 = (a, b) -> a.length() - b.length();
```

**四大函数式接口**：

| 接口 | 方法 | 用途 |
|------|------|------|
| `Predicate<T>` | `boolean test(T)` | 判断/过滤 |
| `Function<T,R>` | `R apply(T)` | 转换/映射 |
| `Consumer<T>` | `void accept(T)` | 消费/遍历 |
| `Supplier<T>` | `T get()` | 生产/工厂 |

### 1.2 Stream API

**三段式**：创建流 → 中间操作 → 终端操作

```java
list.stream()                    // 创建
    .filter(x -> x > 0)          // 中间操作（惰性）
    .map(x -> x * 2)
    .collect(Collectors.toList()); // 终端操作（触发执行）
```

**常用操作**：

| 中间操作 | 说明 | 终端操作 | 说明 |
|---------|------|---------|------|
| filter | 过滤 | collect | 收集 |
| map | 转换 | forEach | 遍历 |
| flatMap | 扁平化 | reduce | 归约 |
| sorted | 排序 | count | 计数 |
| distinct | 去重 | findFirst | 取第一个 |

### 1.3 Optional

**核心思想**：用 Optional 容器替代 null，强制处理空值

```java
// 链式调用替代层层 null 检查
String city = Optional.ofNullable(user)
    .map(User::getAddress)
    .map(Address::getCity)
    .orElse("未知");
```

**取值方式**：

| 方法 | 空时行为 | 推荐度 |
|------|---------|--------|
| `get()` | 抛异常 | ❌ |
| `orElse(default)` | 返回默认值 | ✅ |
| `orElseGet(supplier)` | 懒加载默认值 | ✅ |
| `orElseThrow(...)` | 抛自定义异常 | ✅ |

---

## 二、JDK 10: var 类型推断

**核心概念**：编译时类型推断，减少冗余声明

```java
// 传统
Map<String, List<Integer>> map = new HashMap<String, List<Integer>>();

// var
var map = new HashMap<String, List<Integer>>();
```

**使用规则**：

| 可用 | 不可用 |
|------|--------|
| ✅ 局部变量 | ❌ 类字段 |
| ✅ for 循环 | ❌ 方法参数 |
| ✅ try-with-resources | ❌ 返回类型 |
| | ❌ 初始化为 null |

**最佳实践**：右侧类型明显时用 var，不明显时不用

---

## 三、JDK 11: 新增实用方法

### 3.1 String 新方法

| 方法 | 说明 | 示例 |
|------|------|------|
| `isBlank()` | 是否为空或只有空白 | `"  ".isBlank()` → true |
| `strip()` | 去除首尾空白(Unicode感知) | `" hi ".strip()` → "hi" |
| `lines()` | 按行分割为 Stream | `"a\nb".lines()` |
| `repeat(n)` | 重复 n 次 | `"ab".repeat(3)` → "ababab" |

### 3.2 集合工厂方法 (JDK 9+)

```java
List<String> list = List.of("a", "b", "c");     // 不可变
Set<Integer> set = Set.of(1, 2, 3);
Map<String, Integer> map = Map.of("a", 1, "b", 2);
```

⚠️ 注意：创建的是**不可变集合**，不能添加/删除元素

**为什么需要不可变集合？**
1. **线程安全**：多线程无需加锁
2. **防御性编程**：避免被意外修改
3. **JVM 优化**：内存布局更紧凑

### 3.3 Optional 新方法

| 方法 | 版本 | 说明 |
|------|------|------|
| `isEmpty()` | JDK 11 | 与 isPresent() 相反 |
| `ifPresentOrElse()` | JDK 9 | 有值/无值分别处理 |
| `or()` | JDK 9 | 空时返回另一个 Optional |
| `stream()` | JDK 9 | 转为 0或1 个元素的 Stream |

---

## 四、JDK 14: Switch 表达式

**新语法**：

```java
// 箭头语法 + 多值匹配 + 表达式返回值
String type = switch (day) {
    case "MON", "TUE", "WED", "THU", "FRI" -> "工作日";
    case "SAT", "SUN" -> "周末";
    default -> "未知";
};

// yield 用于代码块返回值
int result = switch (x) {
    case 1 -> 10;
    case 2 -> {
        System.out.println("计算中...");
        yield 20;  // yield 返回
    }
    default -> 0;
};
```

**对比传统 switch**：

| 特性 | 传统 switch | 新 switch |
|------|-------------|-----------|
| 语法 | `case: break;` | `case ->` |
| 穿透问题 | 忘记 break 会穿透 | 无穿透 |
| 返回值 | 不支持 | 支持表达式 |
| 多值匹配 | 需要多个 case | `case A, B, C ->` |

---

## 五、JDK 15: 文本块 (Text Blocks)

**核心语法**：三引号 `"""` 定义多行字符串

```java
// 传统
String sql = "SELECT *\n" +
             "FROM users\n" +
             "WHERE age > 18";

// 文本块
String sql = """
        SELECT *
        FROM users
        WHERE age > 18""";
```

**适用场景**：SQL、JSON、HTML、正则表达式

**缩进规则**：
- 结束引号 `"""` 的位置决定基准缩进
- 末尾换行：结束引号单独一行会产生末尾换行

---

## 六、JDK 16: Record + Pattern Matching

### 6.1 Record 记录类

**一行定义不可变数据类**：

```java
record Person(String name, int age) {}

// 自动生成：
// - 构造函数
// - getter: name(), age()
// - equals(), hashCode(), toString()
```

**紧凑构造函数**：

```java
record Email(String address) {
    public Email {  // 紧凑构造函数，用于验证
        if (!address.contains("@")) {
            throw new IllegalArgumentException("无效邮箱");
        }
        address = address.toLowerCase();  // 可以修改参数
    }
}
```

### 6.2 Pattern Matching for instanceof

```java
// 传统
if (obj instanceof String) {
    String s = (String) obj;  // 繁琐的强制转换
    System.out.println(s.length());
}

// 新语法
if (obj instanceof String s) {
    System.out.println(s.length());  // s 直接可用
}
```

---

## 七、JDK 17: Sealed Classes (密封类)

### 7.1 核心概念

**限制哪些类可以继承/实现一个类或接口**：

```java
// 只允许 Circle, Rectangle, Triangle 实现
sealed interface Shape permits Circle, Rectangle, Triangle {}

final class Circle implements Shape {}        // 不能再被继承
sealed class Rectangle implements Shape       // 继续限制
    permits Square {}
non-sealed class Triangle implements Shape {} // 开放继承
```

**子类修饰符**：

| 修饰符 | 含义 |
|--------|------|
| `final` | 不能再被继承 |
| `sealed` | 继续限制，需指定 permits |
| `non-sealed` | 开放继承 |

### 7.2 配合 Pattern Matching

```java
// 编译器知道所有可能的子类型
String describe(Shape s) {
    if (s instanceof Circle c) {
        return "圆形，半径: " + c.radius();
    } else if (s instanceof Rectangle r) {
        return "矩形: " + r.width() + "×" + r.height();
    } else if (s instanceof Triangle t) {
        return "三角形";
    }
    return "未知";  // JDK 21 的 switch 可以省略这行
}
```

### 7.3 实际应用：表达式树

```java
sealed interface Expr permits Constant, Variable, BinaryOp {}

record Constant(int value) implements Expr {}
record Variable(String name) implements Expr {}
record BinaryOp(Expr left, String op, Expr right) implements Expr {}

// 构建表达式: (x + 5) * 2
Expr expr = new BinaryOp(
    new BinaryOp(new Variable("x"), "+", new Constant(5)),
    "*",
    new Constant(2)
);
```

---

## 八、代数数据类型 (ADT)

### 8.1 什么是 ADT？

**代数数据类型 = 积类型 + 和类型**

```
┌─────────────────────┐    ┌─────────────────────┐
│   Product Type      │    │    Sum Type         │
│   (积类型/AND)      │    │   (和类型/OR)       │
├─────────────────────┤    ├─────────────────────┤
│ A AND B AND C       │    │ A OR B OR C         │
│                     │    │                     │
│ 例：Point(x, y)     │    │ 例：Shape = Circle  │
│ 同时有 x 和 y       │    │      | Rectangle    │
│                     │    │      | Triangle     │
│ Java: record        │    │ Java: sealed class  │
└─────────────────────┘    └─────────────────────┘
```

### 8.2 Java 实现 ADT

```java
// 和类型 (Sum Type)
sealed interface Result<T, E> permits Success, Failure {}
record Success<T, E>(T value) implements Result<T, E> {}
record Failure<T, E>(E error) implements Result<T, E> {}

// 使用
Result<User, String> result = findUser(1);
String msg = switch (result) {
    case Success<User, String> s -> "找到: " + s.value().name();
    case Failure<User, String> f -> "错误: " + f.error();
};
```

### 8.3 ADT 的威力：穷尽性检查

```
新增子类型时，所有 switch 都会编译报错
→ 强制处理新情况，不会遗漏
```

### 8.4 与其他语言对比

| 语言 | 语法 |
|------|------|
| Haskell | `data Shape = Circle Double \| Rectangle Double Double` |
| Scala | `sealed trait` + `case class` |
| Rust | `enum Shape { Circle(f64), Rectangle(f64, f64) }` |
| Kotlin | `sealed class` + `data class` |
| **Java 17+** | `sealed interface` + `record` |

---

## 九、JDK 21: Pattern Matching for switch

### 9.1 核心改进

**JDK 17 的 if-else 链 → JDK 21 的 switch 表达式**

```java
// JDK 17：冗长的 if-else
if (shape instanceof Circle c) {
    return "圆形";
} else if (shape instanceof Rectangle r) {
    return "矩形";
}
return "未知";  // 必须有

// JDK 21：简洁的 switch
return switch (shape) {
    case Circle c    -> "圆形";
    case Rectangle r -> "矩形";
    // 不需要 default！Sealed 保证穷尽
};
```

### 9.2 when 守卫条件

```java
String category = switch (num) {
    case Integer n when n < 0   -> "负数";
    case Integer n when n == 0  -> "零";
    case Integer n when n <= 10 -> "小正数";
    case Integer n              -> "大数";  // 默认
};
```

### 9.3 null 处理

```java
String result = switch (input) {
    case null -> "空值";
    case String s when s.isEmpty() -> "空字符串";
    case String s -> "有效: " + s;
};
```

### 9.4 实战：HTTP 响应处理

```java
sealed interface HttpResponse permits Success, ClientError, ServerError {}

String handle(HttpResponse response) {
    return switch (response) {
        case Success s when s.code() == 200 -> "OK: " + s.body();
        case Success s when s.code() == 201 -> "Created: " + s.body();
        case Success s -> "Success [" + s.code() + "]";
        case ClientError e -> "客户端错误 [" + e.code() + "]";
        case ServerError e -> "服务器错误 [" + e.code() + "]";
        // 不需要 default！
    };
}
```

### 9.5 注意事项

| 特性 | JDK 21 支持 | 备注 |
|------|-------------|------|
| 引用类型匹配 | ✅ | Integer, String, Object 等 |
| 基元类型匹配 | ❌ | int, double 等需要 JDK 23 |
| null 匹配 | ✅ | `case null ->` |
| when 守卫 | ✅ | `case T t when ... ->` |
| 穷尽性检查 | ✅ | Sealed 类型无需 default |

---

## 核心面试题

### JDK 8

1. **Lambda vs 匿名内部类的区别？**
   - Lambda 只能实现函数式接口，匿名内部类可以实现任何接口
   - Lambda 没有独立作用域，this 指向外层类
   - Lambda 编译为 invokedynamic，性能更好

2. **Stream 的惰性求值是什么意思？**
   - 中间操作不会立即执行，只有终端操作触发时才真正执行
   - 多个中间操作会合并为一次遍历

3. **Optional 的 orElse 和 orElseGet 区别？**
   - `orElse(T)`: 无论是否有值，都会执行参数表达式
   - `orElseGet(Supplier)`: 只在空时才执行 Supplier

### JDK 10-11

4. **var 是动态类型吗？**
   - 不是！var 是编译时类型推断，类型一旦确定就不能改变

5. **List.of() 和 new ArrayList() 的区别？**
   - List.of() 返回不可变集合，不能增删改
   - ArrayList 是可变集合

### JDK 14-17

6. **新 switch 表达式有什么优势？**
   - 无穿透问题、支持返回值、多值匹配、更简洁

7. **Record 和普通类的区别？**
   - Record 是不可变的，字段默认 final
   - 自动生成 equals/hashCode/toString
   - 不能继承其他类（但可以实现接口）

8. **Sealed Classes 解决什么问题？**
   - 限制继承，让编译器知道所有子类型
   - 支持穷尽性检查，新增子类时强制处理

9. **什么是 ADT？Java 如何支持？**
   - 代数数据类型 = 积类型(Record) + 和类型(Sealed Classes)
   - 两者结合可以精确建模领域概念

---

## 运行方式

```bash
# 确保 JDK 17+
java -version

# 运行示例
cd jdk/src/main/java
javac jdk/features/jdk17/SealedClasses.java
java jdk.features.jdk17.SealedClasses
```

---

## 学习路线图

```
JDK 8 ──────────────────────────────────────────────────────────────┐
│ Lambda + Stream + Optional                                        │
│ (函数式编程基础，最重要！)                                          │
└───────────────────────────────────────────────────────────────────┘
                              ↓
JDK 10-11 ──────────────────────────────────────────────────────────┐
│ var + String新方法 + 集合工厂                                       │
│ (语法糖，提升开发效率)                                              │
└───────────────────────────────────────────────────────────────────┘
                              ↓
JDK 14-15 ──────────────────────────────────────────────────────────┐
│ Switch表达式 + 文本块                                               │
│ (更简洁的语法)                                                      │
└───────────────────────────────────────────────────────────────────┘
                              ↓
JDK 16-17 ──────────────────────────────────────────────────────────┐
│ Record + Sealed Classes + Pattern Matching                        │
│ (ADT 支持，向函数式语言看齐)                                        │
└───────────────────────────────────────────────────────────────────┘
                              ↓
JDK 21+ (展望) ─────────────────────────────────────────────────────┐
│ Pattern Matching for switch (完全体)                               │
│ Record Patterns (解构模式)                                         │
│ Virtual Threads (虚拟线程)                                         │
└───────────────────────────────────────────────────────────────────┘
```

---

**🎉 恭喜完成 JDK 8-17 核心特性学习！**
