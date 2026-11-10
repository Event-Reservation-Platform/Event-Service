package com.platform.eventreservex.Repository;


import com.platform.eventreservex.Domain.DTO.Event;
import com.platform.eventreservex.Domain.DTO.UpdateEventRequest;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class EventRepository {
    private final DSLContext dslContext;

    public List<Event> getPaginatedEvents(int page, int size) {
        int offset = (page - 1) * size;

        return dslContext.selectFrom(DSL.table(DSL.name("events")))
                .orderBy(DSL.field(DSL.name("id")))
                .limit(size)
                .offset(offset)
                .fetchInto(Event.class);
    }

    public Optional<Event> getEventById(Long eventId) {
        return dslContext.selectFrom(DSL.table(DSL.name("events")))
                .where(DSL.field(DSL.name("id")).eq(eventId))
                .fetchOptionalInto(Event.class);
    }

    public int removeEvent(Long eventId) {
        return dslContext.deleteFrom(DSL.table(DSL.name("events")))
                .where(DSL.field(DSL.name("id")).eq(eventId))
                .execute();
    }

    public int updateEvent(UpdateEventRequest request) {
        return dslContext.update(DSL.table(DSL.name("events")))
                .set(DSL.field(DSL.name("name")), request.getName())
                .set(DSL.field(DSL.name("description")), request.getDescription())
                .set(DSL.field(DSL.name("location")), request.getLocation())
                .set(DSL.field(DSL.name("starts_at")), request.getStartsAt())
                .set(DSL.field(DSL.name("ends_at")), request.getEndsAt())
                .set(DSL.field(DSL.name("updated_at")), Instant.now())
                .where(DSL.field(DSL.name("id")).eq(request.getId()))
                .and(DSL.field(DSL.name("starts_at")).gt(Instant.now()))
                .execute();
    }
}
