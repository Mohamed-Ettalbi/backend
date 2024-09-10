package com.IBMIntenship.backend.service.ticketservices;

import com.IBMIntenship.backend.config.FeignConfig;
import com.IBMIntenship.backend.config.SecurityService;
import com.IBMIntenship.backend.feign.AuthServiceClient;
import com.IBMIntenship.backend.feign.TicketServiceClient;
import com.IBMIntenship.backend.model.ticketservicedtos.CommentBody;
import com.IBMIntenship.backend.model.ticketservicedtos.CommentDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private TicketServiceClient ticketServiceClient;
    @Autowired
    private SecurityService securityServiceye;
    @Autowired
    private FeignConfig feignConfig;

    private static final Logger logger = LoggerFactory.getLogger(TicketService.class);



    public CommentDTO updateComment(Long commentId, String message) {
        logger.debug("sending to the service   {}    {}", commentId , message  );
        return ticketServiceClient.updateComment(commentId, message);
    }

    public CommentDTO addCommentToTicket(Long ticketId, CommentBody commentBody) {
        String token = feignConfig.getJwtToken();
        String authorEmail = securityServiceye.getUserDetailsFromToken(token).getEmail();
        CommentDTO commentDTO = new CommentDTO();
        //to be sure who created this ticket
        commentDTO.setAuthorEmail(authorEmail);
        commentDTO.setParrentCommentId(commentBody.getCommentParrentID());
        commentDTO.setMessage(commentBody.getContent());
        commentDTO.setReplies(Collections.emptyList());



        return ticketServiceClient.addCommentToTicket(ticketId, commentDTO);
    }

    public List<CommentDTO> getCommentsForTicket(Long ticketId) {
        List<CommentDTO> allComments = ticketServiceClient.getCommentsForTicket(ticketId);

            List<CommentDTO> rootComments = new ArrayList<>();
            for (CommentDTO comment : allComments) {
                if (comment.getParrentCommentId() == null) {
                    rootComments.add(comment);
                } else {
                    for (CommentDTO parentComment : allComments) {
                        if (parentComment.getId().equals(comment.getParrentCommentId())) {
                            if (parentComment.getReplies() == null) {
                                parentComment.setReplies(new ArrayList<>());
                            }
                            parentComment.getReplies().add(comment);
                            break;
                        }
                    }
                }
            }

            return rootComments;
        }





    public void deleteComment(Long commentId) {
        if (securityServiceye.validateTokenAndRole("ROLE_ADMIN")) {

            ticketServiceClient.deleteComment(commentId);
        } else {
            String token = feignConfig.getJwtToken();
            String authorEmail = securityServiceye.getUserDetailsFromToken(token).getEmail();

            ticketServiceClient.deleteComment(commentId, authorEmail);
        }
    }

}