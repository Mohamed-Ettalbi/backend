package com.IBMIntenship.backend.controller;

import com.IBMIntenship.backend.feign.AuthServiceClient;
import com.IBMIntenship.backend.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    private final AuthServiceClient authServiceClient;

    @Autowired
    public EmployeeController(AuthServiceClient authServiceClient) {
        this.authServiceClient = authServiceClient;
    }

//    @PostMapping("/add")
//    public ResponseEntity<UserDTO> addEmployee(@RequestBody UserDTO employeeDTO) {
//        UserDTO createdEmployee = authServiceClient.addEmployee(employeeDTO);
//        return ResponseEntity.ok(createdEmployee);
//    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllEmployees() {
        List<UserDTO> employees = authServiceClient.getAllEmployees();
        return ResponseEntity.ok(employees);
        //   // Endpoint to update an existing employee
//    @PutMapping("/{id}")
//    public ResponseEntity<UserDTO> updateEmployee(@PathVariable Long id, @RequestBody UserDTO employeeDTO) {
//        authServiceClient.updateUser(id, employeeDTO);
//        return ResponseEntity.ok(employeeDTO);
//    }
//
//    // Endpoint to delete an employee
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
//        authServiceClient.deleteUser(id);
//        return ResponseEntity.noContent().build();
//    }
//
//    // Endpoint to get a specific employee by ID
//    @GetMapping("/{id}")
//    public ResponseEntity<UserDTO> getEmployeeById(@PathVariable Long id) {
//        UserDTO employee = authServiceClient.getUserById(id);
//        return ResponseEntity.ok(employee);
//    }
//
//}
    }

}

