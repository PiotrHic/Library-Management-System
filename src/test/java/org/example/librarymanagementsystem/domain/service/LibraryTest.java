package org.example.librarymanagementsystem.domain.service;

import org.example.librarymanagementsystem.domain.model.Book;
import org.example.librarymanagementsystem.domain.model.User;
import org.example.librarymanagementsystem.exception.BookAlreadyBorrowedException;
import org.example.librarymanagementsystem.exception.BookNotFoundException;
import org.example.librarymanagementsystem.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {

    @Autowired
    private Library library;

    @BeforeEach
    void setUp() {
        library = new Library();
    }

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

    @Test
    void bookWasSuccessfullyBorrowed() {

        Book book = library.addBook("title","author");
        User addedUser = library.registerUser("Piotr Hic");

        library.borrowBook(addedUser.getId(),book.getId());

        assertNotNull(book);
        assertNotNull(addedUser);
        assertFalse(book.isAvailable());
        assertTrue(addedUser.getBorrowedBooks().contains(book));
    }

    @Test
    void tryToBookAlreadyBorrowedBook() {

        Book book = library.addBook("title","author");
        User addedUser = library.registerUser("Piotr Hic");

        library.borrowBook(addedUser.getId(),book.getId());

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

    @Test
    void noExistingUser() {

        Book book = library.addBook("title","author");
        User addedUser = library.registerUser("Piotr Hic");

        assertThrows(UserNotFoundException.class, () ->
                library.borrowBook(null,book.getId())
        );
    }

    /*

    Returning

    Success scenario

    Return book not borrowed

    Return non-existing book

    Return non-existing user
    */

    @Test
    void bookWasSuccessfullyReturned() {

        Book book = library.addBook("title","author");
        User addedUser = library.registerUser("Piotr Hic");

        library.borrowBook(addedUser.getId(),book.getId());
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

        assertFalse(book.isAvailable());

        library.returnBook(user.getId(), book.getId());

        assertTrue(book.isAvailable());
    }
}
