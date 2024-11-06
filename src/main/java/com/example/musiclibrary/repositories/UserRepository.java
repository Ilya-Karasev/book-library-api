package com.example.musiclibrary.repositories;
import com.example.musiclibrary.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByName(String name);
    Optional<User> findByName(String name);
    Optional<User> findById(UUID uuid);
}