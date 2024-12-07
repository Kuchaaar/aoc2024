import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Aoc5 {

    public long part1(List<Pair<Integer, Integer>> pairs, List<List<Integer>> list){
        long value = 0;
        for (List<Integer> line : list) {
            boolean correct = checkIfCorrect(pairs,line);
            if (correct) {
                int middle = (line.size() - 1) / 2;
                value += line.get(middle);
            }
        }
        return value;
    }
    public long part2(List<Pair<Integer, Integer>> pairs, List<List<Integer>> list){
        long value = 0;
        for (List<Integer> line : list) {
            boolean correct = true;
            List<Integer> list1 = new ArrayList<>(line);
            for (int i = 0; i < line.size(); i++) {
                for (int j = i + 1; j < line.size(); j++) {
                    if (!pairs.contains(Pair.of(line.get(i),line.get(j))))
                        correct = false;
                        list1.sort((page1, page2) -> {
                            if (pairs.contains(Pair.of(page1,page2))) {
                                return -1;
                            }
                            if (pairs.contains(Pair.of(page2,page1))) {
                                return 1;
                            }
                            return 0;
                        });
                }
            }
            if (!correct) {
                int middle = (list1.size() - 1) / 2;
                value += list1.get(middle);
            }
        }
        return value ;
    }
    public boolean checkIfCorrect(List<Pair<Integer, Integer>> pairs,List<Integer> line){
        boolean correct = true;
        for (int i = 0; i < line.size(); i++) {
            for (int j = i + 1; j < line.size(); j++) {
                if (!pairs.contains(Pair.of(line.get(i),line.get(j))))
                    correct = false;
            }
        }
        return correct;
    }
}
