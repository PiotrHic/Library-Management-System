📚 Library Management System – API Documentation (MVP)

📌 Overview

This document describes the application interface for the Library Management System MVP.

The MVP is implemented as a console-based Java application.

There is:

No REST API

No HTTP endpoints

No JSON communication

No external integrations

The system is fully local and interacts with the user via terminal input/output.

All data is stored in memory and lost when the application terminates.


🖥 Application Interface

Main Menu

When the application starts, the user is presented with a console menu:

1. Add new book
2. View all books
3. View available books
4. Register new user
5. View users
6. Borrow book
7. Return book
8. Exit

User selects an option by entering a number.


📖 Book Operations

1. Add New Book

Input Required:

Title

Author

System Behavior:

Generates unique ID

Sets availability to true

Stores book in memory

Output Example:

Book added successfully.
ID: 3

2. View All Books

System Behavior:

Displays full list of books

Output Example:

ID: 1 | Title: Clean Code | Author: Robert C. Martin | Available: true
ID: 2 | Title: Effective Java | Author: Joshua Bloch | Available: false


3. View Available Books

Displays only books where available = true.

👤 User Operations


4. Register New User

Input Required:

User name

System Behavior:

Generates unique ID

Creates empty borrowed books list

Output Example:

User registered successfully.
ID: 2

5. View Users

Displays all registered users.

Output Example:

ID: 1 | Name: Anna | Borrowed books: 1
ID: 2 | Name: Tom | Borrowed books: 0

🔄 Borrowing & Returning

6. Borrow Book

Input Required:

User ID

Book ID

Success Output:

Book borrowed successfully.

Failure Cases:

Book not found

User not found

Book already borrowed

Example:

Error: Book is already borrowed.


7. Return Book

Input Required:

User ID

Book ID

Success Output:

Book returned successfully.

Failure Cases:

Book not found

User not found

Book not borrowed by this user

⚠ Error Handling

The system handles invalid input cases such as:

Non-numeric ID values

Non-existing book or user

Borrowing already borrowed book

Returning book not borrowed

Errors are displayed as console messages.

No exceptions are exposed to the end user.


🔒 Security Rules

No authentication required

Single-operator system

No role-based access control

No data encryption

Local execution only

⚙ System Constraints

Java 11 or higher

Console-based interaction

In-memory data storage

No frameworks required

No external APIs

Maven or Gradle build system


✅ Summary

The Library Management System MVP does not expose a REST API.

It provides:

Console-based interaction

Deterministic and rule-based borrowing logic

In-memory data handling

Clear and consistent output formatting

The system is:

Simple

Educational

Extensible

Future extensions may include:

REST API (Spring Boot)

Database persistence

Authentication

Due dates and penalties

Frontend integration