package com.IBMIntenship.backend.service.ticketservices;

import com.IBMIntenship.backend.config.CorsConfig;
import com.IBMIntenship.backend.config.FeignConfig;
import com.IBMIntenship.backend.config.SecurityService;
import com.IBMIntenship.backend.feign.TicketServiceClient;
import com.IBMIntenship.backend.model.ticketservicedtos.CreateTicketDTO;
import com.IBMIntenship.backend.model.ticketservicedtos.TicketDTO;
import com.IBMIntenship.backend.model.ticketservicedtos.UpdateTicketDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

    @Service
    public class TicketService {

        @Autowired
        private SecurityService securityServiceye;

        @Autowired
        private TicketServiceClient ticketServiceClient;
        @Autowired
        private FeignConfig feignConfig;



        public TicketDTO updateTicketWithValidation(Long id, UpdateTicketDTO updateTicketDTO) {

            // Forward the request to the Feign client
          Boolean isValidated = securityServiceye.validateTokenAndRole("ROLE_ADMIN");
           if (isValidated) {
            return ticketServiceClient.updateTicket(id, updateTicketDTO);
        }
    else throw new  RuntimeException("you dont have permission to access this endpoint");
        }

        public List<TicketDTO> getAllTickets() {
            Boolean isValidated = securityServiceye.validateTokenAndRole("ROLE_ADMIN");
            if (isValidated) {
                List<TicketDTO> tickets = ticketServiceClient.getAllTickets();
                return tickets;
        }else throw new  RuntimeException("you dont have permission to access this endpoint");
        }

        public TicketDTO createTicket(CreateTicketDTO createTicketDTO ){
            String token = feignConfig.getJwtToken();
            String createdBy = securityServiceye.getUserDetailsFromToken(token).getEmail();

            return ticketServiceClient.createTicket(createTicketDTO, createdBy);
        }

        public TicketDTO getTicketById(Long id) {
            // Example: Additional error handling or transformation
            TicketDTO ticket = ticketServiceClient.getTicketById(id);
            if (ticket == null) {
                throw new RuntimeException("Ticket not found");
            }
            return ticket;
        }

        public void deleteTicket(Long id) {
            ticketServiceClient.deleteTicket(id);
        }
    }



