package Aoc17;

import java.util.ArrayList;
import java.util.List;

public class Program {
    public long a;
    public long b;
    public long c;
    public List<Integer> programInputs;
    public List<Integer> output;
    int i;

    public Program(long a, long b, long c, List<Integer> programInputs) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.programInputs = programInputs;
        i = 0;
        output = new ArrayList<>();
    }

    public void execute() {
        while (i < programInputs.size()) {
            int literal = programInputs.get(i + 1);
            long combo = getCombo(programInputs.get(i + 1));
            boolean skipFlag = false;
            switch (programInputs.get(i)) {
                case 0 -> {
                    long div = (long) Math.pow(2, combo);
                    a = a / div;
                }
                case 1 -> b = b ^ ((long) literal);
                case 2 -> b = combo % 8;
                case 3 -> {
                    if (a != 0) {
                        i = literal;
                        skipFlag = true;
                    }
                }
                case 4 -> b = b ^ c;
                case 5 -> output.add((int) (combo % 8L));
                case 6 -> {
                    long div = (long) Math.pow(2, combo);
                    b = a / div;
                }
                case 7 -> {
                    long div = (long) Math.pow(2, combo);
                    c = a / div;
                }
            }
            if (!skipFlag) {
                i += 2;
            }
        }
    }

    public long getCombo(int value) {
        return switch (value % 8) {
            case 4 -> a;
            case 5 -> b;
            case 6 -> c;
            case 7 -> Long.MIN_VALUE;
            default -> value;
        };
    }

    @Override
    public String toString() {
        return String.join(",", output.stream().map(Long::toString).toList());
    }
}
