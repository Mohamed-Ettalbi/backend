package com.IBMIntenship.backend.service.ticketservices;

import com.IBMIntenship.backend.feign.TicketServiceClient;
import com.IBMIntenship.backend.model.ticketservicedtos.TicketDTO;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;


@Service
public class TicketAssignmentService {

    @Autowired
    private TicketServiceClient ticketServiceClient;

    public TicketDTO unassignTicket(Long ticketId) {
        // Forward the request to the ticket-service via Feign
        return ticketServiceClient.unassignTicket(ticketId);
    }

    public TicketDTO assignTicketToUser(Long ticketId, Long userId) {
        // Forward the request to the ticket-service via Feign
        return ticketServiceClient.assignTicketToUser(ticketId, userId);
    }

    public TicketDTO assignTicketToGroup(Long ticketId, Long groupId) {
        // Forward the request to the ticket-service via Feign
        return ticketServiceClient.assignTicketToGroup(ticketId, groupId);
    }
}
