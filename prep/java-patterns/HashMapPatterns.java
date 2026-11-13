import java.util.*;

/**
 * HashMap / frequency patterns — O(n) lookups instead of nested loops.
 */
public class HashMapPatterns {

    /** Two sum → indices. O(n) */
    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> seen = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int need = target - nums[i];
            if (seen.containsKey(need)) return new int[]{seen.get(need), i};
            seen.put(nums[i], i);
        }
        return new int[]{-1, -1};
    }

    /** Number of subarrays with sum == k (prefix + map). O(n) */
    public static int subarraySumEqualsK(int[] nums, int k) {
        Map<Integer, Integer> freq = new HashMap<>();
        freq.put(0, 1);
        int sum = 0, count = 0;
        for (int n : nums) {
            sum += n;
            count += freq.getOrDefault(sum - k, 0);
            freq.merge(sum, 1, Integer::sum);
        }
        return count;
    }

    /** Group anagrams. O(n * m log m) with sort key, or O(n*m) with count key. */
    public static List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        for (String s : strs) {
            char[] ch = s.toCharArray();
            Arrays.sort(ch);
            String key = new String(ch);
            map.computeIfAbsent(key, x -> new ArrayList<>()).add(s);
        }
        return new ArrayList<>(map.values());
    }

    /** Character frequency map helper. */
    public static Map<Character, Integer> charFreq(String s) {
        Map<Character, Integer> m = new HashMap<>();
        for (char c : s.toCharArray()) m.merge(c, 1, Integer::sum);
        return m;
    }

    /**
     * First distinct character in a string in O(n) — Barclays-reported coding ask.
     * Returns the char, or '\0' if none.
     */
    public static char firstDistinctChar(String s) {
        LinkedHashMap<Character, Integer> order = new LinkedHashMap<>();
        for (char c : s.toCharArray()) order.merge(c, 1, Integer::sum);
        for (Map.Entry<Character, Integer> e : order.entrySet()) {
            if (e.getValue() == 1) return e.getKey();
        }
        return '\0';
    }

    /** Majority element (> n/2) — Boyer-Moore, but HashMap version shown for clarity. */
    public static int majorityElement(int[] nums) {
        Map<Integer, Integer> freq = new HashMap<>();
        for (int n : nums) {
            int f = freq.merge(n, 1, Integer::sum);
            if (f > nums.length / 2) return n;
        }
        return nums[0];
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(twoSum(new int[]{2, 7, 11, 15}, 9))); // [0,1]
        System.out.println(subarraySumEqualsK(new int[]{1, 1, 1}, 2));           // 2
        System.out.println(groupAnagrams(new String[]{"eat", "tea", "tan", "ate", "nat", "bat"}));
        System.out.println(firstDistinctChar("leetcode")); // 'l'
        System.out.println(majorityElement(new int[]{2, 2, 1, 1, 1, 2, 2})); // 2
    }
}
