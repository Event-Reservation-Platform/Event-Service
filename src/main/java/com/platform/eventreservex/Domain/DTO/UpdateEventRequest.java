package com.platform.eventreservex.Domain.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@AllArgsConstructor
@Getter
@Setter
public class UpdateEventRequest {
    private Long id;
    private String name;
    private String description;
    private String location;
    private Instant startsAt;
    private Instant endsAt;
}
