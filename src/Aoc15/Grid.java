package Aoc15;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public record Grid(Coordinate position, Set<Coordinate> boxes, Set<Coordinate> walls, List<Head> heads,
            int instructionPointer, boolean wide) {
    public static Grid from(String input, boolean wide) {
        Coordinate position = null;
        Set<Coordinate> boxes = new HashSet<>();
        Set<Coordinate> walls = new HashSet<>();
        var parts = input.split("\r\n\r\n");
        int y = 0;
        for (String line : parts[0].split("\n")) {
            int x = 0;
            for (char c : line.toCharArray()) {
                Coordinate coordinate = new Coordinate(x, y);
                switch (c) {
                    case '@' -> position = coordinate;
                    case '#' -> walls.add(coordinate);
                    case 'O' -> boxes.add(coordinate);
                }
                if (wide && c == '#') {
                    walls.add(coordinate.east());
                }
                x += wide ? 2 : 1;
            }
            y++;
        }
        List<Head> heads = new ArrayList<>();
        for (String line : parts[1].split("\r\n")) {
            for (char c : line.toCharArray()) {
                heads.add(Head.from(c));
            }
        }
        return new Grid(position, boxes, walls, heads, 0, wide);
    }

    boolean isBoxNextToWall(Coordinate box, Head head) {
        return walls.contains(box.move(head)) || wide && walls.contains(box.move(head).east());
    }

    Set<Coordinate> movableBoxes(Coordinate initialPos, Head head) {
        Set<Coordinate> visited = new HashSet<>();
        Deque<Coordinate> queue = new ArrayDeque<>();
        if (!boxes.contains(initialPos)) {
            throw new IllegalStateException();
        }
        queue.addLast(initialPos);
        while (!queue.isEmpty()) {
            var pos = queue.removeFirst();
            if (visited.contains(pos)) {
                continue;
            }
            visited.add(pos);
            var newPos = pos.move(head);
            if (boxes.contains(newPos)) {
                queue.addLast(newPos);
            }
            if (wide) {
                if (boxes.contains(newPos.west())) {
                    queue.addLast(newPos.west());
                }
                if (boxes.contains(newPos.east())) {
                    queue.addLast(newPos.east());
                }
            }
        }
        return visited.stream().noneMatch(c -> isBoxNextToWall(c, head)) ? visited : Collections.emptySet();
    }

    boolean isRobotNextToWall(Head head) {
        return walls.contains(position.move(head));
    }

    Coordinate boxNextToRobot(Head head) {
        Coordinate next = position.move(head);
        if (boxes.contains(next)) {
            return next;
        }
        if (wide) {
            Coordinate west = next.west();
            if (boxes.contains(west)) {
                return west;
            }
        }
        return null;
    }

    Grid move() {
        if (instructionPointer >= heads.size()) {
            return null;
        }
        var heading = heads.get(instructionPointer);
        boolean robotCanMove;
        Set<Coordinate> movableBoxes;
        if (isRobotNextToWall(heading)) {
            robotCanMove = false;
            movableBoxes = Collections.emptySet();
        } else {
            var possibleBox = boxNextToRobot(heading);
            if (possibleBox == null) {
                robotCanMove = true;
                movableBoxes = Collections.emptySet();
            } else {
                movableBoxes = movableBoxes(possibleBox, heading);
                robotCanMove = !movableBoxes.isEmpty();
            }
        }
        Set<Coordinate> newBoxes;
        if (movableBoxes.isEmpty()) {
            newBoxes = boxes;
        } else {
            newBoxes = new HashSet<>();
            newBoxes.addAll(boxes.stream().filter(Predicate.not(movableBoxes::contains)).collect(Collectors.toSet()));
            newBoxes.addAll(boxes.stream().filter(movableBoxes::contains).map(c -> c.move(heading)).collect(Collectors.toSet()));
        }
        return new Grid(robotCanMove ? position.move(heading) : position, newBoxes, walls, heads, instructionPointer + 1, wide);
    }

    long gpsSum() {
        return boxes.stream().mapToLong(c -> c.x() + 100L * c.y()).sum();
    }
}
