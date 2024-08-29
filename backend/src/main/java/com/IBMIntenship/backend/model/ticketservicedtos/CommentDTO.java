package com.IBMIntenship.backend.model.ticketservicedtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {


    private Long id;
    private String message;
    private Long ticketId;
    private LocalDateTime createdAt;
}