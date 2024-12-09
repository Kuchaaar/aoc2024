import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class Aoc9 {
    List<Integer> input;

    public Aoc9(List<Integer> list){
        this.input = list;
    }
    public long part1(){
        long result =0;
        List<Integer> listCopy = new ArrayList<>(input);
        int L = 0;
        int R = listCopy.size()-1;
        while(L<R){
            if(listCopy.get(L) == -1 && listCopy.get(R) != -1){
                Collections.swap(listCopy,L,R);
            }
            else if(listCopy.get(L) != -1){
                L++;
            }
            else if(listCopy.get(R) == -1){
                R--;
            }
            else{
                L++;
                R--;
            }
        }
        for(int j=0;j<listCopy.size();j++){
            if(listCopy.get(j) == -1){
                break;
            }
            else{
                result = result + (listCopy.get(j) * j);
            }
        }
        return result;
    }
    public long part2(){
        long result = 0;
        List<Pair<Integer, Integer>> pairs = convertToPairList(input);
        for(int i=pairs.size()-1;i>=0;i--){
            for(int j=0;j<i;j++){
                if(pairs.get(j).getKey() == -1 && pairs.get(i).getKey() != -1){
                    if(pairs.get(i).getValue() == pairs.get(j).getValue()){
                        Collections.swap(pairs,j,i);
                        break;
                    }
                    else if(pairs.get(i).getValue() < pairs.get(j).getValue()){
                        Pair<Integer,Integer> val = pairs.get(i);
                        pairs.set(j,Pair.of(-1,pairs.get(j).getValue() - pairs.get(i).getValue()));
                        pairs.set(i,Pair.of(-1,val.getValue()));
                        pairs.add(j,val);
                        i++;
                        break;
                    }
                }
            }
        }
        List<Integer> list = new ArrayList<>();
        for(int i=0;i<pairs.size();i++){
            for(int j=0;j<pairs.get(i).getValue();j++){
                list.add(pairs.get(i).getKey());
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
