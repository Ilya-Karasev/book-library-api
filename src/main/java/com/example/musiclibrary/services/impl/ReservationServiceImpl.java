package com.example.musiclibrary.services.impl;
import com.example.musiclibrary.dtos.RentalDto;
import com.example.musiclibrary.dtos.ReservationDto;
import com.example.musiclibrary.dtos.show.RentalShow;
import com.example.musiclibrary.dtos.show.ReservationShow;
import com.example.musiclibrary.models.Book;
import com.example.musiclibrary.models.Reservation;
import com.example.musiclibrary.models.User;
import com.example.musiclibrary.rabbitmq.RabbitMQConfig;
import com.example.musiclibrary.rabbitmq.RentalMessageReceiver;
import com.example.musiclibrary.rabbitmq.ReservationMessageReceiver;
import com.example.musiclibrary.repositories.BookRepository;
import com.example.musiclibrary.repositories.ReservationRepository;
import com.example.musiclibrary.repositories.UserRepository;
import com.example.musiclibrary.services.ReservationService;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private ReservationMessageReceiver reservationReceiver;
    @Override
    public ReservationDto addReservation(ReservationDto reserv, String user, String book) throws InterruptedException {
        Reservation r = modelMapper.map(reserv, Reservation.class);
        User u = userRepository.findByName(user).get();
        Book b = bookRepository.findByTitle(book).get();
        r.setUser(u);
        r.setBook(b);
        rabbitTemplate.convertAndSend(RabbitMQConfig.topicExchangeName, "library.reservation.queue", "Бронирование книги " + b.getTitle() + " для пользователя " + u.getName() + " было оформлено");
        reservationReceiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
        return modelMapper.map(reservationRepository.save(r), ReservationDto.class);
    }

    @Override
    public Optional<ReservationShow> findReservation(UUID id) throws InterruptedException {
        rabbitTemplate.convertAndSend(RabbitMQConfig.topicExchangeName, "library.reservation.queue", "Поиск бронирования книги (" + modelMapper.map(reservationRepository.findById(id), ReservationShow.class).getId() + ")");
        reservationReceiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
        return Optional.ofNullable(modelMapper.map(reservationRepository.findById(id), ReservationShow.class));
    }

    @Override
    public Optional<ReservationDto> findReservationDto(UUID id) throws InterruptedException {
        rabbitTemplate.convertAndSend(RabbitMQConfig.topicExchangeName, "library.reservation.queue", "Поиск DTO-объекта аренды книги (" + modelMapper.map(reservationRepository.findById(id), ReservationDto.class).getId() + ")");
        reservationReceiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
        return Optional.ofNullable(modelMapper.map(reservationRepository.findById(id), ReservationDto.class));
    }

    @Override
    public List<ReservationShow> getAllReservations() throws InterruptedException {
        rabbitTemplate.convertAndSend(RabbitMQConfig.topicExchangeName, "library.reservation.queue", "Вывод всех записей о бронировании книги");
        reservationReceiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
        return reservationRepository.findAll().stream().map((r) -> modelMapper.map(r, ReservationShow.class)).collect(Collectors.toList());
    }

    @Override
    public Optional<ReservationDto> editReservation(UUID id, ReservationDto reserv) throws InterruptedException {
        ReservationDto r = modelMapper.map(reservationRepository.findById(id), ReservationDto.class);
        r.setReservation_date(reserv.getReservation_date());
        r.setExpiry_date(reserv.getExpiry_date());
        r.setIs_active(reserv.getIs_active());
        reservationRepository.save(modelMapper.map(r, Reservation.class));
        rabbitTemplate.convertAndSend(RabbitMQConfig.topicExchangeName, "library.reservation.queue", "Редактирование записи бронирования книги (" + modelMapper.map(reservationRepository.findById(id), ReservationDto.class).getId() + ")");
        reservationReceiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
        return Optional.ofNullable(modelMapper.map(reservationRepository.findById(id), ReservationDto.class));
    }

    @Override
    public void deleteReservation(UUID id) throws InterruptedException {
        rabbitTemplate.convertAndSend(RabbitMQConfig.topicExchangeName, "library.rental.queue", "Удаление записи бронирования книги (" + modelMapper.map(reservationRepository.findById(id), ReservationShow.class).getId() + ")");
        reservationReceiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
        reservationRepository.delete(modelMapper.map(reservationRepository.findById(id), Reservation.class));
    }
}
