package com.IBMIntenship.backend.service.ticketservices;

import com.IBMIntenship.backend.feign.TicketServiceClient;
import com.IBMIntenship.backend.model.ticketservicedtos.CommentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private TicketServiceClient ticketServiceClient;

    public CommentDTO updateComment(Long commentId, String message) {
        // Forward the request to the comment-service via Feign
        return ticketServiceClient.updateComment(commentId, message);
    }

    public CommentDTO addCommentToTicket(Long ticketId, String message) {
        // Forward the request to the comment-service via Feign
        return ticketServiceClient.addCommentToTicket(ticketId, message);
    }

    public List<CommentDTO> getCommentsForTicket(Long ticketId) {
        // Forward the request to the comment-service via Feign
        return ticketServiceClient.getCommentsForTicket(ticketId);
    }

    public void deleteComment(Long commentId) {
        // Forward the request to the comment-service via Feign
        ticketServiceClient.deleteComment(commentId);
    }
}