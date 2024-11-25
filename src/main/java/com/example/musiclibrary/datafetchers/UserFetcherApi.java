package com.example.musiclibrary.datafetchers;

import com.example.musiclibrary.datafetchers.records.SubmittedUser;
import com.example.musiclibrary.dtos.UserDto;
import com.example.musiclibrary.dtos.show.ReservationShow;
import com.example.musiclibrary.dtos.show.UserShow;
import com.netflix.graphql.dgs.InputArgument;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.List;

@Tag(name = "users/graphql", description = "Управление пользователей через GraphQL")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Успешная обработка запроса"),
        @ApiResponse(responseCode = "400", description = "Ошибка валидации"),
        @ApiResponse(responseCode = "404", description = "Ресурс не найден"),
        @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
})
public interface UserFetcherApi {
    @Operation(summary = "Получить список всех пользователей")
    List<UserShow> getAllUsers() throws InterruptedException;

    @Operation(summary = "Создать нового пользователя")
    UserDto register(@Valid @InputArgument SubmittedUser input) throws InterruptedException;

    @Operation(summary = "Получить информацию о пользователе по имени")
    UserDto getUser(@InputArgument String name) throws InterruptedException;

    @Operation(summary = "Редактировать пользователя по имени")
    UserDto editUser(@InputArgument String name, @Valid @InputArgument SubmittedUser input) throws InterruptedException;

    @Operation(summary = "Удалить пользователя")
    String deleteUser(@InputArgument String name) throws InterruptedException;
}
