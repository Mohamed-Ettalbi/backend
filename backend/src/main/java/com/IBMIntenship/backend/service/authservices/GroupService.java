
package com.IBMIntenship.backend.service.authservices;

import com.IBMIntenship.backend.feign.AuthServiceClient;
import com.IBMIntenship.backend.model.authservicedtos.AddGroupDTORequest;
import com.IBMIntenship.backend.model.authservicedtos.GroupDTO;
import com.IBMIntenship.backend.model.authservicedtos.TechnicianDTOResponse;
import feign.FeignException;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class GroupService {

    @Autowired
    private  AuthServiceClient authServiceClient;

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);



    public ResponseEntity<GroupDTO> addGroup(AddGroupDTORequest group) {
        try{
        GroupDTO response =  authServiceClient.addGroup(group);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
         } catch (FeignException.Conflict e) {
        logger.error("Conflict error during registration for group: {}", group.getGroupName(), e);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    } catch (FeignException e) {
        logger.error("Error during registration for email: {}",  group.getGroupName(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }



    }

    public void deleteGroup(Long id) {
        authServiceClient.deleteGroup(id);
    }

    public List<GroupDTO> getAllGroups() {
        return authServiceClient.getAllGroups();
    }

    public GroupDTO getGroupById(Long id) {
        return authServiceClient.getGroupById(id);
    }

    public List<TechnicianDTOResponse> getTechniciansByGroupId(Long id) {
        return authServiceClient.getTechniciansByGroupId(id);
    }
}


