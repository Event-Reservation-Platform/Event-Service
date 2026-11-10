package com.platform.eventreservex.Domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;


@AllArgsConstructor
@Getter
@Setter
public class EventSaveRequest {
    private String name;
    private String description;
    private String location;
    private Instant startsAt;
    private Instant endsAt;
    private BigDecimal price;
}
