package org.example.librarymanagementsystem.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(of = "id")
@ToString
public class Book {

    private final Long id;
    private String title;
    private String author;
    @Setter
    private boolean available = true;

    public Book(Long id, String title, String author) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title is required");
        }
        if (author == null || author.isBlank()) {
            throw new IllegalArgumentException("Author is required");
        }

        this.id = id;
        this.title = title;
        this.author = author;
    }

    public void setTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title is required");
        }
        this.title = title;
    }

    public void setAuthor(String author) {
        if (author == null || author.isBlank()) {
            throw new IllegalArgumentException("Author is required");
        }
        this.author = author;
    }

}
