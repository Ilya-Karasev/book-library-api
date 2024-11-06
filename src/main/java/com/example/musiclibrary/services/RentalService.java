package com.example.musiclibrary.services;

import com.example.musiclibrary.dtos.RentalDto;
import com.example.musiclibrary.dtos.show.RentalShow;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RentalService {
    RentalDto addRental(RentalDto rental, String user, String book) throws InterruptedException;
    Optional<RentalShow> findRental(UUID id) throws InterruptedException;
    Optional<RentalDto> findRentalDto(UUID id) throws InterruptedException;
    List<RentalShow> getAllRentals() throws InterruptedException;
    Optional <RentalDto> editRental(UUID id, RentalDto rental) throws InterruptedException;
    void deleteRental(UUID id) throws InterruptedException;
}