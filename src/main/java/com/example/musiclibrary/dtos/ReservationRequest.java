package com.example.musiclibrary.dtos;

import jakarta.validation.constraints.*;
public record ReservationRequest(
        @NotBlank(message = "Имя бронирующего пользователя должно присутствовать")
        String user,
        @NotBlank(message = "Название бронируемой книги должно присутствовать")
        String book,
        String reservation_date,
        String expiry_date,
        Boolean is_active
) {
}
