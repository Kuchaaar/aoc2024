import java.util.*;

public class Aoc10 {
    List<List<Integer>> input;

    public Aoc10(List<List<Integer>> input) {
        this.input = input;
    }

    private final int[] dRow = {-1, 1, 0, 0};
    private final int[] dCol = {0, 0, -1, 1};

    public int part(boolean part2) {
        int totalScore = 0;
        for (int r = 0; r < input.size(); r++) {
            for (int c = 0; c < input.get(0).size(); c++) {
                if (input.get(r).get(c) == 0) {
                    totalScore += bfs(r, c, part2);
                }
            }
        }
        return totalScore;
    }

    private int bfs(int startRow, int startCol, boolean part2) {
        int val = 0;
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{startRow, startCol});
        Set<String> reachableNine = new HashSet<>();
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int r = curr[0];
            int c = curr[1];
            if (input.get(r).get(c) == 9) {
                reachableNine.add(r + "," + c);
                val++;
            }
            for (int i = 0; i < 4; i++) {
                int newRow = r + dRow[i];
                int newCol = c + dCol[i];
                if (newRow >= 0 && newRow < input.size() && newCol >= 0 && newCol < input.get(0).size()
                        && input.get(newRow).get(newCol) == input.get(r).get(c) + 1) {
                    queue.offer(new int[]{newRow, newCol});
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
