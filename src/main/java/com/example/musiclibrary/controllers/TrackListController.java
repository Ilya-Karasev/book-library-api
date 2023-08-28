package com.example.musiclibrary.controllers;
import com.example.musiclibrary.dtos.TrackDto;
import com.example.musiclibrary.dtos.TrackListDto;
import com.example.musiclibrary.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
public class TrackListController {
    @Autowired
    private TrackListService trackListService;
    public TrackListController(TrackListService trackListService) {
        this.trackListService = trackListService;
    }
    @GetMapping("/playlists/{username}/{pl_name}/tracks")
    List<? extends TrackDto> allTracksInPlaylist(@PathVariable String username, @PathVariable String pl_name) {
        return trackListService.getAllTracksFromPlaylist(username, pl_name);
    }
    @PostMapping("/playlists/{username}/{pl_name}/addTrack/{trackname}/{artist}")
    TrackListDto newTrackInPlaylist(@PathVariable String username, @PathVariable String pl_name, @PathVariable String trackname, @PathVariable String artist) {  return trackListService.addTrackToPlaylist(username, pl_name, trackname, artist); }
    @GetMapping("/playlists/{username}/{pl_name}/tracks/{trackname}/{artist}")
    TrackListDto findTrackInPlaylist(@PathVariable String username, @PathVariable String pl_name, @PathVariable String trackname, @PathVariable String artist) throws Throwable {
        return (TrackListDto) trackListService.findTrackInPlaylist(username, pl_name, trackname, artist).orElseThrow(() -> new NotFoundException(trackname));
    }
    @GetMapping("/myPlaylists/{username}/{pl_name}/tracks")
    List<? extends TrackListDto> allTracksInPlaylistByUser(@PathVariable String username, @PathVariable String pl_name) {
        return trackListService.getAllTracksFromPlaylistByUser(username, pl_name);
    }
    @GetMapping("/myPlaylists/{username}/{pl_name}/tracks/{trackname}/{artist}")
    TrackListDto findTrackInPlaylistByUser(@PathVariable String username, @PathVariable String pl_name, @PathVariable String trackname, @PathVariable String artist) throws Throwable {
        return (TrackListDto) trackListService.findTrackInPlaylistByUser(username, pl_name, trackname, artist).orElseThrow(() -> new NotFoundException(trackname));
    }
    @GetMapping("/mySubs/{username}/{pl_name}/tracks")
    List<? extends TrackListDto> allTracksInSubByUser(@PathVariable String username, @PathVariable String pl_name) {
        return trackListService.getAllTracksFromSubByUser(username, pl_name);
    }
    @GetMapping("/mySubs/{username}/{pl_name}/tracks/{trackname}/{artist}")
    TrackListDto findTrackInSubByUser(@PathVariable String username, @PathVariable String pl_name, @PathVariable String trackname, @PathVariable String artist) throws Throwable {
        return (TrackListDto) trackListService.findTrackInSubByUser(username, pl_name, trackname, artist).orElseThrow(() -> new NotFoundException(trackname));
    }
    @DeleteMapping("/playlists/{username}/{pl_name}/deleteTrack/{trackname}/{artist}")
    void deleteTrack(@PathVariable String username, @PathVariable String pl_name, @PathVariable String trackname, @PathVariable String artist) {
        trackListService.deleteTrackFromPlaylist(username, pl_name, trackname, artist);
    }
}