package com.IBMIntenship.backend.model.ticketservicedtos;

import com.IBMIntenship.backend.model.ticketserviceenumerations.TicketPriorityEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTicketDTO {

    private String title;
    private String description;
    private TicketPriorityEnum priority;
    private Long TicketCategory;


    // Getters and Setters

}
