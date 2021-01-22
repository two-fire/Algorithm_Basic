import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 求二叉树的最大宽度
 *      建立一个发现机制：发现某层的开始或结束
 */
public class Code06_TreeMaxWidth {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int v) {
            value = v;
        }
    }

    /**
     * 用map的机制
     * @param head
     * @return
     */
    public static int maxWidthUseMap(Node head) {
        if (head == null) {
            return 0;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        // key 在 value 层
        HashMap<Node, Integer> levelMap = new HashMap<>();
        levelMap.put(head, 1);
        int curLevel = 1; // 当前正在统计哪一层的宽度
        int curLevelNodes = 0; // 当前层目前的宽度 弹出时统计
        int max = 0; // 捕获最大宽度
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            int curNodeLevel = levelMap.get(cur); // 当前节点所在层
            if (cur.left != null) {
                queue.add(cur.left);
                levelMap.put(cur.left, curNodeLevel + 1);
            }
            if (cur.right != null) {
                queue.add(cur.right);
                levelMap.put(cur.right, curNodeLevel + 1);
            }
            if (curNodeLevel == curLevel) { // 还未换层
                curLevelNodes++;
            } else {
                // 每当到下一层，把上一层与max比较更新
                max = Math.max(max, curLevelNodes);
                curLevelNodes = 1; // 重置
                curLevel++;
            }
        }
        // 最后一层下面没有了，要单独更新
        max = Math.max(max, curLevelNodes);
        return max;
    }

    /**
     * 不用map的机制
     *      处于当前层时要为发现下一层的最右节点做准备
     */
    public static int maxWidthNoMap(Node head) {
        if (head == null) {
            return 0;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        Node curEnd = head; // 当前层最右节点
        Node nextEnd = null; // 下一层最右节点
        int curLevelNodes = 0;
        int max = 0;

        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            if (cur.left != null) {
                queue.add(cur.left);
                nextEnd = cur.left;
            }
            if (cur.right != null) {
                queue.add(cur.right);
                nextEnd = cur.right;
            }
            curLevelNodes++;
            if (cur == curEnd) {
                max = Math.max(max, curLevelNodes);
                curLevelNodes = 0;
                curEnd = nextEnd;
            }
        }
        return max;
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
        int maxLevel = 10;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (maxWidthUseMap(head) != maxWidthNoMap(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");

    }


}
