import java.util.Stack;

/**
 * 给定一个单链表的头节点为head，判断该链表是否是回文结构
 *      回文：逆着读和顺着读内容相同
 *      1）笔试：栈方法
 *      2）面试：改原链表
  */

public class Code02_IsPalindromeList {
    public static class Node {
        public int value;
        public Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    // 需要n个额外空间 栈空间放入，再倒出进行比较
    public static boolean isPalindrome1(Node head) {
        Stack<Node> stack = new Stack<>();
        Node cur = head;
        while (cur != null) {
            stack.push(cur);
            cur = cur.next;
        }
        while (head != null) {
            if (head.value != stack.pop().value) {
                return false;
            }
            head = head.next;
        }
        return true;
    }

    // 需要n/2个额外空间 栈空间放入后一半，再倒出与前一半进行比较
    public static boolean isPalindrome2(Node head) {
        // 输入链表头节点，（>=2个节点时）奇数长度返回中点后一个，偶数长度返回下中点
        // 空或单节点一定为回文
        if (head == null || head.next == null) {
            return true;
        }
        Node cur = head.next;
        Node fast = head;
        while (fast.next != null && fast.next.next != null) {
            cur = cur.next;
            fast = fast.next.next;
        }
        // 此时cur为中点
        Stack<Node> stack = new Stack<>();
        while (cur != null) {
            stack.push(cur);
            cur = cur.next;
        }
        while (!stack.isEmpty()) {
            if (head.value != stack.pop().value) {
                return false;
            }
            head = head.next;
        }
        return true;
    }

    // 需要 O(1) 额外空间 把中点指向null，后一半的节点反向链接指向中点
    public static boolean isPalindrome3(Node head) {
        // 输入链表头节点，（>=2个节点时）奇数长度返回中点，偶数长度返回上中点
        // 空或单节点一定为回文
        if (head == null || head.next == null) {
            return true;
        }
        Node cur = head;
        Node fast = head.next;
        while (fast.next != null && fast.next.next != null) {
            cur = cur.next;
            fast = fast.next.next;
        }
        // 右半部分逆序
        fast = cur.next;
        cur.next = null;
        Node p = null;
        while (fast != null) {
            p = fast.next;
            fast.next = cur;
            cur = fast;
            fast = p;
        }
        // cur,fast 分别代表左边、右边的头，每一步比对
        p = cur; // 记录最后一个节点位置
        fast = head;
        boolean res = true;
        while (cur != null && fast != null) {
            if (cur.value != fast.value) {
                res = false;
                break;
            }
            cur = cur.next;
            fast = fast.next;
        }
        // 右半部分逆序回来
        cur = p.next;
        p.next = null;
        while (cur != null) {
            fast = cur.next;
            cur.next = p;
            p = cur;
            cur = fast;
        }
        return res;
    }

    public static void printLinkedList(Node node) {
        System.out.println("Linked List: ");
        while (node != null) {
            System.out.println(node.value + " ");
            node = node.next;
        }
        System.out.println();
    }
    public static void main(String[] args) {

        Node head = null;
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(2);
        head.next.next.next = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(2);
        head.next.next.next.next = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

    }
}

