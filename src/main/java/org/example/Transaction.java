package org.example;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Transaction {

    // ===== TRANSACTION FIELDS (MODEL DATA) =====
    private LocalDate date;
    private LocalTime time;
    private String description;
    private String vendor;
    private double amount;

    // Formatter for consistent time display (HH:mm format)
    private static final DateTimeFormatter TIME_FORMAT =
            DateTimeFormatter.ofPattern("HH:mm");

    // ===== CONSTRUCTOR =====
    public Transaction(LocalDate date, LocalTime time, String description, String vendor, double amount) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    // ===== EXPORT TRANSACTION AS CSV FORMAT =====
    public String toCSV() {
        return date + "|" + time + "|" + description + "|" + vendor + "|" + amount;
    }

    // ===== FORMATTED DISPLAY (TABLE VIEW WITH COLOR OUTPUT) =====
    @Override
    public String toString() {

        String amountStr;

        // Positive = deposit (green), Negative = payment (red)
        if (amount >= 0) {
            amountStr = UI.GREEN + String.format("$%.2f", amount) + UI.RESET;
        } else {
            amountStr = UI.RED + String.format("$%.2f", amount) + UI.RESET;
        }

        return String.format(
                "| %-10s | %-5s | %-20s | %-15s | %10s |",
                date,
                time.format(TIME_FORMAT),
                formatText(description, 20),
                formatText(vendor, 15),
                amountStr
        );
    }

    // ===== TEXT TRUNCATION (PREVENT TABLE BREAKING) =====
    private String formatText(String text, int length) {
        if (text == null) return "";
        return text.length() <= length ? text : text.substring(0, length - 3) + "...";
    }

    // ===== GETTER METHODS  =====
    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getVendor() {
        return vendor;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }
}