package com.example.musiclibrary.services.impl;
import com.example.musiclibrary.dtos.UserDto;
import com.example.musiclibrary.models.User;
import com.example.musiclibrary.repositories.UserRepository;
import com.example.musiclibrary.services.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class UserServiceImpl implements UserService<Integer> {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public UserDto register(UserDto user) {
        if (!userRepository.existsByUsername(user.getUsername())) {
        User u = modelMapper.map(user, User.class);
        return modelMapper.map(userRepository.save(u), UserDto.class);
        } else return null;
    }
    @Override
    public Optional<UserDto> findUser(String username) {
        return Optional.ofNullable(modelMapper.map(userRepository.findByUsername(username), UserDto.class));
    }
    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map((u) -> modelMapper.map(u, UserDto.class)).collect(Collectors.toList());
    }
    @Override
    public Optional<UserDto> changeUserInfo(String username, UserDto user) {
        UserDto u = modelMapper.map(userRepository.findByUsername(username), UserDto.class);
        u.setUsername(user.getUsername());
        u.setPassword(user.getPassword());
        userRepository.save(modelMapper.map(u, User.class));
        return Optional.ofNullable(modelMapper.map(userRepository.findByUsername(user.getUsername()), UserDto.class));
    }
    @Override
    public void quit(String username) {
        userRepository.delete(modelMapper.map(userRepository.findByUsername(username), User.class));
    }
}