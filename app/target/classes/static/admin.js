$(document).ready(function() {
    $('body').append(`
        <div id="editModal" class="modal">
            <div class="modal-content">
                <span class="close">&times;</span>
                <h2>Edit Book</h2>
                <form id="editBookForm">
                    <input type="hidden" id="editBookId">
                    <div>
                        <label for="editIsbn">ISBN:</label>
                        <input type="number" id="editIsbn" required>
                    </div>
                    <div>
                        <label for="editTitle">Title:</label>
                        <input type="text" id="editTitle" required>
                    </div>
                    <div>
                        <label for="editAuthor">Author:</label>
                        <input type="text" id="editAuthor" required>
                    </div>
                    <div>
                        <label for="editQuantity">Quantity:</label>
                        <input type="number" id="editQuantity" required>
                    </div>
                    <div>
                        <label for="editPrice">Price:</label>
                        <input type="number" id="editPrice" required>
                    </div>
                    <div>
                        <label for="editCoverImage">Cover Image:</label>
                        <input type="file" id="editCoverImage" accept="image/*">
                    </div>
                    <button type="submit">Save Changes</button>
                </form>
            </div>
        </div>
    `);
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
                                <strong>Author:</strong> ${book.author} <br>
                                <strong>Quantity:</strong> ${book.quantity}<br>
                                <strong>Price:</strong> ${book.price}
                                <button class="delete-btn" onclick="deleteBook(${book.id})">Delete Book</button>
                                <button class="edit-btn" onclick="openEditModal(${book.id}, '${book.isbn}', '${book.title}', '${book.author}', '${book.quantity}')">Edit Book</button>
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

    window.openEditModal = function(bookId, isbn, title, author, quantity, price) {
        $("#editBookId").val(bookId);
        $("#editIsbn").val(isbn);
        $("#editTitle").val(title);
        $("#editAuthor").val(author);
        $("#editQuantity").val(quantity)
        $("#editPrice").val(price)
        $("#editModal").show();
    }

    // Close modal when clicking the X
    $(".close").click(function() {
        $("#editModal").hide();
    });

    
    $(window).click(function(event) {
        if ($(event.target).is("#editModal")) {
            $("#editModal").hide();
        }
    });


    $(document).on("click", "#logoutButton", function(){

        if(confirm("Are you sure you want to logout?")){
            sessionStorage.removeItem("username");
            $("#logoutButton").remove();
            $("#settingsButton").remove();
            $("#cartButton").remove();
            $("#bookRecommendations").remove();
            $("#header").append(
                `<h2 id = "login"><a href = "/loginEntry"><img src="/Login.png" class = "logout-icon"></a></h2>`
            )
        }
    });

    
    $("#editBookForm").submit(function(e) {
        e.preventDefault();
        
        const formData = new FormData();
        formData.append("id", $("#editBookId").val());
        formData.append("isbn", $("#editIsbn").val());
        formData.append("title", $("#editTitle").val());
        formData.append("author", $("#editAuthor").val());
        formData.append("quantity", $("#editQuantity").val());
        formData.append("price", $("#editPrice").val())
        
        const coverImageFile = $("#editCoverImage")[0].files[0];
        if (coverImageFile) {
            formData.append("coverImage", coverImageFile);
        }

        $.ajax({
            url: "/book/update",
            type: "POST",
            data: formData,
            processData: false,
            contentType: false,
            success: function(response) {
                alert("Book updated successfully!");
                $("#editModal").hide();
                loadBooks(); 
            },
            error: function(xhr, status, error) {
                console.error("Error updating book:", error);
                alert("Error updating book: " + error);
            }
        });
    });
    loadBooks();

    // search for books
    function appendBooks(response){
        $("#bookList").empty();

        response.forEach(function(book) {
            const isOutOfStock = book.quantity === 0;
            const coverImage = book.coverImage && book.coverImage.trim() ? `data:image/jpeg;base64,${book.coverImage}` : '/images/default-cover.jpg';
            const bookItem = `
                <li class="book-item">
                    <div class="image-container">
                        <img src="${coverImage}" alt="Cover of ${book.title}" class="book-cover">
                    </div>
                          <div class="book-details">
                                <strong>ISBN:</strong> ${book.isbn} <br>
                                <strong>Title:</strong> ${book.title} <br>
                                <strong>Author:</strong> ${book.author} <br>
                                <strong>Quantity:</strong> ${book.quantity}<br>
                                <strong>Price:</strong> ${book.price}
                                <button class="delete-btn" onclick="deleteBook(${book.id})">Delete Book</button>
                                <button class="edit-btn" onclick="openEditModal(${book.id}, '${book.isbn}', '${book.title}', '${book.author}', '${book.quantity}')">Edit Book</button>
                            </div>

                </li>`;
            $("#bookList").append(bookItem);
        });
    }

    function getAll(){
        $.ajax({
            url: "/book/getAll",
            type: "GET",
            success: function(response) {
                appendBooks(response)
            },
            error: function(xhr, status, error) {
                alert("ERROR");
                console.log(error);
                console.log(status);
            }
        });
    }


    getAll();

 


    const debounce = (func, delay) => {
        let timeout;
        return function (...args) {
            clearTimeout(timeout);
            timeout = setTimeout(() => func.apply(this, args), delay);
        };
    };

    // Function to fetch books based on the query
    function searchBooks(query) {
        if (query === "") {
            // If input is empty, fetch all books
            getAll();
        } else {
            $.ajax({
                url: `/book/search?query=${query}`,
                type: "GET",
                success: function (response) {
                    appendBooks(response); // Function to display books in the UI
                },
                error: function (xhr, status, error) {
                    alert("Error searching books: " + error);
                }
            });
        }
    }

    // Call getAll to load all books initially
    getAll();

    // Listen for input changes on the search box
    $("#search").on("input", debounce(function () {
        const query = $(this).val(); // Get the search input value
        searchBooks(query);          // Trigger search
    }, 300)); // Debounce delay of 300ms


    function sortFunction(sortOption){
        $.ajax({
            url : `/book/sort?sortBy=${sortOption}`,
            type : "GET",
            success : function(response){
                appendBooks(response);

            },

            error: function(xhr, status, error){
                alert("Sorting failed..." + error);
                console.log(error);
                console.log(status);
            }
        })

    }

    $("#sort").on('change', function(){
        var sortOption = $(this).val();
        console.log(sortOption);

        switch(sortOption){
            case "clear filters":
                sortFunction(sortOption);
                break;
            case "title A-Z":
                sortFunction(sortOption);
                break;
            case "title Z-A":
                sortFunction(sortOption);
                break;
            case "authour A-Z":
                sortFunction(sortOption);
                break;
            case "authour Z-A":
                sortFunction(sortOption);
                break;
            case "price high to low":
                sortFunction(sortOption);
                break;
            case "price low to high":
                sortFunction(sortOption);
                break;
        }



    });
});
