package com.example.musiclibrary.services;
import com.example.musiclibrary.dtos.TrackListDto;
import java.util.List;
import java.util.Optional;
public interface TrackListService<ID> {
    TrackListDto addTrackToPlaylist(String username, String pl_name, String trackname, String artist);
    Optional<TrackListDto> findTrackInPlaylist(String username, String pl_name, String trackname, String artist);
    List<TrackListDto> getAllTracksFromPlaylist(String username, String pl_name);
    Optional<TrackListDto> findTrackInPlaylistByUser(String username, String pl_name, String trackname, String artist);
    List<TrackListDto> getAllTracksFromPlaylistByUser(String username, String pl_name);
    Optional<TrackListDto> findTrackInSubByUser(String username, String pl_name, String trackname, String artist);
    List<TrackListDto> getAllTracksFromSubByUser(String username, String pl_name);
    void deleteTrackFromPlaylist(String username, String pl_name, String trackname, String artist);
}