package jdk.collection;

/**
 * 简化版 LinkedList 实现（双向链表）
 *
 * 学习目标：
 * 1. 理解双向链表的节点结构
 * 2. 理解头尾插入/删除的 O(1) 操作
 * 3. 理解 get(index) 的优化（前半/后半）
 *
 * 数据结构：双向链表
 *
 *   first                                        last
 *     │                                            │
 *     ▼                                            ▼
 *  ┌──────┐     ┌──────┐     ┌──────┐     ┌──────┐
 *  │ null │ ←── │ prev │ ←── │ prev │ ←── │ prev │
 *  │  A   │     │  B   │     │  C   │     │  D   │
 *  │ next │ ──→ │ next │ ──→ │ next │ ──→ │ null │
 *  └──────┘     └──────┘     └──────┘     └──────┘
 */
public class SimpleLinkedList<E> {

    // ==================== 内部节点 ====================

    /**
     * 双向链表节点
     */
    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    // ==================== 成员变量 ====================

    /** 链表长度 */
    int size = 0;

    /** 头节点 */
    Node<E> first;

    /** 尾节点 */
    Node<E> last;

    // ==================== 构造方法 ====================

    public SimpleLinkedList() {
    }

    // ==================== 核心方法 ====================

    /**
     * 在头部插入元素
     *
     * 插入前：first → A ←→ B ←→ C
     * 插入后：first → X ←→ A ←→ B ←→ C
     */
    public void addFirst(E e) {
        linkFirst(e);
    }

    /**
     * 在尾部插入元素
     *
     * 插入前：A ←→ B ←→ C ← last
     * 插入后：A ←→ B ←→ C ←→ X ← last
     */
    public void addLast(E e) {
        linkLast(e);
    }

    /**
     * 默认添加到尾部
     */
    public boolean add(E e) {
        linkLast(e);
        return true;
    }

    /**
     * 在头部插入节点
     *
     * 步骤图解：
     *   插入前：first ──→ A ←──→ B ←──→ C
     *   插入后：first ──→ newNode ←──→ A ←──→ B ←──→ C
     */
    private void linkFirst(E e) {
        final Node<E> f = first;
        final Node<E> newNode = new Node<>(null, e, f);
        first = newNode;
        if (f == null) {
            last = newNode;  // 空链表，last 也指向新节点
        } else {
            f.prev = newNode;  // 让旧头的 prev 指向新节点
        }
        size++;
    }

    /**
     * 在尾部插入节点
     *
     * 步骤图解：
     *   插入前：A ←──→ B ←──→ C ←── last
     *   插入后：A ←──→ B ←──→ C ←──→ newNode ←── last
     */
    private void linkLast(E e) {
        final Node<E> l = last;
        final Node<E> newNode = new Node<>(l, e, null);
        last = newNode;
        if (l == null) {
            first = newNode;  // 空链表，first 也指向新节点
        } else {
            l.next = newNode;  // 让旧尾的 next 指向新节点
        }
        size++;
    }

    /**
     * 获取头部元素
     */
    public E getFirst() {
        final Node<E> f = first;
        if (f == null)
            throw new java.util.NoSuchElementException();
        return f.item;
    }

    /**
     * 获取尾部元素
     */
    public E getLast() {
        final Node<E> l = last;
        if (l == null)
            throw new java.util.NoSuchElementException();
        return l.item;
    }

    /**
     * 删除头部元素
     */
    public E removeFirst() {
        final Node<E> f = first;
        if (f == null)
            throw new java.util.NoSuchElementException();
        return unlinkFirst(f);
    }

    /**
     * 删除尾部元素
     */
    public E removeLast() {
        final Node<E> l = last;
        if (l == null)
            throw new java.util.NoSuchElementException();
        return unlinkLast(l);
    }

    /**
     * 删除头节点
     *
     * 步骤图解：
     *   删除前：first → A ←→ B ←→ C
     *   删除后：first → B ←→ C
     *   (A 被删除，A.next 和 A.item 置 null 帮助 GC)
     */
    private E unlinkFirst(Node<E> f) {
        final E element = f.item;
        final Node<E> next = f.next;
        f.item = null;   // 帮助 GC
        f.next = null;   // 帮助 GC
        first = next;
        if (next == null) {
            last = null;  // 删除后链表为空
        } else {
            next.prev = null;  // 新头的 prev 应该是 null
        }
        size--;
        return element;
    }

    /**
     * 删除尾节点
     */
    private E unlinkLast(Node<E> l) {
        final E element = l.item;
        final Node<E> prev = l.prev;
        l.item = null;
        l.prev = null;
        last = prev;
        if (prev == null) {
            first = null;
        } else {
            prev.next = null;
        }
        size--;
        return element;
    }

    /**
     * 根据索引获取元素
     *
     * 优化：判断 index 在前半还是后半
     * - 前半：从 first 正向遍历
     * - 后半：从 last 反向遍历
     */
    public E get(int index) {
        checkElementIndex(index);
        return node(index).item;
    }

    /**
     * 根据索引定位节点（带优化）
     */
    Node<E> node(int index) {
        if (index < (size >> 1)) {
            // 前半：从头开始
            Node<E> x = first;
            for (int i = 0; i < index; i++)
                x = x.next;
            return x;
        } else {
            // 后半：从尾开始
            Node<E> x = last;
            for (int i = size - 1; i > index; i--)
                x = x.prev;
            return x;
        }
    }

    // ==================== 辅助方法 ====================

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }

    /**
     * 打印链表结构
     */
    public void printStructure() {
        System.out.println("========== LinkedList 内部结构 ==========");
        System.out.println("size: " + size);
        System.out.print("正向: ");
        Node<E> node = first;
        while (node != null) {
            System.out.print(node.item);
            if (node.next != null) System.out.print(" ←→ ");
            node = node.next;
        }
        System.out.println();

        System.out.print("反向: ");
        node = last;
        while (node != null) {
            System.out.print(node.item);
            if (node.prev != null) System.out.print(" ←→ ");
            node = node.prev;
        }
        System.out.println();
        System.out.println("==========================================");
    }

    // ==================== 测试 ====================

    public static void main(String[] args) {
        SimpleLinkedList<String> list = new SimpleLinkedList<>();

        // 测试尾部添加
        System.out.println("===== 测试 addLast =====");
        list.addLast("A");
        list.addLast("B");
        list.addLast("C");
        list.printStructure();

        // 测试头部添加
        System.out.println("\n===== 测试 addFirst =====");
        list.addFirst("X");
        list.addFirst("Y");
        list.printStructure();

        // 测试获取
        System.out.println("\n===== 测试 get =====");
        System.out.println("getFirst() = " + list.getFirst());
        System.out.println("getLast() = " + list.getLast());
        System.out.println("get(2) = " + list.get(2));

        // 测试删除头部
        System.out.println("\n===== 测试 removeFirst =====");
        System.out.println("removeFirst() = " + list.removeFirst());
        list.printStructure();

        // 测试删除尾部
        System.out.println("\n===== 测试 removeLast =====");
        System.out.println("removeLast() = " + list.removeLast());
        list.printStructure();
    }
}
