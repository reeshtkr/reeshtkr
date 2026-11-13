import java.util.*;

/**
 * Sliding window — expand right, shrink left when invalid.
 * Use when: contiguous subarray/substring + constraint (size, sum, distinct count).
 */
public class SlidingWindow {

    /** Longest substring with at most K distinct characters. O(n) */
    public static int longestWithAtMostKDistinct(String s, int k) {
        if (k <= 0 || s.isEmpty()) return 0;
        Map<Character, Integer> freq = new HashMap<>();
        int left = 0, best = 0;
        for (int right = 0; right < s.length(); right++) {
            freq.merge(s.charAt(right), 1, Integer::sum);
            while (freq.size() > k) {
                char L = s.charAt(left++);
                freq.put(L, freq.get(L) - 1);
                if (freq.get(L) == 0) freq.remove(L);
            }
            best = Math.max(best, right - left + 1);
        }
        return best;
    }

    /** Minimum window substring covering all chars of t. O(n + m) */
    public static String minWindowSubstring(String s, String t) {
        if (t.isEmpty() || s.length() < t.length()) return "";
        Map<Character, Integer> need = new HashMap<>();
        for (char c : t.toCharArray()) need.merge(c, 1, Integer::sum);

        int missing = need.size();
        int left = 0, bestL = 0, bestLen = Integer.MAX_VALUE;
        Map<Character, Integer> have = new HashMap<>();

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            have.merge(c, 1, Integer::sum);
            if (need.containsKey(c) && have.get(c).intValue() == need.get(c).intValue()) {
                missing--;
            }
            while (missing == 0) {
                if (right - left + 1 < bestLen) {
                    bestLen = right - left + 1;
                    bestL = left;
                }
                char L = s.charAt(left++);
                have.put(L, have.get(L) - 1);
                if (need.containsKey(L) && have.get(L) < need.get(L)) {
                    missing++;
                }
            }
        }
        return bestLen == Integer.MAX_VALUE ? "" : s.substring(bestL, bestL + bestLen);
    }

    /** Max sum of any contiguous window of fixed size K. O(n) */
    public static int maxSumFixedWindow(int[] a, int k) {
        if (a.length < k) return 0;
        int sum = 0;
        for (int i = 0; i < k; i++) sum += a[i];
        int best = sum;
        for (int i = k; i < a.length; i++) {
            sum += a[i] - a[i - k];
            best = Math.max(best, sum);
        }
        return best;
    }

    /** Longest substring without repeating characters. O(n) */
    public static int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> last = new HashMap<>();
        int left = 0, best = 0;
        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            if (last.containsKey(c) && last.get(c) >= left) {
                left = last.get(c) + 1;
            }
            last.put(c, right);
            best = Math.max(best, right - left + 1);
        }
        return best;
    }

    public static void main(String[] args) {
        System.out.println(longestWithAtMostKDistinct("eceba", 2));       // 3 ("ece")
        System.out.println(minWindowSubstring("ADOBECODEBANC", "ABC"));   // BANC
        System.out.println(maxSumFixedWindow(new int[]{2, 1, 5, 1, 3, 2}, 3)); // 9
        System.out.println(lengthOfLongestSubstring("abcabcbb"));         // 3
    }
}
