package com.IBMIntenship.backend.controller;

import com.IBMIntenship.backend.feign.AuthServiceClient;
import com.IBMIntenship.backend.model.TechnicianDTOResponse;
import com.IBMIntenship.backend.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/technician")
public class TechnicianController {

    private final AuthServiceClient authServiceClient;

    @Autowired
    public TechnicianController(AuthServiceClient authServiceClient) {
        this.authServiceClient = authServiceClient;
    }


        @GetMapping("/all")
        public ResponseEntity<List<TechnicianDTOResponse>> getAllTechnicians() {
            List<TechnicianDTOResponse> technicians = authServiceClient.getAllTechnicians();
            return ResponseEntity.ok(technicians);
        }
}
