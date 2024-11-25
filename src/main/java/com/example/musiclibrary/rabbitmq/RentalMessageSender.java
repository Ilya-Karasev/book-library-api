package com.example.musiclibrary.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RentalMessageSender {
    private final RabbitTemplate rabbitTemplate;

    public RentalMessageSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendRentalMessage(String message) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.topicExchangeName, "library.rental.queue", message);
        System.out.println("Отправлено в rental-queue: <" + message + ">");
    }
}