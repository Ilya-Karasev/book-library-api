package com.example.musiclibrary.controllers;

import com.example.musiclibrary.dtos.RentalDto;
import com.example.musiclibrary.dtos.RentalRequest;
import com.example.musiclibrary.dtos.RentalResponse;
import com.example.musiclibrary.dtos.show.RentalShow;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@Tag(name = "rentals", description = "Управление записями аренды")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Успешная обработка запроса"),
        @ApiResponse(responseCode = "400", description = "Ошибка валидации"),
        @ApiResponse(responseCode = "404", description = "Ресурс не найден"),
        @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
})
public interface RentalApi {
    @Operation(summary = "Получить список всех аренд")
    @GetMapping(value = "/rentals", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<RentalShow>> all() throws Throwable;

    @Operation(summary = "Создать новую аренду")
    @PostMapping(value = "/rentals/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<RentalShow> newRental(@Valid @RequestBody RentalDto newRental) throws Throwable;

    @Operation(summary = "Получить информацию об аренде по ID")
    @GetMapping(value = "/rentals/info/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<RentalShow> findRental(@PathVariable("id") UUID id) throws Throwable;

    @Operation(summary = "Редактировать аренду по ID")
    @PutMapping(value = "/rentals/edit/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<RentalShow> editRental(@PathVariable("id") UUID id, @Valid @RequestBody RentalDto rental) throws Throwable;

    @Operation(summary = "Удалить аренду по ID")
    @DeleteMapping(value = "/rentals/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    Link deleteRental(@PathVariable("id") UUID id) throws Throwable;
}
