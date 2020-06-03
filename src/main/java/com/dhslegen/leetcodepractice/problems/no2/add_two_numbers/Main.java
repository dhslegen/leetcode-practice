package com.dhslegen.leetcodepractice.problems.no2.add_two_numbers;

/**
 * <h2>两数相加
 * <p>
 * 给出两个 <b>非空</b> 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 <b>逆序</b> 的方式存储的，并且它们的每个节点只能存储 <b>一位</b> 数字。
 * <p>
 * <p>
 * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
 * <p>
 * <p>
 * 您可以假设除了数字 <b>0</b> 之外，这两个数都不会以 <b>0</b> 开头。
 * <p>
 * <h3>示例：
 * <p>
 * <b>输入：</b>(2 -> 4 -> 3) + (5 -> 6 -> 4)
 * <p>
 * <b>输出：</b>7 -> 0 -> 8
 * <p>
 * <b>原因：</b>342 + 465 = 807
 * <p>
 *
 * @author dhslegen
 */
public class Main {

    public static void main(String[] args) {
        ListNode listNode1 = new ListNode(9);
        ListNode listNode2 = new ListNode(6);
        ListNode listNode3 = new ListNode(9);
        listNode3.next = listNode2;
        ListNode listNode = addTwoNumbers(listNode1, listNode3);
        System.out.println(listNode);
    }

    private static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode p = l1;
        ListNode q = l2;
        ListNode dummyHead = new ListNode(0);
        ListNode currentNode = dummyHead;
        int carry = 0;
        while (p != null || q != null) {
            int i = p != null ? p.value : 0;
            int j = q != null ? q.value : 0;
            int sum = i + j + carry;
            carry = sum / 10;
            currentNode.next = new ListNode(sum % 10);
            currentNode = currentNode.next;
            if (p != null) {
                p = p.next;
            }
            if (q != null) {
                q = q.next;
            }
        }
        if (carry > 0) {
            currentNode.next = new ListNode(carry);
        }
        return dummyHead.next;
    }

}
