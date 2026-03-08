package org.example.librarymanagementsystem.app;

import org.example.librarymanagementsystem.domain.model.Book;
import org.example.librarymanagementsystem.domain.model.User;
import org.example.librarymanagementsystem.domain.service.Library;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LibraryIntegrationTest {

    private Library library;
    private User user;
    private Book book;

    @BeforeEach
    void setUp() {
        library = new Library();
    }

    @Test
    void fullFlowScenario() {
        // 1️⃣ Register user
        user = library.registerUser("Jan");

        // 2️⃣ Add book
        book = library.addBook("Clean Code", "Robert C. Martin");

        // 3️⃣ Borrow book
        library.borrowBook(user.getId(), book.getId());

        assertTrue(user.getBorrowedBooks().contains(book));
        assertTrue(library.getAllBooks().contains(book));
        assertFalse(library.getAllUsers().isEmpty());
        assertFalse(library.getAllBooks().isEmpty());

        // 4️⃣ Return book
        library.returnBook(user.getId(), book.getId());

        assertFalse(user.getBorrowedBooks().contains(book));
        assertTrue(library.getAllBooks().contains(book));

        // 5️⃣ Verify final state
        assertEquals(1, library.getAllBooks().size(), "Library should contain exactly 1 book");
        assertEquals(1, library.getAllUsers().size(), "Library should contain exactly 1 user");
        assertTrue(library.getAllBooks().contains(book), "Book should be available after return");
    }
}
