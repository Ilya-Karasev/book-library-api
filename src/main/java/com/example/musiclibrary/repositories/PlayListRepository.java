package com.example.musiclibrary.repositories;
import com.example.musiclibrary.models.PlayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
@Repository
public interface PlayListRepository extends JpaRepository<PlayList, Integer> {
    boolean existsByUserUsernameAndPlaylistname(String username, String pl_name);
    List<PlayList> findByPlaylistname(String pl_name);
    @Query(value = "select p1 from PlayList p1 join p1.user u1 where u1.username=:username")
    List<PlayList> findByUserUsername(@Param(value = "username") String username);
    Optional<PlayList> findByUserUsernameAndPlaylistname(String username, String plName);
}