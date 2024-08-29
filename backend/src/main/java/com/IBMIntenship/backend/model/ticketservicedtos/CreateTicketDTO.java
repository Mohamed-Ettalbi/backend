package com.IBMIntenship.backend.model.ticketservicedtos;


import com.IBMIntenship.backend.model.ticketserviceenumerations.TicketPriorityEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTicketDTO {

    private String title;
    private String description;
    private TicketPriorityEnum priority;
    private Long TicketCategory;

}
