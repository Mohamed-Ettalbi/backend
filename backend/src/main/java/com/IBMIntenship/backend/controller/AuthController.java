package com.IBMIntenship.backend.controller;


import com.IBMIntenship.backend.feign.AuthServiceClient;
import com.IBMIntenship.backend.model.AuthenticationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthServiceClient authServiceClient;

    public AuthController(AuthServiceClient authServiceClient) {
        this.authServiceClient = authServiceClient;
    }


    @PostMapping("/login")
    public String login(@RequestBody AuthenticationRequest authRequest) {
        return authServiceClient.login(authRequest);
    }

}

