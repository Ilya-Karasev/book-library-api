package com.example.musiclibrary.controllers;
import com.example.musiclibrary.dtos.PlayListDto;
import com.example.musiclibrary.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
public class PlayListController {
    @Autowired
    private PlayListService playListService;
    public PlayListController(PlayListService playListService) {
        this.playListService = playListService;
    }
    @GetMapping("/playlists")
    List<? extends PlayListDto> all() {
        return playListService.getAllPlaylists();
    }
    @GetMapping("/myPlaylists/{username}")
    List<? extends PlayListDto> allByUser(@PathVariable String username) {
        return playListService.getAllPlaylistsByUser(username);
    }
    @PostMapping("/playlists/{username}")
    PlayListDto newPlaylist(@RequestBody PlayListDto playList, @PathVariable String username) {  return playListService.createPlaylist(playList, username); }
    @GetMapping("/playlists/{pl_name}")
    List<? extends PlayListDto> findByPlaylistname(@PathVariable String pl_name) {
        return playListService.findPlaylist(pl_name);
    }
    @GetMapping("/myPlaylists/{username}/{pl_name}")
    PlayListDto findByPlaylistnameAndUser(@PathVariable String username, @PathVariable String pl_name) throws Throwable {
        return (PlayListDto) playListService.findPlaylistByUser(username, pl_name)
                .orElseThrow(() -> new NotFoundException(pl_name));
    }
    @PutMapping("/playlists/{username}/{pl_name}")
    PlayListDto one(@PathVariable String username, @PathVariable String pl_name, @RequestBody PlayListDto playlist) throws Throwable {
        playListService.changePlaylistInfo(username, pl_name, playlist);
        return (PlayListDto) playListService.findPlaylistByUser(username, playlist.getPlayListName())
                .orElseThrow(() -> new NotFoundException(playlist.getPlayListName()));
    }
    @DeleteMapping("/playlists/{username}/{pl_name}")
    void deleteTrack(@PathVariable String username, @PathVariable String pl_name) {
        playListService.deletePlaylist(username, pl_name);
    }
}