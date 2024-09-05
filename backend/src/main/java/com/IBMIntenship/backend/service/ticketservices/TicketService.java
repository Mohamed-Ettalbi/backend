package com.IBMIntenship.backend.service.ticketservices;

import com.IBMIntenship.backend.config.CorsConfig;
import com.IBMIntenship.backend.config.FeignConfig;
import com.IBMIntenship.backend.config.SecurityService;
import com.IBMIntenship.backend.feign.AuthServiceClient;
import com.IBMIntenship.backend.feign.TicketServiceClient;
import com.IBMIntenship.backend.model.authservicedtos.TechnicianDTOResponse;
import com.IBMIntenship.backend.model.authservicedtos.UserDTO;
import com.IBMIntenship.backend.model.ticketservicedtos.CreateTicketDTO;
import com.IBMIntenship.backend.model.ticketservicedtos.TicketDTO;
import com.IBMIntenship.backend.model.ticketservicedtos.UpdateTicketDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
    public class TicketService {

        @Autowired
        private SecurityService securityServiceye;

        @Autowired
        private AuthServiceClient authServiceClient;

        @Autowired
        private TicketServiceClient ticketServiceClient;
        @Autowired
        private FeignConfig feignConfig;

    private static final Logger logger = LoggerFactory.getLogger(TicketService.class);



    public TicketDTO updateTicketWithValidation(Long id, UpdateTicketDTO updateTicketDTO) {

            // Forward the request to the Feign client
          Boolean isValidated = securityServiceye.validateTokenAndRole("ROLE_EMPLOYEE");
           if (isValidated) {
            return ticketServiceClient.updateTicket(id, updateTicketDTO);
        }
    else throw new  RuntimeException("you dont have permission to access this endpoint");
        }

//        public List<TicketDTO> getAllTickets() {
//
//            if (securityServiceye.validateTokenAndRole("ROLE_ADMIN")) {
//                List<TicketDTO> tickets = ticketServiceClient.getAllTickets();
//                return tickets;
//
//
//            } else throw new  RuntimeException(" Access denied");
//        }
//        public List<TicketDTO>  getTicketsByUserEmail(String email) {
//            if(securityServiceye.validateTokenAndRole("ROLE_EMPLOYEE")){
//
//                List<TicketDTO> tickets = ticketServiceClient.getAllTickets();
//                List<TicketDTO> filteredTickets = tickets.stream()
//                        .filter(ticket -> ticket.getCreatedBy().equals(email))
//                        .collect(Collectors.toList());
//                return filteredTickets;
//
//            }else if (securityServiceye.validateTokenAndRole("ROLE_TECHNICIAN"))
//            {
//                List<TicketDTO> tickets = ticketServiceClient.getAllTickets();
//
//               List< TechnicianDTOResponse> technicians = authServiceClient.getAllTechnicians();
//
//               Optional<TechnicianDTOResponse> ourTechnicians = technicians.stream()
//                        .filter(technician -> technician.getEmail().equals(email))
//                        .findFirst();
//
//
//               String groupId = ourTechnicians.get().getGroupId();
//                List<TicketDTO> filteredTickets = tickets.stream()
//                        .filter(ticket -> ticket.getAssignedGroup().equals(groupId))
//                        .collect(Collectors.toList());
//
//                return filteredTickets;
//
//            }else throw new  RuntimeException(" Access denied");
//
//
//
//        }


    public List<TicketDTO> getAllTickets() {
        logger.debug("Checking role for ROLE_ADMIN");
        if (securityServiceye.validateTokenAndRole("ROLE_ADMIN")) {
            logger.debug("Role validated for ROLE_ADMIN, fetching all tickets.");
            List<TicketDTO> tickets = ticketServiceClient.getAllTickets();
            logger.debug("Fetched {} tickets.", tickets.size());
            return tickets;
        } else {
            logger.error("Access denied for getAllTickets - user is not an admin");
            throw new RuntimeException("Access denied");
        }
    }

    public List<TicketDTO> getTicketsByUserEmail(String email) {
        logger.debug("Attempting to fetch tickets for user with email: {}", email);
        logger.debug("Checking role for ROLES");
        if (securityServiceye.validateTokenAndRole("ROLE_EMPLOYEE")) {
            logger.debug("Validated ROLE_EMPLOYEE, fetching all tickets to filter by email.");
            List<TicketDTO> tickets = ticketServiceClient.getAllTickets();
            List<TicketDTO> filteredTickets = tickets.stream()
                    .filter(ticket -> ticket.getCreatedBy().equals(email))
                    .collect(Collectors.toList());
            logger.debug("Filtered down to {} tickets for employee with email {}", filteredTickets.size(), email);
            return filteredTickets;

        } else if (securityServiceye.validateTokenAndRole("ROLE_TECHNICIAN")) {
            logger.debug("Validated ROLE_TECHNICIAN, fetching all tickets and technician details.");
            List<TicketDTO> tickets = ticketServiceClient.getAllTickets();
            TechnicianDTOResponse technician = authServiceClient.getTechnicianByEmail(email);



            if (technician == null) {
                logger.error("No technician found with email: {}", email);
                throw new RuntimeException("Technician not found");
            }
            Long groupId = technician.getGroupId();

            List<TicketDTO> filteredTickets = tickets.stream()
                    .filter(ticket -> ticket.getAssignedGroup() != null && ticket.getAssignedGroup().equals(groupId))
                    .collect(Collectors.toList());

            if (filteredTickets.isEmpty()) {
                logger.debug("No tickets found for technician group ID {}", groupId);
            } else {
                logger.debug("Filtered down to {} tickets for technician group ID {}", filteredTickets.size(), groupId);
            }

            return filteredTickets;


        } else {
            logger.error("Access denied for getTicketsByUserEmail - user does not have a valid role");
            throw new RuntimeException("Access denied");
        }
    }





        public TicketDTO createTicket(CreateTicketDTO createTicketDTO ){
            String token = feignConfig.getJwtToken();
            String createdBy = securityServiceye.getUserDetailsFromToken(token).getEmail();

            return ticketServiceClient.createTicket(createTicketDTO, createdBy);
        }

        public TicketDTO getTicketById(Long id) {
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



