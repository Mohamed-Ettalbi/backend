package com.IBMIntenship.backend.model.ticketservicedtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

    private Long id;
    private String message;
    private String authorEmail;

    private Long ticketId;
    private LocalDateTime createdAt;
    private Long parrentCommentId;
    private List<CommentDTO> replies;

}