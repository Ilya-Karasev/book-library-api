package com.example.musiclibrary.datafetchers;

import com.example.musiclibrary.datafetchers.records.SubmittedReservation;
import com.example.musiclibrary.dtos.ReservationDto;
import com.example.musiclibrary.dtos.show.ReservationShow;
import com.example.musiclibrary.services.BookService;
import com.example.musiclibrary.services.ReservationService;
import com.example.musiclibrary.services.UserService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@DgsComponent
public class ReservationDataFetcher {
    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ModelMapper modelMapper;

    @DgsQuery
    public ReservationDto getReservation(@InputArgument String id) throws InterruptedException {
        Optional<ReservationDto> r = reservationService.findReservationDto(UUID.fromString(id));
        return r.orElse(null);
    }

    @DgsQuery
    public List<ReservationShow> getAllReservations() throws InterruptedException {
        return reservationService.getAllReservations();
    }

    @DgsMutation
    public ReservationDto addReservation(@InputArgument SubmittedReservation input) throws InterruptedException {
        ReservationDto r = new ReservationDto();
        r.setUser(userService.findUserDto(input.user()).orElseThrow());
        r.setBook(bookService.findBookDto(input.book()).orElseThrow());
        if (input.is_active() != null) {
            r.setIs_active(input.is_active());
        }
        if (input.reservation_date() != null && !input.reservation_date().isEmpty()) {
            r.setReservation_date(LocalDate.parse(input.reservation_date()));
        }
        if (input.expiry_date() != null && !input.expiry_date().isEmpty()) {
            r.setExpiry_date(LocalDate.parse(input.expiry_date()));
        }
        r.setCreated(LocalDateTime.now());
        r.setModified(LocalDateTime.now());
        return reservationService.addReservation(r, r.getUser().getName(), r.getBook().getTitle());
    }

    @DgsMutation
    public ReservationDto editReservation(@InputArgument String id, @InputArgument SubmittedReservation input) throws InterruptedException {
        ReservationDto r = modelMapper.map(reservationService.findReservation(UUID.fromString(id)), ReservationDto.class);
        r.setIs_active(input.is_active());
        if (input.reservation_date() != null) {
            r.setReservation_date(LocalDate.parse(input.reservation_date()));
        }
        if (input.expiry_date() != null) {
            r.setExpiry_date(LocalDate.parse(input.expiry_date()));
        }
        r.setModified(LocalDateTime.now());
        return reservationService.editReservation(UUID.fromString(id), r).orElse(null);
    }

    @DgsMutation
    public String deleteReservation(@InputArgument String id) throws InterruptedException {
        reservationService.deleteReservation(UUID.fromString(id));
        return "Запись бронирования '" + id + "' удалена";
    }
}
