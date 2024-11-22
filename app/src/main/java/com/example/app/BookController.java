package com.example.app;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookInventory bookInventory;
    /**
     * Default Constructor for the BookController
     */
    BookController() {

    }

    
    /** 
     * @return String
     */
    @GetMapping("/adminAddBookPage")
    public String addBookHTML() {
        return "adminAddBookPage";
    }
    /**
     * Retreives a specific book from the repository based on the books id
     * @param id
     * @return
     */
    @GetMapping("/get")
    public ResponseEntity<Optional<Book>> getBook(@RequestParam Long id) {
        Optional<Book> retreivedBooks = bookInventory.findById(id);
        return ResponseEntity.ok(retreivedBooks);
    }
    /**
     * Retreives all books from the book repository
     * @return
     */

    @GetMapping("/getAll")
    public ResponseEntity<List<Book>> getAllBooks() {

        try{
            List<Book> books = StreamSupport.stream(bookInventory.findAll().spliterator(), false).collect(Collectors.toList());

            for (Book book: books){
                System.out.println(book.getISBN());
                System.out.println(book.getAuthor());
                System.out.println(book.getTitle());
                System.out.println(book.getQuantity());
                System.out.println(book.getCoverImage());
                System.out.println("Book id = " + book.getId());
            }
                                
            return ResponseEntity.ok(books);
        }catch (Exception e){
            System.out.println("failed to retrieve books.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    /**
     * Add's a Book to the book repository with the below parameters
     * @param ISBNnum
     * @param title
     * @param author
     * @param coverImage
     * @return
     */
    @PostMapping("/add")
    public ResponseEntity<Book> addBook(
        @RequestParam int ISBNnum,
        @RequestParam String title,
        @RequestParam String author,
        @RequestParam int quantity,
        @RequestParam("coverImage") MultipartFile coverImage
    ) {
        try {
            System.out.println("isbn number added: " + ISBNnum);
            Book book = new Book(ISBNnum, title, author);
            book.setQuantity(quantity);
            book.setCoverImage(coverImage.getBytes()); 
            Book savedBook = bookInventory.save(book);
            System.out.println("Cover image size: " + coverImage.getSize() + " bytes");
            return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    /**
     * Delete's a specific book based on it's id
     * @param id
     * @return
     */

    @DeleteMapping("/del")
    public ResponseEntity<String> deleteBook(@RequestParam Long id) {
        Optional<Book> books = bookInventory.findById(id);

        if (books.isPresent()) {
            bookInventory.delete(books.get());
            return ResponseEntity.ok("The book has been deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found.");
        }
    }
    /**
     * Updates a Book within the book repository (didn't use PUT because complication)
     * @param id
     * @param isbn
     * @param title
     * @param author
     * @param coverImage
     * @return
     */

    @PostMapping("/update")
    public ResponseEntity<String> postMethodName(
        @RequestParam("id") Long id,
        @RequestParam("isbn") int isbn,
        @RequestParam("title") String title,
        @RequestParam("author") String author,
        @RequestParam("quantity") int quantity,
        @RequestParam(value = "coverImage", required = false) MultipartFile coverImage
    ) {
        Optional<Book> books = bookInventory.findById(id);
        if (books.isPresent()){
            Book book = books.get();
            book.setISBN(isbn);
            book.setAuthor(author);
            book.setTitle(title);
            book.setQuantity(quantity);
            try {
                book.setCoverImage(coverImage.getBytes());

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            bookInventory.save(book);

            return ResponseEntity.ok("Book Updated");

            

        }
        else{
            return ResponseEntity.ok("No Book Found");
        }
    }

    /**
     * Search's for a book based on a query (irrespective to what parameter of the book it is)
     * @param query
     * @return
     */
    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam String query) {
        List<Book> validBooks;
        try {
            
            Integer isbnnum = Integer.parseInt(query);
            validBooks = bookInventory.findByISBNnum(isbnnum);
        } catch (NumberFormatException e) {
           
            validBooks = bookInventory.findByTitleContainingOrAuthorContaining(query, query);
        }
        return ResponseEntity.ok(validBooks);
    }
}
