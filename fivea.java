import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class fivea {

    private int numCities;
    private int[][] distances;
    private double[][] pheromones;
    private int numAnts;
    private double evaporationRate;
    private double alpha;
    private double beta;
    private int[] bestTour;
    private double bestTourLength;

    public fivea(int numCities, int[][] distances, int numAnts, double evaporationRate, double alpha, double beta) {
        this.numCities = numCities;
        this.distances = distances;
        this.pheromones = new double[numCities][numCities];
        this.numAnts = numAnts;
        this.evaporationRate = evaporationRate;
        this.alpha = alpha;
        this.beta = beta;
        this.bestTour = new int[numCities];
        this.bestTourLength = Double.MAX_VALUE;
    }

    public void runACO(int numIterations) {
        for (int iteration = 0; iteration < numIterations; iteration++) {
            List<Ant> ants = createAnts();

            for (Ant ant : ants) {
                ant.constructTour();
                ant.calculateTourLength();

                if (ant.getTourLength() < bestTourLength) {
                    bestTourLength = ant.getTourLength();
                    bestTour = Arrays.copyOf(ant.getTour(), numCities);
                }
            }

            updatePheromones();

            evaporatePheromones();
        }
    }

    private List<Ant> createAnts() {
        List<Ant> ants = new ArrayList<>();
        for (int i = 0; i < numAnts; i++) {
            ants.add(new Ant());
        }
        return ants;
    }

    private void updatePheromones() {
        for (int i = 0; i < numCities; i++) {
            for (int j = 0; j < numCities; j++) {
                pheromones[i][j] *= (1.0 - evaporationRate);
            }
        }

        for (int i = 0; i < numCities; i++) {
            for (int j = 0; j < numCities; j++) {
                for (Ant ant : createAnts()) {
                    if (ant.containsEdge(i, j)) {
                        pheromones[i][j] += 1.0 / ant.getTourLength();
                    }
                }
            }
        }
    }

    private void evaporatePheromones() {
        for (int i = 0; i < numCities; i++) {
            for (int j = 0; j < numCities; j++) {
                pheromones[i][j] *= (1.0 - evaporationRate);
            }
        }
    }

    public int[] getBestTour() {
        return bestTour;
    }

    public double getBestTourLength() {
        return bestTourLength;
    }

    public class Ant {
        private int[] tour;
        private double tourLength;

        public Ant() {
            this.tour = new int[numCities];
            this.tourLength = 0.0;
        }

        public void constructTour() {
            Random random = new Random();

            boolean[] visited = new boolean[numCities];
            tour[0] = random.nextInt(numCities);
            visited[tour[0]] = true;

            for (int i = 1; i < numCities; i++) {
                int currentCity = tour[i - 1];
                int nextCity = selectNextCity(currentCity, visited);
                tour[i] = nextCity;
                visited[nextCity] = true;
            }
        }

        public void calculateTourLength() {
            double length = 0.0;
            for (int i = 0; i < numCities - 1; i++) {
                length += distances[tour[i]][tour[i + 1]];
            }
            length += distances[tour[numCities - 1]][tour[0]]; // Return to the starting city
            tourLength = length;
        }

        public int[] getTour() {
            return tour;
        }

        public double getTourLength() {
            return tourLength;
        }

        public boolean containsEdge(int city1, int city2) {
            for (int i = 0; i < numCities - 1; i++) {
                if ((tour[i] == city1 && tour[i + 1] == city2) || (tour[i] == city2 && tour[i + 1] == city1)) {
                    return true;
                }
            }
            return false;
        }

        private int selectNextCity(int currentCity, boolean[] visited) {
            double[] probabilities = calculateProbabilities(currentCity, visited);
            double rand = Math.random();
            double cumulativeProbability = 0.0;

            for (int i = 0; i < numCities; i++) {
                cumulativeProbability += probabilities[i];
                if (rand <= cumulativeProbability) {
                    return i;
                }
            }

            return numCities - 1; // Should never reach here
        }

        private double[] calculateProbabilities(int currentCity, boolean[] visited) {
            double[] probabilities = new double[numCities];
            double denominator = 0.0;

            for (int i = 0; i < numCities; i++) {
                if (!visited[i]) {
                    probabilities[i] = Math.pow(pheromones[currentCity][i], alpha)
                            * Math.pow(1.0 / distances[currentCity][i], beta);
                    denominator += probabilities[i];
                }
            }

            for (int i = 0; i < numCities; i++) {
                probabilities[i] /= denominator;
            }

            return probabilities;
        }
    }

    public static void main(String[] args) {
        int numCities = 5;
        int[][] distances = {
                { 0, 2, 1, 4, 5 },
                { 2, 0, 3, 2, 3 },
                { 1, 3, 0, 2, 1 },
                { 4, 2, 2, 0, 3 },
                { 5, 3, 1, 3, 0 }
        };

        int numAnts = 5;
        double evaporationRate = 0.5;
        double alpha = 1.0;
        double beta = 2.0;

        fivea antColony = new fivea(numCities, distances, numAnts, evaporationRate, alpha, beta);
        antColony.runACO(100);

        int[] bestTour = antColony.getBestTour();
        double bestTourLength = antColony.getBestTourLength();

        System.out.println("Best Tour: " + Arrays.toString(bestTour));
        System.out.println("Best Tour Length: " + bestTourLength);
    }
}
