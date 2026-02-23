package org.example.librarymanagementsystem;

import org.example.librarymanagementsystem.domain.service.Library;
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
                switch (choice) {
                    case "1" -> addBook();
                    case "2" -> registerUser();
                    case "3" -> borrowBook();
                    case "4" -> returnBook();
                    case "5" -> showBooks();
                    case "6" -> showUsers();
                    case "0" -> running = false;
                    default -> System.out.println("Invalid option.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            System.out.println();
        }
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
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Author: ");
        String author = scanner.nextLine();

        library.addBook(title, author);
        System.out.println("Book added.");
    }

    private void registerUser() {
        System.out.print("Name: ");
        String name = scanner.nextLine();

        library.registerUser(name);
        System.out.println("User registered.");
    }

    private void borrowBook() {
        Long userId = readLong("User ID: ");
        Long bookId = readLong("Book ID: ");

        library.borrowBook(userId, bookId);
        System.out.println("Book borrowed.");
    }

    private void returnBook() {
        Long userId = readLong("User ID: ");
        Long bookId = readLong("Book ID: ");

        library.returnBook(userId, bookId);
        System.out.println("Book returned.");
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
}
