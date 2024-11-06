package com.example.musiclibrary.repositories;

import com.example.musiclibrary.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
    boolean existsByTitle(String title);
    Optional<Book> findByTitle(String title);
    @Query(value = "select b from Book b join b.user u where u.name = :name")
    List<Book> findByUser(@Param(value = "name") String name);
    Optional<Book> findById(UUID uuid);
}
