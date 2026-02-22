📋 Library Management System – Business Rules & Validation (MVP)


📌 Overview

This document defines the business rules and validation constraints for the Library Management System MVP.

The system ensures deterministic and transparent handling of:

Book registration

User registration

Borrowing logic

Returning logic

Validation is performed at:

Presentation layer (basic input format validation)

Service layer (business rule enforcement)

The MVP is:

Console-based

In-memory

Single-user

Stateless between application restarts

No authentication is required.


📖 Book Rules

Book Creation Rules
Required Fields

When adding a new book:

Title is mandatory

Author is mandatory

If any required field is missing or empty:

→ Operation is rejected
→ Error message is displayed

ID Rules

Each book must have a unique identifier

ID is generated internally by the system

ID cannot be modified after creation

Availability Rules

A newly created book is always available

Availability can only be changed via:

Borrow operation

Return operation

Manual modification is not allowed.


👤 User Rules
User Registration Rules

When registering a new user:

Name is mandatory

Name cannot be empty

If validation fails:

→ Operation is rejected

User ID Rules

Each user must have a unique identifier

ID is generated internally

ID cannot be modified

Borrowed Books Rules

A user may borrow multiple books

Borrowed books are tracked internally

Borrowed books list must always reflect actual system state


🔄 Borrowing Rules

Borrowing a book is allowed only if all conditions are met:

User exists

Book exists

Book is available

If any condition fails:

→ Borrowing is rejected
→ System state remains unchanged

Availability Constraint

A book qualifies for borrowing only if:

available = true

If:

available = false

→ Borrowing is rejected
→ Message: Book is already borrowed

State Update Rules (On Success)

When borrowing succeeds:

Book.available → false

Book added to user's borrowedBooks list

Both updates must occur together.
Partial updates are not allowed.


🔁 Returning Rules

Returning a book is allowed only if:

User exists

Book exists

The book is currently borrowed by that user

If any condition fails:

→ Operation is rejected
→ No state modification

State Update Rules (On Success)

When returning succeeds:

Book.available → true

Book removed from user's borrowedBooks list

Both updates must occur atomically (within same method execution).


⚠️ Invalid Operation Rules

The following scenarios must be rejected:

Borrowing non-existing book

Borrowing with non-existing user

Borrowing already borrowed book

Returning non-existing book

Returning book not borrowed by user

Registering book with empty title

Registering user with empty name

Rejected operations must:

Not modify system state

Provide clear error message


📚 Collection Consistency Rules

To ensure system consistency:

A book may belong to at most one user's borrowed list

A borrowed book must always have available = false

An available book must not appear in any user's borrowed list

Internal collections must not contain duplicates


🔒 Security Rules

No authentication required (local console usage)

No role-based access control

No permission levels

Single operator assumption

No concurrent modifications


⚙️ Validation Rules
Input Validation (Presentation Layer)

ID values must be numeric

Required fields must not be empty

Invalid input format must be rejected

Business Validation (Service Layer)

Before performing state changes:

Verify existence of user

Verify existence of book

Verify availability state

Verify ownership during return

Error Handling Rules

Errors must:

Be handled gracefully

Not crash the application

Not expose internal stack traces

The system must continue running after invalid operation.


🧠 Service Layer Enforcement

The service layer (Library class) is responsible for:

Enforcing borrowing rules

Enforcing returning rules

Guaranteeing state consistency

Preventing partial updates

Maintaining unique IDs

The presentation layer is responsible only for:

Reading input

Displaying output

Basic format validation


🗝️ Key Business Guarantees

The system guarantees:

Deterministic borrowing behavior

No hidden logic

No inconsistent state

No partial updates

No duplicate IDs

No silent failures

Clear distinction between:

Invalid input (rejected operation)

Valid operation (state updated)

Unexpected system error (handled safely)


✅ Summary

The MVP business rules ensure:

Strict validation before state changes

Deterministic and predictable behavior

Clear separation between input validation and business logic

Consistent in-memory state

Safe execution without data corruption

The system remains:

Simple

Deterministic

In-memory

Educational

Easily extensible

Future enhancements may include:

Borrow limits per user

Due dates and penalties

Persistent storage

REST API validation layer

Authentication and authorization