package com.IBMIntenship.backend.model.authservicedtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTOResponse {

    private Long id;


    @NotBlank(message = "first name can not be blank")
    @Size(min = 2, max = 50)
    @Pattern(regexp = "^[a-zA-Z\\s-]+$", message = "message = \"Name can only contain letters, spaces, or hyphens")
    private String firstName;

    @NotBlank(message = "last name can not be blank ")
    @Size(min = 2, max = 50)
    @Pattern(regexp = "^[a-zA-Z\\s-]+$", message = "message = \"Name can only contain letters, spaces, or hyphens")
    private String lastName;

    @NotBlank(message = "this field can not be blank")
    @Email(message = "this must be a valid email")
    @Size(max = 100)
    private String email;

    @NotBlank(message = "this field can not be blank")
    @Pattern(regexp = "^\\d{10}$", message = "Don't add the country code just the number straight")
    private String phone;

    @NotBlank(message = "this field can not be blank")
    @Size(min = 8, max = 64)
    private String password;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean isApproved;

    @Pattern(regexp = "TECHNICIAN|EMPLOYEE", message = "Role must be TECHNICIAN, EMPLOYEE, or ADMIN")
    @NotBlank
    private String role;


}


