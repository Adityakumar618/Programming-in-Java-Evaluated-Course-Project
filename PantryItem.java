package com.example.pantry.model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents a single pantry item.
 */
public class PantryItem {
    private final String id;
    private String name;
    private String category;
    private int quantity;
    private LocalDate expiryDate;

    public PantryItem(String id, String name, String category, int quantity, LocalDate expiryDate) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        this.category = Objects.requireNonNull(category);
        this.quantity = quantity;
        this.expiryDate = Objects.requireNonNull(expiryDate);
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public int getQuantity() { return quantity; }
    public LocalDate getExpiryDate() { return expiryDate; }

    public void setName(String name) { this.name = Objects.requireNonNull(name); }
    public void setCategory(String category) { this.category = Objects.requireNonNull(category); }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = Objects.requireNonNull(expiryDate); }

    @Override
    public String toString() {
        return String.format("ID=%s | %s | category=%s | qty=%d | expiry=%s",
                id, name, category, quantity, expiryDate);
    }
}
