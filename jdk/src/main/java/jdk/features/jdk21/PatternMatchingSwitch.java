package jdk.features.jdk21;

/**
 * JDK 21: Pattern Matching for switch（正式版）
 *
 * 核心特性：
 * 1. switch 支持类型模式匹配
 * 2. 配合 Sealed Classes 实现穷尽性检查
 * 3. when 守卫条件
 * 4. null 处理
 *
 * 这是 JDK 17 ADT 能力的完善版！
 */
public class PatternMatchingSwitch {

    public static void main(String[] args) {
        PatternMatchingSwitch demo = new PatternMatchingSwitch();

        System.out.println("=== 1. 基本类型匹配 ===");
        demo.basicTypeMatching();

        System.out.println("\n=== 2. when 守卫条件 ===");
        demo.guardedPatterns();

        System.out.println("\n=== 3. Sealed Classes 穷尽性检查 ===");
        demo.exhaustiveMatching();

        System.out.println("\n=== 4. null 处理 ===");
        demo.nullHandling();

        System.out.println("\n=== 5. 实战练习 ===");
        demo.practice();
    }

    // ============================================================
    // 1. 基本类型匹配
    // ============================================================
    public void basicTypeMatching() {
        Object[] items = {42, "hello", 3.14, true, null};

        for (Object item : items) {
            String result = switch (item) {
                case Integer i -> "整数: " + i + " (平方=" + i * i + ")";
                case String s  -> "字符串: \"" + s + "\" (长度=" + s.length() + ")";
                case Double d  -> "小数: " + d;
                case null      -> "空值!";
                default        -> "其他类型: " + item.getClass().getSimpleName();
            };
            System.out.println(result);
        }
    }

    // ============================================================
    // 2. when 守卫条件
    // ============================================================
    public void guardedPatterns() {
        // 注意：JDK 21 的 Pattern Matching 只支持引用类型
        // 基元类型（int, double 等）要到 JDK 23 才支持
        Integer[] numbers = {-5, 0, 42, 100};

        for (Integer num : numbers) {
            String category = switch (num) {
                case Integer n when n < 0   -> "负数";
                case Integer n when n == 0  -> "零";
                case Integer n when n <= 10 -> "小正数 (1-10)";
                case Integer n when n <= 99 -> "中等数 (11-99)";
                case Integer n              -> "大数 (≥100)";  // 默认分支
            };
            System.out.println(num + " -> " + category);
        }

        // 字符串长度分类
        String[] words = {"", "Hi", "Hello", "Programming"};
        for (String word : words) {
            String lengthCategory = switch (word) {
                case String s when s.isEmpty() -> "空字符串";
                case String s when s.length() <= 3 -> "短";
                case String s when s.length() <= 6 -> "中";
                case String s -> "长";
            };
            System.out.println("\"" + word + "\" -> " + lengthCategory);
        }
    }

    // ============================================================
    // 3. Sealed Classes 穷尽性检查（重点！）
    // ============================================================

    // 定义密封接口
    sealed interface Shape permits Circle, Rectangle, Triangle {}

    record Circle(double radius) implements Shape {}
    record Rectangle(double width, double height) implements Shape {}
    record Triangle(double base, double height) implements Shape {}

    public void exhaustiveMatching() {
        Shape[] shapes = {
            new Circle(5),
            new Rectangle(4, 6),
            new Triangle(3, 4)
        };

        for (Shape shape : shapes) {
            // 穷尽性检查：不需要 default！
            // 如果新增 Shape 子类，这里会编译报错
            String description = switch (shape) {
                case Circle c    -> "圆形，半径: " + c.radius() +
                                    ", 面积: " + String.format("%.2f", Math.PI * c.radius() * c.radius());
                case Rectangle r -> "矩形，宽: " + r.width() + ", 高: " + r.height() +
                                    ", 面积: " + (r.width() * r.height());
                case Triangle t  -> "三角形，底: " + t.base() + ", 高: " + t.height() +
                                    ", 面积: " + (0.5 * t.base() * t.height());
            };
            System.out.println(description);
        }

        // 对比 JDK 17 写法（冗长！）
        System.out.println("\n--- 对比 JDK 17 的写法 ---");
        for (Shape shape : shapes) {
            String oldWay;
            if (shape instanceof Circle c) {
                oldWay = "圆形，半径: " + c.radius();
            } else if (shape instanceof Rectangle r) {
                oldWay = "矩形: " + r.width() + "×" + r.height();
            } else if (shape instanceof Triangle t) {
                oldWay = "三角形";
            } else {
                oldWay = "未知";  // 必须有，即使不可能到达
            }
            System.out.println("JDK17: " + oldWay);
        }
    }

    // ============================================================
    // 4. null 处理
    // ============================================================
    public void nullHandling() {
        String[] inputs = {"hello", null, ""};

        for (String input : inputs) {
            // 传统方式需要先判断 null
            // if (input == null) { ... }

            // JDK 21: switch 直接处理 null
            String result = switch (input) {
                case null -> "输入为 null";
                case String s when s.isEmpty() -> "输入为空字符串";
                case String s -> "输入: " + s;
            };
            System.out.println(result);
        }
    }

    // ============================================================
    // 5. 实战练习
    // ============================================================

    // 练习用的 ADT：HTTP 响应
    sealed interface HttpResponse permits Success, ClientError, ServerError {}
    record Success(int code, String body) implements HttpResponse {}
    record ClientError(int code, String message) implements HttpResponse {}
    record ServerError(int code, String message) implements HttpResponse {}

    public void practice() {
        HttpResponse[] responses = {
            new Success(200, "{\"status\": \"ok\"}"),
            new ClientError(404, "Not Found"),
            new ClientError(401, "Unauthorized"),
            new ServerError(500, "Internal Server Error"),
            new Success(201, "{\"id\": 123}")
        };

        for (HttpResponse response : responses) {
            // TODO(human): 使用 Pattern Matching for switch 处理 HTTP 响应
            // 要求：
            // 1. Success: 根据 code 区分 (200="OK", 201="Created", 其他="Success")
            // 2. ClientError: 输出 "客户端错误 [code]: message"
            // 3. ServerError: 输出 "服务器错误 [code]: message"
            // 提示：可以使用 when 守卫条件处理 Success 的不同 code

            String result = switch (response) {
                // ✅ 使用 when 守卫条件区分 Success 的不同 code
                case Success s when s.code() == 200 -> "OK: " + s.body();
                case Success s when s.code() == 201 -> "Created: " + s.body();
                case Success s -> "Success [" + s.code() + "]: " + s.body();
                // ✅ 可以为特定错误码添加额外处理
                case ClientError c when c.code() == 401 -> "认证失败: " + c.message();
                case ClientError c when c.code() == 404 -> "资源不存在: " + c.message();
                case ClientError c -> "客户端错误 [" + c.code() + "]: " + c.message();
                case ServerError e -> "服务器错误 [" + e.code() + "]: " + e.message();
                // ✅ 不需要 default！Sealed interface 保证穷尽性
            };
            System.out.println(result);
        }
    }
}
