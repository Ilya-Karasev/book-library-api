package com.example.musiclibrary.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String topicExchangeName = "spring-boot-exchange";
    public static final String queueUser = "user-queue";
    public static final String queueBook = "book-queue";
    public static final String queueRental = "rental-queue";
    public static final String queueReservation = "reservation-queue";
    public static final String queueRegistration = "registration-queue";
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
    Queue registrationQueue() {
        return new Queue(queueRegistration, false);
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
    Binding registrationBinding(Queue registrationQueue, TopicExchange exchange) {
        return BindingBuilder.bind(registrationQueue).to(exchange).with("library.registration.#");
    }
    @Bean
    Binding userBinding(Queue userQueue, TopicExchange exchange) {
        return BindingBuilder.bind(userQueue).to(exchange).with("library.user.#");
    }
    @Bean
    Binding bookBinding(Queue bookQueue, TopicExchange exchange) {
        return BindingBuilder.bind(bookQueue).to(exchange).with("library.book.#");
    }
    @Bean
    SimpleMessageListenerContainer rentalContainer(ConnectionFactory connectionFactory,
                                                   MessageListenerAdapter rentalListenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueRental);
        container.setMessageListener(rentalListenerAdapter);
        return container;
    }
    @Bean
    SimpleMessageListenerContainer reservationContainer(ConnectionFactory connectionFactory,
                                                        MessageListenerAdapter reservationListenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueReservation);
        container.setMessageListener(reservationListenerAdapter);
        return container;
    }
    @Bean
    SimpleMessageListenerContainer registrationContainer(ConnectionFactory connectionFactory,
                                                         MessageListenerAdapter registrationListenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueRegistration);
        container.setMessageListener(registrationListenerAdapter);
        return container;
    }
    @Bean
    SimpleMessageListenerContainer userContainer(ConnectionFactory connectionFactory,
                                                        MessageListenerAdapter userListenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueUser);
        container.setMessageListener(userListenerAdapter);
        return container;
    }
    @Bean
    SimpleMessageListenerContainer bookContainer(ConnectionFactory connectionFactory,
                                                         MessageListenerAdapter bookListenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueBook);
        container.setMessageListener(bookListenerAdapter);
        return container;
    }
    @Bean
    MessageListenerAdapter rentalListenerAdapter(RentalMessageReceiver rentalReceiver) {
        return new MessageListenerAdapter(rentalReceiver, "receiveMessage");
    }
    @Bean
    MessageListenerAdapter reservationListenerAdapter(ReservationMessageReceiver reservationReceiver) {
        return new MessageListenerAdapter(reservationReceiver, "receiveMessage");
    }
    @Bean
    MessageListenerAdapter registrationListenerAdapter(RegistrationMessageReceiver registrationReceiver) {
        return new MessageListenerAdapter(registrationReceiver, "receiveMessage");
    }
    @Bean
    MessageListenerAdapter userListenerAdapter(UserMessageReceiver userReceiver) {
        return new MessageListenerAdapter(userReceiver, "receiveMessage");
    }
    @Bean
    MessageListenerAdapter bookListenerAdapter(BookMessageReceiver bookReceiver) {
        return new MessageListenerAdapter(bookReceiver, "receiveMessage");
    }
}