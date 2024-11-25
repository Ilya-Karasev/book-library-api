package com.example.musiclibrary.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserMessageSender {
    private final RabbitTemplate rabbitTemplate;

    public UserMessageSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendUserMessage(String message) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.topicExchangeName, "library.user.queue", message);
        System.out.println("Отправлено в user-queue: <" + message + ">");
    }
}