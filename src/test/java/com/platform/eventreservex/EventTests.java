package com.platform.eventreservex;


import com.platform.eventreservex.Business.EventService;
import com.platform.eventreservex.Domain.DTO.Event;
import com.platform.eventreservex.Domain.DTO.UpdateEventRequest;
import com.platform.eventreservex.Exceptions.*;
import com.platform.eventreservex.Repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EventTests {

    @Mock
    private EventRepository repository;

    @InjectMocks
    private EventService eventService;

    private Event event;

    @BeforeEach
    void setUp(){
        BigDecimal price = BigDecimal.valueOf(3.12);
        event = new Event(1L, "test", "this is a test", "home", Instant.now(), Instant.now(), price , Instant.now(), Instant.now());
    }

    @Test
    void shouldReturnAListOfEvents() {
        BigDecimal price = BigDecimal.valueOf(4.322);
        Event event2 = new Event(1L, "test", "this is a test", "home", Instant.now(), Instant.now(), price , Instant.now(), Instant.now());
        List<Event> events = List.of(event, event2);

        when(repository.getPaginatedEvents(1, 10)).thenReturn(List.of(event, event2));

        assertEquals(events, eventService.getEvents(1, 10));
    }

    @Test
    void shouldThrowAnExceptionIfPageIsBelowOne() {
        assertThrows(PageException.class, () -> {
            eventService.getEvents(0, 10);
        });

        verifyNoInteractions(repository);
    }

    @Test
    void shouldThrowAnExceptionIfSizeIsGreaterThanHundred() {
        assertThrows(SizeException.class, () -> {
            eventService.getEvents(1, 101);
        });

        verifyNoInteractions(repository);
    }

    @Test
    void shouldRetrieveAnEvent() {
        when(repository.getEventById(1L)).thenReturn(Optional.of(event));

        assertEquals(event, eventService.getEvent(1L));
    }

    @Test
    void shouldThrowExceptionWhenEventDoesNotExist(){
        when(repository.getEventById(2L)).thenReturn(Optional.empty());

        assertThrows(EventNotFoundException.class, () -> {
            eventService.getEvent(2L);
        });
    }

    @Test
    void shouldThrowAnExceptionIfDeletionFails() {
        when(repository.removeEvent(2L)).thenReturn(0);

        assertThrows(DeletionFailedException.class, () -> {
            eventService.removeEvent(2L);
        });
    }

    @Test
    void eventNotFoundWhenTryingToUpdate() {

        UpdateEventRequest request = new UpdateEventRequest(event.getId(), event.getName(), event.getDescription(), event.getLocation(), event.getStartsAt(), event.getEndsAt());

        when(repository.getEventById(2L)).thenReturn(Optional.empty());

        assertThrows(EventNotFoundException.class, () -> {
            eventService.updateEvent(request);
        });
    }

    @Test
    void shouldThrowAnExceptionIfUpdateFails() {

        UpdateEventRequest request = new UpdateEventRequest(event.getId(), event.getName(), event.getDescription(), event.getLocation(), event.getStartsAt(), event.getEndsAt());

        when(repository.updateEvent(request)).thenReturn(0);

        assertThrows(UpdateFailedException.class, () -> {
            eventService.updateEvent(request);
        });
    }
}
