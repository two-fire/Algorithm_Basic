/**
 * 二叉树递归套路
 *  1）假设以X节点为头，假设可以向X左树和X右树要任何信息
 *  2）在上一步的假设下，讨论以X为头节点的树，得到答案的可能性（最重要）
 *  3）列出所有可能性后，确定到底需要向左树、右树要什么样的信息
 *  4）把左树信息和右树信息求全集，即任何一棵树都需要返回的信息S
 *  5）递归函数都返回S，每一棵子树都这么要求
 *  6）写代码，在代码中考虑如何把左树信息和右树信息整合出整棵树的信息
 *
 *
 *  给定一棵二叉树头节点head，返回这棵二叉树是否是平衡二叉树
 */
public class Code01_IsBalanced {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int v) {
            value = v;
        }
    }

    public static boolean isBalanced1(Node head) {
        boolean[] ans = new boolean[1];
        ans[0] = true;
        process1(head, ans);
        return ans[0];
    }

    public static int process1(Node head, boolean[] ans) {
        if (!ans[0] || head == null) {
            return -1;
        }
        int leftHeight = process1(head.left, ans);
        int rightHeight = process1(head.right, ans);
        if (Math.abs(leftHeight - rightHeight) > 1) {
            ans[0] = false;
        }
        return Math.max(leftHeight, rightHeight) + 1;
    }

    /**
     * 判断二叉树是否是平衡二叉树
     * @param head 此树的头节点
     * @return
     */
    public static boolean isBalanced2(Node head) {
        return process2(head).isBalanced;
    }

    // 左右要求一样，Info信息返回的结构体
    public static class Info {
        public boolean isBalanced;
        public int height;

        public Info(boolean b, int h) {
            isBalanced = b;
            height = h;
        }
    }

    public static Info process2(Node X) {
        if (X == null) {
            return new Info(true, 0);
        }
        Info leftInfo = process2(X.left);
        Info rightInfo = process2(X.right);

        int height = Math.max(leftInfo.height, rightInfo.height) + 1;

        boolean isBalanced = true;
        if (!leftInfo.isBalanced || !rightInfo.isBalanced ||
                Math.abs(rightInfo.height - leftInfo.height) > 1) {
            isBalanced = false;
        }
        return new Info(isBalanced, height);
    }

    // for test
    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // for test
    public static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    public static void main(String[] args) {
        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (isBalanced1(head) != isBalanced2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }


}
