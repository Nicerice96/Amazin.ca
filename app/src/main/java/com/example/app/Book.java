package com.example.app;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

/**
 * Entity class representing a book in the system.
 */
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String title;
    String author;
    int ISBNnum;
    int quantity;
    @Lob
    byte [] coverImage;

    /**
     * Default constructor.
     */
    public Book(){}

    /**
     * Constructor to create a Book with specified ISBN, title, and author.
     *
     * @param ISBNnum the ISBN number of the book
     * @param title the title of the book
     * @param author the author of the book
     */
    public Book(int ISBNnum, String title, String author, int quantity){
        this.ISBNnum = ISBNnum;
        this.title = title;
        this.author = author;
        this.quantity = quantity;
    }

    /**
     * Returns the ID of the book.
     *
     * @return the ID of the book
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Sets the ID of the book.
     *
     * @param id the ID to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the ISBN number of the book.
     *
     * @return the ISBN number of the book
     */
    public int getISBN(){
        return this.ISBNnum;
    }

    /**
     * Sets the ISBN number of the book.
     *
     * @param ISBNnum the ISBN number to set
     */
    public void setISBN(int ISBNnum){
        this.ISBNnum = ISBNnum;
    }

    /**
     * Returns the title of the book.
     *
     * @return the title of the book
     */
    public String getTitle(){
        return this.title;
    }

    /**
     * Sets the title of the book.
     *
     * @param title the title to set
     */
    public void setTitle(String title){
        this.title = title;
    }

    /**
     * Returns the author of the book.
     *
     * @return the author of the book
     */
    public String getAuthor(){
        return this.author;
    }

    /**
     * Sets the author of the book.
     *
     * @param author the author to set
     */
    public void setAuthor(String author){
        this.author = author;
    }

    /**
     * Returns the quantity of the books in stock.
     *
     * @return the ID of the book
     */
    public int getQuantity() {
        return this.quantity;
    }

    /**
     * Sets the ID of the book.
     *
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Returns the cover image of the book.
     *
     * @return the cover image of the book
     */
    public byte[] getCoverImage() {
        return this.coverImage;
    }

    /**
     * Sets the cover image of the book.
     *
     * @param coverImage the cover image to set
     */
    public void setCoverImage(byte[] coverImage) {
        this.coverImage = coverImage;
    }
}
