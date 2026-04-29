package org.example;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // ===== MAIN APPLICATION LOOP =====
        while (true) {

            UI.printHeader();

            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");
            System.out.print("Select: ");

            String choice = scanner.nextLine().toUpperCase();

            switch (choice) {

                case "D":
                    addTransaction(scanner, true);
                    break;

                case "P":
                    addTransaction(scanner, false);
                    break;

                case "L":
                    ledgerMenu(scanner);
                    break;

                case "X":
                    UI.success("Thanks for using the app. See you next time!");
                    return;

                default:
                    UI.error("Invalid option");
            }
        }
    }

    // ===== ADD NEW TRANSACTION (DEPOSIT OR PAYMENT) =====
    public static void addTransaction(Scanner scanner, boolean isDeposit) {

        System.out.print("Description: ");
        String description = scanner.nextLine();

        System.out.print("Vendor: ");
        String vendor = scanner.nextLine();

        System.out.print("Amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        // Ensure payments are stored as negative values
        if (!isDeposit) {
            amount = -Math.abs(amount);
        }

        // Create transaction object with current date and time
        Transaction t = new Transaction(
                LocalDate.now(),
                LocalTime.now().withNano(0),
                description,
                vendor,
                amount
        );

        FileHandler.saveTransaction(t);
        UI.success("Transaction saved.");
    }

    // ===== LEDGER MENU (VIEW TRANSACTIONS) =====
    public static void ledgerMenu(Scanner scanner) {

        while (true) {

            UI.title("LEDGER");

            System.out.println("A) All");
            System.out.println("D) Deposits");
            System.out.println("P) Payments");
            System.out.println("R) Reports");
            System.out.println("H) Home");
            System.out.print("Select: ");

            String choice = scanner.nextLine().toUpperCase();

            ArrayList<Transaction> list = Ledger.getAllTransactions();
            Collections.reverse(list); // Show newest first

            switch (choice) {

                case "A":
                    printLedger(list);
                    break;

                case "D":
                    ArrayList<Transaction> deposits = new ArrayList<>();
                    for (Transaction t : list) {
                        if (t.getAmount() > 0) deposits.add(t);
                    }
                    printLedger(deposits);
                    break;

                case "P":
                    ArrayList<Transaction> payments = new ArrayList<>();
                    for (Transaction t : list) {
                        if (t.getAmount() < 0) payments.add(t);
                    }
                    printLedger(payments);
                    break;

                case "R":
                    reports(scanner, list);
                    break;

                case "H":
                    return;

                default:
                    UI.error("Invalid option");
            }
        }
    }

    // ===== DISPLAY LEDGER IN TABLE FORMAT =====
    public static void printLedger(ArrayList<Transaction> list) {

        System.out.println("--------------------------------------------------------------------------");
        System.out.println("| Date       | Time  | Description            | Vendor        | Amount    |");
        System.out.println("---------------------------------------------------------------------------");

        double total = 0;

        for (Transaction t : list) {
            System.out.println(t);
            total += t.getAmount();
        }

        System.out.println("---------------------------------------------------------------------------");
        System.out.printf("TOTAL BALANCE: $%.2f%n", total);
    }

    // ===== REPORTING MODULE (FILTER TRANSACTIONS) =====
    public static void reports(Scanner scanner, ArrayList<Transaction> list) {

        UI.title("REPORTS");

        System.out.println("1) Month To Date");
        System.out.println("2) Year To Date");
        System.out.println("3) Search by Vendor");
        System.out.println("4) Previous Month");
        System.out.println("5) Previous Year");
        System.out.println("0) Back");
        System.out.print("Select: ");

        String choice = scanner.nextLine();
        LocalDate today = LocalDate.now();

        switch (choice) {

            // ===== MONTH TO DATE TRANSACTIONS =====
            case "1":
                ArrayList<Transaction> month = new ArrayList<>();
                for (Transaction t : list) {
                    if (t.getDate().getYear() == today.getYear()
                            && t.getDate().getMonth() == today.getMonth()) {
                        month.add(t);
                    }
                }
                printLedger(month);
                break;

            // ===== YEAR TO DATE TRANSACTIONS =====
            case "2":
                ArrayList<Transaction> year = new ArrayList<>();
                for (Transaction t : list) {
                    if (t.getDate().getYear() == today.getYear()) {
                        year.add(t);
                    }
                }
                printLedger(year);
                break;

            // ===== SEARCH TRANSACTIONS BY VENDOR OR DESCRIPTION =====
            case "3":
                System.out.print("Enter vendor/keyword: ");
                String v = scanner.nextLine().toLowerCase();

                ArrayList<Transaction> search = new ArrayList<>();

                for (Transaction t : list) {
                    if (t.getVendor().toLowerCase().contains(v)
                            || t.getDescription().toLowerCase().contains(v)) {
                        search.add(t);
                    }
                }

                if (search.isEmpty()) {
                    UI.error("No transactions found");
                } else {
                    printLedger(search);
                }
                break;

            // ===== PREVIOUS MONTH TRANSACTIONS =====
            case "4":
                LocalDate firstDayLastMonth = today.minusMonths(1).withDayOfMonth(1);
                LocalDate lastDayLastMonth = today.withDayOfMonth(1).minusDays(1);

                ArrayList<Transaction> prevMonth = new ArrayList<>();

                for (Transaction t : list) {
                    if (!t.getDate().isBefore(firstDayLastMonth)
                            && !t.getDate().isAfter(lastDayLastMonth)) {
                        prevMonth.add(t);
                    }
                }

                printLedger(prevMonth);
                break;

            // ===== PREVIOUS YEAR TRANSACTIONS =====
            case "5":
                int lastYear = today.getYear() - 1;

                ArrayList<Transaction> prevYear = new ArrayList<>();

                for (Transaction t : list) {
                    if (t.getDate().getYear() == lastYear) {
                        prevYear.add(t);
                    }
                }

                printLedger(prevYear);
                break;

            case "0":
                return;

            default:
                UI.error("Invalid option");
        }
    }
}