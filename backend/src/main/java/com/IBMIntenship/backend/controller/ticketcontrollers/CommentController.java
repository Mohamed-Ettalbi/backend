package com.IBMIntenship.backend.controller.ticketcontrollers;



import com.IBMIntenship.backend.model.ticketservicedtos.CommentDTO;
import com.IBMIntenship.backend.service.ticketservices.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PutMapping("/update/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable("commentId") Long commentId, @RequestBody String message) {
        CommentDTO updatedComment = commentService.updateComment(commentId, message);
        return ResponseEntity.ok(updatedComment);
    }

    @PostMapping("/{ticketId}")
    public ResponseEntity<CommentDTO> addCommentToTicket(@PathVariable("ticketId") Long ticketId, @RequestBody String message) {
        CommentDTO newComment = commentService.addCommentToTicket(ticketId, message);
        return ResponseEntity.ok(newComment);
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<List<CommentDTO>> getCommentsForTicket(@PathVariable("ticketId") Long ticketId) {
        List<CommentDTO> comments = commentService.getCommentsForTicket(ticketId);
        return ResponseEntity.ok(comments);
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable("commentId") Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}

