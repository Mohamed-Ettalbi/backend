package com.IBMIntenship.backend.controller.authcontrollers;

import com.IBMIntenship.backend.model.authservicedtos.UserDTO;
import com.IBMIntenship.backend.service.authservices.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    private  EmployeeService employeeService;



    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllEmployees() {
        List<UserDTO> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }
}
