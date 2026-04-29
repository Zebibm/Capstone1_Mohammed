package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Ledger {

    // ===== FILE CONFIGURATION (TRANSACTION DATA SOURCE) =====
    private static final String FILE_PATH = "src/main/resources/transactions.csv";

    // ===== LOAD ALL TRANSACTIONS FROM CSV FILE =====
    public static ArrayList<Transaction> getAllTransactions() {

        ArrayList<Transaction> list = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {

            String line;

            // Read file line by line
            while ((line = reader.readLine()) != null) {

                // Skip header row
                if (line.startsWith("date")) continue;

                String[] parts = line.split("\\|");

                // Validate correct CSV structure
                if (parts.length < 5) continue;

                try {
                    // Convert CSV row into Transaction object
                    Transaction t = new Transaction(
                            LocalDate.parse(parts[0].trim()),
                            LocalTime.parse(parts[1].trim()),
                            parts[2].trim(),
                            parts[3].trim(),
                            Double.parseDouble(parts[4].trim())
                    );

                    list.add(t);

                } catch (Exception ignored) {
                    // Skip invalid or corrupted rows
                }
            }

        } catch (Exception e) {
            System.out.println("Error reading file.");
        }

        return list;
    }
}