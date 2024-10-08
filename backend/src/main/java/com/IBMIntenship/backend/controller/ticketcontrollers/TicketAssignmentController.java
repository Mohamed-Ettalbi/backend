package com.IBMIntenship.backend.controller.ticketcontrollers;


import com.IBMIntenship.backend.model.ticketservicedtos.TicketDTO;
import com.IBMIntenship.backend.service.ticketservices.TicketAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ticket/assignment")
public class TicketAssignmentController {

    @Autowired
    private TicketAssignmentService ticketAssignmentService;

    @PostMapping("/assignToUser/{ticketId}/{userId}")
    public ResponseEntity<TicketDTO> assignTicketToUser(
            @PathVariable Long ticketId,
            @PathVariable Long userId) {
        TicketDTO updatedTicket = ticketAssignmentService.assignTicketToUser(ticketId, userId);
        return ResponseEntity.ok(updatedTicket);
    }

    @PostMapping("/assignToGroup/{ticketId}/{groupId}")
    public ResponseEntity<TicketDTO> assignTicketToGroup(
            @PathVariable Long ticketId,
            @PathVariable Long groupId) {
        TicketDTO updatedTicket = ticketAssignmentService.assignTicketToGroup(ticketId, groupId);
        return ResponseEntity.ok(updatedTicket);
    }

    @PostMapping("/unassign/{ticketId}")
    public ResponseEntity<TicketDTO> unassignTicket(@PathVariable Long ticketId) {
        TicketDTO updatedTicket = ticketAssignmentService.unassignTicket(ticketId);
        return ResponseEntity.ok(updatedTicket);
    }
}
