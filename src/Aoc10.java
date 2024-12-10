import java.util.*;

public class Aoc10 {
    List<List<Integer>> input;

    public Aoc10(List<List<Integer>> input) {
        this.input = input;
    }

    private static final int[] dRow = {-1, 1, 0, 0};
    private static final int[] dCol = {0, 0, -1, 1};

    public int part(boolean part2) {
        boolean[][] visited = new boolean[input.size()][input.get(0).size()];
        int totalScore = 0;
        for (int r = 0; r < input.size(); r++) {
            for (int c = 0; c < input.get(0).size(); c++) {
                if (input.get(r).get(c) == 0 && !visited[r][c]) {
                    totalScore += bfs(r, c, part2);
                    visited[r][c] = true;
                }
            }
        }
        return totalScore;
    }

    private int bfs(int startRow, int startCol, boolean part2) {
        int val = 0;
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{startRow, startCol});
        boolean[][] visited = new boolean[input.size()][input.get(0).size()];
        visited[startRow][startCol] = true;
        Set<String> reachableNine = new HashSet<>();
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int r = curr[0];
            int c = curr[1];
            if (input.get(r).get(c) == 9) {
                reachableNine.add(r + "," + c);
            }
            for (int i = 0; i < 4; i++) {
                int newRow = r + dRow[i];
                int newCol = c + dCol[i];
                if (newRow >= 0 && newRow < input.size() && newCol >= 0 && newCol < input.get(0).size()
                        && !visited[newRow][newCol] && input.get(newRow).get(newCol) == input.get(r).get(c) + 1) {
                    queue.offer(new int[]{newRow, newCol});
                    if (part2) {
                        if (input.get(newRow).get(newCol) == 9) {
                            val++;
                        }
                    } else {
                        visited[newRow][newCol] = true;
                    }
                }
            }
        }
        if (!part2) {
            return reachableNine.size();
        } else {
            return val;
        }
    }
}
