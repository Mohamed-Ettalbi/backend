package com.IBMIntenship.backend.controller;

import com.IBMIntenship.backend.feign.AuthServiceClient;
import com.IBMIntenship.backend.model.AddGroupDTORequest;
import com.IBMIntenship.backend.model.GroupDTO;
import com.IBMIntenship.backend.model.TechnicianDTOResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/group")
public class GroupController {


    private final AuthServiceClient authServiceClient;

    @Autowired
    public GroupController(AuthServiceClient authServiceClient) {
        this.authServiceClient = authServiceClient;
    }

    @PostMapping("/add")
    public ResponseEntity<GroupDTO> addGroup(@RequestBody AddGroupDTORequest group) {
        GroupDTO groupDTO = authServiceClient.addGroup(group);
        return ResponseEntity.ok(groupDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteGroup(@PathVariable Long id) {
        authServiceClient.deleteGroup(id);
         return ResponseEntity.status(HttpStatus.OK).body("The user with the ID: " + id + " has been deleted");

    }

    @GetMapping("/all")
    public ResponseEntity<List<GroupDTO>> getAllGroups() {
        List<GroupDTO> groups = authServiceClient.getAllGroups();
        return ResponseEntity.ok(groups);
    }
    @GetMapping("/{id}")
    public ResponseEntity<GroupDTO> getGroupById(@PathVariable Long id) {
        GroupDTO groupDTO = authServiceClient.getGroupById(id);
        return ResponseEntity.ok(groupDTO);
    }


    // Get Technicians by Group ID
    @GetMapping("/{id}/technicians")
    public ResponseEntity<List<TechnicianDTOResponse>> getTechniciansByGroupId(@PathVariable Long id) {
        List<TechnicianDTOResponse> technicians = authServiceClient.getTechniciansByGroupId(id);
        return ResponseEntity.ok(technicians);
    }




}


