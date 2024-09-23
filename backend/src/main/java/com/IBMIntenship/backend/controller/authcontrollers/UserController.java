package com.IBMIntenship.backend.controller.authcontrollers;

import com.IBMIntenship.backend.model.authservicedtos.UserDTOResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import com.IBMIntenship.backend.model.authservicedtos.UserDTO;
import com.IBMIntenship.backend.service.authservices.UserService;

        import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private  UserService userService;




    @GetMapping("/{id}")
    public ResponseEntity<UserDTOResponse> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserDTOResponse> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateUser(id, userDTO));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDTOResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("The user with the ID : " + id + " has been deleted");
    }
}


