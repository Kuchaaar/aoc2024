import java.util.*;

public class Aoc8 {
    List<String> list;

    Aoc8(List<String> list) {
        this.list = list;
    }

    public int part(boolean part1) {
        Map<Character, List<int[]>> map = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(0).length(); j++) {
                char c = list.get(i).charAt(j);
                if (c != '.') {
                    map.computeIfAbsent(c, k -> new ArrayList<>());
                    map.get(c).add(new int[]{i, j});
                }
            }
        }
        Set<String> antiNodes = new HashSet<>();
        for (Character c : map.keySet()) {
            List<int[]> freq = map.get(c);
            for (int i = 0; i < freq.size(); i++) {
                for (int j = i + 1; j < freq.size(); j++) {
                    int[] a = freq.get(i);
                    int[] b = freq.get(j);
                    addAntiNodes(a, b, antiNodes, part1);
                    if (!part1) {
                        antiNodes.add(a[0] + "," + a[1]);
                        antiNodes.add(b[0] + "," + b[1]);
                    }
                }
            }
        }
        return antiNodes.size();
    }

    private void addAntiNodes(int[] a, int[] b, Set<String> antiNodes, boolean part1) {
        int diffX = a[0] - b[0];
        int diffY = a[1] - b[1];
        if (part1) {
            int[] p1 = {b[0] - diffX, b[1] - diffY};
            int[] p2 = {a[0] + diffX, a[1] + diffY};
            if (isValidPosition(p1[0], p1[1])) {
                antiNodes.add(p1[0] + "," + p1[1]);
            }
            if (isValidPosition(p2[0], p2[1])) {
                antiNodes.add(p2[0] + "," + p2[1]);
            }
        } else {
            int[] p1 = {b[0] - diffX, b[1] - diffY};
            int[] p2 = {a[0] + diffX, a[1] + diffY};
            while (isValidPosition(p1[0], p1[1])) {
                antiNodes.add(p1[0] + "," + p1[1]);
                p1[0] -= diffX;
                p1[1] -= diffY;
            }
            while (isValidPosition(p2[0], p2[1])) {
                antiNodes.add(p2[0] + "," + p2[1]);
                p2[0] += diffX;
                p2[1] += diffY;
            }
        }
    }

    public boolean isValidPosition(int x, int y) {
        return x >= 0 && x < list.size() && y >= 0 && y < list.get(0).length();
    }
}
