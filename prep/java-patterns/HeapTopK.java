import java.util.*;

/**
 * Heap / Top-K — PriorityQueue as min-heap or max-heap.
 */
public class HeapTopK {

    /** Kth largest element. O(n log k) with min-heap of size k. */
    public static int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> min = new PriorityQueue<>();
        for (int n : nums) {
            min.offer(n);
            if (min.size() > k) min.poll();
        }
        return min.peek();
    }

    /** Top K frequent elements. O(n log k) */
    public static int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> freq = new HashMap<>();
        for (int n : nums) freq.merge(n, 1, Integer::sum);

        PriorityQueue<Map.Entry<Integer, Integer>> min =
                new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));
        for (Map.Entry<Integer, Integer> e : freq.entrySet()) {
            min.offer(e);
            if (min.size() > k) min.poll();
        }
        int[] out = new int[k];
        for (int i = k - 1; i >= 0; i--) out[i] = min.poll().getKey();
        return out;
    }

    /** Merge K sorted lists via min-heap of nodes — skeleton using int arrays. */
    public static List<Integer> mergeKSorted(List<int[]> lists) {
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        // entry: {value, listIndex, elementIndex}
        for (int i = 0; i < lists.size(); i++) {
            if (lists.get(i).length > 0) {
                pq.offer(new int[]{lists.get(i)[0], i, 0});
            }
        }
        List<Integer> out = new ArrayList<>();
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            out.add(cur[0]);
            int li = cur[1], ei = cur[2] + 1;
            if (ei < lists.get(li).length) {
                pq.offer(new int[]{lists.get(li)[ei], li, ei});
            }
        }
        return out;
    }

    public static void main(String[] args) {
        System.out.println(findKthLargest(new int[]{3, 2, 1, 5, 6, 4}, 2)); // 5
        System.out.println(Arrays.toString(topKFrequent(new int[]{1, 1, 1, 2, 2, 3}, 2))); // [1,2]
        System.out.println(mergeKSorted(List.of(
                new int[]{1, 4, 7},
                new int[]{2, 5, 8},
                new int[]{3, 6, 9}
        ))); // 1..9
    }
}
