📚 Business Context (MVP)

📌 Overview

The Library Management System MVP is designed to simulate the core operations of a small library.

The system allows librarians to manage books and users, and to handle the process of borrowing and returning books in a clear and controlled way.

This MVP focuses on:

Core object-oriented design principles

Clear business rules for borrowing and returning books

Deterministic and predictable system behavior

Clean and maintainable Java architecture

Console-based interaction (no GUI or web interface)

The application is delivered as a standalone Java console application.


🎯 Business Goal

Provide a simple and reliable system that allows a small library to:

Register books in the system

Register library users

Allow users to borrow available books

Allow users to return borrowed books

Display current library state (books and users)

Ensure that:

A book cannot be borrowed if it is already borrowed

A user cannot return a book they did not borrow

The system state is always consistent


🧩 Key Features

1️⃣ Book Management

Each book in the system contains:

Unique identifier (ID)

Title

Author

Availability status

The system allows:

Adding new books

Viewing all books

Viewing only available books

Books are stored in memory using Java collections.


2️⃣ User Management

Each user contains:

Unique identifier (ID)

Name

List of currently borrowed books

The system allows:

Registering new users

Viewing registered users

Tracking borrowed books per user


3️⃣ Borrowing Logic

The borrowing process follows strict business rules:

A book must exist in the system

A user must exist in the system

The book must be available

Once borrowed:

Book availability is set to false

The book is added to the user’s borrowed list

If any condition fails, the operation is rejected.


4️⃣ Returning Logic

The returning process ensures:

The book exists

The user exists

The user has actually borrowed the book

Once returned:

The book availability is set to true

The book is removed from the user’s borrowed list

Invalid return attempts are rejected.


5️⃣ Console Interface

The system provides a simple console-based menu:

Add book

Register user

Borrow book

Return book

Display books

Display users

Exit

The interface is designed for clarity and learning purposes.


🚦 Scope

✅ In Scope (MVP)

Pure Java (no frameworks required)

Object-oriented design (classes, encapsulation)

In-memory data storage (List / Map)

Basic validation and error handling

Console-based interaction

Maven or Gradle build configuration

README with run instructions


❌ Out of Scope (MVP)

Database persistence

REST API

Spring Boot

Authentication / Authorization

GUI or web interface

Advanced search or filtering

File storage

Multithreading


💡 Key Assumptions

The system is used by a single operator (librarian)

Data is stored only in memory and is lost after application shutdown

IDs are generated internally by the system

The system runs locally as a standalone Java application

Concurrency is not required

⚠ Exceptional Scenarios Handling

The application handles the following cases:

Borrowing a non-existing book → operation rejected

Borrowing an already borrowed book → operation rejected

Returning a book not borrowed by the user → operation rejected

Registering a duplicate ID → prevented internally

Invalid user input → error message displayed

All undefined exceptional scenarios are handled with defensive programming principles to maintain system consistency.


🏗 Architectural Context

Java 17 (or Java 11+)

Console-based application

Layered structure:

Main (application entry point)

Library (core service logic)

Book (domain model)

User (domain model)

Key concepts demonstrated:

Object-Oriented Programming (OOP)

Encapsulation

Separation of responsibilities

Basic collection handling (List / Map)

Simple business rule enforcement


✅ Summary

This document defines the context and purpose of the Library Management System MVP.

The MVP focuses on:

Clean object-oriented design

Deterministic borrowing and returning logic

Clear separation of concerns

Readable and maintainable code

The system provides a solid foundation for future improvements such as:

Database integration (JPA / Hibernate)

REST API with Spring Boot

User authentication

Due dates and penalties

File persistence

GUI or web frontend