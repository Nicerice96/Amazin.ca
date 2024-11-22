$(document).ready(function() {

    const username = sessionStorage.getItem("username");
    if (username) {
        $("#login").remove();
        $("#header").append(
            `<a href="/cart/displayCart" id="cartButton"> 
                <img src="/cart.png" alt="Shopping Cart" class="cart-icon">
            </a>
            
            <a href="/settings/settingsEntry" id="cartButton"> 
                <img src="/Settings.png" alt="Settings"  style="width: 60px; height: 60px;">
            </a>
            
            <a href="/logout" id="cartButton"> 
                <img src="/Logout.png" alt="Log Out" class="cart-icon">
            </a>`
        )
        $(document).on("click", ".add-to-cart", function() {
            const bookId = $(this).data('book-id');
            $.ajax({
                url: `/cart/addToCart?bookID=${bookId}&username=${username}`,
                type: "POST",
<<<<<<< HEAD
                success: function(response) {
                    alert("Book added to cart!");
=======
                success: function(response){
                    alert(response);
>>>>>>> fb38a9c4a78443c299adf55316dce6e12d91221b
                },
                error: function(xhr, status, error) {
                    console.log(error);
                    alert("Failed to add book to cart");
                }
            });
        });
    } else {
        sessionStorage.removeItem("username");
    }

    $.ajax({
        url: "/book/getAll",
        type: "GET",
        success: function(response) {
            // Clear the current book list
            $("#bookList").empty();

            response.forEach(function(book) {
                const coverImage = book.coverImage && book.coverImage.trim()
                    ? `data:image/jpeg;base64,${book.coverImage}`
                    : '/images/default-cover.jpg';

                const isOutOfStock = book.quantity <= 0;

                const bookItem = `
                <li class="book-item">
                    <div class="image-container">
                        <img src="${coverImage}" 
                             alt="Cover of ${book.title}" 
                             class="book-cover">
                    </div>
                    <div class="book-details">
                        <strong>ISBN:</strong> ${book.isbn} <br>
                        <strong>Title:</strong> ${book.title} <br>
                        <strong>Author:</strong> ${book.author} <br>
                        <strong>Quantity:</strong> ${book.quantity} 
                    </div>
                    <button class="add-to-cart" 
                            data-book-id="${book.id}" 
                            ${isOutOfStock ? 'disabled' : ''}>
                        ${isOutOfStock ? 'Out of Stock' : 'Add to Cart'}
                    </button>
                </li>`;

                $("#bookList").append(bookItem);
            });
        },

        // Error handling for AJAX request
        error: function(xhr, status, error) {
            alert("ERROR");
            console.log(error);
            console.log(status);
        }
    });

    $("#searchForm").submit(function(event) {
        event.preventDefault();
        const query = $("#search").val();

        $.ajax({
            url: `/book/search?query=${query}`,
            type: "GET",
            success: function(response) {
                $("#bookList").empty();

                response.forEach(function(book) {
                    const coverImage = book.coverImage && book.coverImage.trim() ? `data:image/jpeg;base64,${book.coverImage}` : '/images/default-cover.jpg';
                    const isOutOfStock = book.quantity <= 0;

                    const bookItem = `
                        <li class="book-item">
                            <div class="image-container">
                                <img src="${coverImage}" alt="Cover of ${book.title}" class="book-cover">
                            </div>
                            <div class="book-details">
                                <strong>ISBN:</strong> ${book.isbn} <br>
                                <strong>Title:</strong> ${book.title} <br>
                                <strong>Author:</strong> ${book.author} <br>
                                <strong>Quantity:</strong> ${book.quantity}
                            </div>
                            <button class="add-to-cart" 
                                    data-book-id="${book.id}" 
                                    ${isOutOfStock ? 'disabled' : ''}>
                                ${isOutOfStock ? 'Out of Stock' : 'Add to Cart'}
                            </button>
                        </li>`;

                    $("#bookList").append(bookItem);
                });
            },
            error: function(xhr, status, error) {
                alert("Error searching books: " + error);
            }
        });
    });
});