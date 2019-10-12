package ru.asu;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Analizer {
    private FileIO fileIO;

    public Analizer(String inputFilePath, String outputFilePath) throws IOException {
        this.fileIO = new FileIO(inputFilePath, outputFilePath);
    }


    public void buildLabyrinth() {
        int countLab = Integer.parseInt(fileIO.readLine());
        List<Labyrinth> ways = new ArrayList<>();
        readWays(countLab, ways);


    }


    private void readWays(int countLab, List<Labyrinth> ways) {
        for (int i = 0; i < countLab; i++) {
            String line = fileIO.readLine();
            String[] s = line.split(" ");
            Labyrinth labyrinth = new Labyrinth(s[0], s[1]);
            ways.add(labyrinth);
            System.out.println(line);
        }
    }
}
