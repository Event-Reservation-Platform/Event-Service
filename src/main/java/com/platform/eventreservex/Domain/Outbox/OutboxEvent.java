package com.platform.eventreservex.Domain.Outbox;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@AllArgsConstructor
@Getter
@Setter
public class OutboxEvent {
    private UUID id;
    private String eventType;
    private String payload;
}
