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

### ⚠️ 强制性要求

项目**强制要求**使用 `commit_message.txt` 文件进行提交，**禁止直接使用 `git commit -m` 命令**：

```bash
# ❌ 禁止的提交方式
git commit -m "简单提交信息"  # 绝对禁止！
git commit -am "快速提交"      # 绝对禁止！

# ✅ 唯一允许的提交方式
# 1. 添加变更
git add .

# 2. 创建提交信息文件（必须步骤）
echo "feat(leetcode): 实现两数之和算法

详细说明实现的算法和优化点

Changes:
- leetcode/Two_Sum_1.java: 添加哈希表解法
- leetcode/Two_Sum_1.java: 优化时间复杂度到O(n)" > commit_message.txt

# 3. 使用文件提交（必须步骤）
git commit -F commit_message.txt

# 4. 清理临时文件（必须步骤）
rm commit_message.txt
```

### 📋 约定式提交规范

遵循[约定式提交](https://www.conventionalcommits.org/zh-hans)规范：

```
<类型>[可选scope]: <描述>

[可选 body]

[可选 footer(s)]
```

### 🏷️ 提交类型

| 类型 | 说明 | 示例 |
|------|------|------|
| `feat` | 新功能（新增算法题解） | `feat(leetcode): 添加二叉树遍历算法` |
| `fix` | 错误修复 | `fix(ZuoGod): 修复并查集路径压缩问题` |
| `docs` | 文档变更 | `docs: 更新算法复杂度说明` |
| `style` | 代码格式化（不影响代码逻辑） | `style: 统一代码缩进格式` |
| `refactor` | 代码重构（不新增功能或修复bug） | `refactor(heap): 优化堆排序实现` |
| `test` | 测试相关 | `test(leetcode): 添加边界测试用例` |
| `perf` | 性能优化 | `perf(dp): 优化动态规划空间复杂度` |
| `chore` | 构建过程或辅助工具的变动 | `chore: 更新Maven依赖版本` |
| `ci` | CI配置相关 | `ci: 添加GitHub Actions工作流` |
| `build` | 构建系统或外部依赖 | `build: 升级JUnit到4.13` |

### 🎯 作用域 (Scope)

本项目常用作用域：
- `leetcode` - LeetCode题解相关
- `ZuoGod` - 左神算法课程实现
- `jdk` - JDK模块相关
- `dp` - 动态规划专题
- `heap` - 堆相关算法
- `graph` - 图算法相关
- `test` - 测试相关

### 📝 提交信息模板

#### 基础模板
```
<类型>(scope): <简洁描述>

<详细说明 what 和 why>

Changes:
- 文件1: 具体变更说明
- 文件2: 具体变更说明

Features/Fixes:
* 功能点1
* 功能点2

Closes #issue编号
```

#### 实际示例

**新功能示例：**
```
feat(leetcode): 添加三数之和算法实现

实现了LeetCode第15题三数之和的双指针解法
优化了去重逻辑，时间复杂度O(n²)

Changes:
- leetcode/Three_Sum_15.java: 新增双指针解法
- leetcode/Three_Sum_15.java: 添加完整测试用例

Features:
* 双指针优化算法
* 自动去重处理
* 边界条件处理

Closes #15
```

**修复示例：**
```
fix(ZuoGod/UnionFind): 修复并查集路径压缩错误

修复了在特定情况下路径压缩导致的父节点指向错误
添加了额外的测试用例验证修复效果

Changes:
- ZuoGod/UnionFind/Number_of_Good_Paths_2421.java: 修正find方法逻辑
- ZuoGod/UnionFind/Number_of_Good_Paths_2421.java: 添加边界测试

Fixes #234
```

### ✅ 提交前检查清单

提交前请确认：
- [ ] 使用 `commit_message.txt` 文件提交（强制要求）
- [ ] 提交类型正确
- [ ] 作用域准确（如适用）
- [ ] 描述使用祈使句，简洁明了
- [ ] 包含Changes部分列出文件变更
- [ ] 关联相关issue（如适用）

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