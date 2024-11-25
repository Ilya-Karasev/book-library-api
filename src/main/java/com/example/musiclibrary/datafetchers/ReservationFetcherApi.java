package com.example.musiclibrary.datafetchers;

import com.example.musiclibrary.datafetchers.records.SubmittedReservation;
import com.example.musiclibrary.dtos.ReservationDto;
import com.example.musiclibrary.dtos.show.ReservationShow;
import com.netflix.graphql.dgs.InputArgument;
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

@Tag(name = "reservations/graphql", description = "Управление записями бронирований")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Успешная обработка запроса"),
        @ApiResponse(responseCode = "400", description = "Ошибка валидации"),
        @ApiResponse(responseCode = "404", description = "Ресурс не найден"),
        @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
})
public interface ReservationFetcherApi {
    @Operation(summary = "Получить список всех бронирований")
    List<ReservationShow> getAllReservations() throws InterruptedException;

    @Operation(summary = "Создать новое бронирование")
    ReservationDto addReservation(@Valid @InputArgument SubmittedReservation input) throws InterruptedException;

    @Operation(summary = "Получить информацию о бронировании по ID")
    ReservationDto getReservation(@InputArgument String id) throws InterruptedException;

    @Operation(summary = "Редактировать бронирование по ID")
    ReservationDto editReservation(@InputArgument String id, @Valid @InputArgument SubmittedReservation input) throws InterruptedException;

    @Operation(summary = "Удалить бронирование по ID")
    String deleteReservation(@InputArgument String id) throws InterruptedException;
}
