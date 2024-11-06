package com.example.musiclibrary.services.impl;

import com.example.musiclibrary.dtos.RentalDto;
import com.example.musiclibrary.dtos.show.RentalShow;
import com.example.musiclibrary.models.Book;
import com.example.musiclibrary.models.Rental;
import com.example.musiclibrary.models.User;
import com.example.musiclibrary.rabbitmq.RabbitMQConfig;
import com.example.musiclibrary.rabbitmq.RegistrationMessageReceiver;
import com.example.musiclibrary.rabbitmq.RentalMessageReceiver;
import com.example.musiclibrary.repositories.BookRepository;
import com.example.musiclibrary.repositories.RentalRepository;
import com.example.musiclibrary.repositories.UserRepository;
import com.example.musiclibrary.services.RentalService;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
@Service
public class RentalServiceImpl implements RentalService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private RentalRepository rentalRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private RentalMessageReceiver rentalReceiver;
    @Override
    public RentalDto addRental(RentalDto rental, String user, String book) throws InterruptedException {
        Rental r = modelMapper.map(rental, Rental.class);
        User u = userRepository.findByName(user).get();
        Book b = bookRepository.findByTitle(book).get();
        r.setUser(u);
        r.setBook(b);
        rabbitTemplate.convertAndSend(RabbitMQConfig.topicExchangeName, "library.rental.queue", "Аренда книги " + b.getTitle() + " для пользователя " + u.getName() + " была оформлена");
        rentalReceiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
        return modelMapper.map(rentalRepository.save(r), RentalDto.class);
    }

    @Override
    public Optional<RentalShow> findRental(UUID id) throws InterruptedException {
        rabbitTemplate.convertAndSend(RabbitMQConfig.topicExchangeName, "library.rental.queue", "Поиск аренды книги (" + modelMapper.map(rentalRepository.findById(id), RentalShow.class).getId() + ")");
        rentalReceiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
        return Optional.ofNullable(modelMapper.map(rentalRepository.findById(id), RentalShow.class));
    }

    @Override
    public Optional<RentalDto> findRentalDto(UUID id) throws InterruptedException {
        rabbitTemplate.convertAndSend(RabbitMQConfig.topicExchangeName, "library.rental.queue", "Поиск DTO-объекта аренды книги (" + modelMapper.map(rentalRepository.findById(id), RentalDto.class).getId() + ")");
        rentalReceiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
        return Optional.ofNullable(modelMapper.map(rentalRepository.findById(id), RentalDto.class));
    }

    @Override
    public List<RentalShow> getAllRentals() throws InterruptedException {
        rabbitTemplate.convertAndSend(RabbitMQConfig.topicExchangeName, "library.rental.queue", "Вывод всех записей об арендах книги");
        rentalReceiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
        return rentalRepository.findAll().stream().map((r) -> modelMapper.map(r, RentalShow.class)).collect(Collectors.toList());
    }

    @Override
    public Optional<RentalDto> editRental(UUID id, RentalDto rental) throws InterruptedException {
        RentalDto r = modelMapper.map(rentalRepository.findById(id), RentalDto.class);
        r.setRental_date(rental.getRental_date());
        r.setDue_date(rental.getDue_date());
        r.setExtended_times(rental.getExtended_times());
        r.setIs_returned(rental.getIs_returned());
        r.setReturn_date(rental.getReturn_date());
        r.setModified(LocalDateTime.now());
        rentalRepository.save(modelMapper.map(r, Rental.class));
        rabbitTemplate.convertAndSend(RabbitMQConfig.topicExchangeName, "library.rental.queue", "Редактирование записи аренды книги (" + modelMapper.map(rentalRepository.findById(id), RentalDto.class).getId() + ")");
        rentalReceiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
        return Optional.ofNullable(modelMapper.map(rentalRepository.findById(id), RentalDto.class));
    }

    @Override
    public void deleteRental(UUID id) throws InterruptedException {
        rabbitTemplate.convertAndSend(RabbitMQConfig.topicExchangeName, "library.rental.queue", "Удаление записи аренды книги (" + modelMapper.map(rentalRepository.findById(id), RentalShow.class).getId() + ")");
        rentalReceiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
        rentalRepository.delete(modelMapper.map(rentalRepository.findById(id), Rental.class));
    }
}