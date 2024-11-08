$(document).ready(function() {
        function loadBooks(){
        $.ajax({
            url: "/book/getAll",
            type: "GET",
            success: function(response) {
                $("#bookList").empty();

                response.forEach(function(book) {
                    const coverImage = book.coverImage && book.coverImage.trim() ? `data:image/jpeg;base64,${book.coverImage}` : '/images/default-cover.jpg';

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
                                <strong>Author:</strong> ${book.author}
                                <button class="delete-btn" onclick="deleteBook(${book.id})">Delete Book</button>

                            </div>

                        </li>`;

                    // Append each book item to the list
                    $("#bookList").append(bookItem);
                });
            },

            error: function(xhr, status, error) {
                alert("ERROR");
                console.log(error);
                console.log(status);
            }
        });
    }
    loadBooks();
    window.deleteBook = function(bookId) {
        if (confirm('Are you sure you want to delete this book?')) {
            $.ajax({
                url: `/book/del?id=${bookId}`,
                type: 'DELETE',
                success: function(response) {
                    alert('Book deleted successfully');
                    loadBooks(); // Reload the book list
                },
                error: function(xhr, status, error) {
                    console.error("Error deleting book:", error);
                    alert('Error deleting book: ' + error);
                }
            });
        }
    };

});
