# Project Report — Pantry Tracker (Food Waste Helper)

## 1. Problem Statement
Food waste is common in daily life. Items are purchased and stored, but people forget they exist or don’t notice expiry dates, leading to expired food and unnecessary spending.

## 2. Why this problem is real (environment/community)
In homes, shared apartments, or small community pantries, items often go unused because there is no simple system to track expiry dates and quantities.

## 3. Solution Overview
This Java console application allows users to:
- store pantry items (name, category, quantity, expiry date)
- list items sorted by expiry
- find expired items and items expiring soon
- persist data using a CSV file so information is not lost

## 4. Course Concepts Applied
- Object-Oriented Programming:
  - `PantryItem` represents a real-world object
  - `PantryService` contains business logic
  - `CsvStorage` isolates file persistence concerns
- Collections:
  - items stored in an `ArrayList`
  - sorting/filtering to produce useful lists
- File I/O:
  - read/write CSV file `pantry-items.csv`
- Validation and control flow:
  - menu loop
  - safe handling of invalid input

## 5. Design Decisions
- Chose CSV for storage because it is human-readable and easy to debug.
- Separated packages (`model`, `service`, `storage`) to keep code organized and maintainable.

## 6. Challenges & What I Learned
(Write what you struggled with: parsing dates, validating inputs, saving/loading, sorting, etc.)

## 7. Testing
- Added items with different expiry dates and confirmed sorting works.
- Tested “expiring within N days” with N = 0, 7, 30.
- Verified persistence by restarting the program.

## 8. Limitations
- CSV parsing is simple (commas inside names are sanitized).
- No user accounts; single shared pantry list.

## 9. Future Work
- Better CSV handling
- Notifications
- GUI
- Unit tests with JUnit
