package com.example.app;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @JsonIgnore
    @OneToOne(mappedBy = "cart")
    private User user;

    Cart(){

    }
    /**
     * Set Cart Id
     * @param id cart's Id
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
     * @return list of books contained in the cart
     */
    public List<Book> getBooks() {
        return books;
    }
    /**
     * Set's the Books in the cart
     * @param books books in the cart
     */
    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }
    /**
     * Return's the cart's size
     * @return the size of the cart
     */
    public int getCartSize() {
        return cartSize;
    }
    /**
     * Sets the cart's size
     * @param cartSize the new cart size
     */
    public void setCartSize(int cartSize) {
        this.cartSize = cartSize;
    }
    /**
     * Add's a specific book to the cart
     * @param book the book to add to the cart
     */
    public void addBookToCart(Book book){
        books.add(book);
        cartSize++;
    }
    /**
     * Remove's a specific book from the cart
     * @param book the book to remove from the cart
     */
    public void removeBookFromCart(Book book){
        books.remove(book);
        cartSize--;
    }

    public void setUser(User user){
        this.user = user;
    }
    public User getUser(){
        return this.user;
    }
}
