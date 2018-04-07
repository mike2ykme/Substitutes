package com.icrn.substitutes.model;

import com.icrn.substitutes.model.enumerations.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Request {
    private long requestId;
    private long requestorId;
    private long substituteId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Status status;
}
