# Pantry Tracker (Food Waste Helper) — Java Console App

A simple, well-organized Java console application that helps reduce food waste by tracking pantry items, quantities, and expiry dates. It can list items, search, show expiring-soon items, show expired items, and persist data to a CSV file.

## Why this project?
Food waste is a real daily-life problem. People often forget what they bought and items expire unnoticed. This app supports better decisions: *use items that expire soon first*.

## Features
- Add pantry items (name, category, quantity, expiry date)
- List all items (sorted by expiry date)
- Update quantity
- Remove items by ID
- Search by name or category
- Show expired items
- Show items expiring within N days
- Saves/loads data from `pantry-items.csv`

## Tech / Course Concepts Used
- OOP (classes, encapsulation, separation of concerns)
- Collections (`ArrayList`, sorting, filtering)
- File I/O (CSV persistence)
- Input validation & menu-driven UI
- Clean package structure (`model`, `service`, `storage`)

## How to Run (No Maven/Gradle required)
### Option A: Using terminal (recommended)
From the project root:

```bash
javac -d out src/main/java/com/example/pantry/App.java src/main/java/com/example/pantry/model/PantryItem.java src/main/java/com/example/pantry/service/PantryService.java src/main/java/com/example/pantry/storage/CsvStorage.java
java -cp out com.example.pantry.App
```

### Option B: Using VS Code / IntelliJ
Open the folder and run `App.java`.

## Data File
The app creates/uses a file in the project folder:
- `pantry-items.csv`

Format:
`id,name,category,quantity,expiryDate`

## Example Usage
- Add: `Milk`, category `dairy`, qty `2`, expiry `2026-04-02`
- Show expiring within `7` days to plan meals.

## Future Improvements
- Support multiple households/users
- Better CSV quoting rules
- Notifications / email reminders
- Export shopping list
- GUI version (JavaFX)
