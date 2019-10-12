package ru.asu.dto;

public enum DirectionConst {
    ONE(true, false, false, false, "1"),
    TWO(false, true, false, false, "2"),
    THREE(true, true, false, false, "3"),
    FOUR(false, false, true, false, "4"),
    FIVE(true, false, true, false, "5"),
    SIX(false, true, true, false, "6"),
    SEVEN(true, true, true, false, "7"),
    EIGHT(false, false, false, true, "8"),
    NINE(true, false, false, true, "9"),
    A(false, true, false, true, "a"),
    B(true, true, false, true, "b"),
    C(false, false, true, true, "c"),
    D(true, false, true, true, "d"),
    E(false, true, true, true, "e"),
    F(true, true, true, true, "f");

    private boolean north;
    private boolean south;
    private boolean west;
    private boolean east;
    private String name;


    DirectionConst(boolean north, boolean south, boolean west, boolean east, String name) {
        this.north = north;
        this.south = south;
        this.west = west;
        this.east = east;
        this.name = name;
    }

    public boolean isNorth() {
        return north;
    }

    public boolean isSouth() {
        return south;
    }

    public boolean isWest() {
        return west;
    }

    public boolean isEast() {
        return east;
    }

    public String getName() {
        return name;
    }
}
