package com.IBMIntenship.backend.model.ticketservicedtos;

import com.IBMIntenship.backend.model.ticketserviceenumerations.TicketStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
@AllArgsConstructor
public class StatusUpdateDTO {
    private TicketStatusEnum status;
    private String statusUpdatedBy;
    private Long technicianGroupId;
    private boolean isAdmin;



}
