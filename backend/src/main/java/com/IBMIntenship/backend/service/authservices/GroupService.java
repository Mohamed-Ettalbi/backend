
package com.IBMIntenship.backend.service.authservices;

import com.IBMIntenship.backend.config.FeignConfig;
import com.IBMIntenship.backend.config.SecurityService;
import com.IBMIntenship.backend.exceptions.UnauthorizedAccessException;
import com.IBMIntenship.backend.feign.AuthServiceClient;
import com.IBMIntenship.backend.feign.TicketServiceClient;
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

    @Autowired
    private SecurityService securityServiceye;


    @Autowired
    private FeignConfig feignConfig;

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

        String token = feignConfig.getJwtToken();
        String email = securityServiceye.getUserDetailsFromToken(token).getEmail();
        if (securityServiceye.validateTokenAndRole("ROLE_ADMIN")) {
            logger.debug("Admin user requesting group by ID: {}", id);

            return authServiceClient.getGroupById(id);
        }
        if (securityServiceye.validateTokenAndRole("ROLE_TECHNICIAN")) {
            logger.debug("Technician requesting their group details. Email: {}", email);

            // Fetch technician details by email
            TechnicianDTOResponse technician = authServiceClient.getTechnicianByEmail(email);
            if (technician == null) {
                logger.error("No technician found for email: {}", email);
                throw new UnauthorizedAccessException("No technician found for the provided email");
            }
            Long groupId = technician.getGroupId();
            if (groupId == null) {
                logger.error("Technician with email: {} is not assigned to any group", email);
                throw new UnauthorizedAccessException("Technician is not assigned to any group");
            }

            logger.debug("Fetching group by ID: {} for technician", groupId);
            return authServiceClient.getGroupById(groupId);
        }
        logger.debug("Access denied for user  to access group details" );
        throw new UnauthorizedAccessException("Access denied: You don't have permission to access this group");
    }


    public List<TechnicianDTOResponse> getTechniciansByGroupId(Long id) {
        return authServiceClient.getTechniciansByGroupId(id);
    }

    public GroupDTO updateGroupInfo(Long id, AddGroupDTORequest group) {
        return authServiceClient.updateGroup(id, group);
    }
}


