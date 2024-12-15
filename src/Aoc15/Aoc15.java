package Aoc15;


public class Aoc15 {
    final Grid initialGrid;

    public Aoc15(Grid initialGrid) throws Exception {
        this.initialGrid = initialGrid;
    }

    public long solve() {
        var grid = initialGrid;
        while (true) {
            var newGrid = grid.move();
            if (newGrid == null) {
                break;
            }
            grid = newGrid;
        }
        return grid.gpsSum();
    }
}
