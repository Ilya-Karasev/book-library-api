package com.example.musiclibrary.controllers;
import com.example.musiclibrary.dtos.ActionDto;
import com.example.musiclibrary.dtos.UserDto;
import com.example.musiclibrary.dtos.show.UserShow;
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
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;
    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }
    @GetMapping("/users")
    public ResponseEntity<List<UserShow>> all() throws InterruptedException {
        List<UserShow> users = userService.getAllUsers();
        for (UserShow user : users) {
            addActions(user);
            addUserLinks(user);
        }
        return ResponseEntity.ok(users);
    }
    @PostMapping("/users/add")
    public ResponseEntity<UserShow> newUser(@RequestBody UserDto newUser) throws InterruptedException {
        UserDto user = userService.register(newUser);
        UserShow u = userService.findUser(user.getName()).orElseThrow(() -> new NotFoundException(user.getName()));
        addActions(u);
        addUserLinks(u);
        return ResponseEntity.ok(u);
    }
    @GetMapping("/users/info/{name}")
    public ResponseEntity<UserShow> findUser(@PathVariable String name) throws InterruptedException {
        UserShow user = userService.findUser(name).orElseThrow(() -> new NotFoundException(name));
        addUserLinks(user);
        addActions(user);
        return ResponseEntity.ok(user);
    }
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable UUID id) {
        return ResponseEntity.ok(new UserDto());
    }
    @PutMapping("/users/edit/{name}")
    public ResponseEntity<UserShow> editUser(@PathVariable String name, @RequestBody UserDto user) throws InterruptedException {
        userService.editUser(name, user);
        UserShow u = userService.findUser(user.getName()).orElseThrow(() -> new NotFoundException(user.getName()));
        addUserLinks(u);
        addActions(u);
        return ResponseEntity.ok(u);
    }
    @DeleteMapping("/users/delete/{name}")
    public Link deleteUser(@PathVariable String name) throws InterruptedException {
        userService.delete(name);
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).all()).withRel("all-users");

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
    private void addActions(UserShow user) throws InterruptedException {
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