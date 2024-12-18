package com.example.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignUpController {

    /**
     * Direct to sign up page
     * @return sign up html
     */
    @GetMapping("/sign-up")
    public String signUpPage(){
        return "signUp";
    }
}
