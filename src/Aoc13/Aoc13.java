package Aoc13;

import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class Aoc13 {

    List<ClawMachine> input;

    public Aoc13(List<ClawMachine> input) {
        this.input = input;
    }

    public long part() {
        long res = 0L;
        for (ClawMachine m : input) {
            Pair<Long, Long> pair = cramer(m);
            if (pair != null) {
                res += pair.getKey() * 3 + pair.getValue();
            }
        }
        return res;
    }

    public Pair<Long, Long> cramer(ClawMachine machine) {
        long detA = machine.ax * machine.by - machine.ay * machine.bx;
        long detX = machine.targetx * machine.by - machine.targety * machine.bx;
        long detY = machine.targety * machine.ax - machine.targetx * machine.ay;
        if (detA != 0 && detX % detA == 0 && detY % detA == 0) {
            return Pair.of(detX / detA, detY / detA);
        } else {
            return null;
        }
    }
}
