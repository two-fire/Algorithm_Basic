import java.util.LinkedList;

/**
 * Complete Binary Tree 完全二叉树
 * 给定一颗二叉树的头节点head，判断它是否为CBT:
 *  1. 任何节点有右孩子无左孩子，false；否则继续
 *  2. 一旦遇到左右孩子不双全，后续遇到所有节点必须为叶子节点
 */
public class Code06_IsCBT {
    public static class Node {
        private int value;
        private Node left;
        private Node right;

        public Node(int v) {
            value = v;
        }
    }

    public static boolean isCBT1(Node head) {
        if (head == null) {
            return true;
        }
        // 创建队列用来宽度优先遍历
        LinkedList<Node> queue = new LinkedList<>();
        // 是否遇到孩子不双全的节点
        Boolean leaf = false;
        Node l = null;
        Node r = null;
        queue.add(head);
        while (!queue.isEmpty()) {
            head = queue.poll();
            l = head.left;
            r = head.right;
            // 1. 有右无左 2.  遇到不双全后，又发现当前节点不是叶节点
            if (r != null && l == null || (leaf && !(l == null && r == null))) {
                return false;
            }
            if (l != null) {
                queue.add(l);
            }
            if (r != null) {
                queue.add(r);
            }
            if (r == null || l == null) { // 不能放到最前面，因为第一个不双全的节点后才要进行2. 判断
                leaf = true;
            }
        }
        return true;
    }
}