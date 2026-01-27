package jdk.features.jdk11;

import java.util.*;
import java.util.stream.Collectors;

/**
 * JDK 11 新增实用方法
 *
 * JDK 11 是 LTS 版本，累积了 JDK 9-11 的实用特性：
 * - String 新方法
 * - 集合工厂方法 (JDK 9)
 * - Optional 新方法
 * - var 用于 Lambda 参数
 */
public class JDK11Features {

    public static void main(String[] args) {
        JDK11Features demo = new JDK11Features();

        System.out.println("=== 1. String 新方法 ===");
        demo.stringMethods();

        System.out.println("\n=== 2. 集合工厂方法 ===");
        demo.collectionFactories();

        System.out.println("\n=== 3. Optional 新方法 ===");
        demo.optionalMethods();
    }

    // ============================================================
    // 1. String 新方法
    // ============================================================
    public void stringMethods() {
        // isBlank() vs isEmpty()
        String empty = "";
        String blank = "   ";
        String text = "hello";

        System.out.println("--- isBlank vs isEmpty ---");
        System.out.println("''.isEmpty(): " + empty.isEmpty());     // true
        System.out.println("''.isBlank(): " + empty.isBlank());     // true
        System.out.println("'   '.isEmpty(): " + blank.isEmpty());  // false
        System.out.println("'   '.isBlank(): " + blank.isBlank());  // true ← 更实用

        // strip() vs trim()
        System.out.println("\n--- strip vs trim ---");
        String padded = "  hello  ";
        System.out.println("原始: '" + padded + "'");
        System.out.println("trim(): '" + padded.trim() + "'");
        System.out.println("strip(): '" + padded.strip() + "'");
        System.out.println("stripLeading(): '" + padded.stripLeading() + "'");
        System.out.println("stripTrailing(): '" + padded.stripTrailing() + "'");

        // lines() - 按行分割为 Stream
        System.out.println("\n--- lines() ---");
        String multiline = "第一行\n第二行\n第三行";
        multiline.lines().forEach(line -> System.out.println("  " + line));

        // repeat() - 重复字符串
        System.out.println("\n--- repeat() ---");
        System.out.println("'ab'.repeat(3): " + "ab".repeat(3));
        System.out.println("分隔线: " + "-".repeat(20));

        // 使用 String 新方法处理用户输入
        // 需求：验证用户名 - 去除空白后检查是否为空
        System.out.println("\n--- 用户名验证 ---");
        String[] inputs = {"  Alice  ", "   ", "Bob", ""};
        for (String input : inputs) {
            String trimmed = input.strip();
            if (trimmed.isBlank()) {
                System.out.println("输入: '" + input + "' -> 用户名无效");
            } else {
                System.out.println("输入: '" + input + "' -> 用户名有效: " + trimmed);
            }
        }
    }

    // ============================================================
    // 2. 集合工厂方法 (JDK 9+)
    // ============================================================
    public void collectionFactories() {
        /*
         * 新工厂方法特点：
         * ┌─────────────────────────────────────────────────────────┐
         * │ 1. 创建的是不可变集合                                    │
         * │ 2. 不允许 null 元素                                      │
         * │ 3. Set/Map 不允许重复                                    │
         * └─────────────────────────────────────────────────────────┘
         */

        // List.of()
        List<String> list = List.of("苹果", "香蕉", "橙子");
        System.out.println("List.of: " + list);

        // Set.of()
        Set<Integer> set = Set.of(1, 2, 3, 4, 5);
        System.out.println("Set.of: " + set);

        // Map.of() - 适合少量键值对
        Map<String, Integer> map = Map.of(
            "一", 1,
            "二", 2,
            "三", 3
        );
        System.out.println("Map.of: " + map);

        // Map.ofEntries() - 适合多个键值对
        Map<String, String> bigMap = Map.ofEntries(
            Map.entry("name", "Alice"),
            Map.entry("age", "25"),
            Map.entry("city", "Beijing")
        );
        System.out.println("Map.ofEntries: " + bigMap);

        // ⚠️ 不可变！尝试修改会抛异常
        try {
            list.add("葡萄");
        } catch (UnsupportedOperationException e) {
            System.out.println("❌ 不可变集合无法添加元素");
        }

        // 课程成绩 Map
        System.out.println("\n--- 课程成绩 ---");
        Map<String, Integer> scores = Map.of("语文", 85, "数学", 92, "英语", 78);
        double avg = scores.values().stream().mapToInt(Integer::intValue).average().orElse(0);
        System.out.println("成绩: " + scores);
        System.out.println("平均分: " + avg);
    }

    // ============================================================
    // 3. Optional 新方法
    // ============================================================
    public void optionalMethods() {
        Optional<String> present = Optional.of("Hello");
        Optional<String> absent = Optional.empty();

        // isEmpty() - JDK 11，与 isPresent() 相反
        System.out.println("--- isEmpty() ---");
        System.out.println("present.isEmpty(): " + present.isEmpty());  // false
        System.out.println("absent.isEmpty(): " + absent.isEmpty());    // true

        // ifPresentOrElse() - JDK 9
        System.out.println("\n--- ifPresentOrElse() ---");
        present.ifPresentOrElse(
            value -> System.out.println("有值: " + value),
            () -> System.out.println("无值")
        );
        absent.ifPresentOrElse(
            value -> System.out.println("有值: " + value),
            () -> System.out.println("无值")
        );

        // or() - JDK 9，空时返回另一个 Optional（懒加载）
        System.out.println("\n--- or() ---");
        Optional<String> result = absent.or(() -> Optional.of("默认值"));
        System.out.println("absent.or(...): " + result);

        // stream() - JDK 9，转为 Stream（0或1个元素）
        System.out.println("\n--- stream() ---");
        // 需要先赋值给变量，否则泛型推断会出问题
        List<Optional<String>> optionals = List.of(
            Optional.of("A"),
            Optional.empty(),
            Optional.of("B"),
            Optional.empty(),
            Optional.of("C")
        );
        List<String> values = optionals.stream()
            .flatMap(Optional::stream)  // 过滤掉 empty
            .collect(Collectors.toList());
        System.out.println("过滤 empty 后: " + values);

        // 使用 ifPresentOrElse 处理用户查询结果
        System.out.println("\n--- 用户查询 ---");
        Optional<String> user1 = findUserById(1);   // 存在
        Optional<String> user2 = findUserById(999); // 不存在
        user1.ifPresentOrElse(
            user -> System.out.println("找到用户: " + user),
            () -> System.out.println("用户不存在")
        );
        user2.ifPresentOrElse(
            user -> System.out.println("找到用户: " + user),
            () -> System.out.println("用户不存在")
        );
    }

    // 模拟用户查询
    private Optional<String> findUserById(int id) {
        if (id == 1) return Optional.of("Alice");
        return Optional.empty();
    }
}
