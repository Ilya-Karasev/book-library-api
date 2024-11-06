package com.example.musiclibrary.repositories;

import com.example.musiclibrary.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
    @Query(value = "select r from Reservation r join r.book b where b.title = :title")
    List<Reservation> findByBook(@Param(value = "title") String title);
    @Query(value = "select r from Reservation r join r.user u where u.name = :name")
    List<Reservation> findByUser(@Param(value = "name") String name);
    Optional<Reservation> findById(UUID uuid);
}