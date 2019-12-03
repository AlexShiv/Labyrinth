package ru.asu;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Analyzer analyzer = new Analyzer("src\\main\\resources\\large-test.in.txt", "src\\main\\resources\\labOut.txt");
        analyzer.buildLabyrinth();
    }
}
