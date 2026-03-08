package org.example.librarymanagementsystem.service;

import org.example.librarymanagementsystem.domain.model.Book;
import org.example.librarymanagementsystem.domain.model.User;
import org.example.librarymanagementsystem.domain.service.Library;
import org.example.librarymanagementsystem.exception.BookAlreadyBorrowedException;
import org.example.librarymanagementsystem.exception.BookNotFoundException;
import org.example.librarymanagementsystem.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {

    @Autowired
    private Library library;

    @BeforeEach
    void setUp() {
        library = new Library();
    }

    @Nested
    class BookRegistrationTests {

        @Test
        void validBookShouldBeAdded(){

            Book book = library.addBook("title","author");

            assertEquals("title", book.getTitle());
            assertEquals("author", book.getAuthor());
            assertTrue(book.isAvailable());
            assertEquals(1, library.getAllBooks().size());
        }

        @Test
        void shouldRejectBookWithEmptyTitle() {
            assertThrows(IllegalArgumentException.class, () ->
                    library.addBook("", "Author")
            );
        }

        @Test
        void shouldGenerateUniqueIdsForBooks() {
            Book b1 = library.addBook("Book1", "Author1");
            Book b2 = library.addBook("Book2", "Author2");

            assertNotEquals(b1.getId(), b2.getId());
        }
    }

    @Nested
    class UserRegistrationTests {

        @Test
        void validUserShouldBeAdded(){
            User addedUser = library.registerUser("Piotr Hic");

            assertEquals("Piotr Hic", addedUser.getName());
            assertEquals(1, library.getAllUsers().size());
        }

        @Test
        void shouldRejectUserWithEmptyName() {
            assertThrows(IllegalArgumentException.class, () ->
                    library.registerUser("")
            );
        }

    }

    @Nested
    class BorrowingTests {

        @Test
        void bookWasSuccessfullyBorrowed() {

            Book book = library.addBook("title","author");
            User addedUser = library.registerUser("Piotr Hic");

            assertTrue(bookAvailabilityCheck(book));

            library.borrowBook(addedUser.getId(),book.getId());

            isBookBorrowedByMultipleUsers(book);

            assertFalse(book.isAvailable());
            assertFalse(bookAvailabilityCheck(book));

            assertFalse(book.isAvailable());
            assertTrue(addedUser.getBorrowedBooks().contains(book));
        }

        @Test
        void tryToBookAlreadyBorrowedBook() {

            Book book = library.addBook("title","author");
            User addedUser = library.registerUser("Piotr Hic");

            library.borrowBook(addedUser.getId(),book.getId());
            isBookBorrowedByMultipleUsers(book);
            assertFalse(book.isAvailable());

            assertThrows(BookAlreadyBorrowedException.class, () ->
                    library.borrowBook(addedUser.getId(),book.getId())
            );

        }

        @Test
        void borrowNoExistingBook() {

            User addedUser = library.registerUser("Piotr Hic");

            assertThrows(BookNotFoundException.class, () ->
                    library.borrowBook(addedUser.getId(),null)
            );

        }
    }

    @Nested
    class ReturningTests {

        @Test
        void noExistingUser() {

            Book book = library.addBook("title","author");

            assertThrows(UserNotFoundException.class, () ->
                    library.borrowBook(null,book.getId())
            );
        }

        @Test
        void bookWasSuccessfullyReturned() {

            Book book = library.addBook("title","author");
            User addedUser = library.registerUser("Piotr Hic");

            library.borrowBook(addedUser.getId(),book.getId());
            assertFalse(book.isAvailable());
            isBookBorrowedByMultipleUsers(book);
            library.returnBook(addedUser.getId(),book.getId());

            assertNotNull(book);
            assertNotNull(addedUser);
            assertTrue(book.isAvailable());
            assertFalse(addedUser.getBorrowedBooks().contains(book));
        }

        @Test
        void returnBookNotBorrowed() {

            Book book = library.addBook("title","author");
            User addedUser = library.registerUser("Piotr Hic");

            assertThrows(BookNotFoundException.class, () ->
                    library.returnBook(addedUser.getId(),book.getId())
            );

        }

        @Test
        void returnNotExistingBook(){
            User addedUser = library.registerUser("Piotr Hic");

            assertThrows(BookNotFoundException.class, () ->
                    library.returnBook(addedUser.getId(),null)
            );
        }

        @Test
        void returnNotExistingUser(){
            Book book = library.addBook("title","author");

            assertThrows(UserNotFoundException.class, () ->
                    library.returnBook(null,book.getId())
            );
        }
    }

    @Test
    void shouldNotChangeStateWhenBorrowFails() {
        User user = library.registerUser("John");
        Book book = library.addBook("Clean Code", "Martin");

        try {
            library.borrowBook(user.getId(), 999L);
        } catch (Exception ignored) {}

        assertTrue(book.isAvailable());
        assertTrue(user.getBorrowedBooks().isEmpty());
    }

    @Test
    void availabilityShouldMatchBorrowState() {
        User user = library.registerUser("John");
        Book book = library.addBook("Clean Code", "Martin");

        library.borrowBook(user.getId(), book.getId());
        isBookBorrowedByMultipleUsers(book);
        assertTrue(library.getAllBooks().contains(book));
        assertFalse(book.isAvailable());

        library.returnBook(user.getId(), book.getId());

        assertTrue(book.isAvailable());
    }

    private boolean bookAvailabilityCheck(Book book) {
        for(User user : library.getAllUsers()){
            if(user.getBorrowedBooks().contains(book)) {
                return false;
            }
        }
        return true;
    }

    private void isBookBorrowedByMultipleUsers(Book book) {
        long count = library.getAllUsers().stream()
                .filter(user -> user.getBorrowedBooks().contains(book))
                .count();
        if (count > 1) {
            throw new IllegalStateException("Book cannot belong to two users simultaneously");
        }
    }

}
