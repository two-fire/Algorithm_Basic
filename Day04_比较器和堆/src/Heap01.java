public class Heap01 {
    public static class MyMaxHeap {
        private int[] heap;
        private final int limit;
        private int heapSize;

        public MyMaxHeap(int limit) {
            heap = new int[limit];
            this.limit = limit;
            heapSize = 0;
        }

        public boolean isEmpty() {
            return heapSize == 0;
        }

        public boolean isFull() {
            return heapSize == limit;
        }

        // 加入一个值到堆中
        public void push(int value) {
            if(heapSize == limit) {
                throw new RuntimeException("heap is full");
            }
            heap[heapSize] = value;
            heapInsert(heap, heapSize++);
        }

        // 用户此时，让你返回最大值，并且在大根堆中，把最大值删掉
        // 剩下的数，依然保持大根堆组织
        public int pop() {
            int ans = heap[0];
            swap(heap, 0, --heapSize);
            // 向下调整函数
            heapify(heap, 0, heapSize);
            return ans;
        }

        private void swap(int[] arr, int i, int j) {
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }

        // 从index位置，往下看，不断的下沉，
        // 停：我的孩子都不再比我大；已经没孩子了
        private void heapify(int[] arr, int index, int heapSize) {
            int left = index * 2 + 1;
            while (left < heapSize) {
                // 左、右孩子最大的那个下标给largest
                int largest = left + 1 < heapSize && arr[left + 1] > arr[left] ? left + 1: left;
                // 与arr[index]比较
                largest = arr[largest] > arr[index] ? largest : index;
                if (largest == index) {
                    break;
                }
                swap(arr, largest, index);
                index = largest;
                left = index * 2 + 1;
            }
        }

        // 对末尾新加值进行向上调整
        private void heapInsert(int[] arr, int index) {
            int parent = (index - 1) / 2;
            while (parent >= 0) {
                if(arr[parent] >= arr[index]) {
                    break;
                }
                swap(arr, parent, index);
                index = parent;
                parent = (index - 1) / 2;
            }
        }
    }

    public static void main(String[] args) {
        MyMaxHeap my = new MyMaxHeap(5);
        my.push(1);
        my.push(4);
        my.push(2);
        my.push(6);

        if(my.isEmpty()) {
            System.out.println("空！");
        }
        while (!my.isEmpty()) {
            System.out.println(my.pop());
        }
    }

}
