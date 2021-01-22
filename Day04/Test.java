import java.util.Comparator;
import java.util.PriorityQueue;

public class Test {
    // 返回负数时，认为是o1放前面的情况
    public static class MyComp implements Comparator<Integer> {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o2 - o1;
        }
    }

    public static void main(String[] args) {
        // 自定义大根堆
        //PriorityQueue<Integer> heap = new PriorityQueue<>(new MyComp());
        // 优先级队列底层就是堆  默认小根堆
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        heap.add(8);
        heap.add(1);
        heap.add(4);
        heap.add(5);
        heap.add(1);

        while (!heap.isEmpty()) {
            System.out.println(heap.poll());
        }
    }
}
