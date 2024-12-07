import java.util.List;

public class Aoc4 {
    public long part1(List<String> list){
        long value = 0;
        for(int i=0;i<list.size();i++){
            for(int j=0;j<list.get(i).length();j++){
                // right
                if(list.get(i).length() > j+3) {
                    if (list.get(i).charAt(j) == 'X' && list.get(i).charAt(j+1) == 'M' && list.get(i).charAt(j+2) == 'A' && list.get(i).charAt(j+3) == 'S') {
                        value++;
                    }
                }
                // down
                if(list.size() > i+3){
                    if(list.get(i).charAt(j) == 'X' && list.get(i+1).charAt(j) == 'M' && list.get(i+2).charAt(j) == 'A' && list.get(i+3).charAt(j) == 'S'){
                        value++;
                    }
                }
                // left
                if(j>2){
                    if(list.get(i).charAt(j) == 'X' && list.get(i).charAt(j-1) == 'M' && list.get(i).charAt(j-2) == 'A' && list.get(i).charAt(j-3) == 'S'){
                        value++;
                    }
                }
                //up
                if(i>2){
                    if(list.get(i).charAt(j) == 'X' && list.get(i-1).charAt(j) == 'M' && list.get(i-2).charAt(j) == 'A' && list.get(i-3).charAt(j) == 'S'){
                        value++;
                    }
                }
                // left up
                if(i>2 && j>2){
                    if(list.get(i).charAt(j) == 'X' && list.get(i-1).charAt(j-1) == 'M' && list.get(i-2).charAt(j-2) == 'A' && list.get(i-3).charAt(j-3) == 'S'){
                        value++;
                    }
                }
                // right down
                if(list.get(i).length() > j+3 && list.size() > i+3){
                    if (list.get(i).charAt(j) == 'X' && list.get(i+1).charAt(j+1) == 'M' && list.get(i+2).charAt(j+2) == 'A' && list.get(i+3).charAt(j+3) == 'S') {
                        value++;
                    }
                }
                // right up
                if(i>2 && list.get(i).length() > j+3){
                    if (list.get(i).charAt(j) == 'X' && list.get(i-1).charAt(j+1) == 'M' && list.get(i-2).charAt(j+2) == 'A' && list.get(i-3).charAt(j+3) == 'S') {
                        value++;
                    }
                }
                //left down
                if(j>2 && list.size() > i+3){
                    if (list.get(i).charAt(j) == 'X' && list.get(i+1).charAt(j-1) == 'M' && list.get(i+2).charAt(j-2) == 'A' && list.get(i+3).charAt(j-3) == 'S') {
                        value++;
                    }
                }
            }
        }
        return value;
    }
    public long part2(List<String> list){
        long value = 0;
        for(int i=0;i<list.size();i++){
            for(int j=0;j<list.get(i).length();j++){
                if( list.get(i).charAt(j) == 'A' && list.get(i).length() > j+1 && j>0 && i>0 && list.size() > i+1){
                    if(list.get(i-1).charAt(j-1) == 'M' & list.get(i+1).charAt(j+1) == 'S' || list.get(i-1).charAt(j-1) == 'S' & list.get(i+1).charAt(j+1) == 'M'){
                        if(list.get(i+1).charAt(j-1) == 'M'  && list.get(i-1).charAt(j+1) == 'S' || list.get(i+1).charAt(j-1) == 'S'  && list.get(i-1).charAt(j+1) == 'M'){
                            value++;
                        }
                    }
                }
            }
        }
        return value;
    }
}
