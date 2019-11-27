package ru.asu.dto;

import java.awt.*;

public class LabPoint {

    private Point point;
    private DirectionConst directionConst;

    public LabPoint(Point point) {
        this.point = point;
        this.directionConst = DirectionConst.ZERO;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public DirectionConst getDirectionConst() {
        return directionConst;
    }

    public void setDirectionConst(DirectionConst directionConst) {
        this.directionConst = directionConst;
    }

    @Override
    public String toString() {
        return "LabPoint{" +
                "point=" + point +
                ", directionConst=" + directionConst +
                '}';
    }
}
