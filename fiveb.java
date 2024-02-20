import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class fiveb {

    public static void main(String[] args) {
        int[][] edges = { { 0, 1 }, { 0, 2 }, { 1, 3 }, { 1, 6 }, { 2, 4 }, { 4, 6 }, { 4, 5 }, { 5, 7 } };
        int targetDevice = 4;
        List<Integer> impactedDevices = getImpactedDevices(edges, targetDevice);
        System.out.println(impactedDevices);
    }

    public static List<Integer> getImpactedDevices(int[][] edges, int targetDevice) {
        Map<Integer, List<Integer>> graph = new HashMap<>();

        for (int[] edge : edges) {
            graph.putIfAbsent(edge[0], new ArrayList<>());
            graph.putIfAbsent(edge[1], new ArrayList<>());
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }

        List<Integer> impactedDevices = new ArrayList<>();
        boolean[] visited = new boolean[graph.size()];

        dfs(graph, targetDevice, visited, impactedDevices);

        return impactedDevices;
    }

    private static void dfs(Map<Integer, List<Integer>> graph, int node,
            boolean[] visited, List<Integer> result) {
        visited[node] = true;

        for (int nextNode : graph.get(node)) {
            if (!visited[nextNode]) {
                result.add(nextNode);
                dfs(graph, nextNode, visited, result);
            }
        }
    }
}
