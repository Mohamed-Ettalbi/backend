package com.IBMIntenship.backend.model.ticketservicedtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentBody {
    private String content;
    private Long commentParrentID;
}
