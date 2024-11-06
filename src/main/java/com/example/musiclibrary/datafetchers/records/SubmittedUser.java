package com.example.musiclibrary.datafetchers.records;

import com.example.musiclibrary.dtos.UserDto;
public record SubmittedUser(String name, String email, String password, UserDto.Role role, String membership_date, String phone_number, String address) {
}