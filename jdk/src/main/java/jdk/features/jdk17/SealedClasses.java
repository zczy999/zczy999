package jdk.features.jdk17;

/**
 * JDK 17: Sealed Classes（密封类）
 *
 * 核心概念：
 * - 限制哪些类可以继承/实现一个类或接口
 * - 子类必须是 final、sealed 或 non-sealed
 * - 配合 Pattern Matching 使用更强大
 *
 * 使用场景：
 * - 领域建模（状态机、表达式树）
 * - 确保类型完整性
 * - 替代枚举的复杂场景
 */
public class SealedClasses {

    public static void main(String[] args) {
        SealedClasses demo = new SealedClasses();

        System.out.println("=== 1. Sealed Classes 基本用法 ===");
        demo.basicUsage();

        System.out.println("\n=== 2. 配合 Pattern Matching ===");
        demo.withPatternMatching();

        System.out.println("\n=== 3. 实际应用场景 ===");
        demo.practicalExample();
    }

    // ============================================================
    // 1. Sealed Classes 基本用法
    // ============================================================

    // 定义密封类：只允许 Circle, Rectangle, Triangle 继承
    sealed interface Shape permits Circle, Rectangle, Triangle {
        double area();
    }

    // final: 不能再被继承
    static final class Circle implements Shape {
        private final double radius;

        Circle(double radius) {
            this.radius = radius;
        }

        @Override
        public double area() {
            return Math.PI * radius * radius;
        }

        public double radius() { return radius; }
    }

    // sealed: 继续限制，只允许 Square 继承
    static sealed class Rectangle implements Shape permits Square {
        protected final double width, height;

        Rectangle(double width, double height) {
            this.width = width;
            this.height = height;
        }

        @Override
        public double area() {
            return width * height;
        }
    }

    // final: Square 是 Rectangle 的最终子类
    static final class Square extends Rectangle {
        Square(double side) {
            super(side, side);
        }
    }

    // non-sealed: 开放继承，任何类都可以继承 Triangle
    static non-sealed class Triangle implements Shape {
        private final double base, height;

        Triangle(double base, double height) {
            this.base = base;
            this.height = height;
        }

        @Override
        public double area() {
            return 0.5 * base * height;
        }
    }

    // 因为 Triangle 是 non-sealed，可以自由继承
    static class RightTriangle extends Triangle {
        RightTriangle(double base, double height) {
            super(base, height);
        }
    }

    public void basicUsage() {
        Shape circle = new Circle(5);
        Shape rectangle = new Rectangle(4, 6);
        Shape square = new Square(4);
        Shape triangle = new Triangle(3, 4);

        System.out.println("Circle 面积: " + circle.area());
        System.out.println("Rectangle 面积: " + rectangle.area());
        System.out.println("Square 面积: " + square.area());
        System.out.println("Triangle 面积: " + triangle.area());

        // 编译器知道所有可能的子类型！
        System.out.println("\n密封类的子类是已知的、有限的");
    }

    // ============================================================
    // 2. 配合 Pattern Matching（JDK 17+）
    // ============================================================
    public void withPatternMatching() {
        Shape[] shapes = {
            new Circle(3),
            new Rectangle(4, 5),
            new Square(4),
            new Triangle(3, 4)
        };

        for (Shape shape : shapes) {
            String description = describeShape(shape);
            System.out.println(description);
        }
    }

    private String describeShape(Shape shape) {
        // Pattern Matching for instanceof
        if (shape instanceof Circle c) {
            return "圆形，半径: " + c.radius() + ", 面积: " + c.area();
        } else if (shape instanceof Square s) {
            // 注意：Square 要在 Rectangle 之前判断！
            return "正方形，边长: " + s.width + ", 面积: " + s.area();
        } else if (shape instanceof Rectangle r) {
            return "矩形，宽: " + r.width + ", 高: " + r.height + ", 面积: " + r.area();
        } else if (shape instanceof Triangle t) {
            return "三角形，面积: " + t.area();
        }
        return "未知形状";
    }

    // ============================================================
    // 3. 实际应用场景：表达式树
    // ============================================================

    // 密封接口：数学表达式只能是这三种
    sealed interface Expr permits Constant, Variable, BinaryOp {}

    record Constant(int value) implements Expr {}
    record Variable(String name) implements Expr {}
    record BinaryOp(Expr left, String op, Expr right) implements Expr {}

    public void practicalExample() {
        // 构建表达式: (x + 5) * 2
        Expr expr = new BinaryOp(
            new BinaryOp(new Variable("x"), "+", new Constant(5)),
            "*",
            new Constant(2)
        );

        System.out.println("表达式: " + exprToString(expr));

        // 假设 x = 3，计算结果
        int result = evaluate(expr, 3);
        System.out.println("当 x=3 时，结果: " + result);
    }

    private String exprToString(Expr expr) {
        if (expr instanceof Constant c) {
            return String.valueOf(c.value());
        } else if (expr instanceof Variable v) {
            return v.name();
        } else if (expr instanceof BinaryOp b) {
            return "(" + exprToString(b.left()) + " " + b.op() + " " + exprToString(b.right()) + ")";
        }
        throw new IllegalArgumentException("未知表达式类型");
    }

    private int evaluate(Expr expr, int xValue) {
        if (expr instanceof Constant c) {
            return c.value();
        } else if (expr instanceof Variable v) {
            return xValue;  // 假设只有一个变量 x
        } else if (expr instanceof BinaryOp b) {
            int left = evaluate(b.left(), xValue);
            int right = evaluate(b.right(), xValue);
            return switch (b.op()) {
                case "+" -> left + right;
                case "-" -> left - right;
                case "*" -> left * right;
                case "/" -> left / right;
                default -> throw new IllegalArgumentException("未知运算符: " + b.op());
            };
        }
        throw new IllegalArgumentException("未知表达式类型");
    }
}
