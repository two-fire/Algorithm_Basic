import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * 二叉树的递归套路深度实践：
 *  给定一棵二叉树的头节点head，和另外两个节点a和b。返回a和b的最低公共祖先
 *
 * 方法一：时间复杂度O(n)
 *  遍历二叉树，生成一张map，里面存放节点-对应的父节点
 *  然后找到a的所有父节点放到一个hashset中
 *  然后对b进行向上检索，与hashset中的值进行比较，看是否能找到相同的值。
 *
 * 方法二：
 * 情况：
 *  1. o1，o2，无一个在X为头的树上
 *  2. o1，o2，只有一个在X为头的树上
 *  3. o1，o2，都在X为头的树上
 *      1)左右各一个
 *      2）o1，o2在左树上
 *      3）o1，o2在右树上
 *      4）X自己是o1或者o2
 * 任何子树返回三个信息：o1，o2的最初交汇节点，在这棵子树上你有没有发现o1，有没有发现o2
 */
public class Code07_lowestAncestor {

    public static class Node {
        private int value;
        private Node left;
        private Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    /**
     * 方法一
     * @param head
     * @param o1
     * @param o2
     * @return
     */
    public static Node lowestAncestor1(Node head, Node o1, Node o2) {
        if (head == null) {
            return null;
        }
        HashMap<Node, Node> parentMap = new HashMap<>();
        parentMap.put(head, null);
        // 存放节点-对应的父节点
        fillParentMap(head, parentMap);
        // 找到a的所有父节点放到一个HashSet中
        HashSet<Node> o1Set = new HashSet<>();
        Node cur = o1;
        o1Set.add(cur);
        while (parentMap.get(cur) != null) {
            cur = parentMap.get(cur);
            o1Set.add(cur);
        }
        // b的父节点与o1Set进行比对
        cur = o2;
        while (!o1Set.contains(cur)) {
            cur = parentMap.get(cur);
        }
//        while (parentMap.get(cur) != null) {
//            if(o1Set.contains(cur)) {
//                break;
//            }
//            cur = parentMap.get(cur);
//        }
        return cur;
    }

    /**
     * 存放节点-对应的父节点
     * @param head
     * @param parentMap
     */
    public static void fillParentMap(Node head, HashMap<Node, Node> parentMap) {
        if (head.left != null) {
            parentMap.put(head.left, head);
            fillParentMap(head.left, parentMap);
        }
        if (head.right != null) {
            parentMap.put(head.right, head);
            fillParentMap(head.right, parentMap);
        }
    }

    // 需要的信息：o1，o2的最初交汇节点，在这棵子树上你有没有发现o1，有没有发现o2
    public static class Info {
        private Node ans;
        private boolean findO1;
        private boolean findO2;

        public Info(Node ans, boolean findO1, boolean findO2) {
            this.ans = ans;
            this.findO1 = findO1;
            this.findO2 = findO2;
        }
    }

    public static Node lowestAncestor2(Node head, Node o1, Node o2) {
        return process(head, o1, o2).ans;
    }

    /**
     * 对于任何节点X一定要返回三个信息
     * @param X
     * @param o1
     * @param o2
     * @return
     */
    public static Info process(Node X, Node o1, Node o2) {
        if (X == null) {
            return new Info(null, false, false);
        }
        Info leftInfo = process(X.left, o1, o2);
        Info rightInfo = process(X.right, o1, o2);
        //整合三个信息
        boolean findO1 = X == o1 || leftInfo.findO1 ||rightInfo.findO1;
        boolean findO2 = X == o2 || leftInfo.findO2 ||rightInfo.findO2;
        /**
         * 最初的交汇点在哪？
         *  1）左树上已经提前交汇了
         *  2）右树上已经提前交汇了
         *  3）左右都没有提前交汇，但是o1，o2全了
         *  4）其他情况，意味着还没有发现交汇点，ans=null继续向上
         */
        Node ans = null;
        if (leftInfo.ans != null) {
            ans = leftInfo.ans;
        } else if (rightInfo.ans != null) {
            ans = rightInfo.ans;
        } else if (ans == null && findO1 && findO2) {
            ans = X;
        }
        return new Info(ans, findO1, findO2);
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

    // for test
    public static Node pickRandomOne(Node head) {
        if (head == null) {
            return null;
        }
        ArrayList<Node> arr = new ArrayList<>();
        fillPrelist(head, arr);
        int randomIndex = (int) (Math.random() * arr.size());
        return arr.get(randomIndex);
    }

    // for test
    public static void fillPrelist(Node head, ArrayList<Node> arr) {
        if (head == null) {
            return;
        }
        arr.add(head);
        fillPrelist(head.left, arr);
        fillPrelist(head.right, arr);
    }

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            Node o1 = pickRandomOne(head);
            Node o2 = pickRandomOne(head);
            if (lowestAncestor1(head, o1, o2) != lowestAncestor2(head, o1, o2)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }

}
