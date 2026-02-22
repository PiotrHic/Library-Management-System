🧪 Library Management System – Testing Strategy (MVP)

📌 Overview

This document defines the testing strategy for the Library Management System MVP.

It ensures correctness of:

Borrowing business logic

Returning business logic

Validation rules

State consistency

Error handling

The MVP is a console-based application with in-memory storage.

Testing focuses primarily on:

Service layer logic

Deterministic state transitions

Edge cases in borrowing and returning

No external integrations are involved in MVP.


🧩 Unit Tests

Service Layer
Purpose:

Validate core business rules in isolation from console interaction.

Target Class:

Library

The service layer must be fully testable without user input.

Example Test Scenarios

📖 Book Registration

Add valid book → book stored successfully

Add book with empty title → exception or rejection

Add book with empty author → exception or rejection

Generated IDs must be unique


👤 User Registration

Register valid user → success

Register user with empty name → rejected

User ID uniqueness ensured


🔄 Borrowing Logic
Successful Borrow

Book exists

User exists

Book is available
→ Borrow succeeds
→ Book.available = false
→ Book added to user's borrowedBooks

Borrow Failure Cases

Book does not exist → rejected

User does not exist → rejected

Book already borrowed → rejected


System state unchanged after failure


🔁 Returning Logic
Successful Return

Book exists

User exists

User borrowed the book
→ Return succeeds
→ Book.available = true
→ Book removed from user's borrowedBooks

Return Failure Cases

Book does not exist → rejected

User does not exist → rejected

User did not borrow the book → rejected

System state unchanged


📊 Consistency Tests

Borrowed book must not remain available

Available book must not exist in any borrowed list

Book cannot belong to two users simultaneously

Collections must not contain duplicates

Tools

JUnit 5

AssertJ (optional)

Mockito is not required in MVP (no external dependencies).

🖥 Presentation Layer Tests (Optional)

Console layer may be tested using:

Manual testing

Input simulation (advanced, optional)

Business logic must not depend on console input, so testing focus remains on service layer.


🔄 Integration Tests (Lightweight)

Although there is no REST layer, integration-style tests may validate:

Full flow scenario:

Register user

Add book

Borrow book

Return book

Verify final state

These tests simulate real usage flow in a controlled environment.


📊 Edge Case Testing

Special attention must be given to:

Borrowing the same book twice

Returning the same book twice

Borrowing with invalid IDs

Returning with invalid IDs

Empty system (no books or users)

Very large number of books (basic scalability test)

Boundary-like cases:

First inserted book

First inserted user

Sequential ID generation


⚠️ Error Handling Tests

The system must:

Reject invalid operations without crashing

Preserve system state on failure

Not expose stack traces to console

Example:

Attempt borrow of non-existing book
→ No exception escaping to main
→ Clean error message
→ Collections unchanged


🧠 Test Isolation Rules

Unit tests must:

Not depend on console input

Not share mutable state between tests

Create fresh Library instance per test

Each test should:

Set up its own data

Act

Assert

Clean up automatically


🔄 Continuous Testing

All tests should:

Run automatically via Maven or Gradle

Execute on every build

Fail build if business rule test fails

Critical logic that must be fully covered:

Borrow validation

Return validation

Availability changes

ID generation

State consistency


📈 Code Coverage Goals

High coverage required for:

Borrowing logic

Returning logic

Validation rules

Console layer coverage is less critical.


💡 Performance Considerations (Basic MVP)

Given the MVP constraints:

In-memory operations should execute instantly

No blocking operations

Linear search acceptable for small collections

Optional stress test:

Add 1000 books

Borrow/return operations still consistent


✅ Summary

The testing strategy ensures:

Strict validation of borrowing rules

Deterministic and predictable state transitions

Protection against inconsistent state

Clear rejection of invalid operations

Isolation of business logic from UI

The system is:

Highly testable

Independent of external systems

Deterministic

Safe for further extension

The MVP provides a solid foundation for adding:

REST API layer

Database integration

Advanced business rules

Concurrency handling