package Domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
public class Event {
    private long id;
    private String name;
    private String description;
    private String location;
    private Instant startsAt;
    private Instant endsAt;
    private BigDecimal price;
    private Instant createdAt;
    private Instant updatedAt;
}
