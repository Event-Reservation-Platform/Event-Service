package com.platform.eventreservex.Domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;


@AllArgsConstructor
@Getter
@Setter
public class EventSaveRequest {

    @NonNull
    private String name;
    @NonNull
    private String description;
    @NonNull
    private String location;
    @NonNull
    private Instant startsAt;

    @NonNull
    private Instant endsAt;

    @NonNull
    private BigDecimal price;
}
