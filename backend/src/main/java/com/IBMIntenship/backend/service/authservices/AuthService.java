package com.IBMIntenship.backend.service.authservices;

import com.IBMIntenship.backend.config.CorsConfig;
import com.IBMIntenship.backend.config.FeignConfig;
import com.IBMIntenship.backend.config.UserDetailsFromToken;
import com.IBMIntenship.backend.feign.AuthServiceClient;
import com.IBMIntenship.backend.model.authservicedtos.AuthResponse;
import com.IBMIntenship.backend.model.authservicedtos.AuthenticationRequest;
import com.IBMIntenship.backend.model.authservicedtos.UserDTO;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private  FeignConfig feignConfig;
    @Autowired
    private  AuthServiceClient authServiceClient;
    @Autowired
    private  CorsConfig corsConfig;



    public AuthResponse login(AuthenticationRequest authRequest) {
        AuthResponse authResponse = authServiceClient.login(authRequest);

        String token = authResponse.getToken();
        // Get user details from the token
        UserDetailsFromToken userDetails = corsConfig.getUserDetailsFromToken(token);
        logger.info("User logged in: Email = {}, Roles = {}", userDetails.getEmail(), userDetails.getRoles());
        return authResponse;
    }

    public ResponseEntity<AuthResponse> register(UserDTO userDTO) {
        try {
                 logger.info("Attempting to register user with email: {}", userDTO.getEmail());
            AuthResponse response = authServiceClient.register(userDTO);
                 logger.info("Successfully registered user with email: {}", userDTO.getEmail());

            return ResponseEntity.ok(response);

        } catch (FeignException.Conflict e) {
                 logger.error("Conflict error during registration for email: {}", userDTO.getEmail(), e);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (FeignException e) {
                 logger.error("Error during registration for email: {}", userDTO.getEmail(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
