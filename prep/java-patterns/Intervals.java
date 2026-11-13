import java.util.*;

/**
 * Interval problems — almost always sort first (by start or by end).
 */
public class Intervals {

    /** Merge overlapping intervals. O(n log n) */
    public static int[][] merge(int[][] intervals) {
        if (intervals.length == 0) return new int[0][];
        Arrays.sort(intervals, Comparator.comparingInt(x -> x[0]));
        List<int[]> out = new ArrayList<>();
        int[] cur = intervals[0].clone();
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] <= cur[1]) {
                cur[1] = Math.max(cur[1], intervals[i][1]);
            } else {
                out.add(cur);
                cur = intervals[i].clone();
            }
        }
        out.add(cur);
        return out.toArray(new int[0][]);
    }

    /**
     * Activity selection / max non-overlapping meetings.
     * Sort by end time; greedily pick next that starts >= lastEnd. O(n log n)
     */
    public static int maxNonOverlapping(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(x -> x[1]));
        int count = 0, lastEnd = Integer.MIN_VALUE;
        for (int[] iv : intervals) {
            if (iv[0] >= lastEnd) {
                count++;
                lastEnd = iv[1];
            }
        }
        return count;
    }

    /** Can attend all meetings? (no overlap). O(n log n) */
    public static boolean canAttendMeetings(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(x -> x[0]));
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] < intervals[i - 1][1]) return false;
        }
        return true;
    }

    /** Insert newInterval into sorted non-overlapping list and merge. O(n) */
    public static int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> out = new ArrayList<>();
        int i = 0, n = intervals.length;
        while (i < n && intervals[i][1] < newInterval[0]) {
            out.add(intervals[i++]);
        }
        while (i < n && intervals[i][0] <= newInterval[1]) {
            newInterval[0] = Math.min(newInterval[0], intervals[i][0]);
            newInterval[1] = Math.max(newInterval[1], intervals[i][1]);
            i++;
        }
        out.add(newInterval);
        while (i < n) out.add(intervals[i++]);
        return out.toArray(new int[0][]);
    }

    private static void print(int[][] a) {
        for (int[] x : a) System.out.print(Arrays.toString(x) + " ");
        System.out.println();
    }

    public static void main(String[] args) {
        print(merge(new int[][]{{1, 3}, {2, 6}, {8, 10}, {15, 18}})); // [1,6] [8,10] [15,18]
        System.out.println(maxNonOverlapping(new int[][]{{1, 2}, {2, 3}, {3, 4}, {1, 3}})); // 3
        System.out.println(canAttendMeetings(new int[][]{{0, 30}, {5, 10}, {15, 20}})); // false
        print(insert(new int[][]{{1, 3}, {6, 9}}, new int[]{2, 5})); // [1,5] [6,9]
    }
}
