import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class Aoc9 {
    List<Integer> input;

    public Aoc9(List<Integer> list){
        this.input = list;
    }
    public long part(boolean part1){
        List<Pair<Integer,Integer>> pairs = convertToPairList(input);
        for(int i=pairs.size()-1;i>=0;i--){
            for(int j=0;j<i;j++){
                if(pairs.get(j).getKey() == -1 && pairs.get(i).getKey() != -1){
                    Pair<Integer,Integer> val = pairs.get(i);
                    Pair<Integer,Integer> blank = pairs.get(j);
                    if(val.getValue() == blank.getValue()){
                        Collections.swap(pairs,j,i);
                        break;
                    }
                    else{
                        if(part1){
                            int a =0;
                            while(blank.getValue() !=0 && val.getValue() !=0){
                                pairs.set(j,Pair.of(-1,blank.getValue()-1));
                                pairs.set(i,Pair.of(val.getKey(),val.getValue() -1));
                                blank = Pair.of(blank.getKey(),blank.getValue() -1);
                                val = Pair.of(val.getKey(),val.getValue() -1);
                                a++;
                            }
                            if(blank.getValue() ==0){
                                pairs.remove(j);
                                pairs.add(j,Pair.of(val.getKey(),a));
                            }
                            if(val.getValue() ==0){
                                pairs.remove(i);
                                pairs.add(j,Pair.of(val.getKey(),a));
                            }
                        }
                        else{
                            if(val.getValue() < blank.getValue()){
                                pairs.set(j,Pair.of(-1,blank.getValue() - val.getValue()));
                                pairs.set(i,Pair.of(-1,val.getValue()));
                                pairs.add(j,val);
                                i++;
                                break;
                            }
                        }
                    }
                }
            }
        }
        return counter(pairs);
    }

    public long counter(List<Pair<Integer, Integer>> pairs){
        long result = 0;
        List<Integer> list = new ArrayList<>();
        for (Pair<Integer, Integer> pair : pairs) {
            for (int j = 0; j < pair.getValue(); j++) {
                list.add(pair.getKey());
            }
        }
        for(int i=0;i<list.size();i++){
            if(list.get(i) != -1){
                result = result + (list.get(i) * i);
            }
        }
        return result;
    }

    public List<Pair<Integer, Integer>> convertToPairList(List<Integer> inputList) {
        List<Pair<Integer, Integer>> pairs = new ArrayList<>();
        for(int i=0;i<inputList.size();i++){
            int a = inputList.get(i);
            int counter = i+1;
            int val = 1;
            if(counter < inputList.size()){
                while(inputList.get(counter) == a){
                    val++;
                    counter++;
                    if(counter >= inputList.size()){
                        break;
                    }
                }
            }
            i=counter-1;
            pairs.add(Pair.of(a,val));
        }
        return pairs;
    }

}
