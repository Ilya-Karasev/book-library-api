package com.example.musiclibrary.services;
import com.example.musiclibrary.dtos.TrackDto;
import java.util.List;
import java.util.Optional;
public interface TrackService<ID> {
    TrackDto addTrack(TrackDto track);
    Optional<TrackDto> findTrack(String trackname, String artist);
    List<TrackDto> findTrackByTrackname(String trackname);
    List<TrackDto> findTrackByArtist(String artist);
    List<TrackDto> findTrackByAlbum(String album);
    List<TrackDto> findTrackByGenre(String genre);
    List<TrackDto> getAllTracks();
    Optional<TrackDto> changeTrackInfo(String trackname, String artist, TrackDto track);
    void deleteTrack(String trackname, String artist);
}