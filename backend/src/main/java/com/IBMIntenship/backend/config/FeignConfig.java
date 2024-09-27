package com.IBMIntenship.backend.config;

import com.IBMIntenship.backend.CustomErrorDecoder;
import feign.codec.ErrorDecoder;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;


import org.springframework.cloud.openfeign.support.SpringEncoder;
import feign.Logger;

import java.util.ArrayList;
import java.util.List;


@Configuration
public class FeignConfig {
    @Value("${jwt.secret.key}")
    private String SECRET_KEY;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            String token = getJwtToken();
            if (token != null) {
                requestTemplate.header("Authorization", "Bearer " + token);
            }
        };
    }

    public String getJwtToken() {



            ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attrs != null) {
                HttpServletRequest request = attrs.getRequest();
                String token = request.getHeader("Authorization");
                if (token != null && token.startsWith("Bearer ")) {
                    return token.substring(7); // Remove "Bearer " prefix
                }
            }
            return null;
        }

//    public UserDetailsFromToken getUserDetailsFromToken(String token) {
//        try {
//            Claims claims = Jwts.parser()
//                    .setSigningKey(SECRET_KEY)
//                    .parseClaimsJws(token)
//                    .getBody();
//
//            String email = claims.getSubject(); // Extract email (subject)
//            List<String> roles = claims.get("role", List.class); // Extract roles
//
//            return new UserDetailsFromToken(email, roles);
//        } catch (Exception e) {
//            // Handle any exceptions that may occur while parsing the token
//            return null;
//        }
//    }


//    public YourFeignClientConfiguration(ObjectFactory<HttpMessageConverters> messageConverters) {
//        this.messageConverters = messageConverters;
//    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
//    @Bean
//    public Encoder feignFormEncoder() {
//        return new SpringFormEncoder(new SpringEncoder(() -> {
//            List<HttpMessageConverter<?>> converters = new ArrayList<>();
//            converters.add(new MappingJackson2HttpMessageConverter());
//            return new HttpMessageConverters(converters);
//        }));
//    }
    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }
}
