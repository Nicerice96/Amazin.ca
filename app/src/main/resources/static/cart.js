$(document).ready(function() {
    console.log("cart.js loaded");
    const username = sessionStorage.getItem("username");
    
    if (!username) {
        $("#emptyCartMessage").text("Please log in to view your cart").show();
        return;
    }

    $.ajax({
        url: `/cart/getCart?username=${username}`,
        type: "GET",
        success: function(response) {
            $("#cartItems").empty();
            
            if (!response.books || response.books.length === 0) {
                $("#emptyCartMessage").show();
                return;
            }

            response.books.forEach(book => {
                const coverImage = book.coverImage && book.coverImage.trim()
                    ? `data:image/jpeg;base64,${book.coverImage}`
                    : '/images/default-cover.jpg';
                
                $("#cartItems").append(`
                    <li class="book-item">
                        <div class="image-container">
                            <img src="${coverImage}"
                                alt="Cover of ${book.title}"
                                class="book-cover">
                        </div>
                        <div class="book-details">
                            <strong>ISBN:</strong> ${book.isbn} <br>
                            <strong>Title:</strong> ${book.title} <br>
                            <strong>Author:</strong> ${book.author}
                        </div>
                        <button class="removeFromCartButton checkoutFromCartButton" data-book-id="${book.id}">Remove item</button>
                        <button class="removeFromCartButton checkoutFromCartButton">Checkout</button>
                    </li>
                `);
            });

            // Move the click handler outside the loop and use event delegation
            $("#cartItems").on("click", ".removeFromCartButton", function() {
                const bookId = $(this).data("book-id");
                
                $.ajax({
                    url: `/cart/removeFromCart?bookID=${bookId}&username=${username}`,
                    type: "DELETE",
                    success: function(response) {
                        alert("Book removed from cart.");
                        location.reload();
                    },
                    error: function(xhr, status, error) {
                        alert("Failed to remove the book from cart.");
                        console.error("Error:", error);
                    }
                });
            });
        },
        error: function(xhr, status, error) {
            $("#emptyCartMessage").text("Failed to retrieve cart. Please try again.").show();
            console.error("Failed to retrieve cart:", error);
        }
    });
});