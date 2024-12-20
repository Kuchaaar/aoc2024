package Aoc19;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Aoc19 {
    List<String> towels;
    List<String> designs;

    public Aoc19(List<String> towels, List<String> designs) {
        this.towels = towels;
        towels.sort((a, b) -> b.length() - a.length());
        this.designs = designs;
    }

    public String part1(boolean part2) {
        long res = 0;
        Map<String, Long> visited = new HashMap<>();
        for (String design : designs) {
            visited.clear();
            long a = dfs(design, visited, new StringBuilder());
            if (a > 0) {
                if (part2) {
                    res += a;
                } else {
                    res++;
                }
            }
        }
        return String.valueOf(res);
    }

    private long dfs(String target, Map<String, Long> visited, StringBuilder partial) {
        if (partial.toString().equals(target)) {
            return 1L;
        }
        if (visited.containsKey(partial.toString())) {
            return visited.get(partial.toString());
        }

        long res = 0;
        for (String towel : towels) {
            partial.append(towel);
            if (target.startsWith(partial.toString())) {
                res += dfs(target, visited, partial);
            }
            partial.delete(partial.length() - towel.length(), partial.length());
        }
        visited.put(partial.toString(), res);
        return res;
    }
}
