package leetcode;

public class sort_list_148 {

    public static void main(String[] args) {
        ListNode n1 = new ListNode(4);
        ListNode n2 = new ListNode(3);
        ListNode n3 = new ListNode(5);
        ListNode n4 = new ListNode(1);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        sort_list_148 res = new sort_list_148();
        ListNode listNode = res.sortList(n1);
        while (listNode != null) {
            System.out.println(listNode.val);
            listNode = listNode.next;
        }
    }

    /**
     * 归并排序（从底至顶直接合并）
     * @param head
     * @return
     */
    public ListNode sortList(ListNode head) {
        ListNode temp = head;
        int len = 0;
        while (temp != null) {
            temp = temp.next;
            len++;
        }
        ListNode res = head;
        int N = 1;
        for (; N < len; N = N * 2) {

            ListNode cur = res;
            ListNode nextNode = null;
            for (int i = 0; i + N < len; i = i + 2 * N) {
                ListNode next = cur;
                ListNode start = cur, pre = cur;
                ListNode mid = start;
                int num = 0;
                //找到中间的部分，并且分离
                while (num < N) {
                    pre = mid;
                    mid = mid.next;
                    num++;
                }
                pre.next = null;
                ListNode end = mid;
                num = 0;
                //找到两段链表最后的部分并且将其与主链分离
                while (end != null && num < N) {
                    next = end;
                    end = end.next;
                    num++;
                }
                if (end != null) {
                    next.next = null;
                    cur = end;
                } else {
                    cur = null;
                }
                ListNode listNode = mergeTwoLists(start, mid);
                //将上一个已经合并后的链表的最后与现在合并的链表头连起来
                if (nextNode != null){
                    nextNode.next = listNode;
                }
                if (i == 0) {
                    res = listNode;
                }

                while (listNode != null) {
                    nextNode = listNode;
                    listNode = listNode.next;
                }
                //将合并后链表与刚刚断开的主链表连起来，防止这最后一部分不进入循环出现bug
                if (cur != null){
                    nextNode.next = cur;
                }
            }
        }
        return res;
    }

    public ListNode sortList1(ListNode head) {
        ListNode ans = mergeSort(head);
        return ans;

    }

    /**
     * 归并排序法：在动手之前一直觉得空间复杂度为常量不太可能，因为原来使用归并时，都是 O(N)的，
     * 需要复制出相等的空间来进行赋值归并。对于链表，实际上是可以实现常数空间占用的（链表的归并
     * 排序不需要额外的空间）。利用归并的思想，递归地将当前链表分为两段，然后merge，分两段的方
     * 法是使用 fast-slow 法，用两个指针，一个每次走两步，一个走一步，知道快的走到了末尾，然后
     * 慢的所在位置就是中间位置，这样就分成了两段。merge时，把两段头部节点值比较，用一个 p 指向
     * 较小的，且记录第一个节点，然后 两段的头一步一步向后走，p也一直向后走，总是指向较小节点，
     * 直至其中一个头为NULL，处理剩下的元素。最后返回记录的头即可。
     * <p>
     * 主要考察3个知识点，
     * 知识点1：归并排序的整体思想
     * 知识点2：找到一个链表的中间节点的方法
     * 知识点3：合并两个已排好序的链表为一个新的有序链表
     */
    private ListNode mergeSort(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode start = head, end = head, mid = head;
        ListNode pre = start;
        while (end != null && end.next != null) {
            pre = mid;
            mid = mid.next;
            end = end.next.next;
        }
        pre.next = null;
        ListNode left = mergeSort(start);
        ListNode right = mergeSort(mid);
        ListNode res = mergeTwoLists(left, right);
        return res;

    }

    private ListNode mergeTwoLists(ListNode list1, ListNode list2) {

        ListNode prehead = new ListNode(-1);

        ListNode cur = prehead;

        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                prehead.next = list1;
                list1 = list1.next;
            } else {
                prehead.next = list2;
                list2 = list2.next;
            }
            prehead = prehead.next;
        }

        prehead.next = list1 == null ? list2 : list1;

        return cur.next;
    }


    static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}



