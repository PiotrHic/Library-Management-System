🚀 Library Management System – Implementation Plan (MVP)

📌 Overview

This document defines the step-by-step implementation plan for the Library Management System MVP.

It covers:

Coding order

Layer separation

Business rule implementation

Testing strategy

Clean object-oriented structure

The system focuses on:

Book registration

User registration

Borrowing and returning logic

Deterministic state management

In-memory storage

No database and no REST API are included in MVP.

🛠️ Step 1 – Project Setup

Create a standard Java project:

Java 11+ (recommended Java 17)

Choose build tool:

Maven
or

Gradle

Project structure:

src/main/java
src/test/java

Add dependencies:

JUnit 5 (testing)

AssertJ (optional)

No external frameworks required.

Ensure project builds and runs with:

mvn clean install

or

gradle build

🛠️ Step 2 – Define Package Structure
com.example.library
├── app
│   └── Main.java
├── domain
│   ├── model
│   │   ├── Book.java
│   │   └── User.java
│   ├── service
│   │   └── Library.java
├── exception (optional)
└── l origin develop (optional)

Ensure strict separation between:

Presentation layer (console)

Service layer (business logic)

Domain models

🛠️ Step 3 – Implement Domain Models
Implement Book

Fields:

id (Long)

title (String)

author (String)

available (boolean)

Rules:

ID immutable after creation

Title and author required

Default available = true

Implement User

Fields:

id (Long)

name (String)

borrowedBooks (List<Book>)

Rules:

Name required

borrowedBooks initialized as empty list

Encapsulated access to collection


🛠️ Step 4 – Implement Service Layer

Implement Library class.

Responsibilities:

Store books (List<Book>)

Store users (List<User>)

Generate unique IDs

Enforce business rules

Methods:

addBook(title, author)

registerUser(name)

borrowBook(userId, bookId)

returnBook(userId, bookId)

getAllBooks()

getAllUsers()

Ensure:

All validations inside service

No partial updates

Deterministic behavior


🛠️ Step 5 – Implement Borrowing Logic

Inside borrowBook:

Find user

Find book

Validate availability

Set book.available = false

Add book to user.borrowedBooks

Failure cases:

User not found

Book not found

Book already borrowed

State must remain unchanged on failure.


🛠️ Step 6 – Implement Returning Logic

Inside returnBook:

Find user

Find book

Validate that user borrowed the book

Remove book from borrowedBooks

Set book.available = true

Ensure atomic state update.


🛠️ Step 7 – Implement Console Layer

In Main:

Display menu

Read user input

Validate basic format (numeric IDs)

Call Library methods

Display results

Menu options:

Add book

Register user

Borrow book

Return book

Show books

Show users

Exit

Ensure:

No business logic in Main

Errors handled gracefully


🛠️ Step 8 – Exception Handling (Optional Enhancement)

Implement custom exceptions:

BookNotFoundException

UserNotFoundException

BookAlreadyBorrowedException

InvalidOperationException

Catch them in Main and display user-friendly messages.

Alternative (simpler MVP):

Use validation + printed error messages.


🛠️ Step 9 – Unit Testing

Create tests for Library class.

Test categories:

Book Registration

Valid book → added

Empty title → rejected

Unique ID generation

User Registration

Valid user → added

Empty name → rejected

Borrowing

Success scenario

Borrow already borrowed book

Borrow non-existing book

Borrow non-existing user

Returning

Success scenario

Return book not borrowed

Return non-existing book

Return non-existing user

Ensure:

State unchanged on failure

Availability flag consistent


🛠️ Step 10 – Integration-Style Flow Tests

Simulate real scenario:

Register user

Add book

Borrow book

Return book

Validate final state

Test full lifecycle correctness.


🛠️ Step 11 – Documentation

Prepare:

README.md

business-context.md

use-cases.md

data-model.md

architecture.md

business-rules.md

tests.md

implementation-plan.md

README must include:

Project description

How to run

Java version

Example console interaction


📌 Key Notes

Follow layered structure:

Console → Service → Domain

No logic inside domain models except state representation.

All business rules enforced in service.

Collections must remain consistent at all times.

IDs generated internally (e.g., incremental counter).

⚙️ Recommended Order of Implementation

Project setup

Domain models

Service class (without console)

Unit tests for service

Borrow/return validation

Console layer

Exception improvements

Final refactoring

Documentation


🧪 MVP Completion Criteria

The MVP is complete when:

Books can be added

Users can be registered

Borrowing works correctly

Returning works correctly

Invalid operations are rejected

System state remains consistent

Unit tests pass

Project builds successfully

Documentation is complete


🚀 Future Upgrade Path (Phase 2)

After MVP completion, the system can evolve into:

Spring Boot REST API

Database persistence (JPA)

Borrow entity with due dates

Borrow limit per user

Authentication layer

Search and filtering

Dockerized deployment


✅ Summary

The implementation plan ensures:

Clean coding order

Clear separation of concerns

Deterministic and consistent logic

High testability

Professional project structure

The MVP remains:

Simple

Educational

Fully functional

Easy to extend