package ru.asu;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Analizer analizer = new Analizer("src\\main\\resources\\labIn.txt", "src\\main\\resources\\labOut.txt");
        analizer.buildLabyrinth();
    }
}
