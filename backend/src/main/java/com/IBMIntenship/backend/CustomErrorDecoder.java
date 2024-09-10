package com.IBMIntenship.backend;


import com.IBMIntenship.backend.exceptions.CustomErrorResponse;
import com.IBMIntenship.backend.exceptions.RestApiClientException;
import com.IBMIntenship.backend.exceptions.RestApiServerException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


@Component
@Slf4j
public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        String requestUrl = response.request().url();
       String responseBody = getResponseBody(response);
        HttpStatus responseStatus = HttpStatus.valueOf(response.status());
        String clientName = getCLientName(methodKey);

        CustomErrorResponse errorResponse = new CustomErrorResponse(clientName, responseBody, response.status());

        log.info("Error Decoding: {} , {} " , methodKey , response.status());
        if (responseStatus.is5xxServerError()) {
            return new RestApiServerException(errorResponse);
        } else if (responseStatus.is4xxClientError()) {
            return new RestApiClientException(errorResponse);
        } else {
            return new Exception("Generic exception");
        }
    }
    private String getResponseBody(Response response) {
        if (response.body() != null) {
            try {
                return new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8);
            } catch (IOException e) {
                log.error("Failed to read response body", e);
            }
        }
        return "No response body";
    }


    private String getCLientName(String methodKey){
        if (methodKey.startsWith("AuthServiceClient")){
            return ("AuthServiceClient");

        }else
            return ("TicketServiceClient");
    }
}
