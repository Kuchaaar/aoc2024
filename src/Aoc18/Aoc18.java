package Aoc18;

import java.util.*;

public class Aoc18 {
    Pos start = new Pos(0, 0);
    Pos goal = new Pos(70, 70);
    List<Pos> bytes;
    Set<Pos> obstacles;

    public Aoc18(List<Pos> bytes) {
        this.bytes = bytes;
        obstacles = new HashSet<>(bytes.subList(0, 1024));
    }

    public String part1() {
        return bfs().toString();
    }

    public String part2() {
        for (int i = 1024; i < bytes.size(); i++) {
            obstacles.add(bytes.get(i));
            if (bfs() == null) {
                return bytes.get(i).x + "," + bytes.get(i).y;
            }
        }
        return null;
    }

    public Integer bfs() {
        Queue<Pos> q = new LinkedList<>();
        Map<Pos, Integer> dist = new HashMap<>();
        q.add(start);
        dist.put(start, 0);

        while (!q.isEmpty()) {
            Pos current = q.poll();
            for (Pos neighbor : current.getNeighbors()) {
                if (obstacles.contains(neighbor)) {
                    continue;
                }
                if (dist.containsKey(neighbor)) {
                    continue;
                }
                dist.put(neighbor, dist.get(current) + 1);
                if (neighbor.equals(goal)) {
                    return dist.get(neighbor);
                }
                q.add(neighbor);
            }
        }
        return null;
    }

}
