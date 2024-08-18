package com.IBMIntenship.backend.controller;


import com.IBMIntenship.backend.feign.AuthServiceClient;
import com.IBMIntenship.backend.model.AuthenticationRequest;
import com.IBMIntenship.backend.model.UserDTO;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthServiceClient authServiceClient;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);


    public AuthController(AuthServiceClient authServiceClient) {
        this.authServiceClient = authServiceClient;
    }


        @PostMapping("/login")
        public String login(@RequestBody AuthenticationRequest authRequest) {
            return authServiceClient.login(authRequest);
        }

        @PostMapping("/register")
        public String register(@RequestBody UserDTO userDTO) {
                return authServiceClient.register(userDTO);
            }



}



