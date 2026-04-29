package org.example;

public class UI {

        // ===== ANSI COLOR CONSTANTS (TERMINAL OUTPUT STYLING) =====
        public static final String RESET = "\u001B[0m";
        public static final String GREEN = "\u001B[32m";
        public static final String RED = "\u001B[31m";
        public static final String BLUE = "\u001B[34m";
        public static final String YELLOW = "\u001B[33m";
        public static final String CYAN = "\u001B[36m";
        public static final String PURPLE = "\u001B[35m";

        // ===== APPLICATION HEADER DISPLAY =====
        public static void printHeader() {
                System.out.println(CYAN + "====================================");
                System.out.println("   ACCOUNTING LEDGER APPLICATION");
                System.out.println("====================================" + RESET);
        }

        // ===== SECTION TITLE DISPLAY =====
        public static void title(String title) {
                System.out.println(CYAN + "\n===== " + title + " =====" + RESET);
        }

        // ===== SUCCESS MESSAGE OUTPUT =====
        public static void success(String message) {
                System.out.println(GREEN + "✔ " + message + RESET);
        }

        // ===== ERROR MESSAGE OUTPUT =====
        public static void error(String message) {
                System.out.println(RED + " " + message + RESET);
        }
}