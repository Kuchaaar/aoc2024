package Aoc14;

import java.util.ArrayList;
import java.util.List;

public class Aoc14 {
    List<Robot> input;
    int maxX;
    int maxY;

    public Aoc14(List<Robot> input, int maxX, int maxY) {
        this.input = input;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public Long part1() {
        long[] result = new long[4];
        for (Robot robot : input) {
            changeRobotValue(robot, 100);
            checkQuarter(result, robot.x, robot.y);
        }
        return result[0] * result[1] * result[2] * result[3];
    }

    public int part2() {
        for (int i = 0; i <= 12000; i++) {
            List<String> list = new ArrayList<>();
            for (int k = 0; k < maxY; k++) {
                list.add(".".repeat(Math.max(0, maxX)));
            }
            for (Robot robot : input) {
                changeRobotValue(robot, 1);
                String line = list.get(robot.y);
                StringBuilder sb = new StringBuilder(line);
                sb.setCharAt(robot.x, '*');
                list.set(robot.y, sb.toString());
            }
            if (list.stream().anyMatch(s -> s.contains("***********"))) {
                return i + 1;
            }
        }
        System.out.println("Increase a i");
        return 0;
    }

    private void changeRobotValue(Robot robot, int times) {
        robot.setX((robot.x + (robot.vector.getKey() * times)) % maxX);
        if (robot.x < 0) {
            robot.setX(robot.x + maxX);
        }
        robot.setY((robot.y + (robot.vector.getValue() * times)) % maxY);
        if (robot.y < 0) {
            robot.setY(robot.y + maxY);
        }
    }

    private void checkQuarter(long[] result, int x, int y) {
        int halfX = maxX / 2;
        int halfY = maxY / 2;
        if (x < halfX && y < halfY) {
            result[0]++;
        } else if (x > halfX && y > halfY) {
            result[3]++;
        } else if (x > halfX && y < halfY) {
            result[2]++;
        } else if (x < halfX && y > halfY) {
            result[1]++;
        }
    }
}
