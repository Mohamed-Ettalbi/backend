package com.IBMIntenship.backend.controller.ticketcontrollers;


import com.IBMIntenship.backend.feign.TicketServiceClient;
import com.IBMIntenship.backend.model.ticketservicedtos.CreateTicketDTO;
import com.IBMIntenship.backend.model.ticketservicedtos.TicketDTO;
import com.IBMIntenship.backend.model.ticketservicedtos.UpdateTicketDTO;
import com.IBMIntenship.backend.service.ticketservices.TicketService;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketServiceClient ticketServiceClient;

    @Autowired
    private TicketService ticketService;

    @PutMapping("/update/{id}")
    public ResponseEntity<TicketDTO> updateTicket(@PathVariable("id") Long id, @RequestBody UpdateTicketDTO updateTicketDTO) {
        // Use service layer for additional logic
        TicketDTO updatedTicket = ticketService.updateTicketWithValidation(id, updateTicketDTO);
        return ResponseEntity.ok(updatedTicket);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TicketDTO>> getAllTickets(@RequestParam(value = "email", required = false) String email) {

        List<TicketDTO> tickets;
        if (email != null) {

            tickets = ticketService.getTicketsByUserEmail(email);
        } else {
            // Logic to get all tickets
            tickets = ticketService.getAllTickets();
        }
        return ResponseEntity.ok(tickets);
    }

    @PostMapping
    public ResponseEntity<TicketDTO> createTicket(@RequestBody CreateTicketDTO createTicketDTO) {
        // Directly call the Feign client if no additional logic is needed
        TicketDTO newTicket = ticketService.createTicket(createTicketDTO );
        return ResponseEntity.ok(newTicket);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketDTO> getTicketById(@PathVariable("id") Long id) {
        // Use service layer for additional logic
        TicketDTO ticket = ticketService.getTicketById(id);
        return ResponseEntity.ok(ticket);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTicket(@PathVariable("id") Long id) {
        // Use service layer for additional logic
        ticketService.deleteTicket(id);
        return ResponseEntity.ok("Ticket deleted successfully");
    }
}
