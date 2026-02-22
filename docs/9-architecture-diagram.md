          ┌───────────────┐
          │    Console    │
          │   Client UI   │
          └───────┬───────┘
                  │ User Input
          ┌───────▼────────┐
          │  Main / Menu   │
          │   (Presentation│
          │     Layer)     │
          └───────┬────────┘
                  │ Delegates
          ┌───────▼────────┐
          │   Service      │
          │   (Library)    │
          │ Business Logic │
          └───────┬────────┘
                  │ Updates State
          ┌───────▼────────┐
          │ Domain Models  │
          │ - Book         │
          │ - User         │
          │ - BorrowedBooks│
          └────────────────┘

Other Components:

┌─────────────────────────────────────────┐
│ Exception Handling Layer                 │
│ - BookNotFoundException                  │
│ - UserNotFoundException                  │
│ - BookAlreadyBorrowedException           │
│ - InvalidOperationException              │
└─────────────────────────────────────────┘

┌─────────────────────────────────────────┐
│ In-Memory Storage Layer                  │
│ - Collections of Book & User            │
│ - ID Generation                          │
└─────────────────────────────────────────┘

┌─────────────────────────────────────────┐
│ Utility / Helper Layer (Optional)       │
│ - Input Validation                       │
│ - ID Generation (if separate)            │
└─────────────────────────────────────────┘