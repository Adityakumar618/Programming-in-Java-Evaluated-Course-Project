package com.example.pantry;

import com.example.pantry.service.PantryService;
import com.example.pantry.storage.CsvStorage;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class App {
    private static final String DATA_FILE = "pantry-items.csv";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        CsvStorage storage = new CsvStorage(DATA_FILE);
        PantryService service = new PantryService(storage);

        // Load existing items if present
        service.load();

        System.out.println("=== Pantry Tracker (Food Waste Helper) ===");

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt(sc, "Choose an option: ");

            switch (choice) {
                case 1 -> addItem(sc, service);
                case 2 -> service.listAll();
                case 3 -> removeItem(sc, service);
                case 4 -> updateQuantity(sc, service);
                case 5 -> listExpiringSoon(sc, service);
                case 6 -> listExpired(service);
                case 7 -> search(sc, service);
                case 8 -> {
                    service.save();
                    System.out.println("Saved. Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
            System.out.println();
        }

        sc.close();
    }

    private static void printMenu() {
        System.out.println("""
                ----------------------------
                1) Add item
                2) List all items
                3) Remove item
                4) Update quantity
                5) Show items expiring soon
                6) Show expired items
                7) Search by name/category
                8) Save & Exit
                ----------------------------
                """);
    }

    private static void addItem(Scanner sc, PantryService service) {
        System.out.println("Add Item");
        String name = readNonEmpty(sc, "Name: ");
        String category = readNonEmpty(sc, "Category (e.g., dairy, grains): ");
        int quantity = readInt(sc, "Quantity (>= 1): ");
        if (quantity < 1) {
            System.out.println("Quantity must be at least 1.");
            return;
        }

        LocalDate expiry = readDate(sc, "Expiry date (YYYY-MM-DD): ");
        service.addItem(name, category, quantity, expiry);
        System.out.println("Item added.");
    }

    private static void removeItem(Scanner sc, PantryService service) {
        System.out.println("Remove Item");
        String id = readNonEmpty(sc, "Enter item ID to remove: ");
        boolean ok = service.removeById(id);
        System.out.println(ok ? "Removed." : "No item found with that ID.");
    }

    private static void updateQuantity(Scanner sc, PantryService service) {
        System.out.println("Update Quantity");
        String id = readNonEmpty(sc, "Enter item ID: ");
        int newQty = readInt(sc, "New quantity (>= 0): ");
        if (newQty < 0) {
            System.out.println("Quantity cannot be negative.");
            return;
        }
        boolean ok = service.updateQuantity(id, newQty);
        System.out.println(ok ? "Updated." : "No item found with that ID.");
    }

    private static void listExpiringSoon(Scanner sc, PantryService service) {
        int days = readInt(sc, "Expiring within how many days? (e.g., 7): ");
        if (days < 0) {
            System.out.println("Days cannot be negative.");
            return;
        }
        service.listExpiringWithin(days);
    }

    private static void listExpired(PantryService service) {
        service.listExpired();
    }

    private static void search(Scanner sc, PantryService service) {
        String term = readNonEmpty(sc, "Search term (name/category): ");
        service.search(term);
    }

    private static int readInt(Scanner sc, String prompt) {
        System.out.print(prompt);
        String line = sc.nextLine().trim();
        try {
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static String readNonEmpty(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            if (!s.isEmpty()) return s;
            System.out.println("Value cannot be empty.");
        }
    }

    private static LocalDate readDate(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            try {
                return LocalDate.parse(s);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Use YYYY-MM-DD (example: 2026-03-26).");
            }
        }
    }
}
