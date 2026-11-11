package com.platform.eventreservex.Business;


import com.platform.eventreservex.Business.Exceptions.*;
import com.platform.eventreservex.Domain.Event.Event;
import com.platform.eventreservex.Domain.DTO.EventSaveRequest;
import com.platform.eventreservex.Domain.DTO.UpdateEventRequest;
import com.platform.eventreservex.Repository.EventRepository;
import com.platform.eventreservex.Repository.OutboxRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository repository;
    private final OutboxRepository outboxRepository;

    public List<Event> getEvents(int page, int size) {
        if (page < 1) {
            throw new PageException("Page cannot be lower than 1");
        }

        if (size < 1 || size > 100) {
            throw new SizeException("Size has to be between 1 and 100");
        }
        log.debug("Fetching events: page={}, size={}", page, size);

        List<Event> events = repository.getPaginatedEvents(page, size);
        log.info("Loaded {} events ", events.size());

        return events;
    }

    public Event getEvent(Long eventId) {
        if (eventId == null) {
            throw new InvalidArgumentException("Event cannot be null");
        }

        log.debug("Feching event: eventId{}", eventId);
        Optional<Event> event = repository.getEventById(eventId);

        if (event.isEmpty()) {
            throw new EventNotFoundException("Event not found");
        }

        log.info("Fetched event: {}", eventId);
        return event.get();
    }

    public void removeEvent(Long eventId) {
        if (eventId == null) {
           throw new InvalidArgumentException("Event cannot be null");
        }
        int affectedRow = repository.removeEvent(eventId);

        if (affectedRow != 1) {
            log.error("Unexpected deletion count: eventID: {} affectedRows:{}", eventId, affectedRow);
            throw new DeletionFailedException("Could not delete the event");
        }

        log.info("Successfully removed the event: {}", eventId);
    }

    public void updateEvent(UpdateEventRequest request) {
        log.debug("Fetching event during update with eventId: {}", request.getId());
        Optional<Event> eventOpt = repository.getEventById(request.getId());

        if (eventOpt.isEmpty()) {
            throw new EventNotFoundException("Event not found");
        }

        if (!request.getEndsAt().isAfter(request.getStartsAt())) {
            throw new InvalidArgumentException("Event end time must be after start time");
        }

        log.debug("Updating event with new event information: id:{} name: {}",
                request.getId(), request.getName());

        int updatedRows = repository.updateEvent(request);

        if (updatedRows != 1) {
            log.error("Unexpected updating failure, updated rows: {} eventId: {}", updatedRows, request.getId());
            throw new UpdateFailedException("Unexpected error occurred");
        }

        log.info("Successful event update. eventId: {}", request.getId());
    }

    @Transactional
    public Long createEvent(EventSaveRequest request) {
        if (request.getName().isBlank() || request.getDescription().isBlank() || request.getLocation().isBlank()
                || request.getPrice().equals(BigDecimal.valueOf(0))) {
            throw new InvalidArgumentException("One or more of the arguments is empty or price is zero");
        }

        log.debug("Event Creation of {}.", request.getName());

        Long eventId = repository.saveEvent(request);
        int createdRows = outboxRepository.saveEventToOutbox(request);

        if (createdRows != 1) {
            log.error("Failed to create an event to the outbox. EventID: {} Event Name: {}", eventId, request.getName());
            throw new OutboxCreationFailedException("Couldn't save the event to the outbox");
        }

        log.info("Event created successfully. EventID: {}", eventId);

        return eventId;
    }
}
