package leetcode;

public class Merge_k_Sorted_Lists_23 {

    public ListNode mergeKLists(ListNode[] lists) {
        return mergeKLists(lists, 0, lists.length - 1);
    }

    private ListNode mergeKLists(ListNode[] lists, int l, int r) {
        if (l > r) {
            return null;
        }
        if (l == r) {
            return lists[l];
        }
        int mid = (l + r) / 2;
        ListNode left = mergeKLists(lists, l, mid);
        ListNode right = mergeKLists(lists, mid + 1, r);
        return mergeTwoLists(left, right);
    }

    private ListNode mergeTwoLists(ListNode left, ListNode right) {
        ListNode prehead = new ListNode(-1);
        ListNode cur = prehead;
        while (left != null && right != null) {
            if (left.val <= right.val) {
                prehead.next = left;
                left = left.next;
            } else {
                prehead.next = right;
                right = right.next;
            }
            prehead = prehead.next;
        }
        if (left != null){
            prehead.next = left;
        }
        if (right != null){
            prehead.next = right;
        }
        return cur.next;
    }


    class ListNode {
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
