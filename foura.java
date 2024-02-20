import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class foura {
    private static final char[] KEYS = { 'a', 'b', 'c', 'd', 'e', 'f' };
    private static final char[] DOORS = { 'A', 'B', 'C', 'D', 'E', 'F' };
    private int[][] moves = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };

    public int minimumMoves(char[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][] minMoves = new int[m][n];
        Arrays.stream(minMoves).forEach(a -> Arrays.fill(a, Integer.MAX_VALUE));
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[] { 0, 0, 0 });
        minMoves[0][0] = 0;
        Set<Character> keys = new HashSet<>();
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int x = cur[0], y = cur[1], steps = cur[2];
            if (grid[x][y] != 'S' && grid[x][y] != 'P') {
                char c = grid[x][y];
                if (Character.isAlphabetic(c) && Character.isLowerCase(c)) {
                    keys.add(c);
                } else if (Character.isAlphabetic(c) && Character.isUpperCase(c)) {
                    boolean hasKey = false;
                    for (char key : KEYS) {
                        if (keys.contains(key) && DOORS[KEYS.length] == c) {
                            hasKey = true;
                            break;
                        }
                    }
                    if (hasKey) {
                        minMoves[x][y] = Math.min(minMoves[x][y], steps);
                    }
                } else {
                    minMoves[x][y] = Math.min(minMoves[x][y], steps);
                }
            }
            for (int[] move : moves) {
                int newX = x + move[0], newY = y + move[1];
                if (newX >= 0 && newX < m && newY >= 0 && newY < n && minMoves[newX][newY] > steps + 1) {
                    if (grid[newX][newY] == 'P' || grid[newX][newY] == 'S'
                            || (Character.isAlphabetic(grid[newX][newY]) && keys.contains(grid[newX][newY]))) {
                        queue.add(new int[] { newX, newY, steps + 1 });
                        minMoves[newX][newY] = steps + 1;
                    }
                }
            }
        }
        return minMoves[m - 1][n - 1] == Integer.MAX_VALUE ? -1 : minMoves[m - 1][n - 1];
    }
}