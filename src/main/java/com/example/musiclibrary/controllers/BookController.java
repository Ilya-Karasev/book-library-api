package com.example.musiclibrary.controllers;
import com.example.musiclibrary.dtos.ActionDto;
import com.example.musiclibrary.dtos.BookDto;
import com.example.musiclibrary.dtos.UserDto;
import com.example.musiclibrary.dtos.show.BookShow;
import com.example.musiclibrary.dtos.show.UserShow;
import com.example.musiclibrary.services.BookService;
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
public class BookController {
    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;
    @Autowired
    private ModelMapper modelMapper;
    public BookController(UserService userService, BookService bookService, ModelMapper modelMapper) {
        this.userService = userService;
        this.bookService = bookService;
        this.modelMapper = modelMapper;
    }
    @GetMapping("/books")
    public ResponseEntity<List<BookShow>> all() throws Throwable {
        List<BookShow> books = bookService.getAllBooks();
        for (BookShow book : books) {
            addLinks(book);
            addActions(book);
            UserShow u = userService.findUser(book.getUser().getName()).orElseThrow(() -> new NotFoundException(book.getUser().getName()));
            addUserLinks(u);
            addUserActions(u);
            book.setUser(u);
        }
        return ResponseEntity.ok(books);
    }
    @PostMapping("/books/add")
    public ResponseEntity<BookShow> newBook(@RequestBody BookDto newBook) throws Throwable {
        BookDto book = bookService.addBook(newBook, newBook.getUser().getName());
        BookShow b = bookService.findBook(book.getTitle()).orElseThrow(() -> new NotFoundException(book.getTitle()));
        addLinks(b);
        addActions(b);
        UserShow u = userService.findUser(b.getUser().getName()).orElseThrow(() -> new NotFoundException(b.getUser().getName()));
        addUserLinks(u);
        addUserActions(u);
        b.setUser(u);
        return ResponseEntity.ok(b);
    }
    @GetMapping("/books/info/{title}")
    public ResponseEntity<BookShow> findBook(@PathVariable String title) throws Throwable {
        BookShow book = bookService.findBook(title).orElseThrow(() -> new NotFoundException(title));
        addLinks(book);
        addActions(book);
        UserShow u = userService.findUser(book.getUser().getName()).orElseThrow(() -> new NotFoundException(book.getUser().getName()));
        addUserLinks(u);
        addUserActions(u);
        book.setUser(u);
        return ResponseEntity.ok(book);
    }
    @GetMapping("/books/{id}")
    public ResponseEntity<BookDto> getBook(@PathVariable UUID id) {
        return ResponseEntity.ok(new BookDto());
    }
    @PutMapping("/books/edit/{title}")
    ResponseEntity<BookShow> editBook(@PathVariable String title, @RequestBody BookDto book) throws Throwable {
        bookService.editBook(title, book);
        BookShow b = bookService.findBook(book.getTitle()).orElseThrow(() -> new NotFoundException(book.getTitle()));
        addLinks(b);
        addActions(b);
        UserShow u = userService.findUser(b.getUser().getName()).orElseThrow(() -> new NotFoundException(b.getUser().getName()));
        addUserLinks(u);
        addUserActions(u);
        b.setUser(u);
        return ResponseEntity.ok(b);
    }
    @DeleteMapping("/books/delete/{title}")
    public Link deletebook(@PathVariable String title) throws Throwable {
        bookService.deleteBook(title);
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class).all()).withRel("all-books");
    }
    private void addLinks(BookShow book) throws Throwable {
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class)
                        .findBook(book.getTitle()))
                .withSelfRel();

        Link allUsersLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class)
                        .all())
                .withRel("all-books");

        book.add(selfLink);
        book.add(allUsersLink);
    }
    private void addActions(BookShow book) throws Throwable {
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