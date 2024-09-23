package com.IBMIntenship.backend.service.ticketservices;

import com.IBMIntenship.backend.config.SecurityService;
import com.IBMIntenship.backend.exceptions.GroupIdNullException;
import com.IBMIntenship.backend.exceptions.RestApiClientException;
import com.IBMIntenship.backend.exceptions.UnauthorizedAccessException;
import com.IBMIntenship.backend.feign.AuthServiceClient;
import com.IBMIntenship.backend.feign.TicketServiceClient;
import com.IBMIntenship.backend.model.ticketservicedtos.TicketDTO;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;


@Service
public class TicketAssignmentService {

    @Autowired
    private TicketServiceClient ticketServiceClient;

    @Autowired
    private SecurityService securityServiceye;
    @Autowired
    private AuthServiceClient authServiceClient;

    public TicketDTO unassignTicket(Long ticketId) {
        // Forward the request to the ticket-service via Feign
        return ticketServiceClient.unassignTicket(ticketId);
    }

    public TicketDTO assignTicketToUser(Long ticketId, String userEmail) {

        if (securityServiceye.validateTokenAndRole("ROLE_TECHNICIAN")) {

            if (authServiceClient.getTechnicianByEmail(userEmail).getGroupId() != null) {
                Long technicianGroupId = authServiceClient.getTechnicianByEmail(userEmail).getGroupId();
                return ticketServiceClient.assignTicketToUser(ticketId, userEmail, technicianGroupId);
            } else {
                throw new GroupIdNullException("group id is null");
            }
        }
            if (securityServiceye.validateTokenAndRole("ROLE_ADMIN")) {

                Long technicianGroupId = authServiceClient.getTechnicianByEmail(userEmail).getGroupId();
                return ticketServiceClient.assignTicketToUser(ticketId, userEmail, technicianGroupId);

            } else throw new UnauthorizedAccessException("you dont have permission to access this endpoint");
        }


    public TicketDTO assignTicketToGroup(Long ticketId, Long groupId) {
        // Forward the request to the ticket-service via Feign
        if (securityServiceye.validateTokenAndRole("ROLE_ADMIN")) {
            return ticketServiceClient.assignTicketToGroup(ticketId, groupId);
        } else throw new UnauthorizedAccessException("you dont have permission to access this endpoint");
    }
}
