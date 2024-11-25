package com.example.musiclibrary.dtos;

import jakarta.validation.constraints.*;
public record UserRequest(
        @NotBlank(message = "Имя для пользователя обязательно")
        String name,
        @NotBlank(message = "Электронная почта для пользователя обязательна")
        String email,
        @NotBlank(message = "Пароль для пользователя обязателен")
        String password,
        @NotBlank(message = "Роль для пользователя обязательна")
        Role role,
        String membership_date,
        String phone_number,
        String address
) {
}
