package com.example.musiclibrary.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class BookMessageSender {
    private final RabbitTemplate rabbitTemplate;

    public BookMessageSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendBookMessage(String message) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.topicExchangeName, "library.book.queue", message);
        System.out.println("Отправлено в book-queue: <" + message + ">");
    }
}
