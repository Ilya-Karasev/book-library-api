package com.example.musiclibrary.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String topicExchangeName = "spring-boot-exchange";
    public static final String queueUser = "user-queue";
    public static final String queueBook = "book-queue";
    public static final String queueRental = "rental-queue";
    public static final String queueReservation = "reservation-queue";
    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }
    @Bean
    Queue userQueue() {
        return new Queue(queueUser, false);
    }
    @Bean
    Queue bookQueue() {
        return new Queue(queueBook, false);
    }
    @Bean
    Queue rentalQueue() {
        return new Queue(queueRental, false);
    }
    @Bean
    Queue reservationQueue() {
        return new Queue(queueReservation, false);
    }
    @Bean
    Binding rentalBinding(Queue rentalQueue, TopicExchange exchange) {
        return BindingBuilder.bind(rentalQueue).to(exchange).with("library.rental.#");
    }
    @Bean
    Binding reservationBinding(Queue reservationQueue, TopicExchange exchange) {
        return BindingBuilder.bind(reservationQueue).to(exchange).with("library.reservation.#");
    }
    @Bean
    Binding userBinding(Queue userQueue, TopicExchange exchange) {
        return BindingBuilder.bind(userQueue).to(exchange).with("library.user.#");
    }
    @Bean
    Binding bookBinding(Queue bookQueue, TopicExchange exchange) {
        return BindingBuilder.bind(bookQueue).to(exchange).with("library.book.#");
    }
}