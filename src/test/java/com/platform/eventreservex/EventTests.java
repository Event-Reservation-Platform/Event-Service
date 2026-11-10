package com.platform.eventreservex;


import Business.EventService;
import Domain.Event;
import Exceptions.PageException;
import Exceptions.SizeException;
import Repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.List;

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
}
