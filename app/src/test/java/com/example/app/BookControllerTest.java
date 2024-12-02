package com.example.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class BookControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BookInventory bookInventory;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    // Test for the getBook method (success)
    @Test
    public void testGetBook_Success() {
        Long bookId = 1L;
        Book book = new Book(123, "Test Book", "Test Author");
        when(bookInventory.findById(bookId)).thenReturn(Optional.of(book));

        ResponseEntity<Optional<Book>> response = bookController.getBook(bookId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(book, response.getBody().get());
    }

    // Test for the getAllBooks method (success)
    @Test
    public void testGetAllBooks_Success() {
        List<Book> books = Arrays.asList(
                new Book(123, "Test Book 1", "Author 1"),
                new Book(456, "Test Book 2", "Author 2")
        );
        when(bookInventory.findAll()).thenReturn(books);

        ResponseEntity<List<Book>> response = bookController.getAllBooks();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(books, response.getBody());
    }

    // Test for the addBook method (success)
    @Test
    public void testAddBook_Success() {
        Book book = new Book(123, "Test Book", "Test Author");
        when(bookInventory.save(any(Book.class))).thenReturn(book);

        MockMultipartFile coverImage = new MockMultipartFile(
                "coverImage",
                "test.jpg",
                "image/jpeg",
                "test image content".getBytes()
        );

        ResponseEntity<Book> response = bookController.addBook(
                123,
                "Test Book",
                "Test Author",
                5,
                10,
                coverImage
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(book, response.getBody());
    }

    // Test for the deleteBook method (success)
    @Test
    public void testDeleteBook_Success() {
        Long bookId = 1L;
        Book book = new Book(123, "Test Book", "Test Author");
        when(bookInventory.findById(bookId)).thenReturn(Optional.of(book));

        ResponseEntity<String> response = bookController.deleteBook(bookId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("The book has been deleted.", response.getBody());
    }

    // Test for the deleteBook method (book not found)
    @Test
    public void testDeleteBook_NotFound() {
        Long bookId = 1L;
        when(bookInventory.findById(bookId)).thenReturn(Optional.empty());

        ResponseEntity<String> response = bookController.deleteBook(bookId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Book not found.", response.getBody());
    }

//    // Test for the updateBook method (success)
//    @Test
//    public void testUpdateBook_Success() {
//        Long bookId = 1L;
//        Book book = new Book(123, "Test Book", "Test Author");
//        when(bookInventory.findById(bookId)).thenReturn(Optional.of(book));
//        when(bookInventory.save(any(Book.class))).thenReturn(book);
//
//        MockMultipartFile coverImage = new MockMultipartFile(
//                "coverImage",
//                "test.jpg",
//                "image/jpeg",
//                "test image content".getBytes()
//        );
//
//        ResponseEntity<String> response = bookController.postMethodName(
//                bookId,
//                456,
//                "Updated Book",
//                "Updated Author",
//                quantity,
//                coverImage
//        );
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("Book Updated", response.getBody());
//    }

    // Test for the searchBooks method (by ISBN)
    @Test
    public void testSearchBooks_ByISBN() {
        String query = "123";
        List<Book> books = Arrays.asList(new Book(123, "Test Book", "Test Author"));
        when(bookInventory.findByISBNnum(123)).thenReturn(books);

        ResponseEntity<List<Book>> response = bookController.searchBooks(query);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(books, response.getBody());
    }

    // Test for the searchBooks method (by title or author)
    @Test
    public void testSearchBooks_ByTitleOrAuthor() {
        String query = "Test";
        List<Book> books = Arrays.asList(
                new Book(123, "Test Book", "Author"),
                new Book(456, "Book", "Test Author")
        );
        when(bookInventory.findByTitleContainingOrAuthorContainingIgnoreCase(query, query))
                .thenReturn(books);

        ResponseEntity<List<Book>> response = bookController.searchBooks(query);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(books, response.getBody());
    }
}