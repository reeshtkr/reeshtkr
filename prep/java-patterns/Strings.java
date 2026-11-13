import java.util.*;

/**
 * String patterns — frequency arrays, stack, two pointers on chars.
 */
public class Strings {

    /** Valid anagram. O(n) */
    public static boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) return false;
        int[] cnt = new int[26];
        for (int i = 0; i < s.length(); i++) {
            cnt[s.charAt(i) - 'a']++;
            cnt[t.charAt(i) - 'a']--;
        }
        for (int c : cnt) if (c != 0) return false;
        return true;
    }

    /** First non-repeating character index; -1 if none. O(n) */
    public static int firstUniqChar(String s) {
        int[] freq = new int[26];
        for (char c : s.toCharArray()) freq[c - 'a']++;
        for (int i = 0; i < s.length(); i++) {
            if (freq[s.charAt(i) - 'a'] == 1) return i;
        }
        return -1;
    }

    /** Valid parentheses. O(n) */
    public static boolean isValidParentheses(String s) {
        Deque<Character> st = new ArrayDeque<>();
        Map<Character, Character> close = Map.of(')', '(', ']', '[', '}', '{');
        for (char c : s.toCharArray()) {
            if (!close.containsKey(c)) {
                st.push(c);
            } else {
                if (st.isEmpty() || st.pop() != close.get(c)) return false;
            }
        }
        return st.isEmpty();
    }

    /** Run-length style compress: aabccc → a2bc3 (LeetCode 443 variant returning string). */
    public static String compress(String s) {
        if (s.isEmpty()) return s;
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < s.length()) {
            char c = s.charAt(i);
            int j = i;
            while (j < s.length() && s.charAt(j) == c) j++;
            sb.append(c);
            int count = j - i;
            if (count > 1) sb.append(count);
            i = j;
        }
        return sb.toString();
    }

    /** Reverse words in a sentence: "  hello world  " → "world hello". O(n) */
    public static String reverseWords(String s) {
        String[] parts = s.trim().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (int i = parts.length - 1; i >= 0; i--) {
            sb.append(parts[i]);
            if (i > 0) sb.append(' ');
        }
        return sb.toString();
    }

    /** Longest common prefix. O(n * m) */
    public static String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) return "";
        String prefix = strs[0];
        for (int i = 1; i < strs.length; i++) {
            while (!strs[i].startsWith(prefix)) {
                prefix = prefix.substring(0, prefix.length() - 1);
                if (prefix.isEmpty()) return "";
            }
        }
        return prefix;
    }

    /** Check if s is subsequence of t. O(n) */
    public static boolean isSubsequence(String s, String t) {
        int i = 0;
        for (int j = 0; j < t.length() && i < s.length(); j++) {
            if (s.charAt(i) == t.charAt(j)) i++;
        }
        return i == s.length();
    }

    public static void main(String[] args) {
        System.out.println(isAnagram("anagram", "nagaram"));           // true
        System.out.println(firstUniqChar("leetcode"));                 // 0
        System.out.println(isValidParentheses("{[]}"));                // true
        System.out.println(compress("aabccc"));                        // a2bc3
        System.out.println(reverseWords("  hello   world  "));         // world hello
        System.out.println(longestCommonPrefix(new String[]{"flower", "flow", "flight"})); // fl
        System.out.println(isSubsequence("ace", "abcde"));             // true
    }
}
