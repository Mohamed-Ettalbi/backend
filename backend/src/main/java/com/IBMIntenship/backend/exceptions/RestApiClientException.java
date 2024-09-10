package com.IBMIntenship.backend.exceptions;

import lombok.Data;

@Data
public class RestApiClientException extends RuntimeException {
    private final CustomErrorResponse errorResponse;

    public RestApiClientException(CustomErrorResponse errorResponse) {
        super(errorResponse.getMessage());
        this.errorResponse = errorResponse;
    }

}
