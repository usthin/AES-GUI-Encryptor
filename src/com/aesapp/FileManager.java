package com.aesapp;

import java.io.*;

public class FileManager {

    public static void saveToFile(String data, String filename) throws Exception {

        FileWriter writer = new FileWriter(filename);
        writer.write(data);
        writer.close();
    }

    public static String readFromFile(String filename) throws Exception {

        BufferedReader reader = new BufferedReader(new FileReader(filename));

        StringBuilder content = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            content.append(line);
        }

        reader.close();

        return content.toString();
    }
}