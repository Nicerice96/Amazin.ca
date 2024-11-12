package com.example.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private BookInventory bookInventory;
    @Autowired
    private Cart cart;
    @Autowired
    private CartRepository cartRepository;

    public CartController(){
        // empty constructor for now
    }

    /**
     * Displays the current cart
     * @return cart.html
     */
    @GetMapping("/displayCart")
    public String displayCart() {
        return "cart";
    }

    /**
     * Gets the books within the cart and displays to the html
     * @return ResponseEntity
     */
    @GetMapping("/getCart")
    public ResponseEntity<List<Cart>> getCart(){
        try{
            List<Cart> carts = StreamSupport.stream(cartRepository.findAll().spliterator(), false).toList();
            System.out.println("-------Currently in the cart-------");
            for (Cart cart: carts){
                for (Book book: cart.getBooks()){
                    System.out.println(book.getISBN());
                    System.out.println(book.getAuthor());
                    System.out.println(book.getTitle());
                    System.out.println(book.getCoverImage());
                    System.out.println(cart.getCartSize());
                    System.out.println("Book id = " + book.getId());
                }
            }
            System.out.println("-------End of the cart-------");
            return ResponseEntity.ok(carts);
        } catch (Exception e) {
            System.out.println("failed to retrieve the shopping cart.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Adds a book to the shopping cart
     * @param bookID
     * @return RepsonseEntity
     */
    @PostMapping("/addToCart")
    public ResponseEntity<String> addToCart(@RequestParam Long bookID){
        // find if book exists in inventory
        Optional<Book> book = bookInventory.findById(bookID);

        // check if book is present
        if (book.isPresent()) {
            // add to the cart
            cart.addBookToCart(book.get());
            cartRepository.save(cart);

            System.out.println(book.get().getISBN());
            System.out.println(book.get().getAuthor());
            System.out.println(book.get().getTitle());

            return ResponseEntity.ok("Book added to cart successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Book not found.");
        }

    }

    /**
     * Removes book from the shopping cart
     * @param bookID
     * @return RepsonseEntity
     */
    @DeleteMapping("/removeFromCart")
    public ResponseEntity<String> removeFromCart(@RequestParam Long bookID) {
        Optional<Book> bookToRemove = bookInventory.findById(bookID);
        if (bookToRemove.isPresent()) {
            for (Book book : cart.getBooks()) {
                if (book.getId().equals(bookID)) {
                    cart.removeBookFromCart(book);
                    cartRepository.save(cart);
                    return ResponseEntity.ok("Book removed from cart successfully.");
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Book not found in the cart.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Book not found in the inventory.");
        }
    }

    /**
     * Clears the cart of all books
     * @return
     */
    @PostMapping("/clearCart")
    public ResponseEntity<String> emptyCart() {
        try {
            cart.clearCart();
            cartRepository.save(cart);
            return ResponseEntity.ok("Cart has been cleared successfully.");
        } catch (Exception e) {
            System.out.println("Failed to clear the cart.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: Could not clear the cart.");
        }
    }
}
