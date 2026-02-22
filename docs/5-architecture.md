🏗️ Library Management System – Architecture (MVP)

📌 Overview

The application is built as a standalone Java console application following layered architecture principles.

It:

Runs locally in a single JVM process

Uses in-memory collections (no database)

Applies deterministic borrowing and returning logic

Focuses on clean object-oriented design

Separates domain logic from user interaction

The system prioritizes:

Clear separation of responsibilities

Maintainability

Testability

Simplicity suitable for a first project

No REST API or persistence layer is included in the MVP.


⚙️ Architectural Style

Monolithic console application (single executable program)

Layered architecture

In-memory data storage

Synchronous execution flow

Clear separation between:

Presentation layer (console interaction)

Service layer (business logic)

Domain layer (core models)

🏛️ Application Layers

1️⃣ Presentation Layer (Console Layer)
Responsibility:

Display menu options

Read user input

Validate basic input format

Delegate operations to service layer

Display results or error messages

Key Component:

Main class (entry point)

Rules:

Must not contain business logic

Must not manipulate collections directly

Only coordinates input → service → output


2️⃣ Service Layer

Responsibility:

Implement business rules

Coordinate operations between domain objects

Maintain system consistency

Manage collections of books and users

Key Component:

Library class

Core Responsibilities:

Add new book

Register user

Borrow book

Return book

Retrieve lists of books and users

Rules:

All business logic lives here

All validations happen here

Must guarantee consistent state

Fully unit-testable (without console)


3️⃣ Domain Layer

Responsibility:

Represent core business concepts.

Key Models:

Book

User

Book Responsibilities:

Store book data

Maintain availability state

User Responsibilities:

Store user data

Track borrowed books

Rules:

No console logic

No knowledge of UI

Encapsulated fields (private + getters)

State modifications controlled by service layer


4️⃣ Exception Handling Layer (Optional but Recommended)

Responsibility:

Represent domain errors

Improve code readability

Separate error handling from business flow

Example Exceptions:

BookNotFoundException

UserNotFoundException

BookAlreadyBorrowedException

InvalidOperationException

Design Principle:

Business errors are represented explicitly rather than using generic exceptions.

For MVP, simple error messages are acceptable if custom exceptions are not implemented.


🔄 Operation Flow Example – Borrow Book

User selects "Borrow book" in console

Main collects:

userId

bookId

Library.borrowBook(userId, bookId) is invoked

Service layer:

Finds user

Finds book

Validates availability

Updates book.available → false

Adds book to user.borrowedBooks

Result message returned to console

Console prints success or error message


🧠 Design Principles Applied

Single Responsibility Principle

Encapsulation

Separation of concerns

Deterministic business logic

Defensive programming

Controllers (console) contain zero business logic.
Domain objects do not manage collections globally.
Service layer orchestrates all state changes.


🧪 Testing Strategy

Unit tests should target:

Borrowing logic

Returning logic

Validation rules

State consistency

Service layer (Library) can be tested independently of console input.

Example test scenarios:

Borrow available book → success

Borrow already borrowed book → failure

Return book not borrowed → failure

Register user → ID uniqueness ensured


✨ Extensibility

The architecture allows easy future extension:

Add REST API (Spring Boot layer on top)

Add database persistence (JPA entities)

Introduce Borrow entity with due dates

Add maximum borrow limit per user

Add search and filtering logic

Add file-based persistence

Introduce authentication layer

Because domain and service layers are separated from console logic, migrating to REST will require minimal refactoring.


🗝️ Key Design Decisions

No database in MVP (in-memory design)

Sequential ID generation

Borrow relationship modeled via object references

Single service class responsible for system coordination

No multithreading (single-user assumption)

No external dependencies required


🧱 High-Level Component Diagram (Conceptual)

User (Console Input)
↓
Main (Presentation Layer)
↓
Library (Service Layer)
↓
Book / User (Domain Layer)
↓
In-Memory Collections


✅ Summary

The architecture ensures:

Clean separation of responsibilities

Simple and understandable structure

Deterministic and consistent state management

High testability of business logic

Easy migration path to REST and database

The MVP architecture is intentionally simple but professionally structured, providing a strong foundation for future backend development using frameworks such as Spring Boot.