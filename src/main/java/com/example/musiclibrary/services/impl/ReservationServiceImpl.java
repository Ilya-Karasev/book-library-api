package com.example.musiclibrary.services.impl;
import com.example.musiclibrary.dtos.RentalDto;
import com.example.musiclibrary.dtos.ReservationDto;
import com.example.musiclibrary.dtos.show.RentalShow;
import com.example.musiclibrary.dtos.show.ReservationShow;
import com.example.musiclibrary.models.Book;
import com.example.musiclibrary.models.Reservation;
import com.example.musiclibrary.models.User;
import com.example.musiclibrary.rabbitmq.*;
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
    private ReservationMessageSender reservationSender;
    @Override
    public ReservationDto addReservation(ReservationDto reserv, String user, String book) throws InterruptedException {
        Book b = bookRepository.findByTitle(book).orElseThrow(() -> new RuntimeException("Книга не найдена"));

        if (b.getAvailable_copies() == 0) {
            reservationSender.sendReservationMessage("Бронирование не оформлено: нет доступных копий книги " + b.getTitle());
            return null;
        }

        // Уменьшаем количество доступных копий
        b.setAvailable_copies(b.getAvailable_copies() - 1);
        bookRepository.save(b);

        Reservation r = modelMapper.map(reserv, Reservation.class);
        User u = userRepository.findByName(user).orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        r.setUser(u);
        r.setBook(b);

        reservationSender.sendReservationMessage("Бронирование книги " + b.getTitle() + " для пользователя " + u.getName() + " было оформлено");
        return modelMapper.map(reservationRepository.save(r), ReservationDto.class);
    }

    @Override
    public Optional<ReservationShow> findReservation(UUID id) throws InterruptedException {
        reservationSender.sendReservationMessage("Поиск бронирования книги (" + modelMapper.map(reservationRepository.findById(id), ReservationShow.class).getId() + ")");
        return Optional.ofNullable(modelMapper.map(reservationRepository.findById(id), ReservationShow.class));
    }

    @Override
    public Optional<ReservationDto> findReservationDto(UUID id) throws InterruptedException {
        reservationSender.sendReservationMessage("Поиск DTO-объекта аренды книги (" + modelMapper.map(reservationRepository.findById(id), ReservationDto.class).getId() + ")");
        return Optional.ofNullable(modelMapper.map(reservationRepository.findById(id), ReservationDto.class));
    }

    @Override
    public List<ReservationShow> getAllReservations() throws InterruptedException {
        reservationSender.sendReservationMessage("Вывод всех записей о бронировании книги");
        return reservationRepository.findAll().stream().map((r) -> modelMapper.map(r, ReservationShow.class)).collect(Collectors.toList());
    }

    @Override
    public Optional<ReservationDto> editReservation(UUID id, ReservationDto reserv) throws InterruptedException {
        Reservation r = reservationRepository.findById(id).orElseThrow(() -> new RuntimeException("Бронирование не найдено"));
        boolean wasActive = Boolean.TRUE.equals(r.getIs_active());

        // Обновляем данные бронирования
        r.setReservation_date(reserv.getReservation_date());
        r.setExpiry_date(reserv.getExpiry_date());
        r.setIs_active(reserv.getIs_active());

        // Если бронирование было активно и теперь стало неактивным, увеличиваем количество доступных копий
        if (wasActive && Boolean.FALSE.equals(reserv.getIs_active())) {
            Book b = r.getBook();
            b.setAvailable_copies(b.getAvailable_copies() + 1);
            bookRepository.save(b);
            reservationSender.sendReservationMessage("Бронирование книги " + r.getId() + " больше не активно");
        }

        reservationRepository.save(r);
        reservationSender.sendReservationMessage("Редактирование записи бронирования книги (" + r.getId() + ")");
        return Optional.of(modelMapper.map(r, ReservationDto.class));
    }

    @Override
    public void deleteReservation(UUID id) throws InterruptedException {
        reservationSender.sendReservationMessage("Удаление записи бронирования книги (" + modelMapper.map(reservationRepository.findById(id), ReservationShow.class).getId() + ")");
        reservationRepository.delete(modelMapper.map(reservationRepository.findById(id), Reservation.class));
    }
}
