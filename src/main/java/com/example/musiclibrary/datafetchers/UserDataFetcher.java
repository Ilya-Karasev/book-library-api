package com.example.musiclibrary.datafetchers;
import com.example.musiclibrary.datafetchers.records.SubmittedUser;
import com.example.musiclibrary.dtos.UserDto;
import com.example.musiclibrary.dtos.show.UserShow;
import com.example.musiclibrary.services.UserService;
import com.netflix.graphql.dgs.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@DgsComponent
public class UserDataFetcher {
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;
    @DgsQuery
    public UserDto getUser(@InputArgument String name) throws InterruptedException {
        Optional<UserDto> user = userService.findUserDto(name);
        return user.orElse(null);
    }
    @DgsQuery
    public List<UserShow> getAllUsers() throws InterruptedException {
        return userService.getAllUsers();
    }
    @DgsMutation
    public UserDto register(@InputArgument SubmittedUser input) throws InterruptedException {
        UserDto u = new UserDto();
        u.setName(input.name());
        u.setEmail(input.email());
        u.setRole(input.role());
        u.setPassword(input.password());
        u.setAddress(input.address());
        if (input.membership_date() != null) {
            u.setMembership_date(LocalDate.parse(input.membership_date()));
        }
        u.setPhone_number(input.phone_number());
        return userService.register(u);
    }
    @DgsMutation
    public UserDto editUser(@InputArgument String name, @InputArgument SubmittedUser input) throws InterruptedException {
        UserDto u = modelMapper.map(userService.findUser(name), UserDto.class);
        if (input.name() != null && !input.name().isEmpty()) {
            u.setName(input.name());
        }
        if (input.email() != null && !input.email().isEmpty()) {
            u.setEmail(input.email());
        }
        if (input.role() != null) {
            u.setRole(input.role());
        }
        if (input.password() != null && !input.password().isEmpty()) {
            u.setPassword(input.password());
        }
        if (input.address() != null && !input.address().isEmpty()) {
            u.setAddress(input.address());
        }
        if (input.membership_date() != null && !input.membership_date().isEmpty()) {
            u.setMembership_date(LocalDate.parse(input.membership_date()));
        }
        if (input.phone_number() != null && !input.phone_number().isEmpty()) {
            u.setPhone_number(input.phone_number());
        }
        return userService.editUser(name, u).orElse(null);
    }

    @DgsMutation
    public String deleteUser(@InputArgument String name) throws InterruptedException {
        userService.delete(name);
        return "Пользователь " + name + " удалён";
    }
}