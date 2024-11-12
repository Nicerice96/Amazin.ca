$(document).ready(function() {

    $.ajax({
        url: "/book/getAll",
        type: "GET",
        success: function(response) {
            // Clear the current book list
            $("#bookList").empty();

            response.forEach(function(book) {
                // Use base64 image if available; otherwise, use default cover image
                const coverImage = book.coverImage && book.coverImage.trim()
                    ? `data:image/jpeg;base64,${book.coverImage}`
                    : '/images/default-cover.jpg';

                // Generate HTML for each book item
                const bookItem = `
                    <link rel="stylesheet" href="/indexstylesheet.css">
                    <li class="book-item">
                        <div class="image-container">
                            <img src="${coverImage}" 
                                 alt="Cover of ${book.title}" 
                                 class="book-cover">
                        </div>
                        <div class="book-details">
                            <strong>ISBN:</strong> ${book.isbn} <br>
                            <strong >Title:</strong> ${book.title} <br>
                            <strong>Author:</strong> ${book.author}
                        </div>
                        <button class="add-to-cart" data-book-id="${book.id}">Add Book to Cart</button
                    </li>`;

                // Append each book item to the list
                $("#bookList").append(bookItem);

                // add the book to the user's shopping cart
                $(".add-to-cart").on("click", function() {
                    const bookId = $(this).data("book-id"); // stored the book id in the button
                    $.ajax({
                        url: `/cart/addToCart?bookID=${bookId}`,
                        type: "POST",
                        contentType: "application/json",
                        success: function() {
                            alert("Book added to cart successfully!");
                        },
                        error: function(xhr, status, error) {
                            alert("Failed to add book to cart.");
                            console.log(error);
                        }
                    });
                });
            });
        },

        // Error handling for AJAX request
        error: function(xhr, status, error) {
            alert("ERROR");
            console.log(error);
            console.log(status);
        }
    });


    $("#searchForm").submit(function(event){
        event.preventDefault();
        const query = $("#search").val();

        $.ajax({
            url: `/book/search?query=${query}`,
            type: "GET",
            success: function(response) {
                $("#bookList").empty();

                response.forEach(function(book) {
                    const coverImage = book.coverImage && book.coverImage.trim() ? `data:image/jpeg;base64,${book.coverImage}` : '/images/default-cover.jpg';
                    const bookItem = `
                        <li class="book-item">
                            <div class="image-container">
                                <img src="${coverImage}" alt="Cover of ${book.title}" class="book-cover">
                            </div>
                            <div class="book-details">
                                <strong>ISBN:</strong> ${book.isbn} <br>
                                <strong>Title:</strong> ${book.title} <br>
                                <strong>Author:</strong> ${book.author}
                            </div>
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