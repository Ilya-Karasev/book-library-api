package com.example.musiclibrary.services;
import com.example.musiclibrary.dtos.UserDto;
import java.util.List;
import java.util.Optional;
public interface UserService<ID> {
    UserDto register(UserDto user);
    Optional <UserDto> findUser(String username);
    List<UserDto> getAllUsers();
    Optional <UserDto> changeUserInfo(String username, UserDto user);
    void quit(String username);
}