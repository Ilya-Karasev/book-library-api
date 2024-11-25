package com.example.musiclibrary.dtos;

public record ReservationResponse(
        String user,
        String book,
        String reservation_date,
        String expiry_date,
        Boolean is_active
) {
}
