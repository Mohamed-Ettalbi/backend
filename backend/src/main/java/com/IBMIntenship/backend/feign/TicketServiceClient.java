package com.IBMIntenship.backend.feign;

import com.IBMIntenship.backend.config.FeignConfig;

import com.IBMIntenship.backend.model.ticketservicedtos.*;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(name = "ticket-service", url = "${ticket.service.url}/api", configuration = FeignConfig.class)
public interface TicketServiceClient {

    // Ticket Assignment Controller Methods
        @PutMapping("/tickets/{ticketId}/unassign")
        TicketDTO unassignTicket(@PathVariable("ticketId") Long ticketId);

        @PutMapping("/tickets/{ticketId}/assign/user/{userEmail}/{technicianGroupId}")
        TicketDTO assignTicketToUser(@PathVariable("ticketId") Long ticketId, @PathVariable("userEmail") String userEmail , @PathVariable("technicianGroupId") Long technicianGroupId);

        @PutMapping("/tickets/{ticketId}/assign/group/{groupId}")
        TicketDTO assignTicketToGroup(@PathVariable("ticketId") Long ticketId, @PathVariable("groupId") Long groupId);

    // Ticket Controller Methods
        @PutMapping("/tickets/update/{id}")
        TicketDTO updateTicket(@PathVariable("id") Long id, @RequestBody UpdateTicketDTO updateTicketDTO);

        @PutMapping("/tickets/updateStatus/{ticketId}")
        TicketDTO updateTicketStatus(@PathVariable("ticketId") Long ticketId, @RequestBody StatusUpdateDTO statusUpdateDTO);

        @GetMapping("/tickets")
        List<TicketDTO> getAllTickets();

        @PostMapping("/tickets/{createdBy}")
        TicketDTO createTicket(@RequestBody CreateTicketDTO createTicketDTO ,@PathVariable("createdBy") String createdBy);

        @GetMapping("/tickets/{id}")
        TicketDTO getTicketById(@PathVariable("id") Long id);

        @DeleteMapping("/tickets/delete/{id}")
        void deleteTicket(@PathVariable("id") Long id);

    // Comment Controller Methods
        @PutMapping("/comments/update/{commentId}")
        CommentDTO updateComment(@PathVariable("commentId") Long commentId, @RequestBody String message);

        @PostMapping("/comments/{ticketId}")
        CommentDTO addCommentToTicket(@PathVariable("ticketId") Long ticketId, @RequestBody CommentDTO commentDTO );

        @GetMapping("/comments/{ticketId}")
        List<CommentDTO> getCommentsForTicket(@PathVariable("ticketId") Long ticketId);

        @DeleteMapping("/comments/delete/{commentId}")
        void deleteCommentNoEmail(@PathVariable("commentId") Long commentId);

        @DeleteMapping("/comments/delete/{commentId}")
        void deleteComment(@PathVariable("commentId") Long commentId, @RequestParam("email") String email);

    // Attachment Controller Methods
        @PostMapping(value = "/attachment/uploadMultipleFiles/{ticketId}", consumes = "multipart/form-data")
        List<AttachmentDTO> uploadMultipleFiles(@RequestPart("files") MultipartFile[] files, @PathVariable("ticketId") Long ticketId);

//        @PostMapping("/attachment/uploadFile/{ticketId}")
        @PostMapping(value = "/attachment/uploadFile/{ticketId}", consumes = "multipart/form-data")
        AttachmentDTO uploadFile(@RequestPart("file") MultipartFile file, @PathVariable("ticketId") Long ticketId);

    @GetMapping("/attachment/downloadAttachment/{attachmentId}")
        ResponseEntity<Resource> downloadFile(@PathVariable("attachmentId") Long attachmentId);


    @GetMapping("/attachment/attachments/{ticketId}")
    List<AttachmentDTO> getAttachmentsByTicketId(@PathVariable("ticketId") Long ticketId);

    @DeleteMapping("/attachment/attachments/delete/{attachmentId}")
    ResponseEntity<Void> deleteAttachmentById(@PathVariable("attachmentId") Long attachmentId);
}

