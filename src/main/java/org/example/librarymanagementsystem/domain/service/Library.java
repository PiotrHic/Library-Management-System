package org.example.librarymanagementsystem.domain.service;

import org.example.librarymanagementsystem.domain.model.Book;
import org.example.librarymanagementsystem.domain.model.User;
import org.example.librarymanagementsystem.exception.BookAlreadyBorrowedException;
import org.example.librarymanagementsystem.exception.BookNotFoundException;
import org.example.librarymanagementsystem.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class Library {

    private List<Book> books = new ArrayList<>();
    private List<User> users = new ArrayList<>();

    private long nextBookId = 1L;
    private long nextUserId = 1L;

    public Book addBook(String title, String author) {
        validateText(title, "Title");
        validateText(author, "Author");

        Book newBook = new Book(nextBookId++,title,author);
        books.add(newBook);

        return newBook;
    }

    public User registerUser(String name){
        validateText(name, "Name");
        User newUser = new User(nextUserId++, name);
        users.add(newUser);
        return newUser;
    }

    public void borrowBook(Long userId, Long bookId){
        User foundUser = findUser(userId);
        Book foundBook = findBook(bookId);

        if(!foundBook.isAvailable()){
            throw new BookAlreadyBorrowedException(foundBook.getId());
        }

        foundBook.setAvailable(false);
        foundUser.borrowBook(foundBook);

    }

    public void returnBook(Long userId, Long bookId){
        User foundUser = findUser(userId);
        Book foundBook = findBook(bookId);

        if (!foundUser.getBorrowedBooks().contains(foundBook)) {
            throw new BookNotFoundException(foundBook.getId());
        }

        foundBook.setAvailable(true);
        foundUser.returnBook(foundBook);

    }

    public List<Book> getAllBooks() {
        return Collections.unmodifiableList(books);
    }

    public List<User> getAllUsers() {
        return Collections.unmodifiableList(users);
    }

    private Book findBook(Long bookId){
        return books.stream()
                .filter(b -> b.getId().equals(bookId))
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException(bookId));
    }

    private User findUser(Long userId){
        return users.stream()
                .filter(u -> u.getId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException(userId));

    }

    private void validateText(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " is required");
        }
    }
}
