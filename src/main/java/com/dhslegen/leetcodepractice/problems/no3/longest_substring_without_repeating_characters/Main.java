package com.dhslegen.leetcodepractice.problems.no3.longest_substring_without_repeating_characters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <h1>无重复字符的最长子串
 * <p>
 * 给定一个字符串，请你找出其中不含有重复字符的 <b>最长子串</b> 的长度。
 * <p>
 * <h3>示例 1:
 * <p>
 * <b>输入:</b> "abcabcbb"
 * <p>
 * <b>输出:</b> 3
 * <p>
 * <b>解释:</b> 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 * <p>
 * <h3>示例 2:
 * <p>
 * <b>输入:</b> "bbbbb"
 * <p>
 * <b>输出:</b> 1
 * <p>
 * <b>解释:</b> 因为无重复字符的最长子串是 "b"，所以其长度为 1。
 * <p>
 * <h3>示例 3:
 * <p>
 * <b>输入:</b> "pwwkew"
 * <p>
 * <b>输出:</b> 3
 * <p>
 * <b>解释:</b> 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
 * <p>
 * 请注意，你的答案必须是 <b>子串</b> 的长度，"pwke" 是一个子序列，不是子串。
 *
 * @author zhaowenhao
 */
public class Main {

    public static void main(String[] args) {
        String s = "aaasdweratyuio";
        System.out.println(lengthOfLongestSubstring(s));
        System.out.println(lengthOfLongestSubstring1(s));
    }

    /**
     * <h2>滑动窗口
     * <li>我们使用两个指针表示字符串中的某个子串（的左右边界）。其中左指针代表着上文中「枚举子串的起始位置」，而右指针即为上文中的rk;
     * <li>在每一步的操作中，我们会将左指针向右移动一格，表示 我们开始枚举下一个字符作为起始位置，然后我们可以不断地向右移动右指针，但需要保证这两个指针对应的子串中没有重复的字符。在移动结束后，这个子串就对应着 以左指针开始的，不包含重复字符的最长子串。我们记录下这个子串的长度；
     * <li>在枚举结束后，我们找到的最长的子串的长度即为答案。
     * <p>
     * <h2>复杂度分析
     * <li>时间复杂度：O(N)，其中 N 是字符串的长度。左指针和右指针分别会遍历整个字符串一次。
     * <li>空间复杂度：O(∣Σ∣)，其中 Σ 表示字符集（即字符串中可以出现的字符），∣Σ∣ 表示字符集的大小。在本题中没有明确说明字符集，因此可以默认为所有 ASCII 码在 [0,128) 内的字符，即 ∣Σ∣=128。我们需要用到哈希集合来存储出现过的字符，而字符最多有 ∣Σ∣ 个，因此空间复杂度为 O(∣Σ∣)。
     */
    public static int lengthOfLongestSubstring(String s) {
        if (s.length() == 0) {
            return 0;
        }
        // 保存出现过的记录
        List<Character> characters = new ArrayList<Character>();
        // Set<Character> characters = new HashSet<Character>();
        int ans = 0;
        int rk = 0;
        int length = s.length();
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                // 收缩窗口
                characters.remove((Character) s.charAt(i - 1));
            }
            while (rk < length && !characters.contains(s.charAt(rk))) {
                // 扩大窗口
                characters.add(s.charAt(rk));
                rk++;
            }
            ans = Math.max(ans, characters.size());
        }
        return ans;
    }

    /**
     * <h2>滑动窗口的map优化
     * <p>
     * 与以上题解角度不同的实现，这里是先扩展后缩小，加上map的key不重复的独特性，快速查找新的左边界
     * <p>
     * <h3>什么是滑动窗口？
     * <p>
     * 其实就是一个队列,比如例题中的 abcabcbb，进入这个队列（窗口）为 abc 满足题目要求，当再进入 a，队列变成了 abca，这时候不满足要求。所以，我们要移动这个队列！
     * <p>
     * <h3>如何移动？
     * <p>
     * 我们只要把队列的左边的元素移出就行了，直到满足题目要求！一直维持这样的队列，找出队列出现最长的长度时候，求出解！
     * <p>
     * <b>时间复杂度：</b>O(n)
     * <p>
     */
    public static int lengthOfLongestSubstring1(String s) {
        if (s.length() == 0) {
            return 0;
        }
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        int max = 0;
        int left = 0;
        for (int i = 0; i < s.length(); i++) {
            if (map.containsKey(s.charAt(i))) {
                // 这里是点睛之笔，有可能找到的已经在left之前，即淘汰掉了，这样保证始终在存在之中找新的left
                left = Math.max(left, map.get(s.charAt(i)) + 1);
            }
            map.put(s.charAt(i), i);
            max = Math.max(max, i - left + 1);
        }
        return max;
    }


    public static int lengthOfLongestSubstring2(String s) {
        if (s.length() == 0) {
            return 0;
        }
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        int max = 0;
        int left = 0;
        for (int i = 0; i < s.length(); i++) {
            if (map.containsKey(s.charAt(i))) {
                // 这里是点睛之笔，有可能找到的已经在left之前，即淘汰掉了，这样保证始终在存在之中找新的left
                left = Math.max(left, map.get(s.charAt(i)) + 1);
            }
            map.put(s.charAt(i), i);
            max = Math.max(max, i - left + 1);
        }
        return max;
    }

}
