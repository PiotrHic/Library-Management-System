📚 Library Management System – Data Model (MVP)


📌 Overview

The data model supports core library management functionality:

Book representation

User representation

Borrowing relationship handling

In-memory storage model

Deterministic borrowing and returning logic

Designed for MVP simplicity.

No database is required (in-memory collections only).

Clean separation between:

Domain models

Service logic

Console interaction layer

The application does not require persistence in MVP.
All data exists only during application runtime.


📖 Book (Domain Model)

Represents a book available in the library system.

Field	Type	Constraints	Description
id	Long	unique, not null	Unique book identifier
title	String	not null, not empty	Book title
author	String	not null, not empty	Book author
available	boolean	not null	Availability status
Business Meaning

available = true → Book can be borrowed

available = false → Book is currently borrowed

Design Notes

ID is generated internally by the system

Availability is controlled only through service logic

No direct modification from outside the domain/service layer


👤 User (Domain Model)

Represents a registered library user.

Field	Type	Constraints	Description
id	Long	unique, not null	Unique user identifier
name	String	not null, not empty	User name
borrowedBooks	List<Book>	not null	List of currently borrowed books
Business Meaning

A user may borrow multiple books

Borrowed books must reflect the availability status of each book

Design Notes

The borrowedBooks list must remain consistent with Book.available

A book may appear in only one user’s borrowed list at a time


🏛 Library (Domain Service / Aggregate Root)

Acts as the main service layer coordinating operations.

Field	Type	Description
books	List<Book>	All books in the system
users	List<User>	All registered users
Core Responsibilities

Add new books

Register users

Borrow books

Return books

Retrieve data for display

The Library class enforces all business rules.


🔄 Borrowing Relationship (Logical Model)

There is no separate Borrow entity in MVP.

The relationship is modeled implicitly:

User
↓ (contains)
List<Book> borrowedBooks
↓
Book.available = false

Logical Constraints

A book can belong to at most one user’s borrowedBooks list

When borrowed:

Book.available → false

Book added to user.borrowedBooks

When returned:

Book.available → true

Book removed from user.borrowedBooks

System consistency must always be preserved.


⚙️ Business Rules (Domain Constraints)

Book must exist before borrowing

User must exist before borrowing

Book must be available to be borrowed

User can only return books they borrowed

ID uniqueness must be guaranteed

Title and name cannot be empty

All constraints are enforced inside the service layer (Library class).


🔗 Relationships

In MVP there are no persistent database relationships.

Logical model:

Library
↓
Books (1..)
↓
Users (1..)
↓
Borrowed Books (0..*)

Relationship type:

One-to-Many: User → Borrowed Books

Shared object reference: Book tracked globally in Library


⚠ Constraints

Data exists only in memory

No concurrency handling required

IDs generated sequentially (e.g., increment counter)

No duplicate object instances for same book

No persistence after application shutdown


📝 Design Guidelines

No @Entity annotations (no database)

Encapsulation enforced via private fields

Business logic placed in service layer (Library)

Domain models should not handle console input/output

Avoid exposing internal collections directly (return copies if needed)

Follow SOLID principles

Optional improvement:

Make Book ID immutable

Use unmodifiable lists when exposing borrowedBooks


🗂️ Suggested Package Structure
com.example.library
├── app
│   └── Main.java
├── domain
│   ├── model
│   │   ├── Book.java
│   │   └── User.java
│   ├── service
│   │   └── Library.java
├── exception
│   ├── BookNotFoundException.java
│   ├── UserNotFoundException.java
│   └── BookAlreadyBorrowedException.java
└── util

Minimal version may omit custom exceptions and keep simple error handling.


🚀 Future Extension Possibilities

The data model is intentionally simple but allows:

Adding Borrow entity (with borrowDate, dueDate)

Adding database persistence (JPA entities)

Adding REST API layer

Adding maximum borrow limit per user

Adding late return penalties

Adding search and filtering capabilities

File-based storage

Introducing role-based users


✅ Summary

The MVP data model:

Is lightweight and in-memory

Clearly separates domain and service responsibilities

Models borrowing through controlled object references

Enforces deterministic business rules

Is easily extensible without architectural redesign

It provides a strong foundation for learning:

Object-Oriented Programming

Encapsulation

Responsibility separation

Collection management

Basic domain modeling