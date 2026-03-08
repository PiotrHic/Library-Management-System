package org.example.librarymanagementsystem.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.*;

@Getter
@EqualsAndHashCode(of = "id")
@ToString
public class User {

    public User (Long id, String name, Set<Book> borrowedBooks) {
        this.id = id;
        this.name = name;
        this.borrowedBooks = borrowedBooks;
    }

    private final Long id;
    private String name;
    private Set<Book> borrowedBooks = new HashSet<>();


    public User(Long id, String name) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name is required");
        }

        this.id = id;
        this.name = name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name is required");
        }
        this.name = name;
    }

    public Set<Book> getBorrowedBooks() {
        return Collections.unmodifiableSet(borrowedBooks);
    }

    public void borrowBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
        borrowedBooks.add(book);
    }

    public void returnBook(Book book) {
        borrowedBooks.remove(book);
    }

}
