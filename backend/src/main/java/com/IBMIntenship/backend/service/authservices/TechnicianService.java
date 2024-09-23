package com.IBMIntenship.backend.service.authservices;

import com.IBMIntenship.backend.feign.AuthServiceClient;
import com.IBMIntenship.backend.model.authservicedtos.TechnicianDTOResponse;
import com.IBMIntenship.backend.service.ticketservices.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TechnicianService {

    private static final Logger logger = LoggerFactory.getLogger(TicketService.class);


    @Autowired
    private  AuthServiceClient authServiceClient;




    public List<TechnicianDTOResponse> getAllTechnicians() {
        return authServiceClient.getAllTechnicians();
    }



    public void removeTechnicianFromGroup(Long technicianId) {

        logger.debug("Removing technician from group");
          authServiceClient.removeTechnicianFromGroup(technicianId);
}

}
