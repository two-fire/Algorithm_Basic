import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Heap02 {
    // 堆
    public static class MyHeap<T> {
        private ArrayList<T> heap;
        private HashMap<T, Integer> indexMap;
        private int heapSize;
        private Comparator<? super T> comparator;

        public MyHeap(Comparator<? super T> com) {
            heap = new ArrayList<>();
            indexMap = new HashMap<>();
            heapSize = 0;
            comparator = com;
        }

        public boolean isEmpty() {
            return heapSize == 0;
        }

        public int size() {
            return heapSize;
        }
        
        public boolean contains(T key) {
            return indexMap.containsKey(key);
        }
        
        public void  push(T value) {
            heap.add(value);
            indexMap.put(value, heapSize);
            heapInsert(heapSize++);
        }

        // 删除并返回最大值
        public T pop() {
            T ans = heap.get(0);
            int end = heapSize - 1;
            // 根节点和最后一个节点交换
            swap(0, end);
            heap.remove(ans);
            indexMap.remove(ans);
            // 现在的根节点向下调整
            heapify(0, --heapSize);
            return ans;
        }

        // 修改值
        public void resign(T value) {
            int valueIndex = indexMap.get(value);
            heapInsert(valueIndex);
            heapify(valueIndex, heapSize);
        }


        // 下沉
        private void heapify(int index, int heapSize) {
            int left = index * 2 + 1;
            while (left < heapSize) {
                // 有右孩子 右孩子 < 右孩子
                int largest = left + 1 < heapSize &&
                        (comparator.compare(heap.get(left + 1), heap.get(left)) < 0)
                        ? left + 1 : left;
                largest = comparator.compare(heap.get(largest), heap.get(index)) < 0
                        ? largest : index;
                if (largest == index) {
                    break;
                }
                swap(largest, index);
                index = largest;
                left = index * 2 + 1;

            }
        }

        private void heapInsert(int index) {
            // 向上看 比parent小，向上调整
            while (comparator
                    .compare(
                    heap.get(index),
                    heap.get((index - 1) / 2)) < 0) {
                swap(index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }


        private void swap(int i, int j) {
            T o1 = heap.get(i);
            T o2 = heap.get(j);
            heap.set(i, o2);
            heap.set(j, o1);
            indexMap.put(o1, j);
            indexMap.put(o2, 1);
        }
    }

    public static class Student {
        public int classNo;
        public int age;
        public int id;

        public Student(int c, int a, int i) {
            classNo = c;
            age = a;
            id = i;
        }
    }

    public static class StudentComparator implements Comparator<Student> {
        @Override
        public int compare(Student o1, Student o2) {
            return o1.age - o2.age;
        }
    }

    public static void main(String[] args) {
        Student s1 = null;
        Student s2 = null;
        Student s3 = null;
        Student s4 = null;
        Student s5 = null;
        Student s6 = null;

        s1 = new Student(2, 50, 11111);
        s2 = new Student(1, 60, 22222);
        s3 = new Student(6, 10, 33333);
        s4 = new Student(3, 20, 44444);
        s5 = new Student(7, 72, 55555);
        s6 = new Student(1, 14, 66666);

        PriorityQueue<Student> heap = new PriorityQueue<>(new StudentComparator());
        heap.add(s1);
        heap.add(s2);
        heap.add(s3);
        heap.add(s4);
        heap.add(s5);
        heap.add(s6);

        while (!heap.isEmpty()) {
            Student cur = heap.poll();
            System.out.println(cur.classNo + "," + cur.age + "," + cur.id);
        }
        System.out.println("===============");

        MyHeap<Student> myHeap = new MyHeap<>(new StudentComparator());
        myHeap.push(s1);
        myHeap.push(s2);
        myHeap.push(s3);
        myHeap.push(s4);
        myHeap.push(s5);
        myHeap.push(s6);
        while (!myHeap.isEmpty()) {
            Student cur = myHeap.pop();
            System.out.println(cur.classNo + "," + cur.age + "," + cur.id);
        }
        myHeap = new MyHeap<>(new StudentComparator());

        myHeap.push(s1);
        myHeap.push(s2);
        myHeap.push(s3);
        myHeap.push(s4);
        myHeap.push(s5);
        myHeap.push(s6);
        System.out.println("===============");

        s2.age = 6;
        myHeap.resign(s2);
        s4.age = 12;
        myHeap.resign(s4);
        s5.age = 10;
        myHeap.resign(s5);
        s6.age = 84;
        myHeap.resign(s6);

        while (!myHeap.isEmpty()) {
            Student cur = myHeap.pop();
            System.out.println(cur.classNo + "," + cur.age + "," + cur.id);
        }

    }
}
