package com.IBMIntenship.backend.controller.authcontrollers;

import com.IBMIntenship.backend.model.authservicedtos.TechnicianDTOResponse;
import com.IBMIntenship.backend.service.authservices.TechnicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/technician")
public class TechnicianController {

    @Autowired
    private  TechnicianService technicianService;


    @GetMapping("/all")
    public ResponseEntity<List<TechnicianDTOResponse>> getAllTechnicians() {
        List<TechnicianDTOResponse> technicians = technicianService.getAllTechnicians();
        return ResponseEntity.ok(technicians);
    }


    @PutMapping("/removefromgroup/{technicianId}")
    public ResponseEntity<Void> removeTechnicianFromGroup(@PathVariable Long technicianId) {
        technicianService.removeTechnicianFromGroup(technicianId);
        return ResponseEntity.ok().build();
    }


}
