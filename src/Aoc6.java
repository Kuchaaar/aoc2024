import java.util.*;
import java.util.stream.Collectors;

public class Aoc6 {
    int x = 0;
    int y = 0;
    long value = 0;
    long val1 = 0;
    List<List<Character>> list;
    int maxX;
    int maxY;

    Aoc6(List<List<Character>> list) {
        this.list = list;
        findStart();
        maxX = list.size();
        maxY = list.get(0).size();
    }

    public void findStart() {
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).size(); j++) {
                if (list.get(i).get(j) == '^') {
                    x = i -1;
                    y = j;
                    return;
                }
            }
        }
    }

    public void part1() {
        char[] directions = {'N', 'R', 'S', 'L'};
        List<List<Character>> listCopy = list.stream().map(ArrayList::new).collect(Collectors.toList());
        int counter = 0;
        while (true) {
            if (x < 0 || x >= maxX || y < 0 || y >= maxY) {
                break;
            }
            char result = move(directions[counter],listCopy);
            if (result == ' ') {
                break;
            }
            counter = (counter + 1) % directions.length;
        }
        val1 = xcounter(listCopy);
        for(int i=0;i<listCopy.size();i++) {
            for(int j=0;j<listCopy.get(i).size();j++) {
                System.out.print(listCopy.get(i).get(j));
            }
            System.out.println();
        }
    }
    public void part2(){
        List<int[]> hashtags = new ArrayList<>();
        int[] start = new int[2];
        int direction = 1;
        for (int i = 0; i < maxX; i++) {
            for (int j = 0; j < maxY; j++) {
                char charAtX_Y = list.get(i).get(j);
                if (charAtX_Y == '#') {
                    hashtags.add(new int[]{j, i});
                }
                if (charAtX_Y == '^') {
                    start[0] = y;
                    start[1] = x;
                }
            }
        }
        for (int i = 0; i < maxX; i++) {
            for (int j = 0; j < maxY; j++) {
                int[] c = new int[]{j, i};
                if (hashtags.stream().anyMatch(arr -> Arrays.equals(arr, c)) || Arrays.equals(c, start)) {
                    continue;
                }
                List<int[]> hashtagsCopy = new ArrayList<>(hashtags.stream().toList());
                hashtagsCopy.add(new int[]{j, i});
                if (isLoop(direction, start.clone(), hashtagsCopy)) {
                    value++;
                }
            }
        }
    }
    public boolean isLoop(int startingDirection, int[] x_y, List<int[]> obstacles) {
        boolean moving = true;
        int[] start = x_y.clone();
        int direction = startingDirection;
        Set<String> visited = new HashSet<>();
        visited.add(start[0] + "," + start[1] + "," + direction);
        Set<String> obstaclesAsSet = obstacles.stream().map(arr -> arr[0] + "," + arr[1]).collect(Collectors.toSet());
        while (moving) {
            int x = start[0];
            int y = start[1];
            switch (direction) {
                case 1:
                    if (y > 0) {
                        if (obstaclesAsSet.contains(x + "," + (y - 1))) {
                            direction = direction +1;
                        } else {
                            start[1] = start[1] - 1;
                        }
                    } else {
                        moving = false;
                    }
                    break;
                case 2:
                    if (x + 1 < maxX) {
                        if (obstaclesAsSet.contains((x + 1) + "," + y)) {
                            direction += 1;
                        } else {
                            start[0] = start[0] + 1;
                        }
                    } else {
                        moving = false;
                    }
                    break;
                case 3:
                    if (y + 1 < maxY) {
                        if (obstaclesAsSet.contains(x + "," + (y + 1))) {
                            direction += 1;
                        } else {
                            start[1] = start[1] + 1;
                        }
                    } else {
                        moving = false;
                    }
                    break;
                case 4:
                    if (x > 0) {
                        if (obstaclesAsSet.contains((x - 1) + "," + y)) {
                            direction = 1;
                        } else {
                            start[0] = start[0] -1;
                        }
                    } else {
                        moving = false;
                    }
                    break;
            }
            if (moving && visited.contains(start[0] + "," + start[1] + "," + direction)) {
                return true;
            }
            visited.add(start[0] + "," + start[1] + "," + direction);
        }
        return false;
    }

    public char move(char direction,List<List<Character>> listCopy) {
        if (x < 0 || x >= maxX || y < 0 || y >= maxY) {
            return ' ';
        }
        char current = listCopy.get(x).get(y);
        char help = current;
        while(current != '#'){
            listCopy.get(x).set(y, 'X');
            if (x < 0 || x >= maxX -1 || y < 0 || y >= maxY -1) {
                return ' ';
            }
            if(direction=='N'){
                x--;
                help = listCopy.get(x).get(y);
                if(help == '#'){
                    x++;
                    y++;
                    break;
                }
                current = listCopy.get(x).get(y);
            }
            else if(direction=='S'){
                x++;
                help = listCopy.get(x).get(y);
                if(help == '#'){
                    x--;
                    y--;
                    break;
                }
                current = listCopy.get(x).get(y);
            }
            else if(direction=='R'){
                y++;
                help = listCopy.get(x).get(y);
                if(help == '#'){
                    y--;
                    x++;
                    break;
                }
                current = listCopy.get(x).get(y);
            }
            else if(direction=='L'){
                y--;
                help = listCopy.get(x).get(y);
                if(help == '#'){
                    y++;
                    x--;
                    break;
                }
                current = listCopy.get(x).get(y);
            }

        }
        return current;
    }
    public long xcounter(List<List<Character>> copyList){
        long counter =0;
        for (List<Character> characters : copyList) {
            for (Character character : characters) {
                if (character == 'X') {
                    counter++;
                }
            }
        }
        return counter;
    }
}

