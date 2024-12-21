package Aoc20;

import java.util.*;

public class Aoc20 {
    List<String> input;
    Coordinate start;
    Coordinate end;
    int[] DIR_X = new int[]{1, -1, 0, 0};
    int[] DIR_Y = new int[]{0, 0, 1, -1};

    public Aoc20(List<String> input) {
        this.input = input;
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(0).length(); j++) {
                char c = input.get(i).charAt(j);
                if (c == 'S') {
                    start = new Coordinate(i, j);
                }
                if (c == 'E') {
                    end = new Coordinate(i, j);
                }
            }
        }
    }

    public String part(boolean part2) {
        long answer = 0;
        int[][] bfsFromStart = bfs(start);
        int[][] bfsFromEnd = bfs(end);
        int currentDistance = bfsFromEnd[start.x][start.y];
        for (int i = 0; i < bfsFromEnd.length; i++) {
            for (int j = 0; j < bfsFromEnd[0].length; j++) {
                if (input.get(i).charAt(j) != '#') {
                    Set<Coordinate> allNeighbors = new HashSet<>();
                    Coordinate s = new Coordinate(i, j);
                    allNeighbors.add(s);
                    int max = part2 ? 20 : 2;
                    for (int k = 0; k < max; k++) {
                        Set<Coordinate> newAllNeighbors = new HashSet<>(allNeighbors);
                        for (Coordinate c : allNeighbors) {
                            newAllNeighbors.addAll(getNeighbors(c.x, c.y));
                        }
                        allNeighbors = newAllNeighbors;
                    }
                    for (Coordinate c : allNeighbors) {
                        if (input.get(c.x).charAt(c.y) != '#' && (bfsFromStart[i][j] + bfsFromEnd[c.x][c.y] + Math.abs(c.x - i) + Math.abs(c.y - j)) <= currentDistance - 100) {
                            answer++;
                        }
                    }
                }
            }
        }
        return String.valueOf(answer);
    }

    private int[][] bfs(Coordinate end) {
        int[][] bfs = new int[input.size()][input.get(0).length()];
        Queue<Coordinate> queue = new LinkedList<>();
        Set<Coordinate> visited = new HashSet<>();
        queue.add(end);
        visited.add(end);
        int counter = 0;
        while (!queue.isEmpty()) {
            Queue<Coordinate> newQueue = new LinkedList<>();
            for (Coordinate c : queue) {
                bfs[c.x][c.y] = counter;
                for (Coordinate neighbor : getNeighbors(c.x, c.y)) {
                    if (!visited.contains(neighbor) && input.get(neighbor.x).charAt(neighbor.y) != '#') {
                        newQueue.add(neighbor);
                    }
                }
            }
            counter++;
            queue = newQueue;
            visited.addAll(newQueue);
        }
        return bfs;
    }

    public List<Coordinate> getNeighbors(int x, int y) {
        List<Coordinate> neighbors = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            int nx = x + DIR_X[i];
            int ny = y + DIR_Y[i];
            if (isValid(nx, ny)) {
                neighbors.add(new Coordinate(nx, ny));
            }
        }
        return neighbors;
    }

    public boolean isValid(int x, int y) {
        return x >= 0 && x < input.size() && y >= 0 && y < input.get(x).length();
    }
}
