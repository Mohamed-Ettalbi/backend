package com.IBMIntenship.backend.service.authservices;

import com.IBMIntenship.backend.feign.AuthServiceClient;
import com.IBMIntenship.backend.model.authservicedtos.TechnicianDTOResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TechnicianService {

    @Autowired
    private  AuthServiceClient authServiceClient;




    public List<TechnicianDTOResponse> getAllTechnicians() {
        return authServiceClient.getAllTechnicians();
    }
}
