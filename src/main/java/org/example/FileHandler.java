package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileHandler {

    // ===== FILE CONFIGURATION (DATA STORAGE LOCATION) =====
    private static final String FILE_PATH = "src/main/resources/transactions.csv";

    // ===== SAVE TRANSACTION TO CSV FILE =====
    public static void saveTransaction(Transaction t) {

        try {
            File file = new File(FILE_PATH);

            // Check if file is newly created (to add header row only once)
            boolean isNew = !file.exists();

            // Open file in append mode
            FileWriter writer = new FileWriter(file, true);

            // Write CSV header if file is new
            if (isNew) {
                writer.write("date|time|description|vendor|amount\n");
            }

            // Write transaction data in CSV format
            writer.write(t.toCSV() + "\n");

            writer.close();

        } catch (IOException e) {
            System.out.println("\u001B[31mError writing file\u001B[0m");
        }
    }
}