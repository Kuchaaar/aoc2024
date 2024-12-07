import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class Aoc7 {
    List<Pair<Long,List<Long>>> pairs;
    Aoc7(List<Pair<Long,List<Long>>> pairs){
        this.pairs = pairs;
    }
    public long part1(){
        long value =0;
        for(int i=0;i<pairs.size();i++){
            if(recursivePart1(pairs.get(i).getKey(), pairs.get(i).getValue(), 1, pairs.get(i).getValue().get(0))){
                value = value + pairs.get(i).getKey();
            }
        }
        return value;
    }
    public long part2(){
        long value =0;
        for(int i=0;i<pairs.size();i++){
            if(recursivePart2(pairs.get(i).getKey(), pairs.get(i).getValue(), 1, pairs.get(i).getValue().get(0))){
                value = value + pairs.get(i).getKey();
            }
        }
        return value;
    }

    private static boolean recursivePart1(Long target, List<Long> numbers, int index, Long currentResult) {
        if (index == numbers.size()) {
            return currentResult.equals(target);
        }
        Long nextNumber = numbers.get(index);
        if (recursivePart1(target, numbers, index + 1, currentResult + nextNumber)) {
            return true;
        }
        else if (recursivePart1(target, numbers, index + 1, currentResult * nextNumber)) {
            return true;
        }
        return false;
    }
    private static boolean recursivePart2(Long target, List<Long> numbers, int index, Long currentResult) {
        if (index == numbers.size()) {
            return currentResult.equals(target);
        }
        Long nextNumber = numbers.get(index);
        if (recursivePart2(target, numbers, index + 1, currentResult + nextNumber)) {
            return true;
        }
        else if (recursivePart2(target, numbers, index + 1, currentResult * nextNumber)) {
            return true;
        }
        else if(recursivePart2(target, numbers, index + 1, concatenateNumbers(currentResult, nextNumber))){
            return true;
        }
        return false;
    }
    private static Long concatenateNumbers(Long left, Long right) {
        return Long.parseLong(left.toString() + right.toString());
    }

}
