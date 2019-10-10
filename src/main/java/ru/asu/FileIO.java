package ru.asu;

import java.io.*;
import java.util.Scanner;

public class FileIO {

    private final Reader reader;
    private final Scanner scanner;
    private final Writer writer;

    public FileIO(String inputFilePath, String outputFilePath) throws IOException {
        this.reader = new FileReader(inputFilePath);
        this.writer = new FileWriter(outputFilePath);
        this.scanner = new Scanner(reader);
    }

    public String readLine(){
        return scanner.nextLine();
    }

    public boolean writeLine(String line) {
        try {
            writer.write(line);
        } catch (IOException e) {
            System.out.println("Error while writing in file " + line);
            return false;
        }
        return true;
    }
}
