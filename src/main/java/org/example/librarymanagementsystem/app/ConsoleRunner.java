package org.example.librarymanagementsystem.app;

import org.example.librarymanagementsystem.domain.service.Library;
import org.example.librarymanagementsystem.exception.BookAlreadyBorrowedException;
import org.example.librarymanagementsystem.exception.BookNotFoundException;
import org.example.librarymanagementsystem.exception.InvalidOperationException;
import org.example.librarymanagementsystem.exception.UserNotFoundException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final Library library;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleRunner(Library library) {
        this.library = library;
    }

    @Override
    public void run(String... args) {

        boolean running = true;

        while (running) {
            printMenu();
            String choice = scanner.nextLine();

            try {
                running = handleChoice(choice);
            } catch (BookNotFoundException |
                     UserNotFoundException |
                     BookAlreadyBorrowedException |
                     InvalidOperationException e) {

                System.out.println("Operation failed: " + e.getMessage());

            } catch (IllegalArgumentException e) {

                System.out.println("Invalid input: " + e.getMessage());
            }

            System.out.println();
        }
    }

    private boolean handleChoice(String choice) {
        return switch (choice) {
            case "1" -> { addBook(); yield true; }
            case "2" -> { registerUser(); yield true; }
            case "3" -> { borrowBook(); yield true; }
            case "4" -> { returnBook(); yield true; }
            case "5" -> { showBooks(); yield true; }
            case "6" -> { showUsers(); yield true; }
            case "0" -> false;
            default -> {
                System.out.println("Invalid option.");
                yield true;
            }
        };
    }


    private void printMenu() {
        System.out.println("===== LIBRARY MENU =====");
        System.out.println("1 - Add book");
        System.out.println("2 - Register user");
        System.out.println("3 - Borrow book");
        System.out.println("4 - Return book");
        System.out.println("5 - Show books");
        System.out.println("6 - Show users");
        System.out.println("0 - Exit");
        System.out.print("Choose option: ");
    }

    private void addBook() {
        String title = readText("Title: ");
        String author = readText("Author: ");

        library.addBook(title, author);
        System.out.println("Book added successfully.");
    }

    private void registerUser() {
        String name = readText("Name: ");

        library.registerUser(name);
        System.out.println("User registered successfully.");
    }

    private void borrowBook() {
        Long userId = readLong("User ID: ");
        Long bookId = readLong("Book ID: ");

        library.borrowBook(userId, bookId);
        System.out.println("Book borrowed successfully.");
    }

    private void returnBook() {
        Long userId = readLong("User ID: ");
        Long bookId = readLong("Book ID: ");

        library.returnBook(userId, bookId);
        System.out.println("Book returned successfully.");
    }

    private void showBooks() {
        library.getAllBooks().forEach(System.out::println);
    }

    private void showUsers() {
        library.getAllUsers().forEach(System.out::println);
    }

    private Long readLong(String message) {
        System.out.print(message);
        try {
            return Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID must be numeric.");
        }
    }

    private String readText(String message) {
        System.out.print(message);
        String input = scanner.nextLine();

        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("Field cannot be empty.");
        }

        return input;
    }
}
