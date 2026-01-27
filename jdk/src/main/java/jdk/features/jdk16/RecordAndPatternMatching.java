package jdk.features.jdk16;

import java.util.List;

/**
 * JDK 16: Record 记录类 + Pattern Matching for instanceof
 *
 * Record 特性：
 * - 一行定义不可变数据类
 * - 自动生成: 构造函数、getter、equals、hashCode、toString
 * - 字段默认 final，不可变
 *
 * Pattern Matching 特性：
 * - instanceof 时自动绑定变量，无需强制转换
 */
public class RecordAndPatternMatching {

    public static void main(String[] args) {
        RecordAndPatternMatching demo = new RecordAndPatternMatching();

        System.out.println("=== 1. Record 基本用法 ===");
        demo.recordBasics();

        System.out.println("\n=== 2. Record 进阶 ===");
        demo.recordAdvanced();

        System.out.println("\n=== 3. Pattern Matching instanceof ===");
        demo.patternMatching();

        System.out.println("\n=== 4. 实战练习 ===");
        demo.practice();
    }

    // ============================================================
    // 1. Record 基本用法
    // ============================================================
    public void recordBasics() {
        // 定义一个 Record（一行！）
        record Person(String name, int age) {}

        // 自动生成的功能
        var p1 = new Person("Alice", 25);
        var p2 = new Person("Alice", 25);

        // getter：方法名是字段名，不是 getXxx
        System.out.println("name: " + p1.name());
        System.out.println("age: " + p1.age());

        // toString 自动生成
        System.out.println("toString: " + p1);

        // equals 基于所有字段比较
        System.out.println("equals: " + p1.equals(p2));  // true

        // hashCode 基于所有字段计算
        System.out.println("hashCode 相等: " + (p1.hashCode() == p2.hashCode()));

        // Record 是不可变的
        // p1.name = "Bob";  // ❌ 编译错误，字段是 final
    }

    // ============================================================
    // 2. Record 进阶
    // ============================================================
    public void recordAdvanced() {
        // Record 可以有自定义构造函数（紧凑构造函数）
        record Email(String address) {
            // 紧凑构造函数：用于验证
            public Email {
                if (address == null || !address.contains("@")) {
                    throw new IllegalArgumentException("无效邮箱: " + address);
                }
                address = address.toLowerCase();  // 可以修改参数
            }
        }

        var email = new Email("Alice@Test.COM");
        System.out.println("Email: " + email.address());  // alice@test.com

        // Record 可以有静态方法和实例方法
        record Point(int x, int y) {
            // 静态工厂方法
            public static Point origin() {
                return new Point(0, 0);
            }

            // 实例方法
            public double distanceTo(Point other) {
                int dx = this.x - other.x;
                int dy = this.y - other.y;
                return Math.sqrt(dx * dx + dy * dy);
            }
        }

        var origin = Point.origin();
        var p = new Point(3, 4);
        System.out.println("原点到 (3,4) 距离: " + origin.distanceTo(p));

        // Record 可以实现接口
        record NamedPoint(String name, int x, int y) implements Comparable<NamedPoint> {
            @Override
            public int compareTo(NamedPoint other) {
                return this.name.compareTo(other.name);
            }
        }
    }

    // ============================================================
    // 3. Pattern Matching for instanceof
    // ============================================================
    public void patternMatching() {
        Object obj = "Hello, Pattern Matching!";

        // 传统写法：检查类型 + 强制转换
        if (obj instanceof String) {
            String s = (String) obj;  // 繁琐的强制转换
            System.out.println("传统: 长度 = " + s.length());
        }

        // 新写法：Pattern Matching，自动绑定变量
        if (obj instanceof String s) {
            // s 已经是 String 类型，直接使用
            System.out.println("新语法: 长度 = " + s.length());
        }

        // 配合逻辑运算符
        if (obj instanceof String s && s.length() > 10) {
            System.out.println("长字符串: " + s);
        }

        // 在 else 分支中，变量不可用
        if (!(obj instanceof String s)) {
            // s 在这里不可用
            System.out.println("不是字符串");
        } else {
            // s 在这里可用
            System.out.println("是字符串: " + s);
        }
    }

    // ============================================================
    // 4. 实战练习
    // ============================================================
    public void practice() {
        // TODO(human): 定义一个 Rectangle record，包含 width 和 height
        // 并添加一个 area() 方法计算面积
        // record Rectangle(???) { ??? }
        record Rectangle(int width, int height) {

            public int area() {
                return width * height;
            }
        }

        System.out.println("--- Rectangle ---");
         var rect = new Rectangle(10, 5);
         System.out.println("矩形: " + rect);
         System.out.println("面积: " + rect.area());

        // 使用 Pattern Matching 实现类型判断
        System.out.println("\n--- 类型判断 ---");
        Object[] items = {42, "hello", 3.14, true};
        for (Object item : items) {
            if (item instanceof Integer i) {
                System.out.println("整数: " + i);
            } else if (item instanceof String s) {
                System.out.println("字符串: " + s + ", 长度: " + s.length());
            } else if (item instanceof Double d) {
                System.out.println("小数: " + d);
            } else {
                System.out.println("未知类型: " + item);
            }
        }
    }
}
