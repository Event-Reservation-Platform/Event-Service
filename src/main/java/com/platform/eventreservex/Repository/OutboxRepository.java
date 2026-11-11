package com.platform.eventreservex.Repository;


import com.platform.eventreservex.Domain.DTO.EventSaveRequest;
import com.platform.eventreservex.Domain.Outbox.EventStatus;
import com.platform.eventreservex.Domain.Outbox.EventType;
import com.platform.eventreservex.Domain.Outbox.OutboxEvent;
import org.jooq.DSLContext;
import org.jooq.JSONB;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
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

    public List<OutboxEvent> getPendingEvents() {
        var id = DSL.field(DSL.name("id"));
        var eventType = DSL.field(DSL.name("event_type"), String.class);
        var payload = DSL.field(DSL.name("payload"), JSONB.class);
        var status = DSL.field(DSL.name("status"), String.class);
        var createdAt = DSL.field(
                DSL.name("created_at"),
                OffsetDateTime.class
        );

        return dslContext.selectFrom(DSL.table(DSL.name("outbox_events")))
                .where(status.eq(EventStatus.PENDING.toString()))
                .orderBy(createdAt.asc(), id.asc())
                .limit(100)
                .fetch(record -> new OutboxEvent((UUID) record.get(id), record.get(eventType), record.get(payload).data()));
    }

    public int deletePublishedMessage(UUID id) {
        return dslContext.deleteFrom(DSL.table(DSL.name("outbox_events")))
                .where(DSL.field(DSL.name("id")).eq(id))
                .execute();
    }
}
