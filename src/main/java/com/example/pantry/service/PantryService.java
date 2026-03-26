package com.example.pantry.service;

import com.example.pantry.model.PantryItem;
import com.example.pantry.storage.CsvStorage;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class PantryService {
    private final CsvStorage storage;
    private final List<PantryItem> items = new ArrayList<>();

    public PantryService(CsvStorage storage) {
        this.storage = storage;
    }

    public void load() {
        items.clear();
        items.addAll(storage.load());
    }

    public void save() {
        storage.save(items);
    }

    public void addItem(String name, String category, int quantity, LocalDate expiryDate) {
        String id = generateId(name, category, expiryDate);
        items.add(new PantryItem(id, name, category, quantity, expiryDate));
        sortByExpiry();
    }

    public boolean removeById(String id) {
        return items.removeIf(i -> i.getId().equalsIgnoreCase(id));
    }

    public boolean updateQuantity(String id, int newQty) {
        PantryItem item = findById(id);
        if (item == null) return false;
        item.setQuantity(newQty);
        return true;
    }

    public void listAll() {
        if (items.isEmpty()) {
            System.out.println("No items found.");
            return;
        }
        sortByExpiry();
        System.out.println("All items (sorted by expiry):");
        items.forEach(System.out::println);
    }

    public void listExpiringWithin(int days) {
        LocalDate today = LocalDate.now();
        LocalDate cutoff = today.plusDays(days);

        List<PantryItem> result = items.stream()
                .filter(i -> !i.getExpiryDate().isBefore(today)) // not expired
                .filter(i -> !i.getExpiryDate().isAfter(cutoff))
                .sorted(Comparator.comparing(PantryItem::getExpiryDate))
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            System.out.println("No items expiring within " + days + " day(s).");
            return;
        }

        System.out.println("Items expiring within " + days + " day(s):");
        result.forEach(System.out::println);
    }

    public void listExpired() {
        LocalDate today = LocalDate.now();
        List<PantryItem> result = items.stream()
                .filter(i -> i.getExpiryDate().isBefore(today))
                .sorted(Comparator.comparing(PantryItem::getExpiryDate))
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            System.out.println("No expired items.");
            return;
        }

        System.out.println("Expired items:");
        result.forEach(System.out::println);
    }

    public void search(String term) {
        String t = term.toLowerCase(Locale.ROOT);

        List<PantryItem> result = items.stream()
                .filter(i -> i.getName().toLowerCase(Locale.ROOT).contains(t)
                        || i.getCategory().toLowerCase(Locale.ROOT).contains(t))
                .sorted(Comparator.comparing(PantryItem::getExpiryDate))
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            System.out.println("No matches for: " + term);
            return;
        }

        System.out.println("Search results:");
        result.forEach(System.out::println);
    }

    private PantryItem findById(String id) {
        for (PantryItem i : items) {
            if (i.getId().equalsIgnoreCase(id)) return i;
        }
        return null;
    }

    private void sortByExpiry() {
        items.sort(Comparator.comparing(PantryItem::getExpiryDate));
    }

    private String generateId(String name, String category, LocalDate expiryDate) {
        // readable + mostly unique without extra libraries
        String base = (name + "-" + category).toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("^-|-$", "");

        String date = expiryDate.toString(); // YYYY-MM-DD
        String rand = String.valueOf((int)(Math.random() * 9000) + 1000);

        return base + "-" + date + "-" + rand;
    }
}
