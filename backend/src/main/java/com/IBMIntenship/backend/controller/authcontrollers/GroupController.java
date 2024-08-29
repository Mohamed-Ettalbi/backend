package com.IBMIntenship.backend.controller.authcontrollers;

import com.IBMIntenship.backend.model.authservicedtos.AddGroupDTORequest;
import com.IBMIntenship.backend.model.authservicedtos.GroupDTO;
import com.IBMIntenship.backend.model.authservicedtos.TechnicianDTOResponse;
import com.IBMIntenship.backend.service.authservices.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/group")
public class GroupController {
    @Autowired
    private  GroupService groupService;




    @PostMapping("/add")
    public ResponseEntity<GroupDTO> addGroup(@RequestBody AddGroupDTORequest group) {
        return  groupService.addGroup(group);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteGroup(@PathVariable Long id) {
        groupService.deleteGroup(id);
        return ResponseEntity.status(HttpStatus.OK).body("The group with the ID: " + id + " has been deleted");
    }

    @GetMapping("/all")
    public ResponseEntity<List<GroupDTO>> getAllGroups() {
        List<GroupDTO> groups = groupService.getAllGroups();
        return ResponseEntity.ok(groups);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupDTO> getGroupById(@PathVariable Long id) {
        GroupDTO groupDTO = groupService.getGroupById(id);
        return ResponseEntity.ok(groupDTO);
    }

    @GetMapping("/{id}/technicians")
    public ResponseEntity<List<TechnicianDTOResponse>> getTechniciansByGroupId(@PathVariable Long id) {
        List<TechnicianDTOResponse> technicians = groupService.getTechniciansByGroupId(id);
        return ResponseEntity.ok(technicians);
    }
}
