# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 项目结构

这是一个基于 Maven 的多模块 Java 项目，包含以下模块：

- **leetcode**: LeetCode 算法题解实现（Java 11）
- **jdk**: JDK 相关代码和并发编程实验（Java 8）
- **tsymq-arch**: 项目架构相关代码（Java 11）

⚠️ **注意**：jdk 模块使用 Java 8，其他模块使用 Java 11

## 常用命令

### 项目构建
```bash
# 编译整个项目
mvn clean compile

# 打包项目（默认跳过测试）
mvn clean package

# 安装到本地仓库
mvn clean install

# 清理并重新编译
mvn clean && mvn compile

# 编译特定模块
mvn clean compile -pl leetcode
mvn clean compile -pl jdk
mvn clean compile -pl tsymq-arch
```

### 测试相关
```bash
# 运行所有测试（需要临时启用测试）
mvn test -DskipTests=false

# 运行特定模块测试
mvn test -pl leetcode -DskipTests=false

# 运行单个测试类
mvn test -Dtest=Sqrtx_69 -pl leetcode -DskipTests=false

# 运行单个测试方法
mvn test -Dtest=Sqrtx_69#test -pl leetcode -DskipTests=false

# 通过 Java 直接运行测试
cd leetcode
java -cp target/classes:~/.m2/repository/junit/junit/4.12/junit-4.12.jar:~/.m2/repository/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar org.junit.runner.JUnitCore leetcode.Sqrtx_69
```

### 代码工具
```bash
# 格式化 LeetCode 题目名称
java -cp leetcode/target/classes utils.LeetCodeQuestionName

# 查看项目依赖树
mvn dependency:tree

# 分析特定模块依赖
mvn dependency:tree -pl leetcode

# 检查依赖冲突
mvn dependency:analyze
```

## LeetCode 模块详细结构

```
leetcode/src/main/java/
├── DataStructure/          # 数据结构实现
│   └── LFUCache.java      # LFU 缓存等复杂数据结构
├── ZuoGod/                # 左神算法课程实现（高级算法）
│   ├── DynamicProgramming/    # 动态规划
│   │   ├── BitmaskDP/         # 状态压缩 DP
│   │   ├── DigitDP/           # 数位 DP
│   │   └── LIS/               # 最长递增子序列类
│   ├── SlidingWindow/         # 滑动窗口算法
│   ├── Heap/                  # 堆相关算法
│   ├── MonotonicStack/        # 单调栈
│   ├── PrefixSum/             # 前缀和
│   ├── UnionFind/             # 并查集
│   ├── Graph/                 # 图算法（最短路径、拓扑排序等）
│   └── BinarySearch/          # 二分查找变种
├── interview/             # 面试题解
├── leetcode/              # LeetCode 原题（基础到中等）
├── lintcode/              # LintCode 题解
└── utils/                 # 工具类
    ├── LeetCodeQuestionName.java  # 题目名称格式化
    └── LeetcodeInput.java         # 输入数据处理
```

## 命名规范

### LeetCode 题目文件命名
- 格式：`题目名称_题号.java`
- 示例：`Two_Sum_1.java`, `Sliding_Window_Maximum_239.java`
- 使用 `LeetCodeQuestionName` 工具自动格式化：
  ```java
  // 输入: "48. Rotate Image"
  // 输出: "Rotate_Image_48"
  ```

### 测试方法规范
- 每个 LeetCode 题解类包含 `@Test` 注解的测试方法
- 测试方法通常命名为 `test()` 或 `test题目名()`
- 使用 JUnit 4.12 进行单元测试

## Git 提交规范

项目**强制要求**使用 `commit_message.txt` 文件进行提交：

```bash
# 1. 添加变更
git add .

# 2. 创建提交信息文件
echo "feat(leetcode): 实现两数之和算法" > commit_message.txt

# 3. 使用文件提交
git commit -F commit_message.txt

# 4. 清理临时文件
rm commit_message.txt
```

### 提交信息格式
遵循约定式提交规范：`<类型>[可选scope]: <描述>`

常用类型：
- `feat`: 新功能（新增算法题解）
- `fix`: 修复bug
- `refactor`: 代码重构
- `test`: 添加测试
- `docs`: 文档更新
- `style`: 代码格式调整
- `perf`: 性能优化

示例：
```
feat(leetcode): 添加动态规划解法 - 最长递增子序列
fix(ZuoGod): 修复并查集路径压缩问题
refactor(heap): 优化堆排序实现
test(leetcode): 为二叉树问题添加边界测试
```

## 依赖管理

主要依赖版本（在父 POM 中统一管理）：
- Spring Boot: 2.5.5
- Spring Cloud: 2020.0.4
- Spring Cloud Alibaba: 2021.1
- MyBatis Plus: 3.4.0
- Lombok: 1.18.16
- JUnit: 4.12
- Jackson: 2.14.0-rc1（仅 leetcode 模块）

## 开发注意事项

### 环境配置
- **编码**：UTF-8
- **构建工具**：Maven 3.6+
- **JDK 要求**：
  - jdk 模块：Java 8
  - 其他模块：Java 11
- **测试**：默认跳过（`<skipTests>true</skipTests>`）

### 算法题解开发流程
1. 在对应分类目录下创建题解文件
2. 使用 `LeetCodeQuestionName` 工具格式化文件名
3. 实现算法并编写测试方法
4. 本地运行测试验证正确性
5. 使用规范的提交信息提交代码

### 性能优化建议
- 优先使用位运算进行状态压缩（BitmaskDP）
- 滑动窗口问题注意边界处理
- 动态规划注意空间优化（滚动数组）
- 图算法注意避免重复访问

## 项目特色

### 算法分类体系
- **基础算法**：在 `leetcode` 包下，包含常见面试题
- **高级算法**：在 `ZuoGod` 包下，包含复杂算法和优化技巧
- **专题训练**：按技术类型组织，便于系统学习

### 工具支持
- `LeetCodeQuestionName`：自动格式化题目名称
- `LeetcodeInput`：处理复杂输入数据
- 内置测试：每个题解包含可直接运行的测试用例

### 最佳实践
- 算法实现注重可读性和效率平衡
- 保留多种解法供对比学习
- 测试用例覆盖边界情况