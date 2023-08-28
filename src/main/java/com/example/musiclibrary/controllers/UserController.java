package com.example.musiclibrary.controllers;
import com.example.musiclibrary.dtos.UserDto;
import com.example.musiclibrary.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/users")
    List<? extends UserDto> all() {
        return userService.getAllUsers();
    }
    @PostMapping("/users")
    UserDto newUser(@RequestBody UserDto newUser) {  return userService.register(newUser); }
    @GetMapping("/users/{username}")
    UserDto one(@PathVariable String username) throws Throwable {
        return (UserDto) userService.findUser(username).orElseThrow((() -> new NotFoundException(username)));
    }
    @PutMapping("/users/{username}")
    UserDto two(@PathVariable String username, @RequestBody UserDto user) throws Throwable {
        userService.changeUserInfo(username, user);
        return (UserDto) userService.findUser(user.getUsername()).orElseThrow((() -> new NotFoundException(user.getUsername())));
    }
    @DeleteMapping("/users/{username}")
    void deleteUser(@PathVariable String username) {
        userService.quit(username);
    }
}