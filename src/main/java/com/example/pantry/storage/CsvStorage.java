package com.example.pantry.storage;

import com.example.pantry.model.PantryItem;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Stores pantry items in a simple CSV file:
 * id,name,category,quantity,expiryDate
 */
public class CsvStorage {
    private final String filename;

    public CsvStorage(String filename) {
        this.filename = filename;
    }

    public List<PantryItem> load() {
        File f = new File(filename);
        if (!f.exists()) return new ArrayList<>();

        List<PantryItem> items = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                // Basic CSV (no quoted commas for simplicity)
                String[] parts = line.split(",", -1);
                if (parts.length != 5) continue;

                String id = parts[0];
                String name = parts[1];
                String category = parts[2];
                int qty = Integer.parseInt(parts[3]);
                LocalDate expiry = LocalDate.parse(parts[4]);

                items.add(new PantryItem(id, name, category, qty, expiry));
            }
        } catch (Exception e) {
            System.out.println("Warning: Could not fully load file: " + e.getMessage());
        }
        return items;
    }

    public void save(List<PantryItem> items) {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), StandardCharsets.UTF_8))) {
            for (PantryItem i : items) {
                String line = String.join(",",
                        i.getId(),
                        sanitize(i.getName()),
                        sanitize(i.getCategory()),
                        String.valueOf(i.getQuantity()),
                        i.getExpiryDate().toString()
                );
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    private String sanitize(String s) {
        // Keep it simple: replace commas to protect CSV structure
        return s.replace(",", " ");
    }
}
