package com.example.musiclibrary.rabbitmq;

import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class UserMessageReceiver {
    private CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(String message) {
        System.out.println(RabbitMQConfig.queueUser + " <" + message + ">");
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
