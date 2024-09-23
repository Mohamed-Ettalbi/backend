package com.IBMIntenship.backend.feign;

import com.IBMIntenship.backend.config.FeignConfig;
import com.IBMIntenship.backend.model.authservicedtos.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "auth-service", url = "${auth.service.url}", configuration = FeignConfig.class)
public interface AuthServiceClient {

    // User-related endpoints
            @GetMapping("/api/user/{id}")
            UserDTOResponse getUserById(@PathVariable Long id);

            @PutMapping("/api/user/{id}")
            UserDTOResponse updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO);

            @DeleteMapping("/api/user/{id}")
            void deleteUser(@PathVariable Long id);

            //    @PostMapping("/api/user/add")
            //    UserDTO addUser(@RequestBody UserDTO userDTO);

            @GetMapping("/api/user/all")
            List<UserDTOResponse> getAllUsers();


   // Group-related endpoints
            @GetMapping("/api/group/{id}")
            GroupDTO getGroupById(@PathVariable Long id);

            @PutMapping("/api/group/{id}")
            GroupDTO updateGroup(@PathVariable Long id, @RequestBody AddGroupDTORequest groupDTO);

            @DeleteMapping("/api/group/{id}")
            void deleteGroup(@PathVariable Long id);

            @PostMapping("/api/group/add")
            GroupDTO addGroup(@RequestBody AddGroupDTORequest groupRequest);

            @GetMapping("/api/group/groups")
            List<GroupDTO> getAllGroups();

            @GetMapping("/api/group/group/{id}/technicians")
            List<TechnicianDTOResponse> getTechniciansByGroupId(@PathVariable Long id);


    // Admin-related endpoints
            @PutMapping("/api/admin/groups/{groupId}/add/{technicianId}")
            TechnicianDTOResponse addTechnicianToGroup(@PathVariable Long groupId, @PathVariable Long technicianId);

            @PutMapping("/api/admin/disable/{id}")
            UserDTOResponse disableUser(@PathVariable Long id);

            @PutMapping("/api/admin/approve/{id}")
            UserDTOResponse approveUser(@PathVariable Long id);




    // Technician-related endpoints
            @PostMapping("/api/technician/add")
            TechnicianDTOResponse addTechnician(@RequestBody UserDTO technicianDTO);

            @PutMapping("/api/technician/removefromgrup/{technicianId}")
            Void removeTechnicianFromGroup(@PathVariable("technicianId") Long technicianId);

            @GetMapping("/api/technician/all")
            List<TechnicianDTOResponse> getAllTechnicians();


            @GetMapping("/api/technician")
            TechnicianDTOResponse getTechnicianByEmail(@RequestParam("email") String email);


    // Employee-related endpoints
            @PostMapping("/api/employee/add")
            UserDTOResponse addEmployee(@RequestBody UserDTO employeeDTO);

            @GetMapping("/api/employee/all")
            List<UserDTOResponse> getAllEmployees();

    // Auth-related endpoints
            @PostMapping("/api/auth/register")
            AuthResponse register(@RequestBody UserDTO userDTO );


            @PostMapping("/api/auth/login")
            AuthResponse login(@RequestBody AuthenticationRequest authRequest);
}
