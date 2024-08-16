package com.IBMIntenship.backend.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TechnicianDTOResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private Boolean isApproved ;

    private String groupId;




}


