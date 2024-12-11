import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Aoc11 {
    List<Long> input;

    public Aoc11(List<Long> input) {
        this.input = input;
    }

    public Long part(int swapNumber) {
        Long result = 0L;
        Map<Long, Map<Integer, Long>> map = new HashMap<>();
        for (Long stone : input) {
            result = result + swap(stone, swapNumber, map);
        }
        return result;
    }

    public Long swap(Long stone, int swapNumber, Map<Long, Map<Integer, Long>> map) {
        if (swapNumber == 0) {
            return 1L;
        }
        Map<Integer, Long> mapStones = map.get(stone);
        if (mapStones != null && mapStones.get(swapNumber) != null) {
            return mapStones.get(swapNumber);
        }
        String stringOfStone = Long.toString(stone);
        Long res = 0L;
        if (stone == 0) {
            res = swap(1L, swapNumber - 1, map);
        } else if (stringOfStone.length() % 2 == 0) {
            int halfLength = stringOfStone.length() / 2;
            Long left = Long.parseLong(stringOfStone.substring(0, halfLength));
            Long right = Long.parseLong(stringOfStone.substring(halfLength));
            res = swap(left, swapNumber - 1, map) + swap(right, swapNumber - 1, map);
        } else {
            res = swap(stone * 2024, swapNumber - 1, map);
        }
        if (mapStones == null) {
            mapStones = new HashMap<>();
            map.put(stone, mapStones);
        }
        mapStones.put(swapNumber, res);
        return res;
    }
}
