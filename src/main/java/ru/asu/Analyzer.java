package ru.asu;


import ru.asu.dto.DirectionConst;
import ru.asu.dto.LabPoint;
import ru.asu.dto.Labyrinth;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Analyzer {
    private FileIO fileIO;
    private int x = 0;
    private int y = 0;
    private char direction = 's';

    public Analyzer(String inputFilePath, String outputFilePath) throws IOException {
        this.fileIO = new FileIO(inputFilePath, outputFilePath);

    }


    public void buildLabyrinth() {
        List<Labyrinth> ways = readWays();

        for (Labyrinth way : ways) {
            List<LabPoint> labPoints = new ArrayList<>();
            direction = 's';

            String in = way.getIn();
            String out = way.getOut();
            fillLabPath(labPoints, in);
            fillLabPath(labPoints, out);

            System.out.println("*********");
            labPoints.forEach(System.out::println);

        }
    }

    private void fillLabPath(List<LabPoint> labPoints, String way) {
        for (int i = 0; i < way.length(); i++) {
            char c = way.charAt(i);
            if (c == 'W') {
                if (labPoints.isEmpty()) {
                    labPoints.add(new LabPoint(new Point(x, y)));
                    labPoints.get(0).setDirectionConst(DirectionConst.ONE);
                    continue;
                }
                // берем текущую точку
                LabPoint currentPoint = labPoints.get(labPoints.size() - 1);
                DirectionConst currentDirection = currentPoint.getDirectionConst();
                DirectionConst nextDirection;

                // создаем следующую точку маршрута

                int finalX = x;
                int finalY = y;
                LabPoint nextPoint = null;
                Optional<LabPoint> first = labPoints.stream().filter(labPoint -> labPoint.getPoint().getX() == finalX && labPoint.getPoint().getY() == finalY).findFirst();
                if (first.isPresent()){
                    nextPoint = first.get();
                } else {
                    nextPoint = new LabPoint(new Point(x, y));
                }
                // проверяем в какую сторону смотрит герой
                switch (direction) {
                    case 'n':
                        y--;
                        currentDirection = findByCompass(true, currentDirection.isSouth(), currentDirection.isWest(), currentDirection.isEast());
                        if (first.isPresent()) {
                            nextDirection = findByCompass(nextPoint.getDirectionConst().isNorth(), true, nextPoint.getDirectionConst().isWest(), false);
                        } else {
                            nextDirection = findByCompass(false, true, false, false);
                        }
                        break;
                    case 's':
                        y++;
                        currentDirection = findByCompass(currentDirection.isNorth(), true, currentDirection.isWest(), currentDirection.isEast());
                        if (first.isPresent()){
                            nextDirection = findByCompass(true, nextPoint.getDirectionConst().isSouth(), nextPoint.getDirectionConst().isWest(), nextPoint.getDirectionConst().isEast());
                        }
                        else {
                            nextDirection = findByCompass(true, false, false, false);
                        }
                        break;
                    case 'w':
                        x--;
                        currentDirection = findByCompass(currentDirection.isNorth(), currentDirection.isSouth(), true, currentDirection.isEast());
                        if (first.isPresent()){
                            nextDirection = findByCompass(nextPoint.getDirectionConst().isNorth(), nextPoint.getDirectionConst().isSouth(), nextPoint.getDirectionConst().isWest(), true);
                        }
                        else {
                            nextDirection = findByCompass(false, false, false, true);
                        }
                        break;
                    case 'e':
                        x++;
                        currentDirection = findByCompass(currentDirection.isNorth(), currentDirection.isSouth(), currentDirection.isWest(), true);
                        if (first.isPresent()){
                            nextDirection = findByCompass(nextPoint.getDirectionConst().isNorth(), nextPoint.getDirectionConst().isSouth(), true, nextPoint.getDirectionConst().isEast());
                        }
                        else {
                            nextDirection = findByCompass(false, false, true, false);
                        }
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + direction);
                }
                currentPoint.setDirectionConst(currentDirection);
                nextPoint.setDirectionConst(nextDirection);

                labPoints.add(nextPoint);

            } else if (c == 'L') {
                if (direction == 'n') {
                    direction = 'w';
                } else if (direction == 'w') {
                    direction = 's';
                } else if (direction == 's') {
                    direction = 'e';
                } else {
                    direction = 'n';
                }
            } else if (c == 'R') {
                if (direction == 'n') {
                    direction = 'e';
                } else if (direction == 'e') {
                    direction = 's';
                } else if (direction == 's') {
                    direction = 'w';
                } else {
                    direction = 'n';
                }
            }
        }
    }


    private List<Labyrinth> readWays() {
        int countLab = Integer.parseInt(fileIO.readLine());

        List<Labyrinth> ways = new ArrayList<>();
        for (int i = 0; i < countLab; i++) {
            String line = fileIO.readLine();
            String[] s = line.split(" ");
            Labyrinth labyrinth = new Labyrinth(s[0], s[1]);
            ways.add(labyrinth);
            System.out.println(line);
        }

        return ways;
    }

    private DirectionConst findByCompass(boolean north, boolean south, boolean west, boolean east) {
        Optional<DirectionConst> dir = Arrays.stream(DirectionConst.values())
                .filter(directionConst -> directionConst.isNorth() == north
                        && directionConst.isSouth() == south
                        && directionConst.isWest() == west
                        && directionConst.isEast() == east)
                .findAny();
        return dir.orElseThrow();
    }

    private DirectionConst findByDirectionName(String name) {
        Optional<DirectionConst> dir = Arrays.stream(DirectionConst.values())
                .filter(directionConst -> directionConst.getName().equals(name))
                .findAny();
        return dir.orElseThrow();
    }
}
