package com.example.musiclibrary.controllers;

import com.example.musiclibrary.dtos.ReservationDto;
import com.example.musiclibrary.dtos.show.ReservationShow;
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
import java.util.UUID;

@Tag(name = "reservations", description = "Управление записями бронирований")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Успешная обработка запроса"),
        @ApiResponse(responseCode = "400", description = "Ошибка валидации"),
        @ApiResponse(responseCode = "404", description = "Ресурс не найден"),
        @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
})
public interface ReservationApi {
    @Operation(summary = "Получить список всех бронирований")
    @GetMapping(value = "/reservations", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<ReservationShow>> all() throws Throwable;

    @Operation(summary = "Создать новое бронирование")
    @PostMapping(value = "/reservations/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ReservationShow> newReservation(@Valid @RequestBody ReservationDto newRental) throws Throwable;

    @Operation(summary = "Получить информацию о бронировании по ID")
    @GetMapping(value = "/reservations/info/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ReservationShow> findReservation(@PathVariable("id") UUID id) throws Throwable;

    @Operation(summary = "Редактировать бронирование по ID")
    @PutMapping(value = "/reservations/edit/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ReservationShow> editReservation(@PathVariable("id") UUID id, @Valid @RequestBody ReservationDto reservation) throws Throwable;

    @Operation(summary = "Удалить бронирование по ID")
    @DeleteMapping(value = "/reservations/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    Link deleteReservation(@PathVariable("id") UUID id) throws Throwable;
}
