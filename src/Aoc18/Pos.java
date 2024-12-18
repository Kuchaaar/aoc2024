package Aoc18;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Pos {
    public int x;
    public int y;

    public Pos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Pos add(Pos other) {
        return new Pos(this.x + other.x, this.y + other.y);
    }

    public boolean isInBounds() {
        return 0 <= x && x < 71 && 0 <= y && y < 71;
    }

    public List<Pos> getNeighbors() {
        List<Pos> neighbors = new ArrayList<>();
        for (Pos around : Arrays.asList(new Pos(0, 1), new Pos(0, -1), new Pos(1, 0), new Pos(-1, 0))) {
            Pos neighbor = this.add(around);
            if (neighbor.isInBounds()) {
                neighbors.add(neighbor);
            }
        }
        return neighbors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pos pos = (Pos) o;
        return x == pos.x && y == pos.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
