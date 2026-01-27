package jdk.features.jdk8;

import java.util.*;
import java.util.stream.*;

/**
 * JDK 8 Stream API 进阶学习
 *
 * Stream 三段式：
 * ┌─────────────┐    ┌─────────────┐    ┌─────────────┐
 * │  创建流     │ -> │  中间操作    │ -> │  终端操作   │
 * │  Stream.of  │    │  filter/map │    │  collect    │
 * │  list.stream│    │  sorted     │    │  forEach    │
 * └─────────────┘    └─────────────┘    └─────────────┘
 *
 * 核心特点：
 * - 惰性求值：中间操作不会立即执行，直到遇到终端操作
 * - 只能消费一次：流一旦被终端操作消费，就不能再使用
 */
public class StreamAdvanced {

    public static void main(String[] args) {
        StreamAdvanced demo = new StreamAdvanced();

        System.out.println("=== 1. Stream 创建方式 ===");
        demo.createStreams();

        System.out.println("\n=== 2. 中间操作 ===");
        demo.intermediateOperations();

        System.out.println("\n=== 3. 终端操作与收集器 ===");
        demo.terminalOperations();

        System.out.println("\n=== 4. 实战：复杂数据处理 ===");
        demo.practicalExample();
    }

    // ============================================================
    // 1. Stream 创建方式
    // ============================================================
    public void createStreams() {
        // 方式1: 从集合创建
        List<String> list = Arrays.asList("a", "b", "c");
        Stream<String> s1 = list.stream();

        // 方式2: Stream.of()
        Stream<String> s2 = Stream.of("x", "y", "z");

        // 方式3: 数组创建
        String[] arr = {"1", "2", "3"};
        Stream<String> s3 = Arrays.stream(arr);

        // 方式4: 无限流（慎用，需要 limit）
        Stream<Integer> infinite = Stream.iterate(0, n -> n + 2).limit(5);
        System.out.println("无限流(偶数): " + infinite.collect(Collectors.toList()));

        // 方式5: generate 生成
        Stream<Double> randoms = Stream.generate(Math::random).limit(3);
        System.out.println("随机数流: " + randoms.collect(Collectors.toList()));
    }

    // ============================================================
    // 2. 中间操作（惰性求值）
    // ============================================================
    public void intermediateOperations() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        /*
         * 常用中间操作：
         * ┌────────────┬─────────────────────────────────────────┐
         * │ 操作       │ 说明                                     │
         * ├────────────┼─────────────────────────────────────────┤
         * │ filter     │ 过滤元素                                 │
         * │ map        │ 转换元素                                 │
         * │ flatMap    │ 扁平化（一对多映射）                      │
         * │ distinct   │ 去重                                     │
         * │ sorted     │ 排序                                     │
         * │ limit      │ 限制数量                                 │
         * │ skip       │ 跳过前 N 个                              │
         * │ peek       │ 调试用，不改变流                         │
         * └────────────┴─────────────────────────────────────────┘
         */

        // filter + map 链式调用
        List<Integer> result = numbers.stream()
                .filter(n -> n % 2 == 0)  // 保留偶数
                .map(n -> n * 10)          // 乘以10
                .collect(Collectors.toList());
        System.out.println("filter + map: " + result);

        // TODO(human): 使用 flatMap 将二维列表扁平化
        // 输入: [[1,2], [3,4], [5,6]]
        // 输出: [1, 2, 3, 4, 5, 6]
        // 提示: flatMap 接收一个函数，该函数将每个元素转换为一个流
        List<List<Integer>> nested = Arrays.asList(
                Arrays.asList(1, 2),
                Arrays.asList(3, 4),
                Arrays.asList(5, 6)
        );
        List<Integer> flattened = nested.stream().flatMap(n -> n.stream()).collect(Collectors.toList()); // 实现扁平化
        // flattened = nested.stream().flatMap(???).toList();
        System.out.println("flatMap 扁平化: " + flattened);

        // 演示惰性求值
        System.out.println("\n--- 惰性求值演示 ---");
        List<String> words = Arrays.asList("hello", "world", "java", "stream");
        // 注意：没有终端操作，filter 和 map 不会执行
        Stream<String> lazy = words.stream()
                .filter(w -> {
                    System.out.println("过滤: " + w);
                    return w.length() > 4;
                })
                .map(w -> {
                    System.out.println("转换: " + w);
                    return w.toUpperCase();
                });
        System.out.println("流已创建，但还没执行任何操作...");
        System.out.println("现在调用终端操作:");
        List<String> lazyResult = lazy.collect(Collectors.toList());  // 此时才执行
        System.out.println("结果: " + lazyResult);
    }

    // ============================================================
    // 3. 终端操作与收集器
    // ============================================================
    public void terminalOperations() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        /*
         * 常用终端操作：
         * ┌────────────────┬─────────────────────────────────────┐
         * │ 操作           │ 说明                                 │
         * ├────────────────┼─────────────────────────────────────┤
         * │ collect        │ 收集为集合                           │
         * │ toList         │ JDK 16+ 简化版                       │
         * │ forEach        │ 遍历（无返回值）                      │
         * │ count          │ 计数                                 │
         * │ reduce         │ 归约（聚合计算）                      │
         * │ findFirst      │ 找第一个                             │
         * │ anyMatch       │ 任意匹配                             │
         * │ allMatch       │ 全部匹配                             │
         * │ noneMatch      │ 无匹配                               │
         * └────────────────┴─────────────────────────────────────┘
         */

        // reduce: 求和
        int sum = numbers.stream().reduce(0, Integer::sum);
        System.out.println("reduce 求和: " + sum);

        // TODO(human): 使用 reduce 找出最大值
        // 提示: reduce 的两个参数版本 reduce(初始值, BinaryOperator)
        //       或单参数版本 reduce(BinaryOperator) 返回 Optional
        Optional<Integer> max = numbers.stream().reduce(Integer::max); // 实现找最大值
        // max = numbers.stream().reduce(???);
        System.out.println("reduce 最大值: " + max);

        // Collectors 常用收集器
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "Alice", "David");

        // 去重并收集
        Set<String> uniqueNames = names.stream().collect(Collectors.toSet());
        System.out.println("收集为 Set: " + uniqueNames);

        // 分组
        Map<Integer, List<String>> byLength = names.stream()
                .collect(Collectors.groupingBy(String::length));
        System.out.println("按长度分组: " + byLength);

        // TODO(human): 使用 Collectors.joining 将名字用逗号连接
        // 输出应为: "Alice, Bob, Charlie, Alice, David"
        String joined = names.stream().collect(Collectors.joining(", ")); // 实现连接
        // joined = names.stream().collect(Collectors.joining(???));
        System.out.println("joining 连接: " + joined);
    }

    // ============================================================
    // 4. 实战：复杂数据处理
    // ============================================================
    public void practicalExample() {
        // 模拟订单数据
        List<Order> orders = Arrays.asList(
                new Order("Alice", "手机", 5999.0, 1),
                new Order("Bob", "耳机", 299.0, 2),
                new Order("Alice", "平板", 3999.0, 1),
                new Order("Charlie", "手机", 5999.0, 2),
                new Order("Bob", "手机壳", 99.0, 3)
        );

        // 需求1: 找出金额最大的订单
        Order maxOrder = orders.stream()
                .max(Comparator.comparing(o -> o.price * o.quantity))
                .orElse(null);
        System.out.println("最大订单: " + maxOrder);

        // 需求2: 计算每个用户的总消费
        Map<String, Double> totalByUser = orders.stream()
                .collect(Collectors.groupingBy(
                        o -> o.customer,
                        Collectors.summingDouble(o -> o.price * o.quantity)
                ));
        System.out.println("用户消费统计: " + totalByUser);

        // 统计手机类订单的数量和总金额
        // 使用 mapToInt/mapToDouble 更简洁，避免装箱开销
        int phoneCount = orders.stream()
                .filter(o -> o.product.equals("手机"))
                .mapToInt(o -> o.quantity)
                .sum();
        double phoneTotal = orders.stream()
                .filter(o -> o.product.equals("手机"))
                .mapToDouble(o -> o.price * o.quantity)
                .sum();
        System.out.println("手机订单: 数量=" + phoneCount + ", 总金额=" + phoneTotal);
    }

    // 订单类
    static class Order {
        String customer;
        String product;
        double price;
        int quantity;

        Order(String customer, String product, double price, int quantity) {
            this.customer = customer;
            this.product = product;
            this.price = price;
            this.quantity = quantity;
        }

        @Override
        public String toString() {
            return customer + "购买" + product + "×" + quantity;
        }
    }
}
