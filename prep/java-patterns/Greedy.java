import java.util.*;

/**
 * Greedy — sort, then take locally optimal choice.
 * Classic Barclays OA shape: candy store buy-1-get-K-free (min and max cost).
 */
public class Greedy {

    /**
     * Buy 1 get K free. Compute min and max amount to buy all candies.
     * For MIN cost: buy cheapest, get K most expensive free.
     * For MAX cost: buy most expensive, get K cheapest free.
     */
    public static int[] candyStoreMinMax(int[] prices, int k) {
        int[] a = prices.clone();
        Arrays.sort(a);
        int n = a.length;

        // min cost
        int minCost = 0;
        int buy = 0, free = n - 1;
        while (buy <= free) {
            minCost += a[buy++];
            free -= k;
        }

        // max cost
        int maxCost = 0;
        buy = n - 1;
        free = 0;
        while (free <= buy) {
            maxCost += a[buy--];
            free += k;
        }
        return new int[]{minCost, maxCost};
    }

    /** Jump game: can reach last index? O(n) */
    public static boolean canJump(int[] nums) {
        int reach = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i > reach) return false;
            reach = Math.max(reach, i + nums[i]);
        }
        return true;
    }

    /** Assign cookies: maximize content children. O(n log n) */
    public static int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        int i = 0, j = 0;
        while (i < g.length && j < s.length) {
            if (s[j] >= g[i]) i++;
            j++;
        }
        return i;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(candyStoreMinMax(new int[]{3, 2, 1, 4}, 2))); // [3, 7] typical
        System.out.println(canJump(new int[]{2, 3, 1, 1, 4})); // true
        System.out.println(findContentChildren(new int[]{1, 2, 3}, new int[]{1, 1})); // 1
    }
}
