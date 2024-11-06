package com.example.musiclibrary.controllers;

import com.example.musiclibrary.dtos.*;
import com.example.musiclibrary.dtos.show.BookShow;
import com.example.musiclibrary.dtos.show.RentalShow;
import com.example.musiclibrary.dtos.show.UserShow;
import com.example.musiclibrary.services.BookService;
import com.example.musiclibrary.services.RentalService;
import com.example.musiclibrary.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class RentalController {
    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;
    @Autowired
    private RentalService rentalService;
    @Autowired
    private ModelMapper modelMapper;
    public RentalController(RentalService rentalService, BookService bookService, UserService userService, ModelMapper modelMapper) {
        this.rentalService = rentalService;
        this.bookService = bookService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }
    @GetMapping("/rentals")
    public ResponseEntity<List<RentalShow>> all() throws Throwable {
        List<RentalShow> rentals = rentalService.getAllRentals();
        for (RentalShow rental : rentals) {
            addLinks(rental);
            addActions(rental);
            BookShow b = bookService.findBook(rental.getBook().getTitle()).orElseThrow(() -> new NotFoundException(rental.getBook().getTitle()));
            addBookLinks(b);
            addBookActions(b);
            rental.setBook(b);
            UserShow u1 = userService.findUser(rental.getBook().getUser().getName()).orElseThrow(() -> new NotFoundException(rental.getUser().getName()));
            addUserLinks(u1);
            addUserActions(u1);
            b.setUser(u1);
            UserShow u = userService.findUser(rental.getUser().getName()).orElseThrow(() -> new NotFoundException(rental.getUser().getName()));
            addUserLinks(u);
            addUserActions(u);
            rental.setUser(u);
        }
        return ResponseEntity.ok(rentals);
    }
    @PostMapping("/rentals/add")
    ResponseEntity<RentalShow> newRental(@RequestBody RentalDto newRental) throws Throwable {
        RentalDto rental = rentalService.addRental(newRental, newRental.getUser().getName(), newRental.getBook().getTitle());
        RentalShow r = rentalService.findRental(rental.getId()).orElseThrow(() -> new NotFoundException(rental.getId().toString()));
        addLinks(r);
        addActions(r);
        BookShow b = bookService.findBook(rental.getBook().getTitle()).orElseThrow(() -> new NotFoundException(rental.getBook().getTitle()));
        addBookLinks(b);
        addBookActions(b);
        r.setBook(b);
        UserShow u1 = userService.findUser(rental.getBook().getUser().getName()).orElseThrow(() -> new NotFoundException(rental.getUser().getName()));
        addUserLinks(u1);
        addUserActions(u1);
        b.setUser(u1);
        UserShow u = userService.findUser(rental.getUser().getName()).orElseThrow(() -> new NotFoundException(rental.getUser().getName()));
        addUserLinks(u);
        addUserActions(u);
        r.setUser(u);
        return ResponseEntity.ok(r);
    }
    @GetMapping("/rentals/info/{id}")
    ResponseEntity<RentalShow> findRental(@PathVariable UUID id) throws Throwable {
        RentalShow rental = rentalService.findRental(id).orElseThrow((() -> new NotFoundException(id.toString())));
        addLinks(rental);
        addActions(rental);
        BookShow book = bookService.findBook(rental.getBook().getTitle()).orElseThrow(() -> new NotFoundException(rental.getBook().getTitle()));
        addBookLinks(book);
        addBookActions(book);
        rental.setBook(book);
        UserShow u1 = userService.findUser(rental.getBook().getUser().getName()).orElseThrow(() -> new NotFoundException(rental.getUser().getName()));
        addUserLinks(u1);
        addUserActions(u1);
        book.setUser(u1);
        UserShow u = userService.findUser(book.getUser().getName()).orElseThrow(() -> new NotFoundException(book.getUser().getName()));
        addUserLinks(u);
        addUserActions(u);
        rental.setUser(u);
        return ResponseEntity.ok(rental);
    }
    @PutMapping("/rentals/edit/{id}")
    ResponseEntity<RentalShow> editRental(@PathVariable UUID id, @RequestBody RentalDto rental) throws Throwable {
        rentalService.editRental(id, rental);
        RentalShow r = rentalService.findRental(id).orElseThrow((() -> new NotFoundException(id.toString())));
        addLinks(r);
        addActions(r);
        BookShow book = bookService.findBook(r.getBook().getTitle()).orElseThrow(() -> new NotFoundException(rental.getBook().getTitle()));
        addBookLinks(book);
        addBookActions(book);
        r.setBook(book);
        UserShow u1 = userService.findUser(r.getBook().getUser().getName()).orElseThrow(() -> new NotFoundException(rental.getUser().getName()));
        addUserLinks(u1);
        addUserActions(u1);
        book.setUser(u1);
        UserShow u = userService.findUser(book.getUser().getName()).orElseThrow(() -> new NotFoundException(book.getUser().getName()));
        addUserLinks(u);
        addUserActions(u);
        r.setUser(u);
        return ResponseEntity.ok(r);
    }

    @DeleteMapping("/rentals/{id}")
    Link deleteRental(@PathVariable UUID id) throws Throwable {
        rentalService.deleteRental(id);
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RentalController.class).all()).withRel("all-rentals");
    }

    private void addLinks(RentalShow rental) throws Throwable {
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RentalController.class)
                        .findRental(rental.getId()))
                .withSelfRel();

        Link allLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RentalController.class)
                        .all())
                .withRel("all-rentals");

        rental.add(selfLink);
        rental.add(allLink);
    }

    private void addActions(RentalShow rental) throws Throwable {
        List<ActionDto> actions = new ArrayList<>();

        ActionDto updateAction = new ActionDto(
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RentalController.class)
                        .editRental(rental.getId(), modelMapper.map(rental, RentalDto.class))).withRel("update").toUri().toString(),
                "PUT",
                "application/json"
        );
        actions.add(updateAction);

        ActionDto deleteAction = new ActionDto(
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RentalController.class)
                        .deleteRental(rental.getId())).withRel("delete").toUri().toString(),
                "DELETE"
        );
        actions.add(deleteAction);

        rental.setActions(actions);
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