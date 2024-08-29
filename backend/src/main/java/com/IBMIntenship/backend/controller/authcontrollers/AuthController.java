package com.IBMIntenship.backend.controller.authcontrollers;

import com.IBMIntenship.backend.model.authservicedtos.AuthResponse;
import com.IBMIntenship.backend.model.authservicedtos.AuthenticationRequest;
import com.IBMIntenship.backend.model.authservicedtos.UserDTO;
import com.IBMIntenship.backend.service.authservices.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private  AuthService authService;



    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthenticationRequest authRequest) {
        return authService.login(authRequest);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody UserDTO userDTO) {
        return authService.register(userDTO);
    }
}
