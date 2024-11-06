package com.example.musiclibrary.datafetchers;

import com.example.musiclibrary.datafetchers.records.SubmittedRental;
import com.example.musiclibrary.dtos.RentalDto;
import com.example.musiclibrary.dtos.show.RentalShow;
import com.example.musiclibrary.services.BookService;
import com.example.musiclibrary.services.RentalService;
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

import static java.util.Optional.ofNullable;

@DgsComponent
public class RentalDataFetcher {
    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;
    @Autowired
    private RentalService rentalService;
    @Autowired
    private ModelMapper modelMapper;

    @DgsQuery
    public RentalDto getRental(@InputArgument String id) throws InterruptedException {
        Optional<RentalDto> rental = rentalService.findRentalDto(UUID.fromString(id));
        return rental.orElse(null);
    }

    @DgsQuery
    public List<RentalShow> getAllRentals() throws InterruptedException {
        return rentalService.getAllRentals();
    }

    @DgsMutation
    public RentalDto addRental(@InputArgument SubmittedRental input) throws InterruptedException {
        RentalDto r = new RentalDto();
        r.setUser(userService.findUserDto(input.user()).orElseThrow());
        r.setBook(bookService.findBookDto(input.book()).orElseThrow());
        r.setIs_returned(input.is_returned());
        r.setExtended_times(input.extended_times());
        if (input.rental_date() != null) {
            r.setRental_date(LocalDate.parse(input.rental_date()));
        }
        if (input.due_date() != null) {
            r.setDue_date(LocalDate.parse(input.due_date()));
        }
        r.setCreated(LocalDateTime.now());
        r.setModified(LocalDateTime.now());
        return rentalService.addRental(r, r.getUser().getName(), r.getBook().getTitle());
    }

    @DgsMutation
    public RentalDto editRental(@InputArgument String id, @InputArgument SubmittedRental input) throws InterruptedException {
        RentalDto r = modelMapper.map(rentalService.findRental(UUID.fromString(id)), RentalDto.class);
        if (input.is_returned() != null) {
            r.setIs_returned(input.is_returned());
        }
        if (input.extended_times() != null) {
            r.setExtended_times(input.extended_times());
        }
        if (input.rental_date() != null && !input.rental_date().isEmpty()) {
            r.setRental_date(LocalDate.parse(input.rental_date()));
        }
        if (input.return_date() != null && !input.return_date().isEmpty()) {
            r.setReturn_date(LocalDate.parse(input.return_date()));
        }
        if (input.due_date() != null && !input.due_date().isEmpty()) {
            r.setDue_date(LocalDate.parse(input.due_date()));
        }
        r.setModified(LocalDateTime.now());
        return rentalService.editRental(UUID.fromString(id), r).orElse(null);
    }

    @DgsMutation
    public String deleteRental(@InputArgument String id) throws InterruptedException {
        rentalService.deleteRental(UUID.fromString(id));
        return "Запись аренды '" + id + "' удалена";
    }
}