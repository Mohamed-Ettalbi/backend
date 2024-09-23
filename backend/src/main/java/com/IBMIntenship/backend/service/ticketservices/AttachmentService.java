package com.IBMIntenship.backend.service.ticketservices;

import com.IBMIntenship.backend.feign.TicketServiceClient;
import com.IBMIntenship.backend.model.ticketservicedtos.AttachmentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class AttachmentService {

    @Autowired
    private TicketServiceClient ticketServiceClient;

    private static final Logger logger = LoggerFactory.getLogger(AttachmentService.class);


    public List<AttachmentDTO> uploadMultipleFiles(MultipartFile[] files, Long ticketId) {
        // Forward the request to the attachment-service via Feign
        return ticketServiceClient.uploadMultipleFiles(files, ticketId);
    }

    public AttachmentDTO uploadFile(MultipartFile file, Long ticketId) {
        // Forward the request to the attachment-service via Feign
        logger.info("Starting uploadFile method with ticketId: {} and file: {}", ticketId, file.getOriginalFilename());

        // Log the file size and content type
        logger.debug("File size: {} bytes, Content type: {}", file.getSize(), file.getContentType());
        AttachmentDTO response;
        try {
            response = ticketServiceClient.uploadFile(file, ticketId);
            logger.info("File upload successful for ticketId: {}", ticketId);
        } catch (Exception e) {
            logger.error("Error uploading file for ticketId: {}", ticketId, e);
            throw e;
        }


        return response;
    }


    public ResponseEntity<Resource> downloadAttachment(Long attachmentId) {
        // Forward the request to the attachment-service via Feign
        return ticketServiceClient.downloadFile(attachmentId);
    }

    // In AttachmentService in the gateway
    public List<AttachmentDTO> getAttachmentsByTicketId(Long ticketId) {
        return ticketServiceClient.getAttachmentsByTicketId(ticketId);
    }

    public void deleteAttachmentById(Long attachmentId){
        ticketServiceClient.deleteAttachmentById(attachmentId);
    }




}
