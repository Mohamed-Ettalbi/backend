package com.IBMIntenship.backend.model.authservicedtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

        private String username;
        private String password;

        // Getters and setters

}
