package com.IBMIntenship.backend.controller.authcontrollers;

import com.IBMIntenship.backend.feign.AuthServiceClient;
import com.IBMIntenship.backend.model.authservicedtos.UserDTO;
import com.IBMIntenship.backend.service.authservices.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {


    @Autowired
    private AdminService adminService;



    // Approve a user by ID
    @PutMapping("/approve/{id}")
    public ResponseEntity<UserDTO> approveUser(@PathVariable Long id) {
        UserDTO userDTO = adminService.approveUser(id);
        return new ResponseEntity<>(userDTO,HttpStatus.OK);
    }

    // Disable a user by ID
    @PutMapping("/disable/{id}")
    public ResponseEntity<UserDTO> disableUser(@PathVariable Long id) {
        UserDTO userDTO = adminService.disableUser(id);
        return new ResponseEntity<>(userDTO,HttpStatus.OK);
    }

    // Add a technician to a group
    @PutMapping("/groups/{groupId}/add/{technicianId}")
    public ResponseEntity<String> addTechnicianToGroup(@PathVariable Long groupId, @PathVariable Long technicianId) {
        adminService.addTechnicianToGroup(groupId, technicianId);
        return ResponseEntity.status(HttpStatus.OK).body("Technician with ID " + technicianId + " has been added to group with ID " + groupId + ".");
    }
}
