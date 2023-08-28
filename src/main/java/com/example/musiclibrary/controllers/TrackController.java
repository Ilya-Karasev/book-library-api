package com.example.musiclibrary.controllers;
import com.example.musiclibrary.dtos.TrackDto;
import com.example.musiclibrary.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
public class TrackController {
    @Autowired
    private TrackService trackService;
    public TrackController(TrackService trackService) {
        this.trackService = trackService;
    }
    @GetMapping("/tracks")
    List<? extends TrackDto> all() {
        return trackService.getAllTracks();
    }
    @GetMapping("/tracks/{trackname}")
    List<? extends TrackDto> allByTrackname(@PathVariable String trackname) {
        return trackService.findTrackByTrackname(trackname);
    }
    @GetMapping("/tracks/artist/{artist}")
    List<? extends TrackDto> allByArtist(@PathVariable String artist) {
        return trackService.findTrackByArtist(artist);
    }
    @GetMapping("/tracks/album/{album}")
    List<? extends TrackDto> allByAlbum(@PathVariable String album) {
        return trackService.findTrackByAlbum(album);
    }
    @GetMapping("/tracks/genre/{genre}")
    List<? extends TrackDto> allByGenre(@PathVariable String genre) {
        return trackService.findTrackByGenre(genre);
    }
    @PostMapping("/tracks")
    TrackDto newTrack(@RequestBody TrackDto newTrack) {  return trackService.addTrack(newTrack); }
    @GetMapping("/tracks/{trackname}/{artist}")
    TrackDto one(@PathVariable String trackname, @PathVariable String artist) throws Throwable {
        trackService.findTrack(trackname, artist);
        return (TrackDto) trackService.findTrack(trackname, artist).orElseThrow();
    }
    @PutMapping("/tracks/{trackname}/{artist}")
    TrackDto two(@PathVariable String trackname, @PathVariable String artist, @RequestBody TrackDto track) throws Throwable {
        trackService.changeTrackInfo(trackname, artist, track);
        return (TrackDto) trackService.findTrack(track.getTrackName(), track.getArtist())
                .orElseThrow(() -> new NotFoundException(track.getTrackName()));
    }
    @DeleteMapping("/tracks/{trackname}/{artist}")
    void deleteTrack(@PathVariable String trackname, @PathVariable String artist) {
        trackService.deleteTrack(trackname, artist);
    }
}