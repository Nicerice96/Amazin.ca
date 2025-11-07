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
            error: function(xhr, status, error){
                console.log("Login failed.");
                console.log(error);
                
                $("#error").remove();
                $("#adminLoginForm").append(
                    `<label id="error"><span>Whoops! Wrong username or password.</span></label>`
                )
            }
        });
    });

});
