package leetcode;

public class Partition_List_86 {

    /**
     * 分区链表：将所有小于 x 的节点都出现在大于或等于 x 的节点之前
     *
     * @param head 链表头节点
     * @param x 分区值
     * @return 重新排列后的链表头节点
     */
    public ListNode partition(ListNode head, int x) {
        // 创建两个虚拟头节点，分别用于存储小于x和大于等于x的节点
        ListNode beforeX = new ListNode(-1);
        ListNode beforeXHead = beforeX;
        ListNode afterX = new ListNode(-1);
        ListNode afterXHead = afterX;

        // 遍历原链表，将节点分类到两个子链表中
        while (head != null) {
            if (head.val < x) {
                beforeX.next = head;
                beforeX = beforeX.next;
            }else {
                afterX.next = head;
                afterX = afterX.next;
            }
            head = head.next;
        }

        // 切断 afterX 链表末尾的原有连接
        // 确保新构建的链表不会意外连接到原链表的后续节点
        afterX.next = null;
        // 连接两个子链表
        beforeX.next = afterXHead.next;
        return beforeXHead.next;
    }

    public class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
}
