package Aoc23;

import java.util.*;
import java.util.stream.Collectors;

public class Aoc23 {
    Map<String, Set<String>> graph;

    public Aoc23(List<String> input) {
        graph = buildGraph(input);
    }

    public Map<String, Set<String>> buildGraph(List<String> input) {
        Map<String, Set<String>> graph = new HashMap<>();
        for (String s : input) {
            String[] split = s.split("-");
            graph.putIfAbsent(split[0], new HashSet<>());
            graph.putIfAbsent(split[1], new HashSet<>());
            graph.get(split[0]).add(split[1]);
            graph.get(split[1]).add(split[0]);
        }
        return graph;
    }

    public String part(boolean part2) {
        if (part2) {
            Set<String> set = findGroups().stream()
                    .max(Comparator.comparingInt(Set::size))
                    .orElseThrow();
            return set.stream().sorted().collect(Collectors.joining(","));
        }
        return String.valueOf(findTriples(graph).size());
    }

    private Set<Set<String>> findGroups() {
        Set<Set<String>> groups = new HashSet<>();
        for (String firstNode : graph.keySet()) {
            Set<String> checked = new HashSet<>();
            Set<String> passed = new HashSet<>();
            Queue<String> queue = new ArrayDeque<>();
            queue.add(firstNode);
            while (!queue.isEmpty()) {
                String s = queue.poll();
                if (!checked.contains(s) && graph.get(s).containsAll(passed)) {
                    passed.add(s);
                    checked.add(s);
                    for (String next : graph.get(s)) {
                        if (!checked.contains(next)) {
                            queue.add(next);
                        }
                    }
                }
            }
            groups.add(passed);
        }
        return groups;
    }

    public Set<Set<String>> findTriples(Map<String, Set<String>> graph) {
        Set<Set<String>> triples = new HashSet<>();
        for (String comp1 : graph.keySet()) {
            for (String comp2 : graph.get(comp1)) {
                if (comp1.compareTo(comp2) < 0) {
                    for (String comp3 : graph.get(comp2)) {
                        if (comp1.compareTo(comp3) < 0 && graph.get(comp1).contains(comp3)) {
                            if (comp1.startsWith("t") || comp2.startsWith("t") || comp3.startsWith("t")) {
                                Set<String> triple = new HashSet<>(Arrays.asList(comp1, comp2, comp3));
                                triples.add(triple);
                            }
                        }
                    }
                }
            }
        }
        return triples;
    }
}
