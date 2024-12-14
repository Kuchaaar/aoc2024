package Aoc14;

import org.apache.commons.lang3.tuple.Pair;

public class Robot {
    int x;
    int y;
    Pair<Integer, Integer> vector;

    public Robot(int x, int y, Pair<Integer, Integer> vector) {
        this.x = x;
        this.y = y;
        this.vector = vector;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
}
