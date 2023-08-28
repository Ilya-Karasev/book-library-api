package com.example.musiclibrary.services;
import com.example.musiclibrary.dtos.PlayListDto;
import java.util.List;
import java.util.Optional;
public interface PlayListService<ID> {
    PlayListDto createPlaylist(PlayListDto playlist, String username);
    List<PlayListDto> findPlaylist(String pl_name);
    Optional<PlayListDto> findPlaylistByUser(String username, String pl_name);
    List<PlayListDto> getAllPlaylists();
    List<PlayListDto> getAllPlaylistsByUser(String username);
    Optional<PlayListDto> changePlaylistInfo(String username, String pl_name, PlayListDto playlist);
    void deletePlaylist(String username, String pl_name);
}