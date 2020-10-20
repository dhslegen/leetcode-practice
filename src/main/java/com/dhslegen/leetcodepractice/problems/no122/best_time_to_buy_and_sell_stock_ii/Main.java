package com.dhslegen.leetcodepractice.problems.no122.best_time_to_buy_and_sell_stock_ii;

/**
 * <h1>买卖股票的最佳时机 II
 * <p>
 * 给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。
 * <p>
 * 设计一个算法来计算你所能获取的最大利润。你可以尽可能地完成更多的交易（多次买卖一支股票）。
 * <p>
 * <b>注意：</b>你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
 * <p>
 * <h3>示例 1:
 * <p>
 * <b>输入:</b> [7,1,5,3,6,4]
 * <p>
 * <b>输出:</b> 7
 * <p>
 * <b>解释:</b> 在第 2 天（股票价格 = 1）的时候买入，在第 3 天（股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。随后，在第 4 天（股票价格 = 3）的时候买入，在第 5 天（股票价格 = 6）的时候卖出, 这笔交易所能获得利润 = 6-3 = 3 。
 * <p>
 * <h3>示例 2:
 * <p>
 * <b>输入:</b> [1,2,3,4,5]
 * <p>
 * <b>输出:</b> 4
 * <p>
 * <b>解释:</b> 在第 1 天（股票价格 = 1）的时候买入，在第 5 天 （股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。注意你不能在第 1 天和第 2 天接连购买股票，之后再将它们卖出。因为这样属于同时参与了多笔交易，你必须在再次购买前出售掉之前的股票。
 * <p>
 * <h3>示例 3:
 * <p>
 * <b>输入:</b> [7,6,4,3,1]
 * <p>
 * <b>输出:</b> 0
 * <p>
 * <b>解释:</b> 在这种情况下, 没有交易完成, 所以最大利润为 0。
 * <p>
 * <h3>提示：
 * <p>
 * 1 <= prices.length <= 3 * 10 ^ 4
 * <p>
 * 0 <= prices[i] <= 10 ^ 4
 */
public class Main {

    public static void main(String[] args) {
        int[] prices = {7, 1, 5, 3, 6, 4};
        int[] prices1 = {7, 6, 4, 3, 1};
        int maxProfit = maxProfit(prices);
        int maxProfit1 = maxProfit(prices1);
        System.out.println(maxProfit);
        System.out.println(maxProfit1);
    }

    /**
     * <h2>方法一：暴力法</h2>
     * 这种情况下，我们只需要计算与所有可能的交易组合相对应的利润，并找出它们中的最大利润。采用递归法，回扣为开始位置为数组长度时的最高利润为0。
     */
    public static int maxProfit(int[] prices) {
        return calculate(prices, 0);
    }

    /**
     * 从指定位置向后寻找利润最大值
     *
     * @param prices 价格数组
     * @param s      开始下标
     * @return 最大利润
     */
    public static int calculate(int[] prices, int s) {
        if (s >= prices.length) {
            return 0;
        }
        int max = 0;
        // 遍历每个起点
        for (int start = s; start < prices.length; start++) {
            int maxProfit = 0;
            // 遍历每个结尾到起点的利润值
            for (int i = start + 1; i < prices.length; i++) {
                if (prices[i] > prices[start]) {
                    // 递归调用计算后续起点的最大值
                    int profit = prices[i] - prices[start] + calculate(prices, i + 1);
                    if (profit > maxProfit) {
                        maxProfit = profit;
                    }
                }
            }
            if (maxProfit > max) {
                max = maxProfit;
            }
        }
        return max;
    }

    /**
     * <h2>方法二：峰谷法</h2>
     * <p>
     * 假设给定的数组为：
     * <p>
     * [7, 1, 5, 3, 6, 4]
     * <p>
     * 如果我们在图表上绘制给定数组中的数字，我们将会得到：
     * <p>
     * <img src = "https://pic.leetcode-cn.com/d447f96d20d1cfded20a5d08993b3658ed08e295ecc9aea300ad5e3f4466e0fe-file_1555699515174" width = "500"></img>
     * <p>
     * 如果我们分析图表，那么我们的兴趣点是连续的峰和谷。
     * <p>
     * 用数学语言描述为：TotalProfit = ∑i(height(peaki)−height(valleyi))
     * <p>
     * 关键是我们需要考虑到紧跟谷的每一个峰值以最大化利润。如果我们试图跳过其中一个峰值来获取更多利润，那么我们最终将失去其中一笔交易中获得的利润，从而导致总利润的降低。
     * <p>
     * 例如，在上述情况下，如果我们跳过 peaki、valleyj试图通过考虑差异较大的点以获取更多的利润，获得的净利润总是会小与包含它们而获得的净利润，因为 C 总是小于 A+B。
     * <p>
     * <b>复杂度分析</b>
     * <p>
     * <b>时间复杂度：</b>O(n)。遍历一次。
     * <p>
     * <b>空间复杂度：</b>O(1)。需要常量的空间
     */
    public static int maxProfit1(int[] prices) {
        int i = 0;
        int valley;
        int peak;
        int maxProfit = 0;
        while (i < prices.length - 1) {
            while (i < prices.length - 1 && prices[i] >= prices[i + 1]) {
                i++;
            }
            valley = prices[i];
            while (i < prices.length - 1 && prices[i] <= prices[i + 1]) {
                i++;
            }
            peak = prices[i];
            maxProfit += peak - valley;
        }
        return maxProfit;
    }

    /**
     * <h2>方法三：简单的一次遍历</h2>
     * <p>
     * 该解决方案遵循 方法二 的本身使用的逻辑，但有一些轻微的变化。在这种情况下，我们可以简单地继续在斜坡上爬升并持续增加从连续交易中获得的利润，而不是在谷之后寻找每个峰值。最后，我们将有效地使用峰值和谷值，但我们不需要跟踪峰值和谷值对应的成本以及最大利润，但我们可以直接继续增加加数组的连续数字之间的差值，如果第二个数字大于第一个数字，我们获得的总和将是最大利润。这种方法将简化解决方案。
     * <p>
     * 这个例子可以更清楚地展现上述情况：
     * <p>
     * [1, 7, 2, 3, 6, 7, 6, 7]
     * <p>
     * 与此数组对应的图形是：
     * <p>
     * <img src = "https://pic.leetcode-cn.com/6eaf01901108809ca5dfeaef75c9417d6b287c841065525083d1e2aac0ea1de4-file_1555699697692" width = "500">
     * <p>
     * 从上图中，我们可以观察到 A+B+C 的和等于差值 D 所对应的连续峰和谷的高度之差。
     * <p>
     * <b>复杂度分析</b>
     * <p>
     * <b>时间复杂度：</b>O(n)，遍历一次。
     * <p>
     * <b>空间复杂度：</b>O(1)，需要常量的空间。
     */
    public static int maxProfit2(int[] prices) {
        int maxProfit = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                maxProfit += prices[i] - prices[i - 1];
            }
        }
        return maxProfit;
    }

}
