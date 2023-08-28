package com.example.musiclibrary.repositories;
import com.example.musiclibrary.models.PlayList;
import com.example.musiclibrary.models.Subscription;
import com.example.musiclibrary.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
    boolean existsByUserIdAndPlaylistId(Integer user_id, Integer pl_id);
    Optional<Subscription> findByUserUsernameAndPlaylistPlaylistname(String username, String plName);
    @Query("select s1 from Subscription s1 join s1.user u1 join s1.playlist p1 where s1.user in (select u2 from User u2 where u2.username=:username)")
    List<Subscription> findByUserUsername(@Param(value = "username") String username);
    Optional<Subscription> findByUserAndPlaylist(User u, PlayList p);
}