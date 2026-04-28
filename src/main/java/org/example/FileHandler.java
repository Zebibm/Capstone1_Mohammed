package org.example;

import java.io.FileWriter;
import java.io.IOException;

public class FileHandler {

    public static void saveTransaction(Transaction t) {

        try {

            FileWriter writer = new FileWriter("transactions.csv", true);
            writer.write(t.toCSV() + "\n");
            writer.close();

        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }
}

