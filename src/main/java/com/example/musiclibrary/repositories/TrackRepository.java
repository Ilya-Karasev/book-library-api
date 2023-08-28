package com.example.musiclibrary.repositories;
import com.example.musiclibrary.models.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
@Repository
public interface TrackRepository extends JpaRepository<Track, Integer> {
    boolean existsByTracknameAndArtist(String trackname, String artist);
    Optional<Track> findByTrackname(String trackname);
    Optional<Track> findByTracknameAndArtist(String trackname, String artist);
    List<Track> findByArtist(String artist);
    List<Track> findByAlbum(String album);
    List<Track> findByGenre(String genre);
}