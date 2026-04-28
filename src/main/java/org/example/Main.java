package org.example;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        while (true) {

            printMenu();

            String choice = scanner.nextLine().toUpperCase();

            switch (choice) {

                case "D":
                    addDeposit(scanner);
                    break;

                case "P":
                    makePayment(scanner);
                    break;

                case "L":
                    showLedger();
                    break;

                case "X":
                    System.out.println("Goodbye!");
                    return;

                default:
                    System.out.println("Invalid option");
            }
        }
    }

    // ================= MENU =================
    public static void printMenu() {
        System.out.println("\n====================================");
        System.out.println("      ACCOUNTING LEDGER APP");
        System.out.println("====================================");
        System.out.println("D) Add Deposit");
        System.out.println("P) Make Payment");
        System.out.println("L) Ledger");
        System.out.println("X) Exit");
        System.out.print("Select: ");
    }

    // ================= DEPOSIT =================
    public static void addDeposit(Scanner scanner) {

        System.out.print("Description: ");
        String description = scanner.nextLine();

        System.out.print("Vendor: ");
        String vendor = scanner.nextLine();

        System.out.print("Amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        Transaction t = new Transaction(
                LocalDate.now().toString(),
                LocalTime.now().toString(),
                description,
                vendor,
                amount
        );

        FileHandler.saveTransaction(t);

        System.out.println("Deposit saved.");
    }

    // ================= PAYMENT =================
    public static void makePayment(Scanner scanner) {

        System.out.print("Description: ");
        String description = scanner.nextLine();

        System.out.print("Vendor: ");
        String vendor = scanner.nextLine();

        System.out.print("Amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        Transaction t = new Transaction(
                LocalDate.now().toString(),
                LocalTime.now().toString(),
                description,
                vendor,
                -amount
        );

        FileHandler.saveTransaction(t);

        System.out.println("Payment saved.");
    }

    // ================= LEDGER =================
    public static void showLedger() {

        ArrayList<Transaction> list = Ledger.getAllTransactions();

        Collections.reverse(list);

        System.out.println("\n===== LEDGER =====");

        for (Transaction t : list) {
            System.out.println(t);
        }
    }
}