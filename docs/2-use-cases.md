📚 Use Cases (MVP)

📌 Overview

This document describes the core use cases for the Library Management System MVP.

The system is designed to simulate the core operations of a small local library using a console-based Java application.

The system focuses exclusively on:

Book registration and tracking

User registration

Borrowing and returning logic

Deterministic and rule-based operations

In-memory data management

No database, REST API, or graphical interface is included in the MVP.


📖 Book Management Use Cases

1. Add New Book

Description:
Register a new book in the library system.

Preconditions:

Title is provided

Author is provided

Application is running

Postconditions:

Book is added to the internal collection

Unique ID is assigned

Book availability is set to available = true

Business Rules:

Each book must have a unique identifier

Title and author cannot be empty

Book is available immediately after creation


2. View All Books

Description:
Display all books stored in the system.

Preconditions:

Application is running

Postconditions:

List of all books is displayed

Each book shows:

ID

Title

Author

Availability status

Business Rules:

Both available and borrowed books are displayed

Operation does not modify system state


3. View Available Books

Description:
Display only books that are currently available for borrowing.

Preconditions:

At least one book exists in the system

Postconditions:

Only books with available = true are displayed

Business Rules:

Filtering is based strictly on availability status

No state modification occurs

👤 User Management Use Cases

4. Register New User

Description:
Register a new user in the library system.

Preconditions:

User name is provided

Application is running

Postconditions:

User is added to the internal collection

Unique ID is assigned

User starts with an empty borrowed books list

Business Rules:

User must have a unique identifier

Name cannot be empty


5. View Registered Users

Description:
Display all registered users.

Preconditions:

At least one user exists in the system

Postconditions:

List of users is displayed

For each user:

ID

Name

Number of borrowed books

Business Rules:

Operation does not modify system state


🔄 Borrowing & Returning Use Cases

6. Borrow Book

Description:
Allow a registered user to borrow an available book.

Preconditions:

User exists

Book exists

Book is available

Postconditions:

Book availability is set to false

Book is added to the user's borrowed books list

Business Rules:

A book cannot be borrowed if already borrowed

Borrowing fails if user or book does not exist

System state must remain consistent in case of failure


7. Return Book

Description:
Allow a user to return a previously borrowed book.

Preconditions:

User exists

Book exists

User has borrowed the book

Postconditions:

Book availability is set to true

Book is removed from the user's borrowed books list

Business Rules:

User cannot return a book they did not borrow

Operation fails if book or user does not exist

System consistency must be preserved

⚠ Exceptional Scenario Use Cases

8. Handle Invalid Book ID

Description:
Attempt to borrow or return a book with a non-existing ID.

Preconditions:

Provided book ID does not exist

Postconditions:

Operation is rejected

Error message is displayed

No state modification occurs

9. Handle Invalid User ID

Description:
Attempt to borrow or return a book with a non-existing user.

Preconditions:

Provided user ID does not exist

Postconditions:

Operation is rejected

Error message is displayed

No state modification occurs


10. Handle Borrowing Already Borrowed Book

Description:
User attempts to borrow a book that is already borrowed.

Preconditions:

Book exists

Book availability is false

Postconditions:

Operation is rejected

Error message is displayed

No state modification occurs


11. Handle Returning Book Not Borrowed

Description:
User attempts to return a book they did not borrow.

Preconditions:

Book exists

User exists

Book is not in user's borrowed list

Postconditions:

Operation is rejected

Error message is displayed

System state remains unchanged


🔒 Security & Access

No authentication is required in the MVP.

The system assumes:

Single operator usage (librarian)

No concurrent users

No role-based access control

⚙ System Constraints

Java 11 or higher

Console-based interaction

No external frameworks required

No database

In-memory storage using Java Collections (List / Map)

Build system: Maven or Gradle

README with build and run instructions is mandatory


✅ Summary

The MVP supports the following core operations:

Register books

Register users

Borrow books

Return books

Display system state

All use cases are:

Deterministic

Stateless within a single runtime session

Based on in-memory collections

Designed to demonstrate object-oriented programming principles

The system is intentionally minimal and educational, providing a clean foundation for future extensions such as:

Database integration

REST API layer

Due dates and penalties

File persistence

Role-based access

GUI or web frontend