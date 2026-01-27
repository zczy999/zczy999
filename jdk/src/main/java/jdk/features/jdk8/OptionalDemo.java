package jdk.features.jdk8;

import java.util.Optional;

/**
 * JDK 8 Optional 优雅空值处理
 *
 * 核心思想：
 * - Optional 是一个容器，可能包含值，也可能为空
 * - 强制调用者处理"值不存在"的情况，避免 NPE
 * - 不要用 Optional 作为字段或方法参数，主要用于返回值
 *
 * ┌─────────────────────────────────────────────────────────────┐
 * │  Optional<T>                                                │
 * │  ┌─────────────────┐    ┌─────────────────┐                │
 * │  │  Optional[value]│    │  Optional.empty │                │
 * │  │    有值         │    │    无值          │                │
 * │  └─────────────────┘    └─────────────────┘                │
 * └─────────────────────────────────────────────────────────────┘
 */
public class OptionalDemo {

    public static void main(String[] args) {
        OptionalDemo demo = new OptionalDemo();

        System.out.println("=== 1. Optional 创建方式 ===");
        demo.createOptional();

        System.out.println("\n=== 2. 获取值的方式 ===");
        demo.getValue();

        System.out.println("\n=== 3. 链式调用（核心用法） ===");
        demo.chainOperations();

        System.out.println("\n=== 4. 实战：替代 null 检查 ===");
        demo.practicalExample();
    }

    // ============================================================
    // 1. Optional 创建方式
    // ============================================================
    public void createOptional() {
        /*
         * 三种创建方式：
         * ┌──────────────────┬─────────────────────────────────────┐
         * │ 方法             │ 说明                                 │
         * ├──────────────────┼─────────────────────────────────────┤
         * │ Optional.of(v)   │ 值不能为 null，否则抛 NPE            │
         * │ Optional.empty() │ 创建空 Optional                      │
         * │ Optional.ofNullable(v) │ 值可以为 null（最常用）        │
         * └──────────────────┴─────────────────────────────────────┘
         */

        // of(): 确定不为 null 时使用
        Optional<String> opt1 = Optional.of("Hello");
        System.out.println("of(): " + opt1);

        // empty(): 明确表示空
        Optional<String> opt2 = Optional.empty();
        System.out.println("empty(): " + opt2);

        // ofNullable(): 不确定是否为 null 时使用（最安全）
        String maybeNull = Math.random() > 0.5 ? "World" : null;
        Optional<String> opt3 = Optional.ofNullable(maybeNull);
        System.out.println("ofNullable(): " + opt3);

        // ⚠️ 错误示范：of(null) 会抛 NPE
        // Optional<String> bad = Optional.of(null); // NullPointerException!
    }

    // ============================================================
    // 2. 获取值的方式
    // ============================================================
    public void getValue() {
        Optional<String> present = Optional.of("Java");
        Optional<String> absent = Optional.empty();

        /*
         * 获取值的方式对比：
         * ┌──────────────────┬────────────────┬─────────────────────┐
         * │ 方法             │ 空时行为        │ 推荐度               │
         * ├──────────────────┼────────────────┼─────────────────────┤
         * │ get()            │ 抛异常         │ ❌ 不推荐            │
         * │ orElse(default)  │ 返回默认值     │ ✅ 推荐              │
         * │ orElseGet(supplier)│ 懒加载默认值 │ ✅ 推荐（性能更好）   │
         * │ orElseThrow()    │ 抛自定义异常   │ ✅ 需要异常时使用     │
         * └──────────────────┴────────────────┴─────────────────────┘
         */

        // get(): 不推荐，空时抛 NoSuchElementException
        System.out.println("get(): " + present.get());
        // absent.get(); // 会抛异常！

        // orElse(): 提供默认值
        System.out.println("orElse(): " + absent.orElse("默认值"));

        // TODO(human): 使用 orElseGet 提供默认值
        // orElseGet 接收一个 Supplier，只有在值为空时才调用
        // 提示：Supplier 是无参返回值的函数式接口 () -> 返回值
        String result = absent.orElseGet(()->"默认值"); // 用 orElseGet 替换
        // result = absent.orElseGet(???);
        System.out.println("orElseGet(): " + result);

        // orElseThrow(): 空时抛出自定义异常
        try {
            String value = absent.orElseThrow(() -> new IllegalStateException("值不存在"));
        } catch (IllegalStateException e) {
            System.out.println("orElseThrow() 捕获异常: " + e.getMessage());
        }

        // isPresent() 和 ifPresent()
        System.out.println("isPresent(): " + present.isPresent()); // true
        present.ifPresent(v -> System.out.println("ifPresent(): " + v));
    }

    // ============================================================
    // 3. 链式调用（核心用法）
    // ============================================================
    public void chainOperations() {
        /*
         * Optional 链式操作：
         * ┌──────────────────┬─────────────────────────────────────┐
         * │ 方法             │ 说明                                 │
         * ├──────────────────┼─────────────────────────────────────┤
         * │ map(Function)    │ 转换值（自动包装为 Optional）        │
         * │ flatMap(Function)│ 转换值（Function 返回 Optional）    │
         * │ filter(Predicate)│ 过滤，不满足条件变为 empty          │
         * └──────────────────┴─────────────────────────────────────┘
         */

        Optional<String> name = Optional.of("  Java  ");

        // map: 转换值
        Optional<Integer> length = name.map(String::trim).map(String::length);
        System.out.println("map 转换: " + length);

        // filter: 过滤
        Optional<String> longName = name.filter(s -> s.trim().length() > 10);
        System.out.println("filter 过滤(长度>10): " + longName); // empty

        // TODO(human): 实现链式调用
        // 需求：将名字转为大写，如果为空则返回 "UNKNOWN"
        // 提示：使用 map + orElse
        Optional<String> optName = Optional.ofNullable(getName()); // 可能返回 null
        String upperName = optName.map(String::toUpperCase).orElse("UNKNOWN"); // 实现链式调用
        // upperName = optName.map(???).orElse(???);
        System.out.println("链式调用结果: " + upperName);

        // flatMap vs map 的区别
        System.out.println("\n--- flatMap vs map ---");
        Optional<User> user = Optional.of(new User("Alice", "alice@test.com"));

        // 如果 getEmail 返回 Optional，用 flatMap
        // Optional<Optional<String>> nested = user.map(User::getEmailOptional); // 嵌套了！
        Optional<String> email = user.flatMap(User::getEmailOptional);
        System.out.println("flatMap 获取 email: " + email);
    }

    // 模拟可能返回 null 的方法
    private String getName() {
        return Math.random() > 0.5 ? "hello" : null;
    }

    // ============================================================
    // 4. 实战：替代 null 检查
    // ============================================================
    public void practicalExample() {
        // 模拟从数据库查询用户
        User user = findUserById(1);
        User nullUser = findUserById(999);

        // 传统写法：层层 null 检查
        String city1 = "未知";
        if (user != null) {
            Address addr = user.address;
            if (addr != null) {
                city1 = addr.city;
            }
        }
        System.out.println("传统写法获取城市: " + city1);

        // TODO(human): 使用 Optional 链式调用获取城市
        // 需求：从 nullUser 获取城市，如果任何一环为 null，返回 "未知"
        // 提示：Optional.ofNullable(user).map(...).map(...).orElse(...)
        String city2 = Optional.ofNullable(nullUser)
            .map(user1 -> user1.address)
            .map(address -> address.city)
            .orElse("未知"); // 实现 Optional 链式调用
        // city2 = Optional.ofNullable(nullUser)
        //     .map(???)
        //     .map(???)
        //     .orElse("未知");
        System.out.println("Optional 写法获取城市: " + city2);

        // TODO(human): 使用 Optional 实现带条件的查询
        // 需求：查找用户，如果存在且 email 包含 "test"，返回大写的 email，否则返回 "NO EMAIL"
        // 提示：使用 filter 进行条件过滤
        String email = Optional.ofNullable(nullUser).flatMap(user1 -> user1.getEmailOptional()).
                filter(email1 -> email1.contains("test")).
                map(email1 -> email1.toUpperCase()).orElse("NO EMAIL");
        // email = Optional.ofNullable(user)
        //     .map(???)      // 获取 email
        //     .filter(???)   // 过滤包含 "test" 的
        //     .map(???)      // 转大写
        //     .orElse("NO EMAIL");
        System.out.println("条件查询 email: " + email);
    }

    // 模拟数据库查询
    private User findUserById(int id) {
        if (id == 1) {
            User user = new User("Alice", "alice@test.com");
            user.address = new Address("北京", "朝阳区");
            return user;
        }
        if (id == 999) {
            return new User("Bob", null);
        }
        return null; // 未找到
    }

    // ============================================================
    // 辅助类
    // ============================================================
    static class User {
        String name;
        String email;
        Address address;

        User(String name, String email) {
            this.name = name;
            this.email = email;
        }

        Optional<String> getEmailOptional() {
            return Optional.ofNullable(email);
        }
    }

    static class Address {
        String city;
        String district;

        Address(String city, String district) {
            this.city = city;
            this.district = district;
        }
    }
}
