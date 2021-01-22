import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 二叉树结构定义如下：
 * Node {
 * 		int value;
 * 		Node left;
 * 		Node right;
 * 		Node parent;
 * 	}
 * 给你二叉树中某个节点，请返回该节点的后继节点。
 *     后继节点：中序遍历 (左——头——右) 里面下一个节点
 *
 * 解法1：找到头，中序遍历找到后继节点
 *        先序中序后续遍历本质上都是递归序的遍历。节点数为n，时间复杂度O(N)
 * 解法2：我没有右孩子，往上找，直到是父节点的左孩子了停下。这个父节点就是我的后继。
 *        整棵树的最右节点是没有后继的
 *
 */
public class Code07_SuccessorNode {
    public static class Node {
        public int value;
        public Node left;
        public Node right;
        public Node parent;
        public Node(int v) {
            value = v;
        }
    }

    /**
     * 获得后继节点
     * @param node
     * @return
     */
    public static Node getSuccessorNode(Node node) {
        if (node == null) {
            return node;
        }
        if (node.right != null) {
            return getLeftMost(node.right);
        } else { // 无右子树
            Node parent = node.parent;
            while (parent != null && parent.right == node) {
                // 当前节点是其父亲节点右孩子
                node = parent;
                parent = node.parent;

            }
            return parent;
        }
    }

    /**
     * 获得最左子节点
     * @param node
     * @return
     */
    public static Node getLeftMost(Node node) {
        if (node == null) {
            return node;
        }
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    /**
     * 获得前驱、后继节点暴力解法
     * @param node
     */
    public static void getNode2(Node node) {
        if (node == null) {
            return ;
        }
        Node head = getHead(node);
        Stack<Node> sucStack = new Stack<>();
        Queue<Node> preQueue = new LinkedList<>();
        inOrder(head, preQueue, sucStack); // 中序遍历此树
        getPrecursorNode2(node, sucStack); // 获得前驱
        getSuccessorNode2(node, preQueue); // 获得后继

    }

    /**
     * 获得前驱节点
     * @param node
     * @param stack
     */
    public static void getPrecursorNode2(Node node, Stack<Node> stack) {
        System.out.println(node.value + "的前驱节点：");
        while (!stack.isEmpty() && stack.peek() != node) {
            stack.pop();
        }
        stack.pop();
        if (stack.isEmpty()) {
            System.out.println("null");
        } else {
            System.out.println(stack.pop().value );
        }
    }

    /**
     * 获得后继节点
     * @param node
     * @param queue
     */
    public static void getSuccessorNode2(Node node, Queue<Node> queue) {
        System.out.println(node.value + "的后继节点：");
        while (!queue.isEmpty() && queue.peek() != node) {
            queue.poll();
        }
        queue.poll();
        if (queue.isEmpty()) {
            System.out.println("null");
        } else {
            System.out.println(queue.poll().value);
        }
    }

    /**
     * 获得该树的头节点
     * @param node
     * @return
     */
    public static Node getHead(Node node) {
        while (node.parent != null) {
            node = node.parent;
        }
        return node;
    }

    public static void inOrder(Node head, Queue<Node> queue, Stack<Node> stack) {
        if (head == null) {
            return;
        }
        Stack<Node> stack0 = new Stack<>();
        while (!stack0.isEmpty() || head != null) {
            if (head != null) { // 1. 整条左边界依次入栈
                stack0.push(head);
                head = head.left;
            } else { // 2. 弹出就进队列、压栈，然后来到右树位置继续1
                head = stack0.pop();
                queue.add(head);
                stack.push(head);
                head = head.right;
            }
        }
    }
    public static void main(String[] args) {
        Node head = new Node(6);
        head.parent = null;
        head.left = new Node(3);
        head.left.parent = head;
        head.left.left = new Node(1);
        head.left.left.parent = head.left;
        head.left.left.right = new Node(2);
        head.left.left.right.parent = head.left.left;
        head.left.right = new Node(4);
        head.left.right.parent = head.left;
        head.left.right.right = new Node(5);
        head.left.right.right.parent = head.left.right;
        head.right = new Node(9);
        head.right.parent = head;
        head.right.left = new Node(8);
        head.right.left.parent = head.right;
        head.right.left.left = new Node(7);
        head.right.left.left.parent = head.right.left;
        head.right.right = new Node(10);
        head.right.right.parent = head.right;

        Node test = head.left.left;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head.left.left.right;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head.left;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head.left.right;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head.left.right.right;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head.right.left.left;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head.right.left;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head.right;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head.right.right; // 10's next is null
        System.out.println(test.value + " next: " + getSuccessorNode(test));

        System.out.println("=================================");
        Node test1 = head.left.left;
        getNode2(test1);
        test1 = head.left.left.right;
        getNode2(test1);
        test1 = head.left;
        getNode2(test1);
        test1 = head.left.right;
        getNode2(test1);
        test1 = head.left.right.right;
        getNode2(test1);
        test1 = head;
        getNode2(test1);
        test1 = head.right.left.left;
        getNode2(test1);
        test1 = head.right.left;
        getNode2(test1);
        test1 = head.right;
        getNode2(test1);
        test1 = head.right.right; // 10's next is null
        getNode2(test1);
    }
}
