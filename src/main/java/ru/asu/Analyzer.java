package ru.asu;


import ru.asu.dto.Labyrinth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Analyzer {
    private FileIO fileIO;

    public Analyzer(String inputFilePath, String outputFilePath) throws IOException {
        this.fileIO = new FileIO(inputFilePath, outputFilePath);
    }


     public void buildLabyrinth() {
        List<Labyrinth> ways = readWays();
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
}
