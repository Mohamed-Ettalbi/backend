package com.IBMIntenship.backend.service.authservices;


import com.IBMIntenship.backend.feign.AuthServiceClient;
import com.IBMIntenship.backend.model.authservicedtos.UserDTO;
import com.IBMIntenship.backend.model.authservicedtos.UserDTOResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

    @Service
    public class UserService {

        @Autowired
        private  AuthServiceClient authServiceClient;

        public UserDTOResponse getUserById(Long id) {
            return authServiceClient.getUserById(id);
        }

        public UserDTOResponse updateUser(Long id, UserDTO userDTO) {
            return authServiceClient.updateUser(id, userDTO);
        }

        public List<UserDTOResponse> getAllUsers() {
            return authServiceClient.getAllUsers();
        }

        public void deleteUser(Long id) {
            authServiceClient.deleteUser(id);
        }
    }


