package Aoc17;

import java.util.List;

public class Aoc17 {
    long a;
    long b;
    long c;
    public List<Integer> programInputs;

    public Aoc17(long a, long b, long c, List<Integer> programInputs) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.programInputs = programInputs;
    }

    public String part1() {
        Program program = new Program(a, b, c, programInputs);
        program.execute();
        return program.toString();
    }

    public String part2() {
        long newA = 0L;
        for (int i = 0; i < programInputs.size(); i++) {
            for (newA <<= 3; ; newA++) {
                Program pg = new Program(newA, b, c, programInputs);
                pg.execute();
                if (pg.output.size() >= i + 1) {
                    if (checkSlicesEqual(pg.output, i)) {
                        break;
                    }
                }
            }
        }
        return String.valueOf(newA);
    }

    public boolean checkSlicesEqual(List<Integer> pgResult, int i) {
        return pgResult.subList(0, i + 1).equals(programInputs.subList(programInputs.size() - (i + 1), programInputs.size()));
    }
}
