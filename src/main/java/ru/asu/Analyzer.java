package ru.asu;


import ru.asu.dto.DirectionConst;
import ru.asu.dto.LabPoint;
import ru.asu.dto.Labyrinth;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

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

        for (int i = 0; i < ways.size(); i++) {
            Labyrinth way = ways.get(i);
            List<LabPoint> labPoints = new ArrayList<>();
            direction = 's';
            x = 0;
            y = 0;

            int endX = 0;
            int endY = 0;
            String in = way.getIn();
            String out = way.getOut();

            fillLabPath(labPoints, in);

            LabPoint exit = labPoints.get(labPoints.size() - 1);

            endX = (int) exit.getPoint().getX();
            endY = (int) exit.getPoint().getY();
            reverseDirection();
            fillLabPath(labPoints, out);

            Iterator<LabPoint> iterator = labPoints.iterator();
            while (iterator.hasNext()) {
                Point point = iterator.next().getPoint();
                if ((point.getX() == endX && point.getY() == endY) || (point.getX() == 0 && point.getY() == -1)) {
                    iterator.remove();
                }
            }

            String result = "Case #" + (i + 1) + ":\n";
            System.out.print(result);
            writeWays(result + printLab(labPoints));


        }
    }

    private String printLab(List<LabPoint> labPoints) {
        String result = "";
        List<LabPoint> sortedList = labPoints.stream().sorted((o1, o2) -> {
            int res = (int) (o1.getPoint().getY() - o2.getPoint().getY());
            if (res == 0) {
                return (int) (o1.getPoint().getX() - o2.getPoint().getX());
            }
            return res;
        }).collect(Collectors.toList());

        String out = "";
        for (int i = 0; i < sortedList.size() - 1; i++) {
            if (sortedList.get(i).getPoint().getY() == sortedList.get(i + 1).getPoint().getY()) {
                out += sortedList.get(i).getDirectionConst().getName();
            } else {
                out += sortedList.get(i).getDirectionConst().getName();
                System.out.println(out);
                //запись в файл
                result += out + "\n";
                out = "";
            }
        }
        out = out + sortedList.get(sortedList.size() - 1).getDirectionConst().getName();
        System.out.println(out);
        result += out + "\n";
        return result;
    }

    private void reverseDirection() {
        if (direction == 'n') {
            direction = 's';
        } else if (direction == 'e') {
            direction = 'w';
        } else if (direction == 's') {
            direction = 'n';
        } else {
            direction = 'e';
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
                LabPoint currentPoint = labPoints.stream()
                        .filter(labPoint -> labPoint.getPoint().getX() == x && labPoint.getPoint().getY() == y)
                        .findFirst()
                        .orElseThrow();
                DirectionConst currentDirection = currentPoint.getDirectionConst();
                DirectionConst nextDirection;

                // проверяем в какую сторону смотрит герой, меняем x и y
                currentDirection = getDirectionConst(currentDirection);

                int finalX = x;
                int finalY = y;
                Optional<LabPoint> first = labPoints.stream()
                        .filter(labPoint -> labPoint.getPoint().getX() == finalX && labPoint.getPoint().getY() == finalY)
                        .findFirst();

                LabPoint nextPoint = first.orElseGet(() -> {
                    LabPoint labPoint = new LabPoint(new Point(x, y));
                    labPoints.add(labPoint);
                    return labPoint;
                });
                nextDirection = getDirectionConst(nextPoint, first);

                currentPoint.setDirectionConst(currentDirection);
                nextPoint.setDirectionConst(nextDirection);


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

    private DirectionConst getDirectionConst(DirectionConst currentDirection) {
        switch (direction) {
            case 'n':
                y--;
                currentDirection = findByCompass(true, currentDirection.isSouth(), currentDirection.isWest(), currentDirection.isEast());
                break;
            case 's':
                y++;
                currentDirection = findByCompass(currentDirection.isNorth(), true, currentDirection.isWest(), currentDirection.isEast());
                break;
            case 'w':
                x--;
                currentDirection = findByCompass(currentDirection.isNorth(), currentDirection.isSouth(), true, currentDirection.isEast());
                break;
            case 'e':
                x++;
                currentDirection = findByCompass(currentDirection.isNorth(), currentDirection.isSouth(), currentDirection.isWest(), true);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + direction);
        }
        return currentDirection;
    }

    private DirectionConst getDirectionConst(LabPoint nextPoint, Optional<LabPoint> first) {
        DirectionConst nextDirection;
        switch (direction) {
            case 'n':
                if (first.isPresent()) {
                    nextDirection = findByCompass(nextPoint.getDirectionConst().isNorth(), true, nextPoint.getDirectionConst().isWest(), false);
                } else {
                    nextDirection = findByCompass(false, true, false, false);
                }
                break;
            case 's':
                if (first.isPresent()) {
                    nextDirection = findByCompass(true, nextPoint.getDirectionConst().isSouth(), nextPoint.getDirectionConst().isWest(), nextPoint.getDirectionConst().isEast());
                } else {
                    nextDirection = findByCompass(true, false, false, false);
                }
                break;
            case 'w':
                if (first.isPresent()) {
                    nextDirection = findByCompass(nextPoint.getDirectionConst().isNorth(), nextPoint.getDirectionConst().isSouth(), nextPoint.getDirectionConst().isWest(), true);
                } else {
                    nextDirection = findByCompass(false, false, false, true);
                }
                break;
            case 'e':
                if (first.isPresent()) {
                    nextDirection = findByCompass(nextPoint.getDirectionConst().isNorth(), nextPoint.getDirectionConst().isSouth(), true, nextPoint.getDirectionConst().isEast());
                } else {
                    nextDirection = findByCompass(false, false, true, false);
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + direction);
        }
        return nextDirection;
    }

    private List<Labyrinth> readWays() {
        int countLab = Integer.parseInt(fileIO.readLine());

        List<Labyrinth> ways = new ArrayList<>();
        for (int i = 0; i < countLab; i++) {
            String line = fileIO.readLine();
            String[] s = line.split(" ");
            Labyrinth labyrinth = new Labyrinth(s[0], s[1]);
            ways.add(labyrinth);
        }

        return ways;
    }

    private void writeWays(String out) {
        fileIO.writeLine(out);
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
}
