package com.platform.eventreservex.Component;


import com.platform.eventreservex.Domain.Outbox.OutboxEvent;
import com.platform.eventreservex.Repository.OutboxRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@Slf4j
@Component
@AllArgsConstructor
public class OutboxWorker {
    private OutboxRepository repository;
    private OutboxPublisher publisher;

    @Scheduled(fixedDelay = 2000)
    public void publishBatchEvent() {
        log.debug("Retrieving pending events");
        List<OutboxEvent> pendingEvents = repository.getPendingEvents();

        for (OutboxEvent event : pendingEvents) {
            try {
                log.debug("Publishing event: {}", event.getId());
                publisher.publishEvent(event);

                log.info("Event published. EventID: {}", event.getId());

                log.debug("Deleting published message. eventID: {}", event.getId());
                int deletedRow = repository.deletePublishedMessage(event.getId());

                if (deletedRow != 1) {
                    log.warn("Event message wasn't removed from outbox events table. MessageId: {}, rows: {}", event.getId(), deletedRow);
                }
            }
            catch (TimeoutException te) {
                log.error("Request timed out: {}", te.getMessage());
                Thread.currentThread().interrupt();
            }
            catch (InterruptedException ie) {
                log.error("Request got interrupted: {}", ie.getMessage());
                Thread.currentThread().interrupt();
            }
            catch (ExecutionException ee) {
                log.error("Execution failed: {}", ee.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }
}
