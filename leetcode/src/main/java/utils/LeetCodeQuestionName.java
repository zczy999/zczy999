package utils;

import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * LeetCode 题目名称格式化工具类
 *
 * 将各种格式的 LeetCode 题目字符串转换为标准的文件名格式
 * 例如："48. Rotate Image" → "Rotate_Image_48"
 *
 * @author Claude Code
 * @version 2.0
 */
public class LeetCodeQuestionName {

    // 常量定义
    private static final String DOT_SEPARATOR = ".";
    private static final String COLON_SEPARATOR = ":";
    private static final String HYPHEN = "-";
    private static final String SPACE = " ";
    private static final String UNDERSCORE = "_";
    private static final String WHITESPACE_REGEX = "\\s+";

    // 默认分隔符优先级顺序
    private static final String[] SEPARATORS = {DOT_SEPARATOR, COLON_SEPARATOR};

    // 输出格式枚举
    public enum OutputFormat {
        UNDERSCORE,    // 下划线分隔 (默认)
        CAMEL_CASE,    // 驼峰命名
        PASCAL_CASE,   // 帕斯卡命名
        KEBAB_CASE     // 短横线分隔
    }

    /**
     * 主方法，用于演示格式化功能
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        // 简单演示
        String input = "48. Rotate Image";
        String formatted = formatString(input);
        System.out.println("简单演示:");
        System.out.println("输入: \"" + input + "\"");
        System.out.println("输出: \"" + formatted + "\""); // Outputs: Rotate_Image_48
    }

      /**
     * 格式化 LeetCode 题目字符串为标准文件名格式（使用默认下划线格式）
     *
     * 输入示例：
     * - "48. Rotate Image" → "Rotate_Image_48"
     * - "15. 3Sum" → "3Sum_15"
     * - "Two Sum" → "Two_Sum"
     *
     * @param input 输入的题目字符串
     * @return 格式化后的文件名字符串
     */
    public static String formatString(String input) {
        return formatString(input, OutputFormat.UNDERSCORE);
    }

    /**
     * 格式化 LeetCode 题目字符串为指定格式的文件名
     *
     * 支持多种输出格式：
     * - UNDERSCORE: "48. Rotate Image" → "Rotate_Image_48"
     * - CAMEL_CASE: "48. Rotate Image" → "rotateImage48"
     * - PASCAL_CASE: "48. Rotate Image" → "RotateImage48"
     * - KEBAB_CASE: "48. Rotate Image" → "Rotate-Image-48"
     *
     * @param input 输入的题目字符串
     * @param format 输出格式
     * @return 格式化后的文件名字符串
     */
    public static String formatString(String input, OutputFormat format) {
        // 输入验证
        if (input == null || input.trim().isEmpty()) {
            return "";
        }

        // 初始化变量来存储数字前缀和文本部分
        String number = "";
        String text = input.trim();

        // 查找分隔符位置，支持多种分隔符
        int separatorIndex = findSeparatorIndex(text);

        if (separatorIndex != -1 && separatorIndex < text.length() - 1) {
            // 尝试解析数字前缀
            String potentialNumber = text.substring(0, separatorIndex).trim();
            if (isValidNumber(potentialNumber)) {
                number = potentialNumber;
                text = text.substring(separatorIndex + 1).trim(); // 取分隔符后的部分
            }
        }

        // 将所有连字符替换为空格，以便统一处理分隔符
        text = text.replace(HYPHEN, SPACE);

        // 分割文本并转换每个单词
        String[] words = Arrays.stream(text.split(WHITESPACE_REGEX)) // 按空白字符分割
                .filter(word -> !word.isEmpty()) // 过滤空单词
                .map(LeetCodeQuestionName::capitalizeWord) // 恢复大小写转换
                .toArray(String[]::new);

        // 根据格式类型生成输出
        String formattedText = formatWords(words, format);

        // 如果存在题号，根据格式类型添加到末尾
        if (!number.isEmpty()) {
            formattedText = appendNumber(formattedText, number, format);
        }

        return formattedText;
    }

    /**
     * 查找分隔符的索引位置
     * 支持点号、冒号等常见分隔符，按优先级顺序查找
     *
     * @param text 输入文本
     * @return 分隔符的索引，如果没找到返回 -1
     */
    private static int findSeparatorIndex(String text) {
        // 按优先级顺序查找分隔符
        for (String separator : SEPARATORS) {
            int index = text.indexOf(separator);
            if (index != -1) {
                return index;
            }
        }

        return -1;
    }

    /**
     * 验证字符串是否为有效的数字
     *
     * @param str 要验证的字符串
     * @return 如果是有效数字返回 true，否则返回 false
     */
    private static boolean isValidNumber(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }

        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 将单词转换为首字母大写，其余小写的格式
     * 特殊处理：如果单词全部是大写或包含数字，保持原样
     *
     * @param word 输入单词
     * @return 格式化后的单词
     */
    private static String capitalizeWord(String word) {
        if (word == null || word.isEmpty()) {
            return word;
        }

        // 如果单词包含数字，可能是技术术语或变量名，保持原样
        if (word.matches(".*\\d+.*")) {
            return word;
        }

        // 如果单词全部是大写，可能是缩写，保持原样
        if (word.equals(word.toUpperCase())) {
            return word;
        }

        if (word.length() == 1) {
            return word.toUpperCase();
        }

        return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
    }

    /**
     * 根据指定格式格式化单词数组
     *
     * @param words 单词数组
     * @param format 输出格式
     * @return 格式化后的字符串
     */
    private static String formatWords(String[] words, OutputFormat format) {
        if (words == null || words.length == 0) {
            return "";
        }

        switch (format) {
            case UNDERSCORE:
                return String.join(UNDERSCORE, words);

            case CAMEL_CASE:
                StringBuilder camelCase = new StringBuilder();
                for (int i = 0; i < words.length; i++) {
                    String word = words[i];
                    if (i == 0) {
                        camelCase.append(word.toLowerCase());
                    } else {
                        camelCase.append(capitalizeWord(word));
                    }
                }
                return camelCase.toString();

            case PASCAL_CASE:
                StringBuilder pascalCase = new StringBuilder();
                for (String word : words) {
                    pascalCase.append(capitalizeWord(word));
                }
                return pascalCase.toString();

            case KEBAB_CASE:
                return String.join(HYPHEN, words);

            default:
                return String.join(UNDERSCORE, words);
        }
    }

    /**
     * 根据格式类型将数字附加到字符串末尾
     *
     * @param text 原始文本
     * @param number 数字
     * @param format 输出格式
     * @return 附加数字后的字符串
     */
    private static String appendNumber(String text, String number, OutputFormat format) {
        if (text == null || text.isEmpty()) {
            return number;
        }

        switch (format) {
            case UNDERSCORE:
                return text + UNDERSCORE + number;

            case CAMEL_CASE:
            case PASCAL_CASE:
                return text + number;

            case KEBAB_CASE:
                return text + HYPHEN + number;

            default:
                return text + UNDERSCORE + number;
        }
    }

    /**
     * 测试方法，演示各种输入格式的处理结果
     */
    @Test
    public  void runTests() {
        System.out.println("=== LeetCode 题目名称格式化测试 ===\n");

        // 测试基本功能
        testBasicFormats();

        System.out.println("\n" + "=".repeat(80));

        // 测试不同输出格式
        testOutputFormats();

        System.out.println("\n" + "=".repeat(80));

        // 测试边界情况
        testEdgeCases();
    }

    /**
     * 测试基本的格式化功能
     */
    private static void testBasicFormats() {
        System.out.println("基本格式化测试（默认下划线格式）：");
        System.out.println("-".repeat(60));

        String[] testCases = {
            "48. Rotate Image",
            "15. 3Sum",
            "1. Two Sum",
            "121. Best Time to Buy and Sell Stock",
            "Longest Substring Without Repeating Characters",
            "3: Longest Substring Without Repeating Characters",
            "container-with-most-water",
            "Valid Parentheses",
            "",
            null,
            "invalid.format.no.number",
            "12345. Very Long Title With Many Words"
        };

        for (String testCase : testCases) {
            System.out.printf("输入: %-45s → 输出: %s%n",
                "\"" + testCase + "\"",
                "\"" + formatString(testCase) + "\"");
        }
    }

    /**
     * 测试不同的输出格式
     */
    private static void testOutputFormats() {
        System.out.println("多格式输出测试：");
        System.out.println("-".repeat(80));

        String[] testInputs = {
            "48. Rotate Image",
            "15. 3Sum",
            "Two Sum",
            "JSON 101: Introduction to JSON",
            "A-B-C-D-E-Complex-Title",
            "   42.   Spaces   Everywhere   ",
            "1",  // 最短有效输入
            "9999. Very Long Title With Many Words And Mixed CASE letters"
        };

        OutputFormat[] formats = OutputFormat.values();

        System.out.printf("%-25s", "输入");
        for (OutputFormat format : formats) {
            System.out.printf("%-20s", format.name());
        }
        System.out.println();

        for (String input : testInputs) {
            System.out.printf("%-25s", "\"" + input + "\"");
            for (OutputFormat format : formats) {
                String result = formatString(input, format);
                System.out.printf("%-20s", "\"" + result + "\"");
            }
            System.out.println();
        }
    }

    /**
     * 测试边界情况和异常输入
     */
    private static void testEdgeCases() {
        System.out.println("边界情况和异常输入测试：");
        System.out.println("-".repeat(60));

        String[][] edgeCases = {
            // {输入, 期望的描述}
            {"", "空字符串"},
            {"   ", "只有空格"},
            {null, "null 输入"},
            {".", "只有分隔符"},
            {"123.", "只有数字和分隔符"},
            {".No Number", "分隔符开头但没有数字"},
            {"999.", "数字加分隔符但没有文本"},
            {"1.2.3. Multiple Dots", "多个分隔符"},
            {"a.b.c.d.e", "多个分隔符但没有数字"},
            {"   1.    Trim    Spaces   ", "前后都有多余空格"},
            {"1. A", "最短有效题目"},
            {"1. ", "数字和分隔符但无文本"},
            {"No Separator At All", "没有分隔符"},
            {"ABC", "纯大写缩写"},
            {"xml123 parser", "包含数字的单词"},
            {"3D", "数字开头的技术术语"},
            {"C++", "编程语言"},
            {"Node.js", "技术栈名称"}
        };

        for (String[] testCase : edgeCases) {
            String input = testCase[0];
            String description = testCase[1];
            String result = formatString(input);

            System.out.printf("%-30s → %-15s (%s)%n",
                "\"" + input + "\"",
                "\"" + result + "\"",
                description);
        }
    }

    /**
     * 批量格式化多个题目名称
     *
     * @param inputs 题目名称数组
     * @return 格式化后的名称数组
     */
    public static String[] formatMultiple(String[] inputs) {
        if (inputs == null) {
            return new String[0];
        }

        return Arrays.stream(inputs)
                .map(LeetCodeQuestionName::formatString)
                .toArray(String[]::new);
    }
}
