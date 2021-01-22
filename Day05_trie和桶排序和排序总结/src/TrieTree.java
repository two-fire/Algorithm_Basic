import java.util.HashMap;

/**
 * 前缀树 查询特别快 字符有多少个，代价就是多少
 * 可以查询以前缀为单位的某些信息
 */
public class TrieTree {
    /**
     * 字符种类小的时候
     */
    public static class Node1 {
        public int pass;
        public int end;
        public Node1[] nexts;

        public Node1() {
            pass = 0;
            end =0;
            // next[i] == null i方向上的路不存在
            // next[i] ！= null i方向上的路存在
            // 提前准备好26条路 a...z
            nexts = new Node1[26];
        }
    }
    public static class Trie1 {
        private Node1 root;
        public Trie1() {
            root = new Node1();
        }

        /**
         * 加入一个word
         * @param word
         */
        public void insert(String word) {
            if (word == null) {
                return;
            }
            char[] str = word.toCharArray();
            Node1 node = root;
            // 准备加字符，头节点的 pass先 + 1
            node.pass++;
            int path = 0;
            // 从左往右遍历字符
            for (int i = 0; i < str.length; i++) {
                path = str[i] - 'a'; // 字符对应走哪条路
                if (node.nexts[path] == null) { // 没有路，建出一个新结点
                    node.nexts[path] = new Node1();
                }
                node = node.nexts[path];
                node.pass++;
            }
            node.end++;
        }

        /**
         * 删除字符串word
         * @param word
         */
        public void delete(String word) {
            if(search(word) != 0) {
                char[] str = word.toCharArray();
                Node1 node = root;
                node.pass--;
                int path = 0;
                for (int i = 0; i < str.length; i++) {
                    path = str[i] - 'a';
                    if(--node.nexts[path].pass == 0) { // 结点的p为0，之后的结点都可以删除
                        node.nexts[path] = null;
                        return;
                    }
                    node = node.nexts[path];
                }
                node.end--;
            }
        }

        /**
         * word这个单词之前加入过几次
         * @param word
         * @return 加入的次数
         */
        public int search(String word) {
            if(word == null) {
                return 0;
            }
            char[] str = word.toCharArray();
            Node1 node = root;
            int path = 0;
            for (int i = 0; i < str.length; i++) {
                path = str[i] - 'a';
                if (node.nexts[path] == null) { // 没有路了没加入该字符串
                    return 0;
                }
                node = node.nexts[path];
            }
            return node.end;
        }

        /**
         * 所有加入的字符串中，有几个是以pre这个字符串为前缀的
         * @param pre
         * @return 以pre这个字符串为前缀的符串中个数
         */
        public int prefixNumber(String pre) {
            if (pre == null) {
                return 0;
            }
            char[] str = pre.toCharArray();
            Node1 node = root;
            int path = 0;
            for (int i = 0; i < str.length; i++) {
                path = str[i] - 'a';
                if (node.nexts[path] == null) {
                    return 0;
                }
                node = node.nexts[path];
            }
            return node.pass;
        }
    }

    /**
     * 字符种类多时
     */
    public static class Node2 {
        public int pass;
        public int end;
        public HashMap<Integer, Node2> nexts;

        public Node2() {
            pass = 0;
            end = 0;
            nexts = new HashMap<>();
        }
    }
    public static class Trie2 {
        private Node2 root;

        public Trie2() {
            root = new Node2();
        }

        public void insert(String word) {
            if (word == null) {
                return;
            }
            char[] str = word.toCharArray();
            Node2 node = root;
            node.pass++;
            int path = 0;
            for (int i = 0; i < str.length; i++) {
                path = (int)str[i];
                if (!node.nexts.containsKey(path)) {
                    node.nexts.put(path, new Node2());
                }
                node = node.nexts.get(path);
                node.pass++;
            }
            node.end++;
        }

        public void delete(String word) {
            if (search(word) != 0) {
                char[] str = word.toCharArray();
                Node2 node = root;
                node.pass--;
                int path = 0;
                for (int i = 0; i < str.length; i++) {
                    path = (int) str[i];
                    if (--node.nexts.get(path).pass == 0) {
                        node.nexts.remove(path);
                        return;
                    }
                    node = node.nexts.get(path);
                }
                node.end--;
            }
        }

        public int search(String word) {
            if(word == null) {
                return 0;
            }
            char[] str = word.toCharArray();
            Node2 node = root;
            int path = 0;
            for (int i = 0; i < str.length; i++) {
                path = (int)str[i];
                if (!node.nexts.containsKey(path)) { // 没有路了没加入该字符串
                    return 0;
                }
                node = node.nexts.get(path);
            }
            return node.end;
        }

        public int prefixNumber(String pre) {
            if (pre == null) {
                return 0;
            }
            char[] str = pre.toCharArray();
            Node2 node = root;
            int path = 0;
            for (int i = 0; i < str.length; i++) {
                path = (int)str[i];
                if (!node.nexts.containsKey(path)) {
                    return 0;
                }
                node = node.nexts.get(path);
            }
            return node.pass;
        }
    }
    public static class Right {

        private HashMap<String, Integer> box;

        public Right() {
            box = new HashMap<>();
        }

        public void insert(String word) {
            if (!box.containsKey(word)) {
                box.put(word, 1);
            } else {
                box.put(word, box.get(word) + 1);
            }
        }

        public void delete(String word) {
            if (box.containsKey(word)) {
                if (box.get(word) == 1) {
                    box.remove(word);
                } else {
                    box.put(word, box.get(word) - 1);
                }
            }
        }

        public int search(String word) {
            if (!box.containsKey(word)) {
                return 0;
            } else {
                return box.get(word);
            }
        }

        public int prefixNumber(String pre) {
            int count = 0;
            for (String cur : box.keySet()) {
                if (cur.startsWith(pre)) {
                    count += box.get(cur);
                }
            }
            return count;
        }
    }

    // for test
    public static String generateRandomString(int strLen) {
        char[] ans = new char[(int) (Math.random() * strLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            int value = (int) (Math.random() * 6);
            ans[i] = (char) (97 + value);
        }
        return String.valueOf(ans);
    }

    // for test
    public static String[] generateRandomStringArray(int arrLen, int strLen) {
        String[] ans = new String[(int) (Math.random() * arrLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = generateRandomString(strLen);
        }
        return ans;
    }

    public static void main(String[] args) {
        int arrLen = 100;
        int strLen = 20;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            String[] arr = generateRandomStringArray(arrLen, strLen);
            Trie1 trie1 = new Trie1();
            Trie2 trie2 = new Trie2();
            Right right = new Right();
            for (int j = 0; j < arr.length; j++) {
                double decide = Math.random();
                if (decide < 0.25) {
                    trie1.insert(arr[j]);
                    trie2.insert(arr[j]);
                    right.insert(arr[j]);
                } else if (decide < 0.5) {
                    trie1.delete(arr[j]);
                    trie2.delete(arr[j]);
                    right.delete(arr[j]);
                } else if (decide < 0.75) {
                    int ans1 = trie1.search(arr[j]);
                    int ans2 = trie2.search(arr[j]);
                    int ans3 = right.search(arr[j]);
                    if (ans1 != ans2 || ans2 != ans3) {
                        System.out.println("Oops!");
                    }
                } else {
                    int ans1 = trie1.prefixNumber(arr[j]);
                    int ans2 = trie2.prefixNumber(arr[j]);
                    int ans3 = right.prefixNumber(arr[j]);
                    if (ans1 != ans2 || ans2 != ans3) {
                        System.out.println("Oops!");
                    }
                }
            }
        }
        System.out.println("finish!");

    }
}
