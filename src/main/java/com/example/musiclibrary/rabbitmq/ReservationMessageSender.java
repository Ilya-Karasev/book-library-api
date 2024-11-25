package com.example.musiclibrary.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class ReservationMessageSender {
    private final RabbitTemplate rabbitTemplate;

    public ReservationMessageSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendReservationMessage(String message) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.topicExchangeName, "library.reservation.queue", message);
        System.out.println("Отправлено в registration-queue: <" + message + ">");
    }
}