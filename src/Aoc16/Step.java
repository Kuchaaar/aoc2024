package Aoc16;

public class Step {
    int distance;
    int row;
    int col;
    char direction;

    Step(int distance, int row, int col, char direction) {
        this.distance = distance;
        this.row = row;
        this.col = col;
        this.direction = direction;
    }
}
