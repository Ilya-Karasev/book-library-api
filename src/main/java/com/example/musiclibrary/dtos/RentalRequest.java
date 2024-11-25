package com.example.musiclibrary.dtos;

import jakarta.validation.constraints.*;
public record RentalRequest(
        @NotBlank(message = "Имя арендующего пользователя должно присутствовать")
        String user,
        @NotBlank(message = "Название арендуемой книги должно присутствовать")
        String book,
        String rental_date,
        @NotBlank(message = "Дата возвращения книги должна присутствовать")
        String due_date,
        String return_date,
        Integer extended_times,
        Boolean is_returned
) {
}
