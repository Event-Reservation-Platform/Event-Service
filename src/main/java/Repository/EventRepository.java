package Repository;


import Domain.DTO.EventSaveRequest;
import Domain.Event;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import java.util.List;

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
}
