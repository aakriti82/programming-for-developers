import java.util.ArrayList;
import java.util.List;

public class twob {

    public static List<Integer> findIndividuals(int n, int[][] intervals, int firstPerson) {
        List<Integer> knownIndividuals = new ArrayList<>();
        boolean[] receivedSecret = new boolean[n];

        // Initialize the first person who has the secret
        receivedSecret[firstPerson] = true;
        knownIndividuals.add(firstPerson);

        // Simulate the secret-sharing process for each interval
        for (int[] interval : intervals) {
            int start = interval[0];
            int end = interval[1];

            // Share the secret with individuals in the given interval
            for (int i = start; i <= end; i++) {
                if (receivedSecret[i]) {
                    continue; // Skip if the individual has already received the secret
                }

                // Share the secret and mark the individual as known
                receivedSecret[i] = true;
                knownIndividuals.add(i);
            }
        }

        return knownIndividuals;
    }

    public static void main(String[] args) {
        int n = 5;
        int[][] intervals = { { 0, 2 }, { 1, 3 }, { 2, 4 } };
        int firstPerson = 0;

        List<Integer> result = findIndividuals(n, intervals, firstPerson);
        System.out.println("Individuals who eventually know the secret: " + result);
    }
}
