package Aoc21;

import java.util.*;

public class Aoc21 {
    String[] n = {"789", "456", "123", " 0A"};
    String[] d = {" ^A", "<v>"};
    Map<String, Long> cache = new HashMap<>();
    List<String> input;

    public Aoc21(List<String> input) {
        this.input = input;
    }

    public String part(int number) {
        long res = 0;
        for (String s : input) {
            res += solve(s, 0, number) * Integer.parseInt(s.substring(0, 3));
        }
        return Long.toString(res);
    }

    public String path(String[] p, char first, char second) {
        int fx = -1;
        int fy = -1;
        int sx = -1;
        int sy = -1;
        for (int y = 0; y < p.length; y++) {
            for (int x = 0; x < p[y].length(); x++) {
                if (p[y].charAt(x) == first) {
                    fx = x;
                    fy = y;
                }
                if (p[y].charAt(x) == second) {
                    sx = x;
                    sy = y;
                }
            }
        }
        return findPath(p, fx, fy, "", sx, sy);
    }

    private String findPath(String[] grid, int startX, int startY, String currentPath, int targetX, int targetY) {
        if (startX == targetX && startY == targetY) {
            return currentPath + 'A';
        }
        List<String> possiblePaths = new ArrayList<>();
        if (targetX < startX && grid[startY].charAt(startX - 1) != ' ') {
            possiblePaths.add(findPath(grid, startX - 1, startY, currentPath + '<', targetX, targetY));
        }
        if (targetY < startY && grid[startY - 1].charAt(startX) != ' ') {
            possiblePaths.add(findPath(grid, startX, startY - 1, currentPath + '^', targetX, targetY));
        }
        if (targetY > startY && grid[startY + 1].charAt(startX) != ' ') {
            possiblePaths.add(findPath(grid, startX, startY + 1, currentPath + 'v', targetX, targetY));
        }
        if (targetX > startX && grid[startY].charAt(startX + 1) != ' ') {
            possiblePaths.add(findPath(grid, startX + 1, startY, currentPath + '>', targetX, targetY));
        }
        return possiblePaths.stream()
                .min(Comparator.comparingInt(this::countDirectionChanges))
                .orElse("");
    }

    private int countDirectionChanges(String path) {
        int changes = 0;
        for (int i = 0; i < path.length() - 1; i++) {
            if (path.charAt(i) != path.charAt(i + 1)) {
                changes++;
            }
        }
        return changes;
    }

    public long solve(String s, int l, int number) {
        if (l > number) return s.length();
        long result = 0;
        String key = s + "_" + l;
        if (cache.containsKey(key)) {
            return cache.get(key);
        }
        for (int i = 0; i < s.length(); i++) {
            char f = (i == 0) ? 'A' : s.charAt(i - 1);
            char t = s.charAt(i);
            String newPath = path((l == 0) ? n : d, f, t);
            result += solve(newPath, l + 1, number);
        }
        cache.put(key, result);
        return result;
    }
}
