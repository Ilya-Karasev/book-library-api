package com.example.musiclibrary.controllers;

import com.example.musiclibrary.dtos.*;
import com.example.musiclibrary.dtos.show.BookShow;
import com.example.musiclibrary.dtos.show.RentalShow;
import com.example.musiclibrary.dtos.show.ReservationShow;
import com.example.musiclibrary.dtos.show.UserShow;
import com.example.musiclibrary.services.BookService;
import com.example.musiclibrary.services.ReservationService;
import com.example.musiclibrary.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class ReservationController {
    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ModelMapper modelMapper;
    public ReservationController(ReservationService reservationService, BookService bookService, UserService userService, ModelMapper modelMapper) {
        this.reservationService = reservationService;
        this.bookService = bookService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }
    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationShow>> all() throws Throwable {
        List<ReservationShow> reservations = reservationService.getAllReservations();
        for (ReservationShow reservation : reservations) {
            addLinks(reservation);
            addActions(reservation);
            BookShow b = bookService.findBook(reservation.getBook().getTitle()).orElseThrow(() -> new NotFoundException(reservation.getBook().getTitle()));
            addBookLinks(b);
            addBookActions(b);
            reservation.setBook(b);
            UserShow u1 = userService.findUser(reservation.getBook().getUser().getName()).orElseThrow(() -> new NotFoundException(reservation.getUser().getName()));
            addUserLinks(u1);
            addUserActions(u1);
            b.setUser(u1);
            UserShow u = userService.findUser(reservation.getUser().getName()).orElseThrow(() -> new NotFoundException(reservation.getUser().getName()));
            addUserLinks(u);
            addUserActions(u);
            reservation.setUser(u);
        }
        return ResponseEntity.ok(reservations);
    }
    @PostMapping("/reservations/add")
    ResponseEntity<ReservationShow> newReservation(@RequestBody ReservationDto newReservation) throws Throwable {
        ReservationDto reservation = reservationService.addReservation(newReservation, newReservation.getUser().getName(), newReservation.getBook().getTitle());
        ReservationShow r = reservationService.findReservation(reservation.getId()).orElseThrow(() -> new NotFoundException(reservation.getId().toString()));
        addLinks(r);
        addActions(r);
        BookShow b = bookService.findBook(reservation.getBook().getTitle()).orElseThrow(() -> new NotFoundException(reservation.getBook().getTitle()));
        addBookLinks(b);
        addBookActions(b);
        r.setBook(b);
        UserShow u1 = userService.findUser(reservation.getBook().getUser().getName()).orElseThrow(() -> new NotFoundException(reservation.getUser().getName()));
        addUserLinks(u1);
        addUserActions(u1);
        b.setUser(u1);
        UserShow u = userService.findUser(reservation.getUser().getName()).orElseThrow(() -> new NotFoundException(reservation.getUser().getName()));
        addUserLinks(u);
        addUserActions(u);
        r.setUser(u);
        return ResponseEntity.ok(r);
    }
    @GetMapping("/reservations/info/{id}")
    ResponseEntity<ReservationShow> findReservation(@PathVariable UUID id) throws Throwable {
        ReservationShow reservation = reservationService.findReservation(id).orElseThrow((() -> new NotFoundException(id.toString())));
        addLinks(reservation);
        addActions(reservation);
        BookShow book = bookService.findBook(reservation.getBook().getTitle()).orElseThrow(() -> new NotFoundException(reservation.getBook().getTitle()));
        addBookLinks(book);
        addBookActions(book);
        reservation.setBook(book);
        UserShow u1 = userService.findUser(reservation.getBook().getUser().getName()).orElseThrow(() -> new NotFoundException(reservation.getUser().getName()));
        addUserLinks(u1);
        addUserActions(u1);
        book.setUser(u1);
        UserShow u = userService.findUser(book.getUser().getName()).orElseThrow(() -> new NotFoundException(book.getUser().getName()));
        addUserLinks(u);
        addUserActions(u);
        reservation.setUser(u);
        return ResponseEntity.ok(reservation);
    }
    @PutMapping("/reservations/edit/{id}")
    ResponseEntity<ReservationShow> editReservation(@PathVariable UUID id, @RequestBody ReservationDto reservation) throws Throwable {
        reservationService.editReservation(id, reservation);
        ReservationShow r = reservationService.findReservation(id).orElseThrow((() -> new NotFoundException(id.toString())));
        addLinks(r);
        addActions(r);
        BookShow book = bookService.findBook(r.getBook().getTitle()).orElseThrow(() -> new NotFoundException(r.getBook().getTitle()));
        addBookLinks(book);
        addBookActions(book);
        r.setBook(book);
        UserShow u1 = userService.findUser(r.getBook().getUser().getName()).orElseThrow(() -> new NotFoundException(r.getUser().getName()));
        addUserLinks(u1);
        addUserActions(u1);
        book.setUser(u1);
        UserShow u = userService.findUser(book.getUser().getName()).orElseThrow(() -> new NotFoundException(book.getUser().getName()));
        addUserLinks(u);
        addUserActions(u);
        r.setUser(u);
        return ResponseEntity.ok(r);
    }
    @DeleteMapping("/reservations/delete/{id}")
    Link deleteReservation(@PathVariable UUID id) throws Throwable {
        reservationService.deleteReservation(id);
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ReservationController.class).all()).withRel("all-reservations");
    }

    private void addLinks(ReservationShow reservation) throws Throwable {
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ReservationController.class)
                        .findReservation(reservation.getId()))
                .withSelfRel();

        Link allLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ReservationController.class)
                        .all())
                .withRel("all-reservations");

        reservation.add(selfLink);
        reservation.add(allLink);
    }

    private void addActions(ReservationShow reservation) throws Throwable {
        List<ActionDto> actions = new ArrayList<>();

        ActionDto updateAction = new ActionDto(
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ReservationController.class)
                        .editReservation(reservation.getId(), modelMapper.map(reservation, ReservationDto.class))).withRel("update").toUri().toString(),
                "PUT",
                "application/json"
        );
        actions.add(updateAction);

        ActionDto deleteAction = new ActionDto(
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ReservationController.class)
                        .deleteReservation(reservation.getId())).withRel("delete").toUri().toString(),
                "DELETE"
        );
        actions.add(deleteAction);

        reservation.setActions(actions);
    }

    private void addBookLinks(BookShow book) throws Throwable {
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class)
                        .findBook(book.getTitle()))
                .withSelfRel();

        Link allUsersLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class)
                        .all())
                .withRel("all-books");

        book.add(selfLink);
        book.add(allUsersLink);
    }

    private void addBookActions(BookShow book) throws Throwable {
        List<ActionDto> actions = new ArrayList<>();

        ActionDto updateAction = new ActionDto(
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class)
                        .editBook(book.getTitle(), modelMapper.map(book, BookDto.class))).withRel("update").toUri().toString(),
                "PUT",
                "application/json"
        );
        actions.add(updateAction);

        ActionDto deleteAction = new ActionDto(
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class)
                        .deletebook(book.getTitle())).withRel("delete").toUri().toString(),
                "DELETE"
        );
        actions.add(deleteAction);

        book.setActions(actions);
    }

    private void addUserLinks(UserShow user) throws InterruptedException {
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
                        .findUser(user.getName()))
                .withSelfRel();

        Link allUsersLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
                        .all())
                .withRel("all-users");

        user.add(selfLink);
        user.add(allUsersLink);
    }

    private void addUserActions(UserShow user) throws InterruptedException {
        List<ActionDto> actions = new ArrayList<>();

        ActionDto updateAction = new ActionDto(
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
                        .editUser(user.getName(), modelMapper.map(user, UserDto.class))).withRel("update").toUri().toString(),
                "PUT",
                "application/json"
        );
        actions.add(updateAction);

        ActionDto deleteAction = new ActionDto(
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
                        .deleteUser(user.getName())).withRel("delete").toUri().toString(),
                "DELETE"
        );
        actions.add(deleteAction);

        user.setActions(actions);
    }
}