package Aoc24;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class Aoc24 {
    Map<String, Integer> map = new HashMap<>();
    List<Combination> combinations = new ArrayList<>();

    public Aoc24(List<String> input) {
        for (String string : input) {
            if (string.contains(":")) {
                String[] split = string.split(":");
                map.put(split[0].trim(), Integer.parseInt(split[1].trim()));
            } else if (string.contains("->")) {
                combinations.add(Combination.FromString(string));
                String[] split = string.split(" ");
                map.putIfAbsent(split[0].trim(), -1);
                map.putIfAbsent(split[2].trim(), -1);
            }
        }
    }

    public String part(boolean part2) {
        if (part2) {
            return findFaultyGates(combinations).stream()
                    .map(entity -> entity.result())
                    .sorted()
                    .collect(Collectors.joining(","));
        }
        return String.valueOf(Long.parseLong(solver(), 2));
    }

    private List<Combination> findFaultyGates(List<Combination> gates) {
        List<Combination> faultyGates = new ArrayList<>();
        for (Combination c : gates) {
            if (c.result().startsWith("z") && !c.result().equals("z45")) {
                if (!c.operation().equals("XOR")) {
                    faultyGates.add(c);
                }
            } else if (!c.result().startsWith("z")
                    && !(c.first().startsWith("x") || c.first().startsWith("y"))
                    && !(c.second().startsWith("x") || c.second().startsWith("y"))) {
                if (c.operation().equals("XOR")) {
                    faultyGates.add(c);
                }
            } else if (c.operation().equals("XOR")
                    && (c.first().startsWith("x") || c.first().startsWith("y"))
                    && (c.second().startsWith("x") || c.second().startsWith("y"))) {
                if (!(c.first().endsWith("00") && c.second().endsWith("00"))) {
                    String output = c.result();
                    boolean anotherFound = false;
                    for (Combination c2 : gates) {
                        if (!c2.equals(c)) {
                            if ((c2.first().equals(output) || c2.second().equals(output))
                                    && c2.operation().equals("XOR")) {
                                anotherFound = true;
                                break;
                            }
                        }
                    }
                    if (!anotherFound) {
                        faultyGates.add(c);
                    }
                }
            } else if (c.operation().equals("AND")
                    && (c.first().startsWith("x") || c.first().startsWith("y"))
                    && (c.second().startsWith("x") || c.second().startsWith("y"))) {
                if (!(c.first().endsWith("00") && c.second().endsWith("00"))) {
                    String output = c.result();
                    boolean anotherFound = false;
                    for (Combination c2 : gates) {
                        if (!c2.equals(c)) {
                            if ((c2.first().equals(output) || c2.second().equals(output))
                                    && c2.operation().equals("OR")) {
                                anotherFound = true;
                                break;
                            }
                        }
                    }
                    if (!anotherFound) {
                        faultyGates.add(c);
                    }
                }
            }
        }
        return faultyGates;
    }

    public String solver() {
        List<Combination> list = new ArrayList<>(combinations);
        int index = 0;
        while (!list.isEmpty()) {
            if (map.get(list.get(index).first()) != -1 && map.get(list.get(index).second()) != -1) {
                map.put(list.get(index).result(), operation(list.get(index)));
                list.remove(index);
            }
            index++;
            if (index >= list.size()) {
                index = 0;
            }
        }
        return map.entrySet().stream()
                .filter(entry -> entry.getKey().toLowerCase().startsWith("z"))
                .sorted((entry1, entry2) -> entry2.getKey().compareTo(entry1.getKey()))
                .map(Map.Entry::getValue)
                .map(String::valueOf)
                .collect(Collectors.joining(""));
    }

    public int operation(Combination combination) {
        int first = map.get(combination.first());
        int second = map.get(combination.second());
        return switch (combination.operation()) {
            case "XOR" -> first ^ second;
            case "AND" -> first & second;
            case "OR" -> first | second;
            default -> 0;
        };
    }
}
