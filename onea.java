public class onea {

    public static int minCostToDecorate(int[][] costs) {
        if (costs == null || costs.length == 0 || costs[0].length == 0) {
            return 0;
        }

        int venues = costs.length;
        int themes = costs[0].length;

        // dp[i][j] represents the minimum cost to decorate venues 0 to i with venue i
        // decorated with theme j
        int[][] dp = new int[venues][themes];

        // Initialize the first row of dp with the costs of decorating the first venue
        for (int j = 0; j < themes; j++) {
            dp[0][j] = costs[0][j];
        }

        // Fill in the dp matrix using the recurrence relation
        for (int i = 1; i < venues; i++) {
            for (int j = 0; j < themes; j++) {
                int minPrevCost = Integer.MAX_VALUE;

                for (int k = 0; k < themes; k++) {
                    if (k != j) {
                        minPrevCost = Math.min(minPrevCost, dp[i - 1][k]);
                    }
                }

                dp[i][j] = costs[i][j] + minPrevCost;
            }
        }

        // Find the minimum cost from the last row of dp
        int minCost = Integer.MAX_VALUE;
        for (int j = 0; j < themes; j++) {
            minCost = Math.min(minCost, dp[venues - 1][j]);
        }

        return minCost;
    }

    public static void main(String[] args) {
        int[][] costs1 = { { 1, 5, 3 }, { 2, 9, 4 } };
        System.out.println(minCostToDecorate(costs1)); // Output: 5

        int[][] costs2 = { { 1, 3, 2 }, { 4, 6, 8 }, { 3, 1, 5 } };
        System.out.println(minCostToDecorate(costs2)); // Output: 7
    }
}