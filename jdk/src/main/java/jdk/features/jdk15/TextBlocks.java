package jdk.features.jdk15;

/**
 * JDK 15: 文本块 (Text Blocks)
 *
 * 核心特性：
 * 1. 三引号 """ 定义多行字符串
 * 2. 保留格式和缩进
 * 3. 自动处理换行符
 * 4. 适合 SQL、JSON、HTML、正则等
 */
public class TextBlocks {

    public static void main(String[] args) {
        TextBlocks demo = new TextBlocks();

        System.out.println("=== 1. 基本语法 ===");
        demo.basicSyntax();

        System.out.println("\n=== 2. 实际应用场景 ===");
        demo.practicalUsage();

        System.out.println("\n=== 3. 缩进规则 ===");
        demo.indentationRules();

        System.out.println("\n=== 4. 实战练习 ===");
        demo.practice();
    }

    // ============================================================
    // 1. 基本语法
    // ============================================================
    public void basicSyntax() {
        // 传统写法
        String oldWay = "第一行\n" +
                        "第二行\n" +
                        "第三行";
        System.out.println("传统写法:\n" + oldWay);

        // 文本块写法
        String newWay = """
                第一行
                第二行
                第三行""";
        System.out.println("\n文本块写法:\n" + newWay);

        // 两者内容完全相同
        System.out.println("\n内容相等: " + oldWay.equals(newWay));
    }

    // ============================================================
    // 2. 实际应用场景
    // ============================================================
    public void practicalUsage() {
        // SQL 查询
        String sql = """
                SELECT u.id, u.name, u.email
                FROM users u
                JOIN orders o ON u.id = o.user_id
                WHERE u.age > 18
                  AND o.status = 'COMPLETED'
                ORDER BY u.name""";
        System.out.println("SQL:\n" + sql);

        // JSON
        String json = """
                {
                    "name": "Alice",
                    "age": 25,
                    "address": {
                        "city": "Beijing",
                        "district": "Chaoyang"
                    }
                }""";
        System.out.println("\nJSON:\n" + json);

        // HTML
        String html = """
                <html>
                    <body>
                        <h1>Hello World</h1>
                        <p>Welcome to JDK 15!</p>
                    </body>
                </html>""";
        System.out.println("\nHTML:\n" + html);
    }

    // ============================================================
    // 3. 缩进规则
    // ============================================================
    public void indentationRules() {
        /*
         * 文本块会自动去除"公共前导空白"
         * 结束引号 """ 的位置决定了基准缩进
         */

        // 结束引号与内容对齐：保留相对缩进
        String aligned = """
                Line 1
                    Line 2 (缩进4格)
                Line 3""";
        System.out.println("对齐结束引号:\n" + aligned);

        // 结束引号左移：所有内容都会缩进
        String leftEnd = """
                Line 1
                Line 2
        """;  // 结束引号左移了
        System.out.println("\n左移结束引号:\n'" + leftEnd + "'");

        // 末尾换行：结束引号单独一行会产生末尾换行
        String withNewline = """
                Hello
                """;
        String noNewline = """
                Hello""";
        System.out.println("有末尾换行: '" + withNewline + "'");
        System.out.println("无末尾换行: '" + noNewline + "'");
    }

    // ============================================================
    // 4. 实战练习
    // ============================================================
    public void practice() {
        // HTML 表格
        String htmlTable = """
                <table border="1">
                    <tr><th>姓名</th><th>分数</th></tr>
                    <tr><td>Alice</td><td>95</td></tr>
                    <tr><td>Bob</td><td>87</td></tr>
                </table>
                """;
        System.out.println("HTML 表格:\n" + htmlTable);

        // SQL 插入语句
        String insertSql = """
                INSERT INTO students (name, score) VALUES
                ('Alice', 95),
                ('Bob', 87),
                ('Charlie', 92);
                """;
        System.out.println("SQL 插入:\n" + insertSql);
    }
}
