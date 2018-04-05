package com.icrn.substitutes.model;

import lombok.Data;

import java.time.LocalTime;

@Data
public class StartEnd {
    private LocalTime start;
    private LocalTime end;

    public StartEnd(LocalTime start, LocalTime end) {
        this.start= start;
        this.end = end;
    }

}
