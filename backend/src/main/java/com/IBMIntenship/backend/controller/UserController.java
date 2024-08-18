package com.IBMIntenship.backend.controller;

import com.IBMIntenship.backend.feign.AuthServiceClient;
import com.IBMIntenship.backend.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final AuthServiceClient authServiceClient;

    @Autowired
    public UserController(AuthServiceClient authServiceClient) {
        this.authServiceClient = authServiceClient;
    }



    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(authServiceClient.getUserById(id));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(authServiceClient.updateUser(id,userDTO));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {

        return ResponseEntity.ok(authServiceClient.getAllUsers());
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        authServiceClient.deleteUser(id);
        return ResponseEntity.ok("The user with the ID : " +id +"has been deleted");
    }

}

