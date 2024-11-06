package com.example.musiclibrary.services;
import com.example.musiclibrary.dtos.UserDto;
import com.example.musiclibrary.dtos.show.UserShow;
import java.util.List;
import java.util.Optional;
public interface UserService {
    UserDto register(UserDto user) throws InterruptedException;
    Optional <UserDto> findUserDto(String name) throws InterruptedException;
    Optional <UserShow> findUser(String name) throws InterruptedException;
    List<UserShow> getAllUsers() throws InterruptedException;
    Optional <UserDto> editUser(String name, UserDto user) throws InterruptedException;
    void delete(String username) throws InterruptedException;
}