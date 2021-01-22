/*
    一个几乎有序的数组（把数组排序好后，每个元素移动距离不超过K，且K相对于数组长度来比较小）
    请对这个数组进行排序。
        小根堆代价都是logN级别的
        一共放n个位置，复杂度：O(N*logK)
 */
import java.util.PriorityQueue;

public class SortArrayDistanceLessK {
    public static void sortedArrayDistanceLessK(int[] arr, int k) {
        // 默认最小堆
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        // 前K个加入最小堆
        int index = 0;
        for (; index <= Math.min(k, arr.length - 1); index++) {
            heap.add(arr[index]);
        }
        // 每次弹出最小值，再加入一个值进入最小堆
        int i = 0;
        for (; index < arr.length; index++) {
            arr[i++] = heap.poll();
            heap.add(arr[index]);
        }
        // 最后没有能够加入的值，只需要挨个弹出
        while (!heap.isEmpty()) {
            arr[i++] = heap.poll();
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[] {1,4,0,3,5};
        sortedArrayDistanceLessK(arr, 3);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }


    }
}
