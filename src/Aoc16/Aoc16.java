package Aoc16;

import java.util.*;

public class Aoc16 {
    List<String> input;
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private final Map<Character, int[]> directions = Map.of(
            'E', new int[] {0, 1},
            'W', new int[] {0, -1},
            'N', new int[] {-1, 0},
            'S', new int[] {1, 0}
    );
    private final String directionsString = "EWNS";
    private final Map<Character, Character> flip = Map.of(
            'E', 'W',
            'W', 'E',
            'N', 'S',
            'S', 'N'
    );
    public Aoc16(List<String> input) {
        this.input = input;
        for(int i =0;i<input.size();i++){
            for(int k=0;k<input.get(i).length();k++){
                if(input.get(i).charAt(k)=='S'){
                    startX = i;
                    startY = k;
                }
                if(input.get(i).charAt(k)=='E'){
                    endX = i;
                    endY = k;
                }
            }
        }
    }
    public int part1() {
        Map<String, Integer> dist = dijkstra(List.of(new int[] {startX, startY, 'E'}));
        int best = Integer.MAX_VALUE;
        for (char dir : directionsString.toCharArray()) {
            String key = endX + "," + endY + "," + dir;
            if (dist.containsKey(key)) {
                best = Math.min(best, dist.get(key));
            }
        }
        return best;
    }
    public int part2(){
        Map<String,Integer> fromStart = dijkstra(List.of(new int[] {startX, startY, 'E'}));
        List<int[]> startPoints = new ArrayList<>();
        for (char dir : directionsString.toCharArray()) {
            startPoints.add(new int[]{endX, endY, dir});
        }
        Map<String, Integer> fromEnd = dijkstra(startPoints);
        int optimal = part1();
        Set<String> result = new HashSet<>();
        for (int row = 0; row < input.size(); row++) {
            for (int col = 0; col < input.get(0).length(); col++) {
                for (char dir : directionsString.toCharArray()) {
                    String stateFromStart = row + "," + col + "," + dir;
                    char flippedDir = flip.get(dir);
                    String stateFromEnd = row + "," + col + "," + flippedDir;
                    if (fromStart.containsKey(stateFromStart) && fromEnd.containsKey(stateFromEnd)) {
                        if (fromStart.get(stateFromStart) + fromEnd.get(stateFromEnd) == optimal) {
                            result.add(row + "," + col);
                        }
                    }
                }
            }
        }
        return result.size();
    }
    public Map<String, Integer> dijkstra(List<int[]> starts) {
        Map<String, Integer> map = new HashMap<>();
        PriorityQueue<Step> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.distance));
        for (int[] start : starts) {
            int sr = start[0];
            int sc = start[1];
            char dir = (char) start[2];
            map.put(sr + "," + sc + "," + dir, 0);
            pq.offer(new Step(0, sr, sc, dir));
        }
        while (!pq.isEmpty()) {
            Step step = pq.poll();
            int d = step.distance;
            int row = step.row;
            int col = step.col;
            char direction = step.direction;
            String key = row + "," + col + "," + direction;
            if (map.get(key) < d) {
                continue;
            }
            for (char nextDir : "EWNS".toCharArray()) {
                if (nextDir != direction) {
                    String nextKey = row + "," + col + "," + nextDir;
                    if (!map.containsKey(nextKey) || map.get(nextKey) > d + 1000) {
                        map.put(nextKey, d + 1000);
                        pq.offer(new Step(d + 1000, row, col, nextDir));
                    }
                }
            }
            int[] deltaDirection = directions.get(direction);
            int nextRow = row + deltaDirection[0];
            int nextCol = col + deltaDirection[1];
            if (nextRow >= 0 && nextRow < input.size() && nextCol >= 0 && nextCol < input.get(0).length()
                    && input.get(nextRow).charAt(nextCol) != '#') {

                String nextKey = nextRow + "," + nextCol + "," + direction;
                if (!map.containsKey(nextKey) || map.get(nextKey) > d + 1) {
                    map.put(nextKey, d + 1);
                    pq.offer(new Step(d + 1, nextRow, nextCol, direction));
                }
            }
        }
        return map;
    }
}
