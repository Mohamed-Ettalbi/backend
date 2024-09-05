package com.IBMIntenship.backend.config;

import com.IBMIntenship.backend.config.FeignConfig;
import com.IBMIntenship.backend.config.UserDetailsFromToken;
import com.IBMIntenship.backend.service.ticketservices.TicketService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecurityService {
    private static final Logger logger = LoggerFactory.getLogger(TicketService.class);


    @Autowired
    private FeignConfig feignConfig;

    private final String SECRET_KEY = "55454e424a49352b394850384d4761346e2b43425a795170704159694c7433313657556a32587272414f4f71535332586f324d4b6b79684f55494a62443149466a6c787175647970424a793874474d713032324d306f6d71517a2f303347396d344f7270634c6a4f6167572b73616c33726373626c4a2f384e6a4d376e703069446a75316f4d7937384a7a4c53674d7550746c514e6e5065787176624e457a5a7a666c634e2f352b485466324b45517653424a7874596c374365354d4d5257485a79535561515a77743039722f646f693371445565576f4977506e526338754c504f613875334c6f634557752b71792f37575256624e453334355755323648346f79714a73574b72707a364a3551483069674c50675869766e563541746e657768795372684d4d6d5a45646b4265652b345370335963744e42652b42544c38443147414e54437573384c576d6e79514a496845555a6e2f785332447541424f4d4f31324e4f5777324b33303d";

    public boolean validateTokenAndRole(String requiredRole) {
        String token = feignConfig.getJwtToken();
        if (token == null || !validateToken(token)) {
            logger.error("Invalid or expired token");
            throw new RuntimeException("Invalid or expired token");
        }

        UserDetailsFromToken userDetails = getUserDetailsFromToken(token);
        if (userDetails.getRoles() == null || !userDetails.getRoles().contains(requiredRole)) {
            logger.error("Access denied: Insufficient permissions, required role: {}, roles from token: {}", requiredRole, userDetails.getRoles());
        return false;
        }
        return true;
    }

    public String getEmailFromToken() {
        String token = feignConfig.getJwtToken();
        if (token != null && validateToken(token)) {
            UserDetailsFromToken userDetails = getUserDetailsFromToken(token);
            return userDetails.getEmail();
        }
        throw new RuntimeException("Invalid or expired token");
    }

    public UserDetailsFromToken getUserDetailsFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();

            String email = claims.getSubject(); // Extract email (subject)
            List<String> roles = claims.get("role", List.class); // Extract roles

            return new UserDetailsFromToken(email, roles);
        } catch (Exception e) {
            // Handle any exceptions that may occur while parsing the token
            return null;
        }
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(SECRET_KEY) // Set the secret key used for signing
                    .parseClaimsJws(token); // Parse the token and validate its signature

            return true; // If no exceptions are thrown, the token is valid
        } catch (JwtException | IllegalArgumentException e) {
            // Log the error or handle it appropriately
            return false; // The token is invalid
        }
    }
}
