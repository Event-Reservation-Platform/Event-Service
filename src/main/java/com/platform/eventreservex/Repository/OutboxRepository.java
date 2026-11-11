package com.platform.eventreservex.Repository;


import com.platform.eventreservex.Domain.DTO.EventSaveRequest;
import com.platform.eventreservex.Domain.Outbox.EventStatus;
import com.platform.eventreservex.Domain.Outbox.EventType;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class OutboxRepository {
    private final DSLContext dslContext;

    public OutboxRepository (DSLContext dsl) {
        this.dslContext = dsl;
    }

    public int saveEventToOutbox(EventSaveRequest request) {
        var id = DSL.field(DSL.name("id"));
        var eventType = DSL.field(DSL.name("event_type"));
        var payload = DSL.field(DSL.name("payload"));
        var status = DSL.field(DSL.name("status"));

        return dslContext.insertInto(DSL.table(DSL.name("outbox_events")))
                .columns(id, eventType, payload, status)
                .values(UUID.randomUUID(), EventType.EVENT_CREATION.toString(), request, EventStatus.PENDING.toString())
                .execute();
    }
}
