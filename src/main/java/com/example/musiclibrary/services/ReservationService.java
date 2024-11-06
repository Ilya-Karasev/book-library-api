package com.example.musiclibrary.services;

import com.example.musiclibrary.dtos.ReservationDto;
import com.example.musiclibrary.dtos.show.ReservationShow;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReservationService {
    ReservationDto addReservation(ReservationDto rental, String user, String book) throws InterruptedException;
    Optional<ReservationShow> findReservation(UUID id) throws InterruptedException;
    Optional<ReservationDto> findReservationDto(UUID id) throws InterruptedException;
    List<ReservationShow> getAllReservations() throws InterruptedException;
    Optional <ReservationDto> editReservation(UUID id, ReservationDto rental) throws InterruptedException;
    void deleteReservation(UUID id) throws InterruptedException;
}