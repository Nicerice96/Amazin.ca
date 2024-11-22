$(document).ready(function () {
    $("#userLoginForm").on("submit", function (event) {
        event.preventDefault(); // Prevent the default form submission

        const username = $("#username").val(); // Get username input
        const password = $("#password").val(); // Get password input

        $.ajax({
            url: "/user/login", // Server endpoint for login
            type: "POST", // HTTP method
            data: {
                username: username,
                password: password
            },
            success: function (resource) {
                console.log("User login Success!");
                console.log("Login response:", resource);

                // Store username in sessionStorage
                sessionStorage.setItem("username", resource.username);
                console.log("Session Username:", sessionStorage.getItem("username"));

                // Redirect to the homepage on success
                window.location.href = "/";
            },
            error: function (xhr, status, error) {
                console.log("Error:", error);
                console.log("XHR Response:", xhr);

                // Display error message
                const errorMessage = xhr.responseJSON && xhr.responseJSON.message
                    ? xhr.responseJSON.message
                    : "Login failed. Wrong username or password.";

                $("#loginError").text(errorMessage).show();
            }
        });

        // Handle navigation to the sign-up page
        $("#goToSignUp").on("click", function () {
            $.ajax({
                url: "/user/loginPage",
                type: "GET",
                success: function () {
                    window.location.href = "/signup/sign-up";
                },
                error: function (xhr, status, error) {
                    console.error("Navigation failed:", error);
                },
            });
        });
    });
});
