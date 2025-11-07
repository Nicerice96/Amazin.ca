$(document).ready(function() {
    $("#signUpForm").on("submit", function(event) {
        
        event.preventDefault();
        
        
        const userName = $("#username").val();
        const password = $("#password").val();
        
        
        const action = event.originalEvent.submitter.getAttribute("data-action");
        
        let url = '';
        if (action === "user") {
            url = "/user/register";  
        } else if (action === "admin") {
            url = "/admin/register";  
        }

        function wait(ms) {
            return new Promise(resolve => setTimeout(resolve, ms));
        }

        async function handleResponse(response) {
            if (response){
                success()
                await wait(1000);
                window.location.href = "/loginEntry"
            }
            else{fail()}
        }
        $.ajax({
            url: url,
            type: "POST",
            data: {
                username: userName,
                password: password
            },
            success: function(response) {
                handleResponse(response);
           

            },
            error: function(xhr, status, error) {
                console.error("Registration failed", error);
                console.log(error);
                console.log(status);
                console.log(xhr);
            }
        });
    });

    function success(){
        $("#confirmation").html(
        `Sign up Success!`
    ).removeClass('fail').addClass('success');
}

    function fail(){
        $("#confirmation").html(
            `Whoops! Password must have atleast:<br>
            8-10 characters<br>
            1 UpperCase<br>
            1 LowerCase<br>
            1 Digit<br>
            1 Special Character
            `
        ).removeClass('success').addClass('fail');

    }
 

});