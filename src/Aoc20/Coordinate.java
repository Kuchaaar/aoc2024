package Aoc20;

public class Coordinate {
    public int x;
    public int y;
    public int z;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
        this.z = 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Coordinate that = (Coordinate) obj;
        return x == that.x && y == that.y && z == that.z;
    }

    @Override
    public int hashCode() {
        return 10000 * x + 100 * y + z;
    }
}
