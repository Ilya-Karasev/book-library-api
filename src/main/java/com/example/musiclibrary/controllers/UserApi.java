package com.example.musiclibrary.controllers;

import com.example.musiclibrary.dtos.UserDto;
import com.example.musiclibrary.dtos.show.UserShow;
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

@Tag(name = "users", description = "Управление пользователями")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Успешная обработка запроса"),
        @ApiResponse(responseCode = "400", description = "Ошибка валидации"),
        @ApiResponse(responseCode = "404", description = "Ресурс не найден"),
        @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
})
public interface UserApi {

    @Operation(summary = "Получить список всех пользователей")
    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<UserShow>> all() throws InterruptedException;

    @Operation(summary = "Создать нового пользователя")
    @PostMapping(value = "/users/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserShow> newUser(@Valid @RequestBody UserDto newUser) throws InterruptedException;

    @Operation(summary = "Получить информацию о пользователе по имени")
    @GetMapping(value = "/users/info/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserShow> findUser(@PathVariable("name") String name) throws InterruptedException;

    @Operation(summary = "Редактировать пользователя по имени")
    @PutMapping(value = "/users/edit/{name}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserShow> editUser(@PathVariable("name") String name, @Valid @RequestBody UserDto user) throws InterruptedException;

    @Operation(summary = "Удалить пользователя")
    @DeleteMapping(value = "/users/delete/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    Link deleteUser(@PathVariable("name") String name) throws InterruptedException;
}
