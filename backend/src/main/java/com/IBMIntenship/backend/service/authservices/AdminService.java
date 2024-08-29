package com.IBMIntenship.backend.service.authservices;

import com.IBMIntenship.backend.feign.AuthServiceClient;
import com.IBMIntenship.backend.model.authservicedtos.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private AuthServiceClient authServiceClient;

    public UserDTO approveUser(Long id) {
        // Call the AuthServiceClient to approve the user
        return authServiceClient.approveUser(id);
    }

    public UserDTO disableUser(Long id) {
        // Call the AuthServiceClient to disable the user
        return authServiceClient.disableUser(id);
    }

    public void addTechnicianToGroup(Long groupId, Long technicianId) {
        // Call the AuthServiceClient to add the technician to the group
        authServiceClient.addTechnicianToGroup(groupId, technicianId);
    }
}

