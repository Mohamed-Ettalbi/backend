package com.IBMIntenship.backend.service.ticketservices;

import com.IBMIntenship.backend.config.CorsConfig;
import com.IBMIntenship.backend.config.FeignConfig;
import com.IBMIntenship.backend.config.SecurityService;
import com.IBMIntenship.backend.exceptions.UnauthorizedAccessException;
import com.IBMIntenship.backend.feign.AuthServiceClient;
import com.IBMIntenship.backend.feign.TicketServiceClient;
import com.IBMIntenship.backend.model.authservicedtos.TechnicianDTOResponse;
import com.IBMIntenship.backend.model.authservicedtos.UserDTO;
import com.IBMIntenship.backend.model.ticketservicedtos.CreateTicketDTO;
import com.IBMIntenship.backend.model.ticketservicedtos.StatusUpdateDTO;
import com.IBMIntenship.backend.model.ticketservicedtos.TicketDTO;
import com.IBMIntenship.backend.model.ticketservicedtos.UpdateTicketDTO;
import jdk.jshell.Snippet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.Boolean.TRUE;

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
        logger.debug("Checking role for ROLE_ADMIN");
        if (securityServiceye.validateTokenAndRole("ROLE_ADMIN")) {
            updateTicketDTO.setTicketOwnerEmail("admin@gmail.com");

            return ticketServiceClient.updateTicket(id, updateTicketDTO);
        }else if ( securityServiceye.validateTokenAndRole("ROLE_EMPLOYEE")){
            String token = feignConfig.getJwtToken();
            logger.debug("token is there {}" , token );
            String ownerEmail = securityServiceye.getUserDetailsFromToken(token).getEmail();
            logger.debug("ownerEmail is there {}" , ownerEmail );

            updateTicketDTO.setTicketOwnerEmail(ownerEmail);
            return ticketServiceClient.updateTicket(id, updateTicketDTO);
        }



    else throw new UnauthorizedAccessException("Unauthorized access");
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
            throw new UnauthorizedAccessException("Access denied for getAllTickets - user is not an admin");
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
                throw new UnauthorizedAccessException("No technician found with email: " + email);
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


        } else if (securityServiceye.validateTokenAndRole("ROLE_ADMIN")) {
            List<TicketDTO> tickets = ticketServiceClient.getAllTickets();
            TechnicianDTOResponse technician = authServiceClient.getTechnicianByEmail(email);

            if (technician == null) {
                logger.error("No technician found with email: {}", email);
                throw new UnauthorizedAccessException("No technician found with email: " + email);
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


    }{
            logger.error("Access denied for getTicketsByUserEmail - user does not have a valid role");
            throw new UnauthorizedAccessException("Unauthorized access: Missing or invalid token or You Don't have the Required ROle");        }
    }





        public TicketDTO createTicket(CreateTicketDTO createTicketDTO ){
            String token = feignConfig.getJwtToken();
            String createdBy = securityServiceye.getUserDetailsFromToken(token).getEmail();

            return ticketServiceClient.createTicket(createTicketDTO, createdBy);
        }

        public TicketDTO getTicketById(Long id) {
            TicketDTO ticket = ticketServiceClient.getTicketById(id);
            if (ticket == null) {
                throw new UnauthorizedAccessException("Access denied for getTicketById");
            }

            String token = feignConfig.getJwtToken();
            String email = securityServiceye.getUserDetailsFromToken(token).getEmail();

            // Check role-based access
            if (securityServiceye.validateTokenAndRole("ROLE_ADMIN")) {
                logger.debug("User has ROLE_ADMIN, granting access to the ticket.");
                return ticket;
            } else if (securityServiceye.validateTokenAndRole("ROLE_EMPLOYEE")) {
                // Check if the ticket was created by the employee
                if (ticket.getCreatedBy().equals(email)) {
                    logger.debug("User has ROLE_EMPLOYEE, and ticket was created by them. Granting access.");
                    return ticket;
                } else {
                    logger.error("Access denied: Employee does not have permission to view ticket ID: {}", id);
                    throw new UnauthorizedAccessException("Access denied: You do not have permission to view this ticket.");
                }
            }else if (securityServiceye.validateTokenAndRole("ROLE_TECHNICIAN")) {
                // Check if the ticket is assigned to the technician's group
                TechnicianDTOResponse technician = authServiceClient.getTechnicianByEmail(email);
                if (technician == null || technician.getGroupId() == null) {
                    logger.error("Access denied: No technician or group found for email: {}", email);
                    throw new UnauthorizedAccessException("Access denied: No technician or group found for email: " + email);
                }

                Long groupId = technician.getGroupId();
                if (ticket.getAssignedGroup() != null && ticket.getAssignedGroup().equals(groupId)) {
                    logger.debug("User has ROLE_TECHNICIAN, and ticket is assigned to their group. Granting access.");
                    return ticket;
                } else {
                    logger.error("Access denied: Technician does not have permission to view ticket ID: {}", id);
                    throw new UnauthorizedAccessException("Access denied: You do not have permission to view this ticket.");
                }
            } else {
                logger.error("Access denied: User does not have a valid role to view ticket ID: {}", id);
                throw new UnauthorizedAccessException("Access denied: You do not have the required role to view this ticket.");
            }
        }



        public TicketDTO updateStatus(Long ticketId, StatusUpdateDTO statusUpdate ) {


            if (securityServiceye.validateTokenAndRole("ROLE_ADMIN")) {
                StatusUpdateDTO statusUpdateDTO = new StatusUpdateDTO();
                statusUpdateDTO.setAdmin(true);
                statusUpdateDTO.setStatus(statusUpdate.getStatus());
                statusUpdateDTO.setStatusUpdatedBy(statusUpdate.getStatusUpdatedBy());
                logger.debug("UPdating status by admin  is : {}" ,statusUpdateDTO);

                return    ticketServiceClient.updateTicketStatus(ticketId,statusUpdateDTO);
            }else if (securityServiceye.validateTokenAndRole("ROLE_TECHNICIAN")){

                Long technicianGroupId = authServiceClient.getTechnicianByEmail(statusUpdate.getStatusUpdatedBy()).getGroupId();
                logger.debug("technicianGroupId is : {}" ,technicianGroupId);
                StatusUpdateDTO statusUpdateDTO = new StatusUpdateDTO();
                statusUpdateDTO.setAdmin(false);
                statusUpdateDTO.setStatus(statusUpdate.getStatus());
                statusUpdateDTO.setStatusUpdatedBy(statusUpdate.getStatusUpdatedBy());

                statusUpdate.setTechnicianGroupId(technicianGroupId);
                logger.debug("UPdating status by Non admin   Content : {}",statusUpdate.getStatusUpdatedBy());
                return ticketServiceClient.updateTicketStatus(ticketId,statusUpdate);
            }
            else throw new UnauthorizedAccessException("Access denied for getTicketById");
        }







    public void deleteTicket(Long id) {
        TicketDTO ticket = ticketServiceClient.getTicketById(id);
        if (ticket == null) {
            throw new UnauthorizedAccessException("Ticket not found or access denied.");
        }

        String token = feignConfig.getJwtToken();
        String email = securityServiceye.getUserDetailsFromToken(token).getEmail();

        if (securityServiceye.validateTokenAndRole("ROLE_ADMIN")) {
            logger.debug("Admin user {} is deleting ticket with ID: {}", email, id);
            ticketServiceClient.deleteTicket(id);
            logger.info("Ticket ID {} deleted successfully by admin {}", id, email);
        } else if (securityServiceye.validateTokenAndRole("ROLE_EMPLOYEE")) {
            if (ticket.getCreatedBy().equals(email)) {
                logger.debug("Employee {} is deleting their own ticket with ID: {}", email, id);
                ticketServiceClient.deleteTicket(id);
                logger.info("Ticket ID {} deleted successfully by employee {}", id, email);
            } else {
                logger.error("Access denied: Employee {} does not have permission to delete ticket ID {}", email, id);
                throw new UnauthorizedAccessException("Access denied: You do not have permission to delete this ticket.");
            }
        } else {
            logger.error("Access denied: User {} does not have the required role to delete ticket ID {}", email, id);
            throw new UnauthorizedAccessException("Access denied: You do not have the required role to delete this ticket.");
        }
    }

}



