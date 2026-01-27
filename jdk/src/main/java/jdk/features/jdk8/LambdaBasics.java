package jdk.features.jdk8;

import java.util.*;
import java.util.function.*;

/**
 * JDK 8 Lambda 表达式学习
 *
 * 核心概念：
 * - Lambda 是匿名函数，可以作为参数传递
 * - 目标类型必须是函数式接口（@FunctionalInterface）
 * - 简化了"传递行为"的代码
 */
public class LambdaBasics {

    public static void main(String[] args) {
        LambdaBasics demo = new LambdaBasics();

        System.out.println("=== 1. Lambda 基本语法 ===");
        demo.basicSyntax();

        System.out.println("\n=== 2. 常用函数式接口 ===");
        demo.functionalInterfaces();

        System.out.println("\n=== 3. 方法引用 ===");
        demo.methodReferences();
    }

    // ============================================================
    // 1. Lambda 基本语法
    // ============================================================
    public void basicSyntax() {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");

        // JDK 7: 匿名内部类
        Collections.sort(names, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return a.length() - b.length();
            }
        });
        System.out.println("匿名内部类排序: " + names);

        // 重置列表
        names = Arrays.asList("Alice", "Bob", "Charlie", "David");

        // TODO(human): 用 Lambda 实现按字符串长度排序
        // 提示：Lambda 语法 (参数) -> 表达式
        // Collections.sort(names, ???);
        Collections.sort(names, (a, b) -> a.length() - b.length());
        System.out.println("Lambda 排序: " + names);
    }

    // ============================================================
    // 2. 常用函数式接口
    // ============================================================
    public void functionalInterfaces() {
        /*
         * 四大核心函数式接口：
         * ┌────────────────┬──────────────────┬────────────────────┐
         * │ 接口           │ 方法签名          │ 用途               │
         * ├────────────────┼──────────────────┼────────────────────┤
         * │ Predicate<T>   │ boolean test(T)  │ 判断（过滤）        │
         * │ Function<T,R>  │ R apply(T)       │ 转换（映射）        │
         * │ Consumer<T>    │ void accept(T)   │ 消费（遍历）        │
         * │ Supplier<T>    │ T get()          │ 生产（工厂）        │
         * └────────────────┴──────────────────┴────────────────────┘
         */

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // Predicate: 判断是否为偶数
        Predicate<Integer> isEven = n -> n % 2 == 0;
        System.out.println("5 是偶数? " + isEven.test(5));
        System.out.println("6 是偶数? " + isEven.test(6));

        // TODO(human): 实现一个 Function，将整数转为 "数字: X" 格式的字符串
        // 例如：输入 5，输出 "数字: 5"
        Function<Integer, String> toLabel = n -> "数字: " + n; // 替换 null
        if (toLabel != null) {
            System.out.println("Function 转换: " + toLabel.apply(42));
        }

        // TODO(human): 实现一个 Consumer，打印 "处理: X"
        // 例如：输入 5，打印 "处理: 5"
        Consumer<Integer> printer = n -> System.out.println("处理: " + n); // 替换 null
        if (printer != null) {
            System.out.print("Consumer 消费: ");
            printer.accept(100);
        }

        // Supplier: 生成随机数
        Supplier<Double> randomSupplier = () -> Math.random();
        System.out.println("Supplier 生成: " + randomSupplier.get());
    }

    // ============================================================
    // 3. 方法引用（Lambda 的简写形式）
    // ============================================================
    public void methodReferences() {
        /*
         * 方法引用的四种形式：
         * ┌─────────────────────┬───────────────────────┬─────────────────────┐
         * │ 类型                │ Lambda                 │ 方法引用             │
         * ├─────────────────────┼───────────────────────┼─────────────────────┤
         * │ 静态方法引用        │ x -> Math.abs(x)       │ Math::abs           │
         * │ 实例方法引用(对象)  │ x -> obj.method(x)     │ obj::method         │
         * │ 实例方法引用(类)    │ x -> x.method()        │ String::length      │
         * │ 构造方法引用        │ x -> new ArrayList<>(x)│ ArrayList::new      │
         * └─────────────────────┴───────────────────────┴─────────────────────┘
         */

        List<String> names = Arrays.asList("bob", "alice", "charlie");

        // Lambda 写法
        names.forEach(name -> System.out.println(name));

        // 方法引用写法（更简洁）
        System.out.println("方法引用遍历:");
        names.forEach(System.out::println);

        // TODO(human): 使用方法引用将字符串列表转为大写
        // 提示：String 类有 toUpperCase() 实例方法
        // 方法引用形式：String::toUpperCase
        List<String> upperNames = new ArrayList<>();
        for (String name : names) {
            // 使用方法引用的方式，配合 Stream 实现
            // upperNames = names.stream().map(???).toList();
            upperNames.add(name.toUpperCase());
        }
        System.out.println("转大写: " + upperNames);
    }
}
