package org.example.librarymanagementsystem.app;

import org.example.librarymanagementsystem.domain.model.Book;
import org.example.librarymanagementsystem.domain.service.Library;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConsoleRunnerIntegrationTest {

    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;
    private ByteArrayOutputStream outContent;
    private Library library;

    @BeforeEach
    void setUp() {

        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        library = new Library();
    }

    @AfterEach
    void tearDown() {

        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    void shouldAddBookViaConsole() {

        String simulatedInput = "1\nClean Code\nRobert C. Martin\n0\n";
        System.setIn(new ByteArrayInputStream (simulatedInput.getBytes()));

        ConsoleRunner runner = new ConsoleRunner(library);
        runner.run();

        assertEquals(1, library.getAllBooks().size(), "Library should have 1 book");
        Book addedBook = library.getAllBooks().get(0);
        assertEquals("Clean Code", addedBook.getTitle());
        assertEquals("Robert C. Martin", addedBook.getAuthor());

        String consoleOutput = outContent.toString();
        assertTrue(consoleOutput.contains("Book added successfully"), "Should print success message");
    }
}
