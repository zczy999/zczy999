package leetcode;

import java.util.*;

/**
 * LeetCode 380. O(1) 时间插入、删除和获取随机元素
 *
 * 核心思想：数组 + 哈希表双向映射
 * - nums: 存储元素（支持 O(1) 随机访问）
 * - indices: 存储元素到索引的映射（支持 O(1) 查找位置）
 *
 * 关键技巧：O(1) 删除 - 用最后一个元素替换要删除的元素，再删除最后一个
 */
class Insert_Delete_GetRandomO1_380 {
    List<Integer> nums;              // 动态数组，存储所有元素
    Map<Integer, Integer> indices;   // 哈希表，存储 val -> index 的映射
    Random random;                   // 随机数生成器

    public Insert_Delete_GetRandomO1_380() {
        nums = new ArrayList<Integer>();
        indices = new HashMap<Integer, Integer>();
        random = new Random();
    }

    /**
     * 插入元素 - O(1)
     * 直接在数组末尾添加，同时记录索引映射
     */
    public boolean insert(int val) {
        if (indices.containsKey(val)) {
            return false;
        }
        int index = nums.size();
        nums.add(val);
        indices.put(val, index);
        return true;
    }

    /**
     * 删除元素 - O(1)
     *
     * 核心技巧：用最后一个元素替换要删除的元素，然后删除最后一个元素
     *
     * 示例：删除元素 3
     * 删除前: nums = [1, 3, 5, 7]    indices = {1:0, 3:1, 5:2, 7:3}
     *
     * 步骤：
     * 1. 获取 3 的索引 index=1，获取最后元素 last=7
     * 2. 用 7 覆盖位置 1: nums = [1, 7, 5, 7]
     * 3. 更新 7 的索引: indices = {1:0, 7:1, 5:2, 7:3}
     * 4. 删除最后元素: nums = [1, 7, 5]
     * 5. 删除 3 的映射: indices = {1:0, 7:1, 5:2}
     */
    public boolean remove(int val) {
        if (!indices.containsKey(val)) {
            return false;
        }
        int index = indices.get(val);             // 1. 获取要删除元素的索引
        int last = nums.get(nums.size() - 1);     // 2. 获取最后一个元素
        nums.set(index, last);                    // 3. 用最后元素覆盖要删除的位置
        indices.put(last, index);                 // 4. 更新最后元素的索引映射
        nums.remove(nums.size() - 1);             // 5. 删除数组最后一个元素（O(1)）
        indices.remove(val);                      // 6. 删除目标值的索引映射
        return true;
    }

    /**
     * 获取随机元素 - O(1)
     * 数组支持 O(1) 随机访问，直接生成随机索引获取
     */
    public int getRandom() {
        int randomIndex = random.nextInt(nums.size());
        return nums.get(randomIndex);
    }
}

