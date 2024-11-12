package com.example.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

class CartTest {

    private Cart cart;
    private Book book1;
    private Book book2;
    private Book book3;

    @BeforeEach
    void setUp() {
        cart = new Cart();
        book1 = new Book();
        book2 = new Book();
        book3 = new Book();
    }

    @Test
    void testSetAndGetId() {
        cart.setId(1L);
        assertEquals(1L, cart.getId());
    }

    @Test
    void testSetAndGetBooks() {
        ArrayList<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        cart.setBooks(books);

        assertEquals(2, cart.getBooks().size());
        assertTrue(cart.getBooks().contains(book1));
        assertTrue(cart.getBooks().contains(book2));
    }

    @Test
    void testSetAndGetCartSize() {
        cart.setCartSize(5);
        assertEquals(5, cart.getCartSize());
    }

    @Test
    void testAddBookToCart() {
        cart.addBookToCart(book1);

        assertEquals(1, cart.getCartSize());
        assertTrue(cart.getBooks().contains(book1));
    }

    @Test
    void testRemoveBookFromCart() {
        cart.addBookToCart(book1);
        assertEquals(1, cart.getCartSize());

        cart.removeBookFromCart(book1);
        assertEquals(0, cart.getCartSize());
        assertFalse(cart.getBooks().contains(book1));
    }

    @Test
    void testRemoveBookNotInCart() {
        cart.removeBookFromCart(book1);

        assertEquals(-1, cart.getCartSize());
        assertFalse(cart.getBooks().contains(book1));
    }

    @Test
    void testDefaultValues() {
        assertNull(cart.getId());
        assertNotNull(cart.getBooks());
        assertTrue(cart.getBooks().isEmpty());
        assertEquals(0, cart.getCartSize());
    }

    @Test
    void testAddMultipleBooks() {
        cart.addBookToCart(book1);
        cart.addBookToCart(book2);
        cart.addBookToCart(book3);

        assertEquals(3, cart.getCartSize());
        assertTrue(cart.getBooks().contains(book1));
        assertTrue(cart.getBooks().contains(book2));
        assertTrue(cart.getBooks().contains(book3));
    }

    @Test
    void testCartSize() {
        cart.addBookToCart(book1);
        cart.addBookToCart(book2);

        assertEquals(2, cart.getCartSize());

        cart.removeBookFromCart(book1);
        assertEquals(1, cart.getCartSize());

        cart.removeBookFromCart(book2);
        assertEquals(0, cart.getCartSize());
    }

    @Test
    void testClearCart(){
        cart.addBookToCart(book1);
        cart.addBookToCart(book2);
        cart.addBookToCart(book3);

        assertEquals(3, cart.getCartSize());

        cart.clearCart();
        assertEquals(0, cart.getCartSize());
        assertTrue(cart.getBooks().isEmpty());

    }
}
