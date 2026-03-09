package com.aesapp;

import java.io.*;

public class FileManager {

    public static void saveToFile(String data) throws Exception {

        FileWriter writer = new FileWriter("ciphertext.txt");

        writer.write(data);

        writer.close();
    }

    public static String readFromFile() throws Exception {

        BufferedReader reader = new BufferedReader(new FileReader("ciphertext.txt"));

        StringBuilder content = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            content.append(line);
        }

        reader.close();

        return content.toString();
    }
}