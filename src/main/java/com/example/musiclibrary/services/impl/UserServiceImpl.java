package com.example.musiclibrary.services.impl;

import com.example.musiclibrary.dtos.UserDto;
import com.example.musiclibrary.dtos.show.UserShow;
import com.example.musiclibrary.models.User;
import com.example.musiclibrary.rabbitmq.RabbitMQConfig;
import com.example.musiclibrary.rabbitmq.RegistrationMessageReceiver;
import com.example.musiclibrary.rabbitmq.UserMessageReceiver;
import com.example.musiclibrary.repositories.UserRepository;
import com.example.musiclibrary.services.*;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private RegistrationMessageReceiver registrationReceiver;
    @Autowired
    private UserMessageReceiver userReceiver;
    @Override
    public UserDto register(UserDto user) throws InterruptedException {
        if (!userRepository.existsByName(user.getName())) {
            User u = modelMapper.map(user, User.class);
            u.setCreated(LocalDateTime.now());
            u.setModified(LocalDateTime.now());
            rabbitTemplate.convertAndSend(RabbitMQConfig.topicExchangeName, "library.user.queue", "Пользователь " + u.getName() + " был зарегистрирован");
            registrationReceiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
            return modelMapper.map(userRepository.save(u), UserDto.class);
        } else return null;
    }
    @Override
    public Optional<UserDto> findUserDto(String name) throws InterruptedException {
        rabbitTemplate.convertAndSend(RabbitMQConfig.topicExchangeName, "library.user.queue", "Поиск DTO-объекта пользователя (" + modelMapper.map(userRepository.findByName(name), UserDto.class).getId() + ")");
        userReceiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
        return Optional.ofNullable(modelMapper.map(userRepository.findByName(name), UserDto.class));
    }
    @Override
    public Optional<UserShow> findUser(String name) throws InterruptedException {
        rabbitTemplate.convertAndSend(RabbitMQConfig.topicExchangeName, "library.user.queue", "Поиск пользователя" + name + "(" + modelMapper.map(userRepository.findByName(name), UserDto.class).getId() + ")");
        userReceiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
        return Optional.ofNullable(modelMapper.map(userRepository.findByName(name), UserShow.class));

    }
    @Override
    public List<UserShow> getAllUsers() throws InterruptedException {
        rabbitTemplate.convertAndSend(RabbitMQConfig.topicExchangeName, "library.user.queue", "Вывод всех записей о пользователях");
        userReceiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
        return userRepository.findAll().stream().map((u) -> modelMapper.map(u, UserShow.class)).collect(Collectors.toList());
    }
    @Override
    public Optional<UserDto> editUser(String name, UserDto user) throws InterruptedException {
        UserDto u = modelMapper.map(userRepository.findByName(name), UserDto.class);
        u.setName(user.getName());
        u.setEmail(user.getEmail());
        u.setPassword(user.getPassword());
        u.setRole(user.getRole());
        u.setMembership_date(user.getMembership_date());
        u.setPhone_number(user.getPhone_number());
        u.setAddress(user.getAddress());
        u.setModified(LocalDateTime.now());
        userRepository.save(modelMapper.map(u, User.class));
        rabbitTemplate.convertAndSend(RabbitMQConfig.topicExchangeName, "library.user.queue", "Редактирование записи пользователя (" + modelMapper.map(userRepository.findByName(name), UserDto.class).getId() + ")");
        userReceiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
        return Optional.ofNullable(modelMapper.map(userRepository.findByName(user.getName()), UserDto.class));
    }
    @Override
    public void delete(String name) throws InterruptedException {
        rabbitTemplate.convertAndSend(RabbitMQConfig.topicExchangeName, "library.user.queue", "Пользователь " + name + "(" + modelMapper.map(userRepository.findByName(name), User.class).getId() + " был удалён");
        registrationReceiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
        userRepository.delete(modelMapper.map(userRepository.findByName(name), User.class));
    }
}