package ru.asu;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Analizer {
    private FileIO fileIO;

    public Analizer(String inputFilePath, String outputFilePath) throws IOException {
        this.fileIO = new FileIO(inputFilePath, outputFilePath);
    }


    public void buildLabyrinth(){
        int countLab = Integer.parseInt(fileIO.readLine());
        List<String> ways = new ArrayList<>();
        readWays(countLab, ways);

        for(String lab: ways){
            String[] labyrints = lab.split(" ");

        }
    }


    private void readWays(int countLab, List<String> ways) {
        for (int i = 0; i < countLab; i++) {
            String line = fileIO.readLine();
            ways.add(line);
            System.out.println(line);
        }
    }

    private void
}
