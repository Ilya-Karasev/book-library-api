package com.example.musiclibrary.dtos;

public record UserResponse(
        String name,
        String email,
        String password,
        Role role,
        String membership_date,
        String phone_number,
        String address
) {
}
