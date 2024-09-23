package com.IBMIntenship.backend.controller.ticketcontrollers;

import com.IBMIntenship.backend.model.ticketservicedtos.AttachmentDTO;
import com.IBMIntenship.backend.service.ticketservices.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;

@RestController
@RequestMapping("/api/attachment")
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    private static final Logger logger = LoggerFactory.getLogger(AttachmentService.class);


    @PostMapping("/uploadMultipleFiles/{ticketId}")
    public ResponseEntity<List<AttachmentDTO>> uploadMultipleFiles(@RequestPart("files") MultipartFile[] files, @PathVariable("ticketId") Long ticketId) {
        List<AttachmentDTO> responses = attachmentService.uploadMultipleFiles(files, ticketId);
        return ResponseEntity.ok(responses);
    }

//    @PostMapping(value = "/uploadFile/{ticketId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PostMapping(value = "/uploadFile/{ticketId}", consumes = "multipart/form-data")
    public ResponseEntity<AttachmentDTO> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable("ticketId") Long ticketId) {
        AttachmentDTO response = attachmentService.uploadFile(file, ticketId);
        return ResponseEntity.ok(response);
//        return attachmentService.uploadFile(file, ticketId);
    }

    @GetMapping("/downloadAttachment/{attachmentId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("attachmentId") Long attachmentId) {
        return attachmentService.downloadAttachment(attachmentId);
    }

    // In AttachmentController in the gateway
    @GetMapping("/attachments/{ticketId}")
    public ResponseEntity<List<AttachmentDTO>> getAttachmentsByTicketId(@PathVariable Long ticketId) {
        try {
            List<AttachmentDTO> attachments = attachmentService.getAttachmentsByTicketId(ticketId);
            return ResponseEntity.ok(attachments);
        } catch (Exception e) {
            logger.error("Failed to fetch attachments for ticketId: {}", ticketId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/delete/{attachmentId}")
    public ResponseEntity<Void> deleteAttachmentById(@PathVariable Long attachmentId) {
        attachmentService.deleteAttachmentById(attachmentId);
        return ResponseEntity.noContent().build();
    }


}
