package com.example.musiclibrary.datafetchers;

import com.example.musiclibrary.datafetchers.records.SubmittedRental;
import com.example.musiclibrary.dtos.RentalDto;
import com.example.musiclibrary.dtos.show.RentalShow;
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

@Tag(name = "rentals", description = "Управление записями аренды")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Успешная обработка запроса"),
        @ApiResponse(responseCode = "400", description = "Ошибка валидации"),
        @ApiResponse(responseCode = "404", description = "Ресурс не найден"),
        @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
})
public interface RentalFetcherApi {
    @Operation(summary = "Получить список всех аренд")
    List<RentalShow> getAllRentals() throws InterruptedException;

    @Operation(summary = "Создать новую аренду")
    RentalDto addRental(@InputArgument SubmittedRental input) throws InterruptedException;

    @Operation(summary = "Получить информацию об аренде по ID")
    RentalDto getRental(@InputArgument String id) throws InterruptedException;

    @Operation(summary = "Редактировать аренду по ID")
    RentalDto editRental(@InputArgument String id, @Valid @InputArgument SubmittedRental input) throws InterruptedException;

    @Operation(summary = "Удалить аренду по ID")
    String deleteRental(@InputArgument String id) throws InterruptedException;
}
