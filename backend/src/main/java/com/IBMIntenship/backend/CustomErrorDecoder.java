package com.IBMIntenship.backend;


import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {

        log.info("Error Decoding: {} , {} " , methodKey , response);
        return null;
    }
}
