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
            UserDTO getUserById(@PathVariable Long id);

            @PutMapping("/api/user/{id}")
            UserDTO updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO);

            @DeleteMapping("/api/user/{id}")
            void deleteUser(@PathVariable Long id);

            //    @PostMapping("/api/user/add")
            //    UserDTO addUser(@RequestBody UserDTO userDTO);

            @GetMapping("/api/user/all")
            List<UserDTO> getAllUsers();

   // Group-related endpoints
            @GetMapping("/api/group/{id}")
            GroupDTO getGroupById(@PathVariable Long id);

            @PutMapping("/api/group/{id}")
            void updateGroup(@PathVariable Long id, @RequestBody GroupDTO groupDTO);

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
            UserDTO disableUser(@PathVariable Long id);

            @PutMapping("/api/admin/approve/{id}")
            UserDTO approveUser(@PathVariable Long id);




    // Technician-related endpoints
            @PostMapping("/api/technician/add")
            TechnicianDTOResponse addTechnician(@RequestBody UserDTO technicianDTO);

            @GetMapping("/api/technician/all")
            List<TechnicianDTOResponse> getAllTechnicians();

            // Employee-related endpoints
            @PostMapping("/api/employee/add")
            UserDTO addEmployee(@RequestBody UserDTO employeeDTO);

            @GetMapping("/api/employee/all")
            List<UserDTO> getAllEmployees();

    // Auth-related endpoints
            @PostMapping("/api/auth/register")
            AuthResponse register(@RequestBody UserDTO userDTO );


            @PostMapping("/api/auth/login")
            AuthResponse login(@RequestBody AuthenticationRequest authRequest);
}
