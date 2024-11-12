package com.example.app;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Entity
@Component
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    private List<Book> books = new ArrayList<>();

    private int cartSize = 0;

    /**
     * Empty constructor
     */
    public Cart(){

    }

    /**
     * Basic constructor for cart
     * @param books
     * @param cartSize
     */
    public Cart(List<Book> books, int cartSize) {
        this.books = books;
        this.cartSize = cartSize;
    }

    /**
     * Set Cart Id
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }


    /**
     * Return the cart's id
     * @return Long
     */
    public Long getId() {
        return id;
    }
    /**
     * Return's the list of books contained in the Cart
     * @return
     */
    public List<Book> getBooks() {
        return books;
    }
    /**
     * Set's the Books in the cart
     * @param books
     */
    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }
    /**
     * Return's the cart's size
     * @return
     */
    public int getCartSize() {
        return cartSize;
    }
    /**
     * Sets the cart's size
     * @param cartSize
     */
    public void setCartSize(int cartSize) {
        this.cartSize = cartSize;
    }
    /**
     * Add's a specific book to the cart
     * @param book
     */
    public void addBookToCart(Book book){
        books.add(book);
        cartSize++;
    }
    /**
     * Remove's a specific book from the cart
     * @param book
     */
    public void removeBookFromCart(Book book){
        books.remove(book);
        cartSize--;
    }

    /**
     * Clear cart of all books
     */
    public void clearCart(){
        this.books.clear();
        cartSize = 0;
    }
}
