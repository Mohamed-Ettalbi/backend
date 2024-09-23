package com.IBMIntenship.backend.service.authservices;

import com.IBMIntenship.backend.feign.AuthServiceClient;
import com.IBMIntenship.backend.model.authservicedtos.UserDTO;
import com.IBMIntenship.backend.model.authservicedtos.UserDTOResponse;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private AuthServiceClient authServiceClient;


    public UserDTOResponse approveUser(Long id) {
        // Call the AuthServiceClient to approve the user
            return authServiceClient.approveUser(id);

    }

    public UserDTOResponse disableUser(Long id) {
        // Call the AuthServiceClient to disable the user
        return authServiceClient.disableUser(id);
    }

    public void addTechnicianToGroup(Long groupId, Long technicianId) {
        // Call the AuthServiceClient to add the technician to the group
        authServiceClient.addTechnicianToGroup(groupId, technicianId);
    }
}

