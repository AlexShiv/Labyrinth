package ru.asu;

import java.io.*;
import java.util.Scanner;

public class FileIO {

    private final Scanner scanner;
    private final Writer writer;

    public FileIO(String inputFilePath, String outputFilePath) throws IOException {
        Reader reader = new FileReader(inputFilePath);
        this.scanner = new Scanner(reader);
        this.writer = new FileWriter(outputFilePath);
    }

    public String readLine() {
        return scanner.nextLine();
    }

    public boolean writeLine(String line) {
        try {
            writer.write(line);
            writer.flush();
        } catch (IOException e) {
            System.out.println("Error while writing in file " + line);
            return false;
        }
        return true;
    }
}
