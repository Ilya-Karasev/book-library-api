package com.example.musiclibrary.repositories;

import com.example.musiclibrary.models.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RentalRepository extends JpaRepository<Rental, UUID> {
    @Query(value = "select r from Rental r join r.book b where b.title = :title")
    List<Rental> findByBook(@Param(value = "title") String title);
    @Query(value = "select r from Rental r join r.user u where u.name = :name")
    List<Rental> findByUser(@Param(value = "name") String name);
    Optional<Rental> findById(UUID uuid);
}