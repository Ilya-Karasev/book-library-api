package com.example.musiclibrary.dtos;

import jakarta.validation.constraints.*;

public record BookRequest(
        @NotBlank(message = "Название книги обязательно")
        String title,
        @NotBlank(message = "Имя библиотекаря, публикующего материал, должно присутствовать")
        String user,
        @NotBlank(message = "ФИО автора книги должно присутствовать")
        String author,
        @NotBlank(message = "Название издательства должно присутствовать")
        String publisher,
        @Min(value = 1900, message = "Год издания должен быть не раньше 1900")
        @Max(value = 2024, message = "Год издания не может быть из будущего")
        Integer publication_year,
        @NotBlank(message = "Жанр книги должен присутствовать")
        String genre,
        @Min(value = 1, message = "Книга должна быть доступна хотя бы в одном экземпляре")
        Integer available_copies,
        @Min(value = 1, message = "Книга должна быть доступна хотя бы в одном экземпляре")
        Integer total_copies,
        @NotBlank(message = "Описание / аннотация книги должно / должна присутствовать")
        String description
) {

}