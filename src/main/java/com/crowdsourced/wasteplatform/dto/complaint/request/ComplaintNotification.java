package com.crowdsourced.wasteplatform.dto.complaint.request;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ComplaintNotification {
    private String type;
    private String message;
    private UUID complaintId;
}
