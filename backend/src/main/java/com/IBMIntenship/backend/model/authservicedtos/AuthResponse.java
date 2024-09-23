package com.IBMIntenship.backend.model.authservicedtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

        private String token;
        private Object user;  // Can hold either TechnicianDTOResponse or UserDTO



}
