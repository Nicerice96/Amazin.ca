$(document).ready(function(){

    $("#adminLoginForm").on("submit", function(event){
        
        event.preventDefault();
        
        
        const password = $("#password").val();
        const username = $("#username").val();

        
        $.ajax({
            url : "/admin/login",  
            type : "POST",
            data: {
                username: username,
                password: password
            },
            success: function(response){
                console.log("Success!");
                window.location.href = "/admin/home";
                
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
