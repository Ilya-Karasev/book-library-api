package com.example.musiclibrary.controllers;

import com.example.musiclibrary.dtos.BookDto;
import com.example.musiclibrary.dtos.show.BookShow;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "books", description = "Управление книгами")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Успешная обработка запроса"),
        @ApiResponse(responseCode = "400", description = "Ошибка валидации"),
        @ApiResponse(responseCode = "404", description = "Ресурс не найден"),
        @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
})
public interface BookApi {

    @Operation(summary = "Получить список всех книг")
    @GetMapping(value = "/books", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<BookShow>> all() throws Throwable;

    @Operation(summary = "Создать новую книгу")
    @PostMapping(value = "/books/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BookShow> addBook(@Valid @RequestBody BookDto newBook) throws Throwable;

    @Operation(summary = "Получить информацию о книге по названию")
    @GetMapping(value = "/books/info/{title}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BookShow> findBook(@PathVariable("title") String title) throws Throwable;

    @Operation(summary = "Редактировать книгу по названию")
    @PutMapping(value = "/books/edit/{title}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BookShow> editBook(@PathVariable("title") String title, @Valid @RequestBody BookDto book) throws Throwable;

    @Operation(summary = "Удалить книгу по названию")
    @DeleteMapping(value = "/books/delete/{title}", produces = MediaType.APPLICATION_JSON_VALUE)
    Link deleteBook(@PathVariable("title") String title) throws Throwable;
}
