package com.platform.eventreservex.Component;


import com.platform.eventreservex.Domain.Outbox.OutboxEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


@Component
@RequiredArgsConstructor
public class OutboxPublisher {
    private final RabbitTemplate rabbitTemplate;

    public void publishEvent(OutboxEvent event) throws ExecutionException, InterruptedException, TimeoutException {
        CorrelationData correlationData = new CorrelationData(event.getId().toString());

        Message message = MessageBuilder.withBody(event.getPayload().getBytes(StandardCharsets.UTF_8)).setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .setMessageId(event.getId().toString())
                .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                .build();

        rabbitTemplate.send("reservex.events", "event.created", message, correlationData);

        CorrelationData.Confirm confirm = correlationData.getFuture().get(10, TimeUnit.SECONDS);

        if (!confirm.ack()) {
            throw new IllegalStateException("Rabbitmq rejected the message");
        }
    }
}
