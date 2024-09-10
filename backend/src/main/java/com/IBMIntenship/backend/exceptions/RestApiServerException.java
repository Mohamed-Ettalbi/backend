package com.IBMIntenship.backend.exceptions;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
public class RestApiServerException extends RuntimeException {


    private final CustomErrorResponse errorResponse;

    public RestApiServerException( CustomErrorResponse errorResponse) {
        super(errorResponse.getMessage());
        this.errorResponse = errorResponse;
    }


}
