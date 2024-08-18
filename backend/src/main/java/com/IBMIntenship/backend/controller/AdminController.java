package com.IBMIntenship.backend.controller;

import com.IBMIntenship.backend.feign.AuthServiceClient;
import com.IBMIntenship.backend.model.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final AuthServiceClient authServiceClient;

    public AdminController(AuthServiceClient authServiceClient) {
        this.authServiceClient = authServiceClient;
    }

    // Approve a user by ID
    @PutMapping("/approve/{id}")
    public ResponseEntity<UserDTO> approveUser(@PathVariable Long id) {
        UserDTO userDTO = authServiceClient.approveUser(id);
        return new ResponseEntity<>(userDTO,HttpStatus.OK);
    }

    // Disable a user by ID
    @PutMapping("/disable/{id}")
    public ResponseEntity<UserDTO> disableUser(@PathVariable Long id) {
        UserDTO userDTO = authServiceClient.disableUser(id);
        return new ResponseEntity<>(userDTO,HttpStatus.OK);
    }

    // Add a technician to a group
    @PutMapping("/groups/{groupId}/add/{technicianId}")
    public ResponseEntity<String> addTechnicianToGroup(@PathVariable Long groupId, @PathVariable Long technicianId) {
        authServiceClient.addTechnicianToGroup(groupId, technicianId);
        return ResponseEntity.status(HttpStatus.OK).body("Technician with ID " + technicianId + " has been added to group with ID " + groupId + ".");
    }
}
