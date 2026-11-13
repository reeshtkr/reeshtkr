import java.util.*;

/**
 * Two pointers — usually on a sorted array or from both ends.
 * Use when: pair/triplet search, reverse, partition, remove in-place.
 */
public class TwoPointers {

    /** Two sum on sorted array → indices (1-based style optional). O(n) */
    public static int[] twoSumSorted(int[] a, int target) {
        int lo = 0, hi = a.length - 1;
        while (lo < hi) {
            int sum = a[lo] + a[hi];
            if (sum == target) return new int[]{lo, hi};
            if (sum < target) lo++;
            else hi--;
        }
        return new int[]{-1, -1};
    }

    /** Container with most water. O(n) */
    public static int maxArea(int[] height) {
        int lo = 0, hi = height.length - 1, best = 0;
        while (lo < hi) {
            int h = Math.min(height[lo], height[hi]);
            best = Math.max(best, h * (hi - lo));
            if (height[lo] < height[hi]) lo++;
            else hi--;
        }
        return best;
    }

    /** Remove duplicates from sorted array in-place; return new length. O(n) */
    public static int removeDuplicates(int[] a) {
        if (a.length == 0) return 0;
        int w = 1;
        for (int r = 1; r < a.length; r++) {
            if (a[r] != a[w - 1]) a[w++] = a[r];
        }
        return w;
    }

    /** Move zeros to end, preserve relative order of non-zeros. O(n) */
    public static void moveZeroes(int[] a) {
        int w = 0;
        for (int r = 0; r < a.length; r++) {
            if (a[r] != 0) {
                int tmp = a[w];
                a[w++] = a[r];
                a[r] = tmp;
            }
        }
    }

    /** Reverse array / string builder pattern. O(n) */
    public static void reverse(char[] a) {
        int lo = 0, hi = a.length - 1;
        while (lo < hi) {
            char t = a[lo];
            a[lo++] = a[hi];
            a[hi--] = t;
        }
    }

    /** Is palindrome ignoring non-alphanumeric. O(n) */
    public static boolean isPalindrome(String s) {
        int lo = 0, hi = s.length() - 1;
        while (lo < hi) {
            while (lo < hi && !Character.isLetterOrDigit(s.charAt(lo))) lo++;
            while (lo < hi && !Character.isLetterOrDigit(s.charAt(hi))) hi--;
            if (Character.toLowerCase(s.charAt(lo)) != Character.toLowerCase(s.charAt(hi))) {
                return false;
            }
            lo++;
            hi--;
        }
        return true;
    }

    /** Three sum → unique triplets that sum to 0. O(n^2) */
    public static List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> out = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            int lo = i + 1, hi = nums.length - 1;
            while (lo < hi) {
                int sum = nums[i] + nums[lo] + nums[hi];
                if (sum == 0) {
                    out.add(Arrays.asList(nums[i], nums[lo], nums[hi]));
                    while (lo < hi && nums[lo] == nums[lo + 1]) lo++;
                    while (lo < hi && nums[hi] == nums[hi - 1]) hi--;
                    lo++;
                    hi--;
                } else if (sum < 0) lo++;
                else hi--;
            }
        }
        return out;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(twoSumSorted(new int[]{2, 7, 11, 15}, 9))); // [0,1]
        System.out.println(maxArea(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7}));              // 49
        int[] a = {1, 1, 2, 2, 3};
        System.out.println(removeDuplicates(a)); // 3
        System.out.println(isPalindrome("A man, a plan, a canal: Panama")); // true
        System.out.println(threeSum(new int[]{-1, 0, 1, 2, -1, -4}));
    }
}
