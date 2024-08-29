package com.IBMIntenship.backend.service.authservices;


import com.IBMIntenship.backend.feign.AuthServiceClient;
import com.IBMIntenship.backend.model.authservicedtos.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private  AuthServiceClient authServiceClient;

    public List<UserDTO> getAllEmployees() {
        return authServiceClient.getAllEmployees();
    }
}

