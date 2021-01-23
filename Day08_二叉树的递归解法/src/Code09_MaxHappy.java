import java.util.ArrayList;
import java.util.List;

/**派对的最大快乐值
 * 公司每个员工都符合Employee类的描述。
 * 每个员工只存自己的直接下级，整棵树可以写成一个多叉树。
 * 这个公司要办party，你可以决定哪些员工来，哪些员工不来，规则：
 *      1. 如果某个员工来了，那么这个员工的所有直接下级都不能来
 *      2. 派对的整体快乐值是所有到场员工快乐值的累加
 *      3. 目标是让整体快乐值大
 *
 * 思路：
 *      1）大分类：
 *          x
 *        a b c
 *          (1) x不来
 *              0 + max{a来、整棵树的最大快乐值，a不来、整棵树的最大快乐值} +max{b..}+ max{c..}
 *
 *          (2) x来
 *              x.happy + 不包括a的整棵子树的快乐值 + 不包括b.. + 不包括c..
 *      2）子info ：
 *          (1) x不来，整棵子树最大快乐值
 *          (2) x来，整棵子树最大快乐值
 *
 */
public class Code09_MaxHappy {
    public static class Employee {
        public int happy;
        public List<Employee> nexts; // 直接下级

        public Employee(int h) {
            happy = h;
            nexts = new ArrayList<>();
        }
    }
    public static class Info {
        public int yes;
        public int no;

        public Info(int y, int n) {
            yes = y;
            no = n;
        }
    }

    public static Info process2(Employee x) {
        if (x.nexts.isEmpty()) {
            return new Info(x.happy, 0);
        }
        int yes = x.happy;
        int no = 0;
        for (Employee next : x.nexts) {
            Info nextInfo = process2(next);
            yes += nextInfo.no;
            no += Math.max(nextInfo.yes, nextInfo.no);
        }
        return new Info(yes, no);
    }
}