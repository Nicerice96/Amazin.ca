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
            success: function(resource){
                console.log("Success!");
                window.location.href = "/admin/home";
                sessionStorage.setItem("username", resource.username);
                console.log(sessionStorage.getItem("username"));
                window.location.href = "/";


            },
            error: function(xhr, status, error){
                console.log("Login failed.");
                console.log(error);
            }
        });
    });

});
