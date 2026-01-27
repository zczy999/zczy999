package jdk.features.jdk14;

/**
 * JDK 14: Switch 表达式
 *
 * 新特性：
 * 1. 箭头语法 -> 替代 case: break;
 * 2. 可以作为表达式返回值
 * 3. 多值匹配 case "a", "b" ->
 * 4. yield 关键字用于代码块返回值
 */
public class SwitchExpression {

    public static void main(String[] args) {
        SwitchExpression demo = new SwitchExpression();

        System.out.println("=== 1. 传统 switch vs 新 switch ===");
        demo.compareSwitch();

        System.out.println("\n=== 2. Switch 表达式返回值 ===");
        demo.switchAsExpression();

        System.out.println("\n=== 3. yield 关键字 ===");
        demo.yieldKeyword();

        System.out.println("\n=== 4. 实战练习 ===");
        demo.practice();
    }

    // ============================================================
    // 1. 传统 switch vs 新 switch
    // ============================================================
    public void compareSwitch() {
        String day = "WEDNESDAY";

        // 传统 switch（容易忘记 break）
        String oldType;
        switch (day) {
            case "MONDAY":
            case "TUESDAY":
            case "WEDNESDAY":
            case "THURSDAY":
            case "FRIDAY":
                oldType = "工作日";
                break;
            case "SATURDAY":
            case "SUNDAY":
                oldType = "周末";
                break;
            default:
                oldType = "未知";
        }
        System.out.println("传统 switch: " + day + " -> " + oldType);

        // 新 switch（箭头语法 + 多值匹配）
        String newType = switch (day) {
            case "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY" -> "工作日";
            case "SATURDAY", "SUNDAY" -> "周末";
            default -> "未知";
        };
        System.out.println("新 switch: " + day + " -> " + newType);
    }

    // ============================================================
    // 2. Switch 表达式返回值
    // ============================================================
    public void switchAsExpression() {
        // switch 可以直接作为表达式返回值
        int month = 8;

        // 返回该月的天数
        int days = switch (month) {
            case 1, 3, 5, 7, 8, 10, 12 -> 31;
            case 4, 6, 9, 11 -> 30;
            case 2 -> 28;  // 简化，不考虑闰年
            default -> throw new IllegalArgumentException("无效月份: " + month);
        };
        System.out.println(month + " 月有 " + days + " 天");

        // 也可以用在方法调用中
        System.out.println("级别: " + getLevel(85));
        System.out.println("级别: " + getLevel(65));
        System.out.println("级别: " + getLevel(45));
    }

    private String getLevel(int score) {
        return switch (score / 10) {
            case 10, 9 -> "优秀";
            case 8 -> "良好";
            case 7 -> "中等";
            case 6 -> "及格";
            default -> "不及格";
        };
    }

    // ============================================================
    // 3. yield 关键字（代码块中返回值）
    // ============================================================
    public void yieldKeyword() {
        /*
         * yield 用于在 switch 代码块中返回值
         * 当需要多行逻辑时使用
         */
        String day = "WEDNESDAY";

        int numLetters = switch (day) {
            case "MONDAY", "FRIDAY", "SUNDAY" -> 6;
            case "TUESDAY" -> 7;
            case "THURSDAY", "SATURDAY" -> 8;
            case "WEDNESDAY" -> {
                // 代码块：可以有多行逻辑
                System.out.println("  计算 WEDNESDAY 的长度...");
                int length = day.length();
                yield length;  // yield 返回值
            }
            default -> throw new IllegalArgumentException("Invalid day: " + day);
        };
        System.out.println(day + " 有 " + numLetters + " 个字母");
    }

    // ============================================================
    // 4. 实战练习
    // ============================================================
    public void practice() {
        // TODO(human): 使用 switch 表达式实现季节判断
        // 输入月份 (1-12)，返回季节：
        // 3, 4, 5 -> "春季"
        // 6, 7, 8 -> "夏季"
        // 9, 10, 11 -> "秋季"
        // 12, 1, 2 -> "冬季"
        // 其他 -> 抛出 IllegalArgumentException

        int[] months = {1, 4, 7, 10, 13};
        for (int month : months) {
            try {
                // String season = switch (month) { ??? };
                String season = switch (month) {
                    case 3, 4, 5 -> "春季";
                    case 6, 7, 8 -> "夏季";
                    case 9, 10, 11 -> "秋季";
                    case 12, 1, 2 -> "冬季";
                    default -> throw new IllegalArgumentException("无效的月份: " + month);
                };
                System.out.println(month + " 月 -> " + season);
            } catch (IllegalArgumentException e) {
                System.out.println(month + " 月 -> 错误: " + e.getMessage());
            }
        }
    }
}
