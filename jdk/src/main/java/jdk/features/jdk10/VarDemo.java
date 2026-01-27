package jdk.features.jdk10;

import java.util.*;
import java.util.stream.Collectors;

/**
 * JDK 10: var 局部变量类型推断
 *
 * 核心概念：
 * - var 让编译器自动推断变量类型
 * - 只能用于局部变量，不能用于字段、参数、返回值
 * - 必须有初始值，且不能为 null
 *
 * ┌─────────────────────────────────────────────────────────────┐
 * │  var 不是动态类型！                                          │
 * │  var name = "hello";  // 编译时确定为 String                 │
 * │  name = 123;          // ❌ 编译错误！类型已固定               │
 * └─────────────────────────────────────────────────────────────┘
 */
public class VarDemo {

    public static void main(String[] args) {
        VarDemo demo = new VarDemo();

        System.out.println("=== 1. var 基本用法 ===");
        demo.basicUsage();

        System.out.println("\n=== 2. var 适用场景 ===");
        demo.goodUseCases();

        System.out.println("\n=== 3. var 不适用场景 ===");
        demo.badUseCases();
    }

    // ============================================================
    // 1. var 基本用法
    // ============================================================
    public void basicUsage() {
        // 传统写法：类型声明冗余
        Map<String, List<Integer>> oldMap = new HashMap<String, List<Integer>>();
        ArrayList<String> oldList = new ArrayList<String>();

        // var 写法：编译器自动推断
        var map = new HashMap<String, List<Integer>>();  // 推断为 HashMap<String, List<Integer>>
        var list = new ArrayList<String>();               // 推断为 ArrayList<String>
        var name = "Hello";                               // 推断为 String
        var count = 100;                                  // 推断为 int

        System.out.println("map 类型: " + map.getClass().getSimpleName());
        System.out.println("list 类型: " + list.getClass().getSimpleName());
        System.out.println("name 类型: " + ((Object) name).getClass().getSimpleName());

        // var 是编译时类型推断，不是动态类型
        // name = 123;  // ❌ 编译错误：不能将 int 赋给 String
    }

    // ============================================================
    // 2. var 适用场景（推荐使用）
    // ============================================================
    public void goodUseCases() {
        /*
         * 适用场景：
         * ┌─────────────────────────────────────────────────────────┐
         * │ 1. 右侧类型明显时                                        │
         * │ 2. 复杂泛型类型                                          │
         * │ 3. for-each 循环                                        │
         * │ 4. try-with-resources                                   │
         * └─────────────────────────────────────────────────────────┘
         */

        // 场景1: 右侧类型明显（new 表达式）
        var user = new User("Alice", 25);  // 明显是 User
        var numbers = List.of(1, 2, 3);     // 明显是 List

        // 场景2: 复杂泛型（var 大幅简化）
        var groupedData = numbers.stream()
                .collect(Collectors.groupingBy(
                        n -> n % 2 == 0 ? "偶数" : "奇数",
                        Collectors.toList()
                ));
        // 不用 var: Map<String, List<Integer>> groupedData = ...
        System.out.println("分组结果: " + groupedData);

        // 场景3: for-each 循环
        var names = List.of("Alice", "Bob", "Charlie");
        for (var n : names) {  // var 替代 String
            System.out.println("名字: " + n);
        }

        // TODO(human): 使用 var 简化下面的代码
        // 将传统写法改为 var 写法
        // 传统写法:
        Map<String, List<String>> traditionalMap = new HashMap<String, List<String>>();
        traditionalMap.put("fruits", Arrays.asList("apple", "banana"));
        traditionalMap.put("colors", Arrays.asList("red", "blue"));

        // 用 var 重写:
        // var modernMap = ???
        var modernMap = new HashMap<String, List<String>>();
        System.out.println("传统 Map: " + traditionalMap);
    }

    // ============================================================
    // 3. var 不适用场景（避免使用）
    // ============================================================
    public void badUseCases() {
        /*
         * 不适用场景：
         * ┌─────────────────────────────────────────────────────────┐
         * │ 1. 类型不明显，降低可读性                                 │
         * │ 2. 不能用于字段、参数、返回值                             │
         * │ 3. 不能初始化为 null                                     │
         * │ 4. 不能用于 Lambda 参数（JDK 11 可以）                   │
         * └─────────────────────────────────────────────────────────┘
         */

        // ❌ 不好：类型不明显
        var result = getData();  // 返回什么类型？不看方法定义不知道
        System.out.println("getData() 返回: " + result);

        // ❌ 不好：字面量可能推断出非预期类型
        var num = 100;      // int，不是 Integer
        var bigNum = 100L;  // long
        var pi = 3.14;      // double，不是 float

        // ❌ 编译错误示例（已注释）
        // var nothing;           // 必须有初始值
        // var nullValue = null;  // 不能是 null
        // private var field;     // 不能用于字段

        System.out.println("num 类型: " + ((Object) num).getClass().getSimpleName());
    }

    private Object getData() {
        return "some data";
    }

    static class User {
        String name;
        int age;

        User(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }
}
