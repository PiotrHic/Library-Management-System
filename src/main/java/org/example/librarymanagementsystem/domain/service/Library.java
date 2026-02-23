package org.example.librarymanagementsystem.domain.service;

import org.example.librarymanagementsystem.domain.model.Book;
import org.example.librarymanagementsystem.domain.model.User;

import java.util.Collections;
import java.util.List;

public class Library {

    private List<Book> books;
    private List<User> users;

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
        User foundedUser = findUser(userId);
        Book foundedBook = findBook(bookId);

        if(!foundedBook.isAvailable()){
            throw new IllegalStateException("Book is not available");
        }

        foundedBook.setAvailable(false);
        foundedUser.borrowBook(foundedBook);

    }

    public void returnBook(Long userId, Long bookId){
        User foundedUser = findUser(userId);
        Book foundedBook = findBook(bookId);

        if (!foundedUser.getBorrowedBooks().contains(foundedBook)) {
            throw new IllegalStateException("User did not borrow this book");
        }

        foundedBook.setAvailable(true);
        foundedUser.returnBook(foundedBook);

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
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("Book with id %d was not found", bookId)));
    }

    private User findUser(Long userId){
        return users.stream()
                .filter(u -> u.getId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("User with id %d was not found", userId)));

    }

    private void validateText(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " is required");
        }
    }
}
