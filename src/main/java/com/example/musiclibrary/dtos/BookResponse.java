package com.example.musiclibrary.dtos;

import java.time.LocalDateTime;

public record BookResponse(
        String title,
        String user,
        String author,
        String publisher,
        Integer publication_year,
        String genre,
        Integer available_copies,
        Integer total_copies,
        String description,
        LocalDateTime createdAt
) {
}