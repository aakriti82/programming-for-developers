import java.util.HashMap;
import java.util.Map;

public class oneb {

    public static int minBuildTime(int[] engines, int splitCost) {
        int n = engines.length;

        // Memoization map to store already calculated values
        Map<String, Integer> memo = new HashMap<>();

        // Call the recursive helper function
        return helper(engines, splitCost, n, 1, memo);
    }

    private static int helper(int[] engines, int splitCost, int n, int engineers, Map<String, Integer> memo) {
        // Base case: If all engines are built, return 0
        if (n == 0) {
            return 0;
        }

        // Generate a unique key for memoization based on the current state
        String key = n + "-" + engineers;

        // If the result is already calculated, return it
        if (memo.containsKey(key)) {
            return memo.get(key);
        }

        // Calculate the minimum time with the current number of engineers
        int minTime = Integer.MAX_VALUE;
        for (int i = 1; i <= n; i++) {
            int timeWithSplit = Math.max(helper(engines, splitCost, i - 1, engineers, memo),
                    helper(engines, splitCost, n - i, engineers, memo) + splitCost);
            minTime = Math.min(minTime, Math.max(engines[i - 1], timeWithSplit));
        }

        // Memoize the result and return it
        memo.put(key, minTime);
        return minTime;
    }

    public static void main(String[] args) {
        int[] engines = { 1, 2, 3 };
        int splitCost = 4;

        int result = minBuildTime(engines, splitCost);
        System.out.println("Minimum time needed to build all engines: " + result);
    }
}