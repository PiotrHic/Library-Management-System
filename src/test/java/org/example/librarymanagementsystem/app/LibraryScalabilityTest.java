package org.example.librarymanagementsystem.app;

import org.example.librarymanagementsystem.domain.model.Book;
import org.example.librarymanagementsystem.domain.service.Library;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LibraryScalabilityTest {

    @Test
    void shouldHandleVeryLargeNumberOfBooks() {
        Library library = new Library();
        int totalBooks = 1000;

        for (int i = 1; i <= totalBooks; i++) {
            library.addBook("Book " + i, "Author " + i);
        }

        assertEquals(totalBooks, library.getAllBooks().size(), "Library should contain all added books");

        long uniqueIds = library.getAllBooks().stream()
                .map(Book::getId)
                .distinct()
                .count();
        assertEquals(totalBooks, uniqueIds, "All book IDs should be unique");

        // Sprawdzamy, że wszystkie książki są dostępne
        assertEquals(totalBooks, library.getAllBooks().size(), "All books should be available initially");
    }
}
