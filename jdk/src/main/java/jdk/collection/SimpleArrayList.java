package jdk.collection;

import java.util.Arrays;

/**
 * 简化版 ArrayList 实现
 *
 * 学习目标：
 * 1. 理解动态数组的扩容机制
 * 2. 理解 add/remove 时的元素移动
 * 3. 理解 fail-fast 机制
 *
 * 数据结构：Object 数组
 *
 *   elementData[]
 *   ┌─────┬─────┬─────┬─────┬─────┬─────┬─────┐
 *   │  A  │  B  │  C  │null │null │null │null │
 *   └─────┴─────┴─────┴─────┴─────┴─────┴─────┘
 *     0     1     2     3     4     5     6
 *   └───────────────┘
 *        size = 3
 *   └────────────────────────────────────────┘
 *              capacity = 7
 */
public class SimpleArrayList<E> {

    // ==================== 核心参数 ====================

    /** 默认初始容量 */
    private static final int DEFAULT_CAPACITY = 10;

    /** 空数组（用于共享） */
    private static final Object[] EMPTY_ELEMENTDATA = {};

    /** 默认空数组（无参构造时使用，首次 add 时扩容到 DEFAULT_CAPACITY） */
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    // ==================== 成员变量 ====================

    /** 存储元素的数组 */
    Object[] elementData;

    /** 实际元素数量 */
    private int size;

    // ==================== 构造方法 ====================

    /**
     * 无参构造：使用默认空数组，首次 add 时扩容到 10
     */
    public SimpleArrayList() {
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }

    /**
     * 指定初始容量
     */
    public SimpleArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            this.elementData = EMPTY_ELEMENTDATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }
    }

    // ==================== 核心方法 ====================

    /**
     * 获取元素
     */
    @SuppressWarnings("unchecked")
    public E get(int index) {
        rangeCheck(index);
        return (E) elementData[index];
    }

    /**
     * 设置元素
     */
    @SuppressWarnings("unchecked")
    public E set(int index, E element) {
        rangeCheck(index);
        E oldValue = (E) elementData[index];
        elementData[index] = element;
        return oldValue;
    }

    /**
     * 尾部添加元素
     */
    public boolean add(E e) {
        ensureCapacityInternal(size + 1);
        elementData[size++] = e;
        return true;
    }

    /**
     * 在指定位置插入元素
     */
    public void add(int index, E element) {
        rangeCheckForAdd(index);
        ensureCapacityInternal(size + 1);
        System.arraycopy(elementData, index, elementData, index + 1, size - index);
        elementData[index] = element;
        size++;
    }

    /**
     * 删除指定位置的元素
     */
    @SuppressWarnings("unchecked")
    public E remove(int index) {
        rangeCheck(index);

        E oldValue = (E) elementData[index];

        int numMoved = size - index - 1;
        if (numMoved > 0) {
            // 把 [index+1, size) 的元素前移一位
            System.arraycopy(elementData, index + 1, elementData, index, numMoved);
        }
        elementData[--size] = null; // 清除引用，帮助 GC

        return oldValue;
    }

    /**
     * 扩容方法：newCapacity = oldCapacity × 1.5
     */
    private void grow(int minCapacity) {
        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (minCapacity - newCapacity > 0) {
            newCapacity = minCapacity;
        }
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

    // ==================== 辅助方法 ====================

    /**
     * 确保容量足够
     */
    private void ensureCapacityInternal(int minCapacity) {
        // 如果是默认空数组，首次扩容到 DEFAULT_CAPACITY
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
        }
        ensureExplicitCapacity(minCapacity);
    }

    private void ensureExplicitCapacity(int minCapacity) {
        // 如果当前容量不够，进行扩容
        if (minCapacity - elementData.length > 0) {
            grow(minCapacity);
        }
    }

    /**
     * 检查 index 是否越界（用于 get/set/remove）
     */
    private void rangeCheck(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    /**
     * 检查 index 是否越界（用于 add）
     * add 允许 index == size（在末尾添加）
     */
    private void rangeCheckForAdd(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 打印内部结构（调试用）
     */
    public void printStructure() {
        System.out.println("========== ArrayList 内部结构 ==========");
        System.out.println("size: " + size);
        System.out.println("capacity: " + elementData.length);
        System.out.print("elements: [");
        for (int i = 0; i < size; i++) {
            System.out.print(elementData[i]);
            if (i < size - 1) System.out.print(", ");
        }
        System.out.println("]");
        System.out.println("=========================================");
    }

    // ==================== 测试 ====================

    public static void main(String[] args) {
        SimpleArrayList<String> list = new SimpleArrayList<>();

        // 测试 add
        System.out.println("===== 测试尾部添加 =====");
        for (int i = 0; i < 12; i++) {
            list.add("item" + i);
            System.out.println("添加 item" + i + " 后，capacity = " + list.elementData.length);
        }
        list.printStructure();

        // 测试 add(index, element)
        System.out.println("\n===== 测试插入 =====");
        list.add(2, "INSERT");
        list.printStructure();

        // 测试 get/set
        System.out.println("\n===== 测试 get/set =====");
        System.out.println("get(2) = " + list.get(2));
        list.set(2, "UPDATED");
        System.out.println("set(2, 'UPDATED') 后，get(2) = " + list.get(2));

        // 测试 remove
        System.out.println("\n===== 测试 remove =====");
        String removed = list.remove(2);
        System.out.println("remove(2) = " + removed);
        list.printStructure();
    }
}
