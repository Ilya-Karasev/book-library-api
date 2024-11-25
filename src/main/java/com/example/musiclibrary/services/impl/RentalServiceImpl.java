package com.example.musiclibrary.services.impl;

import com.example.musiclibrary.dtos.RentalDto;
import com.example.musiclibrary.dtos.show.RentalShow;
import com.example.musiclibrary.models.Book;
import com.example.musiclibrary.models.Rental;
import com.example.musiclibrary.models.User;
import com.example.musiclibrary.rabbitmq.*;
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
    private RentalMessageSender rentalSender;
    @Override
    public RentalDto addRental(RentalDto rental, String user, String book) throws InterruptedException {
        Book b = bookRepository.findByTitle(book).orElseThrow(() -> new RuntimeException("Книга не найдена"));

        if (b.getAvailable_copies() == 0) {
            rentalSender.sendRentalMessage("Аренда не оформлена: нет доступных копий книги " + b.getTitle());
            return null;
        }

        // Уменьшаем количество доступных копий
        b.setAvailable_copies(b.getAvailable_copies() - 1);
        bookRepository.save(b);

        Rental r = modelMapper.map(rental, Rental.class);
        User u = userRepository.findByName(user).orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        r.setUser(u);
        r.setBook(b);

        rentalSender.sendRentalMessage("Аренда книги " + b.getTitle() + " для пользователя " + u.getName() + " была оформлена");
        return modelMapper.map(rentalRepository.save(r), RentalDto.class);
    }

    @Override
    public Optional<RentalShow> findRental(UUID id) throws InterruptedException {
        rentalSender.sendRentalMessage("Поиск аренды книги (" + modelMapper.map(rentalRepository.findById(id), RentalShow.class).getId() + ")");
        return Optional.ofNullable(modelMapper.map(rentalRepository.findById(id), RentalShow.class));
    }

    @Override
    public Optional<RentalDto> findRentalDto(UUID id) throws InterruptedException {
        rentalSender.sendRentalMessage("Поиск DTO-объекта аренды книги (" + modelMapper.map(rentalRepository.findById(id), RentalDto.class).getId() + ")");
        return Optional.ofNullable(modelMapper.map(rentalRepository.findById(id), RentalDto.class));
    }

    @Override
    public List<RentalShow> getAllRentals() throws InterruptedException {
        rentalSender.sendRentalMessage("Вывод всех записей об арендах книги");
        return rentalRepository.findAll().stream().map((r) -> modelMapper.map(r, RentalShow.class)).collect(Collectors.toList());
    }

    @Override
    public Optional<RentalDto> editRental(UUID id, RentalDto rental) throws InterruptedException {
        Rental r = rentalRepository.findById(id).orElseThrow(() -> new RuntimeException("Аренда не найдена"));
        boolean wasNotReturned = (r.getIs_returned() == null || !r.getIs_returned());

        // Обновляем данные аренды
        r.setRental_date(rental.getRental_date());
        r.setDue_date(rental.getDue_date());
        r.setExtended_times(rental.getExtended_times());
        r.setIs_returned(rental.getIs_returned());
        r.setReturn_date(rental.getReturn_date());
        r.setModified(LocalDateTime.now());

        // Если книга была не возвращена и теперь возвращена, увеличиваем количество доступных копий
        if (wasNotReturned && Boolean.TRUE.equals(rental.getIs_returned())) {
            Book b = r.getBook();
            b.setAvailable_copies(b.getAvailable_copies() + 1);
            bookRepository.save(b);
            rentalSender.sendRentalMessage("Книга " + b.getTitle() + " была возвращена; аренда " + r.getId() + " больше не активна");
        }

        rentalRepository.save(r);
        rentalSender.sendRentalMessage("Редактирование записи аренды книги (" + r.getId() + ")");
        return Optional.of(modelMapper.map(r, RentalDto.class));
    }

    @Override
    public void deleteRental(UUID id) throws InterruptedException {
        rentalSender.sendRentalMessage("Удаление записи аренды книги (" + modelMapper.map(rentalRepository.findById(id), RentalShow.class).getId() + ")");
        rentalRepository.delete(modelMapper.map(rentalRepository.findById(id), Rental.class));
    }
}