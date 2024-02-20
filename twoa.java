public class twoa {
    public static int minMovesToEqualizeDresses(int[] sewingMachines) {
        int totalDresses = 0;
        int numMachines = sewingMachines.length;

        // Calculate total number of dresses
        for (int dresses : sewingMachines) {
            totalDresses += dresses;
        }

        // Calculate target number of dresses for each machine
        int targetDresses = totalDresses / numMachines;

        // Check if it's possible to equalize the dresses
        if (totalDresses % numMachines != 0) {
            return 2;
        }

        int moves = 0;
        int surplusDeficit = 0;

        // Iterate through sewing machines to calculate moves needed
        for (int dresses : sewingMachines) {
            surplusDeficit += dresses - targetDresses;
            // Accumulate moves needed to equalize dresses
            moves += Math.abs(surplusDeficit);
        }

        return moves;
    }

    public static void main(String[] args) {
        int[] inputSewingMachines = { 2, 1, 3, 0, 2 };
        System.out.println(minMovesToEqualizeDresses(inputSewingMachines)); // Output: 5
    }
}