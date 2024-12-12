package Aoc12;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Aoc12 {
    List<String> input;

    public Aoc12(List<String> input) {
        this.input = input;
    }

    public int part(boolean part2) {
        int res = 0;
        Set<String> set = new HashSet<>();
        for (int i = 0; i < input.size(); i++) {
            String s = input.get(i);
            for (int j = 0; j < s.length(); j++) {
                if (set.contains(i + " " + j)) {
                    continue;
                }
                AtomicInteger fieldSize = new AtomicInteger();
                Set<Fence> fences = new HashSet<>();
                plotScanner(input.get(i).charAt(j),i,j,set,fieldSize,fences);
                res += fieldSize.get() * sidesCounter(fences,part2);
            }
        }
        return res;
    }

    public char plantAt(int i, int j) {
        if (i < 0 || i >= input.size() || j < 0 || j >= input.get(0).length()) {
            return ' ';
        }
        return input.get(i).charAt(j);
    }

    public void plotScanner(char c, int i, int j, Set<String> set, AtomicInteger fieldSize, Set<Fence> fences) {
        if (set.contains(i + " " + j)) {
            return;
        }
        set.add(i + " " + j);
        fieldSize.addAndGet(1);
        if (plantAt(i - 1, j) == c) {
            plotScanner(c, i - 1, j, set, fieldSize, fences);
        } else {
            fences.add(new Fence(i - 1, j, 1));
        }
        if (plantAt(i + 1, j) == c) {
            plotScanner(c, i + 1, j, set, fieldSize, fences);
        } else {
            fences.add(new Fence(i + 1, j, 2));
        }
        if (plantAt(i, j - 1) == c) {
            plotScanner(c, i, j - 1, set, fieldSize, fences);
        } else {
            fences.add(new Fence(i, j - 1, 3));
        }
        if (plantAt(i, j + 1) == c) {
            plotScanner(c, i, j + 1, set, fieldSize, fences);
        } else {
            fences.add(new Fence(i, j + 1, 4));
        }
    }

    public int sidesCounter(Set<Fence> fences, boolean part) {
        Map<Integer, List<Fence>> map = fences.stream().collect(Collectors.groupingBy(fe -> fe.type));
        return horizontal(map.get(1),part) + horizontal(map.get(2),part) + vertical(map.get(3),part) + vertical(map.get(4),part);
    }

    public int horizontal(List<Fence> fences, boolean part) {
        Map<Integer, List<Fence>> map;
        if(!part) {
            map = fences.stream().collect(Collectors.groupingBy(f -> f.y));
        }
        else {
            map = fences.stream().collect(Collectors.groupingBy(f -> f.x));
        }
        return counter(map,false);
    }
    public int vertical(List<Fence> fences, boolean part) {
        Map<Integer, List<Fence>> map;
        if(!part){
            map = fences.stream().collect(Collectors.groupingBy(f -> f.x));
        }
        else{
            map = fences.stream().collect(Collectors.groupingBy(f -> f.y));
        }
        return counter(map,true);
    }
    public int counter(Map<Integer, List<Fence>> map, boolean vert){
        int sideCount = 0;
        for (List<Fence> fences : map.values()) {
            sideCount++;
            List<Integer> list;
            if(vert){
                list = fences.stream().map(f -> f.x).sorted().toList();
            }
            else {
                list = fences.stream().map(f -> f.y).sorted().toList();
            }
            int k = 1;
            int p = list.get(0);
            while (k < list.size()) {
                int n = list.get(k);
                if (p != n - 1) {
                    sideCount++;
                }
                p = n;
                k++;
            }
        }
        return sideCount;
    }
}
