package Aoc15;

import java.util.Arrays;
import java.util.function.Function;

public enum Head {
    EAST('>', Coordinate::east),
    SOUTH('v', Coordinate::south),
    WEST('<', Coordinate::west),
    NORTH('^', Coordinate::north);
    final char c;
    final Function<Coordinate, Coordinate> mover;

    Head(char c, Function<Coordinate, Coordinate> mover) {
        this.c = c;
        this.mover = mover;
    }

    public static Head from(char c) {
        return Arrays.stream(values()).filter(t -> t.c == c).findFirst().orElseThrow();
    }
}