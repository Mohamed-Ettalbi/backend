package com.IBMIntenship.backend.model.authservicedtos;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddGroupDTORequest {

    @Size(min = 1, max = 50)
    private String groupName;
    @Size(min = 1, max = 10000)
    private String groupDescription;

}
