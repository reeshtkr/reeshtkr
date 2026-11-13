import java.util.*;

/**
 * Prefix sum — turn range/subarray questions into O(1) or O(n) with a map.
 */
public class PrefixSum {

    /** Build prefix where prefix[i] = sum of a[0..i-1]. */
    public static int[] build(int[] a) {
        int[] p = new int[a.length + 1];
        for (int i = 0; i < a.length; i++) p[i + 1] = p[i] + a[i];
        return p;
    }

    /** Sum of a[L..R] inclusive using prefix. O(1) after build. */
    public static int rangeSum(int[] prefix, int L, int R) {
        return prefix[R + 1] - prefix[L];
    }

    /**
     * Longest subarray with equal number of 0s and 1s.
     * Treat 0 as -1; find longest subarray with sum 0. O(n)
     * Example: [0,1,0,0,1,1,1] → 6
     */
    public static int findMaxLengthEqual01(int[] a) {
        Map<Integer, Integer> first = new HashMap<>();
        first.put(0, -1);
        int sum = 0, best = 0;
        for (int i = 0; i < a.length; i++) {
            sum += (a[i] == 0 ? -1 : 1);
            if (first.containsKey(sum)) {
                best = Math.max(best, i - first.get(sum));
            } else {
                first.put(sum, i);
            }
        }
        return best;
    }

    /** Continuous subarray sum divisible by k (LeetCode 523 style). O(n) */
    public static boolean checkSubarraySum(int[] nums, int k) {
        Map<Integer, Integer> modIndex = new HashMap<>();
        modIndex.put(0, -1);
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            int mod = k == 0 ? sum : sum % k;
            if (modIndex.containsKey(mod)) {
                if (i - modIndex.get(mod) >= 2) return true;
            } else {
                modIndex.put(mod, i);
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4};
        int[] p = build(a);
        System.out.println(rangeSum(p, 1, 3)); // 2+3+4 = 9
        System.out.println(findMaxLengthEqual01(new int[]{0, 1, 0, 0, 1, 1, 1})); // 6
        System.out.println(checkSubarraySum(new int[]{23, 2, 4, 6, 7}, 6)); // true
    }
}
