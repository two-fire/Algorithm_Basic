import java.util.Stack;

/**
 *  任何递归函数都可以改成非递归  压栈实现
 *      先序：头节点放到栈中
 *           while
 *            1. 弹出就打印
 *            2. 如有右孩子压入右孩子
 *            3. 如有左孩子压入左孩子
 *      后序：
 *            方法一：头 右 左 逆着看 （1.弹出 2.压入左 3.压入右）
 *            每次弹出不打印，而是放到另一个栈中，最后弹出打印辅助栈
 *            方法二：
 *            利用两个引用。h指向刚打印的节点，c指向当前节点
 *            左树没处理，先压入左树；左树刚处理完，压入右树；否则处理当前节点
 *      中序：
 *            1. 整条左边界依次入栈
 *            2. 1无法继续了，弹出就打印，然后来到右树位置继续1
 */
public class Code02_UnRecursiveTraversalBT {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int v) {
            value = v;
        }
    }

    /**
     * 先序遍历
     * @param head
     */
    public static void pre(Node head) {
        System.out.println("pre-order: ");
        if (head == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        stack.push(head);
        while (!stack.isEmpty()) {
            head = stack.pop();
            System.out.print(head.value + " "); // 弹出打印
            if (head.right != null) {
                stack.push(head.right);
            }
            if (head.left != null) {
                stack.push(head.left);
            }
        }
        System.out.println();
    }

    /**
     * 中序遍历
     * @param head
     */
    public static void in(Node head) {
        System.out.println("in-order: ");
        if (head == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        while (!stack.isEmpty() || head != null) {
            if (head != null) { // 1. 整条左边界依次入栈
                stack.push(head);
                head = head.left;
            } else { // 2. 弹出就打印，然后来到右树位置继续1
                head = stack.pop();
                System.out.print(head.value + " ");
                head = head.right;
            }
        }
        System.out.println();
    }

    /**
     * 后序排序 方法一
     * @param head
     */
    public static void pos1(Node head) {
        System.out.println("pos1-order: ");
        if (head == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        Stack<Node> stack2 = new Stack<>();
        stack.push(head);
        while (!stack.isEmpty()) {
            head = stack.pop();
            stack2.push(head); // 弹出压入另一个栈
            if (head.left != null) {
                stack.push(head.left);
            }
            if (head.right != null) {
                stack.push (head.right);
            }
        }
        while (!stack2.isEmpty()) {
            System.out.print(stack2.pop().value + " ");
        }
        System.out.println();
    }

    /**
     * 后序排序 方法二
     * @param h
     */
    public static void pos2(Node h) {
        System.out.println("pos2-order: ");
        if (h == null) {
            return;
        }
        Node c = null;
        Stack<Node> stack = new Stack<>();
        stack.push(h);
        while (!stack.isEmpty()) {
            c = stack.peek();
            if (c.left != null && h != c.left && h != c.right) {
                stack.push(c.left);
            } else if (c.right!= null && h != c.right) {
                stack.push(c.right);
            } else {
                System.out.print(stack.pop().value + " ");
                h = c;
            }
        }
        System.out.println();
    }
    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right.left = new Node(6);
        head.right.right = new Node(7);

        pre(head);
        System.out.println("========");
        in(head);
        System.out.println("========");
        pos1(head);
        System.out.println("========");
        pos2(head);
        System.out.println("========");
    }
}
