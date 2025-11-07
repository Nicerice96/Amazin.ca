$(document).ready(function() {
    const username = sessionStorage.getItem("username");
    console.log(username);
    if (username) {
      $("#login").remove();
      $("#header").append(`
        <a href="/cart/displayCart" id="cartButton">
          <img src="/cart.png" alt="Shopping Cart" class="cart-icon">
        </a>
        <a href="/settings/settingsEntry" id="settingsButton">
          <img src="/Settings.png" alt="Settings" class="settings-icon">
        </a>
        <button id="logoutButton">
          <img src="/Logout.png" alt="Log Out" class="logout-icon">
        </button>
      `);

      function bookrecommendations(){
      $.ajax({
        url: `user/bookrecommendations?username=${sessionStorage.getItem("username")}`,
        type: "GET",
        success: function(response) {
          if (response.length > 0) {
            
            if ($("#recommendationsContainer").length == 0) {
              $("#bookList").after(`
                <div id="recommendationsContainer">
                  <h2 id="recommendationTitle">Recommended For You</h2>
                  <div id="bookRecommendations"></div>
                </div>
              `);
            }
  
            response.forEach(function(book) {
              const coverImage = book.coverImage ? `data:image/jpeg;base64,${book.coverImage}` : '/images/default-cover.jpg';
              const bookHtml = `
                <div class="book-item">
                  <div class="image-container">
                    <img src="${coverImage}" alt="Cover of ${book.title}" class="book-cover">
                  </div>
                  <div class="book-details">
                    <strong>Title:</strong> ${book.title} <br>
                    <strong>Author:</strong> ${book.author} <br>
                    <strong>ISBN:</strong> ${book.isbn} <br>
                    <strong>Quantity:</strong> ${book.quantity}<br>
                    <strong>Price:</strong> ${book.price}
                  </div>
                  <button class="add-to-cart" data-book-id="${book.id}">
                    Add to Cart
                  </button>
                </div>
              `;
              $("#bookRecommendations").append(bookHtml);
            });
          }
        },
        error: function(xhr, status, error) {
          
        }
      });
    }

    bookrecommendations();
      $(document).on("click", ".add-to-cart", function() {
        const username = sessionStorage.getItem("username");
        if (!username) {
          alert("Please log in to add items to cart");
          return;
        }
        const bookId = $(this).data('book-id');
        $.ajax({
          url: `/cart/addToCart?bookID=${bookId}&username=${username}`,
          type: "POST",
          success: function(response) {
            alert(response);
            $(`#bookRecommendations .book-item`).each(function() {
                const recommendedBookId = $(this).find('.add-to-cart').data('book-id');
                $("#recommendationsContainer").remove();
                if (recommendedBookId === bookId) {
                  $(this).remove();
                }
              });
            bookrecommendations();
          },
          error: function(xhr, status, error) {
            console.log(error);
            alert("Failed to add book to cart");
          }
        });
      });
  
      $(document).on("click", "#logoutButton", function() {
        if (confirm("Are you sure you want to logout?")) {
          sessionStorage.removeItem("username");
          $("#logoutButton").remove();
          $("#settingsButton").remove();
          $("#cartButton").remove();
          $("#bookRecommendations").remove();
          $("#header").append(`<h2 id = "login"><a href = "/loginEntry"><img src="/Login.png" class = "logout-icon"></a></h2>`);
          $("#recommendationsContainer").remove();
        }
      });
    } else {
      sessionStorage.removeItem("username");
    }


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
                        <strong>Author:</strong> ${book.author}<br>
                        <strong>Quantity:</strong> ${book.quantity}<br>
                        <strong>Price:</strong> ${book.price}
                    </div>
                    <button 
                        class="add-to-cart" 
                        data-book-id="${book.id}" 
                        ${isOutOfStock ? 'disabled' : ''}
                    >
                        ${isOutOfStock ? 'Out of Stock' : 'Add Book to Cart'}
                    </button>
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